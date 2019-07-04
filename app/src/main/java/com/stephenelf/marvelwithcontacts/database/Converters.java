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

import androidx.room.TypeConverter;

import com.stephenelf.marvelwithcontacts.net.response.ThumbnailResponse;

public class Converters {

    @TypeConverter
    public static ThumbnailResponse stringToObject(String string) {
        String[] parts=string.split(",");
        return new ThumbnailResponse(parts[0],parts[1]);
    }

    @TypeConverter
    public static String objectToString(ThumbnailResponse object) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(object.path).append(",").append(object.extension);
        return buffer.toString();
    }
}
