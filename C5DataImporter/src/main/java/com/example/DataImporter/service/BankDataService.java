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

package com.example.DataImporter.service;

import com.example.DataImporter.domain.Bank;
import com.example.DataImporter.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BankDataService {

    @Autowired
    BankRepository bankRepository;

    /*
        Persists bank object in database and place it into Cache at the same time for next fast retrieval
     */
    @Cacheable (value = "BanksInfo", key = "#bank.bankId", unless = "#result == null")
    public Bank saveBankInfo (Bank bank) {
        return bankRepository.save(bank);
    }

    /*
           Performs bulk insert into database using Hibernate Batch
     */
    public void saveAll (List<Bank> banks) {
        bankRepository.saveAll(banks);
    }

    /*
        Retrieves bank object with specified id and place it into Cache for next fast retrieval
     */

    @Cacheable (value = "BanksInfo", key = "#id", unless = "#result == null")
    public Optional<Bank> getBank (long id) {
        return bankRepository.findById(id);
    }


}
