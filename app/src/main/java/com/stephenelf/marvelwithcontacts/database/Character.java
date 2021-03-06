/*
 * Copyright 2019 stephenelf@gmail.com(EB). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stephenelf.marvelwithcontacts.database;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.stephenelf.marvelwithcontacts.net.response.ThumbnailResponse;

@Entity
public class Character {

    @PrimaryKey
    public long id;
    public String name;


    public ThumbnailResponse thumbnail;


    @Override
    public boolean equals(@Nullable Object obj) {
        return ((Character)obj).name.toLowerCase().equalsIgnoreCase(name.toLowerCase());
    }
}
