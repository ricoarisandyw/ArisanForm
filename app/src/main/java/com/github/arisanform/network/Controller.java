package com.github.arisanform.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    private Retrofit retrofit = null;

    public Retrofit getInstance(){
        if (retrofit == null) {
            String BASE_URL = "http://192.168.43.28:5000/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
