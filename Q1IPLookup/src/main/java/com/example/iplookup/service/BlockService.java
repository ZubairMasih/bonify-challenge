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

package com.example.iplookup.service;

import com.example.iplookup.domain.BlockList;
import com.example.iplookup.repository.BlockListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    This service uses Caching to cache all IPs which are added to BlockList table and use that Cache information for
    faster IP lookups
 */

@Service
public class BlockService {

    @Autowired
    private BlockListRepo blockListRepo;

    public BlockService(BlockListRepo blockListRepo) {
        this.blockListRepo = blockListRepo;
    }

    /*
        This method check if coming IP is exists within the BlockList table by first checking the Cache
        and then going through database lookup if Cache does not have any entry with this IP address.
     */

    @Cacheable (value = "ipCache", key = "#ip")
    public Boolean checkIPBlocked(String ip) {
        return blockListRepo.findById(ip).isPresent();
    }

    /*
        This method add particular IP to Block table and caches it in memory for later lookup
     */

    @Cacheable (value = "ipCache", key = "#ip", unless="#result == null")
    public Boolean add2BlockList(String ip , String country) {
        Optional<BlockList> check = blockListRepo.findById(ip);
        if (check.isPresent())
            return true;

        BlockList blocked = new BlockList(ip, country);
        blockListRepo.save(blocked);
        return true;
    }

    public Long count () {
        return blockListRepo.count();
    }

    /*
        This method flush all data from Cache
     */
    @CacheEvict (value = "ipCache",allEntries = true)
    public void evictAll (){

    }
}
