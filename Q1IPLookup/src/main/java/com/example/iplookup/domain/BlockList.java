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

package com.example.iplookup.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

/*
    This table is used to store all Blocked IP addresses.
 */

@Entity
@Table (name ="blocked_ips")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BlockList implements Serializable {
    @Id
    @Column (length = 15)
    private String ip ="";

    private String country;

    public BlockList(String ip, String country) {
        this.ip = ip;
        this.country = country;
    }
}

