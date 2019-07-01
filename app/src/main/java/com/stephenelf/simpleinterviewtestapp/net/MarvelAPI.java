package com.stephenelf.simpleinterviewtestapp.net;

import com.stephenelf.simpleinterviewtestapp.net.response.CharacterResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelAPI {

    /*
     a md5 digest of the ts parameter, your private key and your public
key (e.g. md5(ts+privateKey+publicKey))
     */

    @GET("chareacters")
    Single<CharacterResponse> getCharacters(@Query("apikey") String apiKey, @Query("ts") long timestamp, @Query("hash") String hash);
}
