/**
 * Copyright 2019 Esfandiyar Talebi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.DataImporter;

import com.example.DataImporter.domain.Bank;
import com.example.DataImporter.service.BankDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableCaching
public class DataImporterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataImporterApplication.class, args);
    }

}

/*
    This is a functional interface used ss Producer while streaming the input data from input file
 */
interface IOperation {
    Bank aggregateAndSaveBankInfo (Bank bankInfo);
}

@Component
@Slf4j
class AppRunner implements CommandLineRunner, IOperation {

    public static final int startRange = 1000000;
    public static final int endRange = 1001000;

    @Value(value = "${data.file}")
    private String DATA_FILE = "sample.csv";

    @Value(value = "${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private Integer batchSize = 10;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    BankDataService bankDataService;

    public List<Bank> bankData = new ArrayList<>();

    public Integer pollCounter = 0;

    @Override
    public void run(String... args) throws Exception {

        // Generate some sample data and writes them into sample.csv file.
        writeSampleData ();

        // Read the sample.csv file and persist data in database.
        readAndProcessDataUsingNIO();

        // Since the persistence operations are done in Asynchronous manner, we put some delay before looking up the data
        Thread.sleep(3000);

        lookupBank(startRange+ 1);
        lookupBank(startRange+ 2);

        // This will return from Cache
        lookupBank(startRange+ 2);

        // This will return from Cache
        lookupBank(startRange+ 3);

        // This will return from Cache
        lookupBank(startRange+ 1);
    }


    public void writeSampleData() throws IOException {
        Path path = Paths.get(DATA_FILE);

        if (Files.exists(path)) {
            //Use try-with-resource to get auto-closeable writer instance
            try (BufferedWriter writer = Files.newBufferedWriter(path))
            {
                writer.write("bank_id,bank_name");
                writer.newLine();

                // Write 10000 sample records to our sample csv file
                IntStream.range(startRange, endRange)
                        .forEach(i-> {
                            try {
                                writer.write(i +", Bank" + i);
                                writer.newLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } else {
            log.error("File not found!");
        }
    }

    /*
        This approach works very well in an efficient way when we are dealing with large data files
     */
    public void readAndProcessDataUsingNIO() throws Exception {
        Path path = Paths.get(DATA_FILE);

        if (Files.exists(path)) {

            try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

                reader.lines()
                        .skip(1)
                        .map(line -> line.trim().split(","))
                        .filter(row -> row.length == 2 && !row[0].isEmpty() && row[0].matches("[0-9]+"))
                        .map(row -> new Bank(Long.valueOf(row[0]), row[1]))
                        .forEach(this::aggregateAndSaveBankInfo);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            log.error("File not found!");
        }
    }

    /*
        This method provides an alternative approach when the data file is not too big.
     */
    public void readDataUsingNIO() throws Exception {
        Path path = Paths.get(DATA_FILE);

        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

                List<Bank> banks = reader.lines()
                        .skip(1)
                        .map(line -> line.trim().split(","))
                        .filter(row -> row.length == 2 && !row[0].isEmpty() && row[0].matches("[0-9]+"))
                        .map(row -> new Bank(Long.valueOf(row[0]), row[1]))
                        .collect(Collectors.toList());

                bankDataService.saveAll(banks);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            log.error("File not found!");
        }
    }

    /*
        this method persists each instances of bank information and put that object in Cache at the same time

        It can be used only if we need to keep objects in cache for future lookups event on first lookup
     */
    public void saveBankInfo (Bank bankInfo) {
        bankDataService.saveBankInfo(bankInfo);
    }

    /*
        This method aggregate input bank objects into a list with the size of Hibernate pool size

        and persist the list at once in one batch operation saving persisting time. However, these objects are

        not placed in Cache.
     */
    public Bank aggregateAndSaveBankInfo (Bank bank) {
       pollCounter ++;

        if (pollCounter == batchSize ){
            eventPublisher.publishEvent(new BufferFlushEvent (this, new ArrayList<>(bankData)));
            bankData.clear();
            pollCounter = 0;
        }
        bankData.add(bank);

        return bank;
    }

    @EventListener
    @Async
    public void handleFlushEvent (BufferFlushEvent event) {
        log.info("persisting current chunk of bank data to database. Thread:" + Thread.currentThread().getName());

        bankDataService.saveAll(event.getBanks());
    }

    public void lookupBank (long id){
        Optional<Bank> found = bankDataService.getBank(id);

        if (found.isPresent()){
            log.info("Bank found with name: " + found.get().getName());
        }
        else{
            log.warn("No such bank found with id:" + id);
        }
    }
}

/*
    This class defines a custom event to be triggered when a chunk of data was read from source
 */
class BufferFlushEvent extends ApplicationEvent {

    private List<Bank> banks;

    public BufferFlushEvent(Object source, List<Bank> banks) {
        super(source);
        this.banks = banks;
    }

    public List<Bank>  getBanks() {
        return banks;
    }
}


