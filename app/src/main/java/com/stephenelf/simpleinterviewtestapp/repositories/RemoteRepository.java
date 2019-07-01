package com.stephenelf.simpleinterviewtestapp.repositories;

import android.content.Context;
import android.util.Log;

import com.stephenelf.simpleinterviewtestapp.R;
import com.stephenelf.simpleinterviewtestapp.net.MarvelAPI;
import com.stephenelf.simpleinterviewtestapp.net.response.CharacterResponse;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

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
        StringBuilder md5 = new StringBuilder();
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0,now);
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(buffer.array());
            digest.update(context.getString(R.string.marvel_public_key).getBytes());
            digest.update(context.getString(R.string.marvel_private_key).getBytes());

            // Create Hex String

            for (byte aMessageDigest : digest.digest()) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                md5.append(h);
            }
        }catch (Exception e){
            Log.e(getClass().getName(),"",e);
        }
       return marvelAPI.getCharacters(context.getString(R.string.marvel_public_key),now, md5.toString());
    }
}
