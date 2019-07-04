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

package com.stephenelf.marvelwithcontacts.net;

import com.stephenelf.marvelwithcontacts.net.response.CharacterResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelAPI {

    int LIMIT = 50;

    @GET("characters")
    Single<CharacterResponse> getCharacters(@Query("apikey") String apiKey, @Query("ts") long timestamp, @Query("hash") String hash,
                                            @Query("limit") int limit, @Query("offset") int offset);
}
