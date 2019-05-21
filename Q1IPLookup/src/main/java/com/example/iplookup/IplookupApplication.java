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

package com.example.iplookup;

import com.example.iplookup.service.BlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableCaching
public class IplookupApplication {

    public static void main(String[] args) {
        SpringApplication.run(IplookupApplication.class, args);
    }
}

@Component
@Slf4j
class AppRunner implements CommandLineRunner {

    @Autowired
    BlockService blockService;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void run(String... args) {
        log.info("using cache manager: " + cacheManager.getClass().getName());

        // Clearing the Cache at the beginning.
        blockService.evictAll();

        generateSampleData();

        log.info(".... check the incoming ip request.");

        // This lookup will return result from cache because this IP address has already been cached
        lookupSample("10.10.255.5");

        // This lookup will return result from database lookup since it has not been cached before and the result will be
        // stored in Cache for future lookup. The hibernate will show the generate select statement for this invocation
        lookupSample("10.10.10.2");

        // This lookup will return result from cache because this IP address already cached in last invocation
        lookupSample("10.10.10.2");

        // We flush all the cache entries
        blockService.evictAll();

        // This lookup will return result from database lookup since the cache is just flushed and the result will be
        // stored in Cache for future lookup. The hibernate will show the generate select statement for this invocation
        lookupSample("10.10.255.5");

    }

    /*
        Generate some sample IP addresses and adds them to Blocked list table
     */
    private void generateSampleData() {

        if (blockService.count() == 0L) {
            IntStream.range(255, 256).forEach(i -> {

                IntStream.range(1, 256).forEach(j -> {
                    String ip = "10.10." + i + "." + j;
                    blockService.add2BlockList(ip, "XYZ");
                });
            });

            log.info("Total IP blocked:" + blockService.count());
        }
    }

    /*
        Checks if coming IP address already blocked or not.
     */
    private void lookupSample(String ip) {
        log.info("Ip Lookup result for ip "+ ip+" : " + blockService.checkIPBlocked(ip));
    }
}
