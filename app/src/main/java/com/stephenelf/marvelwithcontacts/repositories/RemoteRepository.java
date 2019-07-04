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

package com.stephenelf.marvelwithcontacts.repositories;

import android.content.Context;

import com.stephenelf.marvelwithcontacts.simpleinterviewtestapp.R;
import com.stephenelf.marvelwithcontacts.net.MarvelAPI;
import com.stephenelf.marvelwithcontacts.net.response.CharacterResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Single;

public class RemoteRepository {

    private MarvelAPI marvelAPI;

    private Context context;

    public RemoteRepository(Context context,MarvelAPI marvelAPI) {
        this.context=context;
        this.marvelAPI = marvelAPI;
    }

    public Single<CharacterResponse> getCharacters() {
        long now=System.currentTimeMillis();
        String md5=calculateHash(String.valueOf(now),context.getString(R.string.marvel_private_key),
                context.getString(R.string.marvel_public_key));

       return marvelAPI.getCharacters(context.getString(R.string.marvel_public_key),now, md5,
               MarvelAPI.LIMIT,0);
    }

    private String calculateHash(String timeStamp, String privateKey, String publicKey) {
       StringBuffer buffer=new StringBuffer();
       buffer.append(timeStamp).append(privateKey).append(publicKey);
        byte[] digest = generateDigest(buffer.toString());
        return toHexString(digest);
    }

    private byte[] generateDigest(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());

            return md.digest();
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return null;
    }

    private String toHexString(byte[] digest) {
        if (digest == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (byte digestByte : digest) {
            stringBuilder.append(Integer.toString((digestByte & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
}
