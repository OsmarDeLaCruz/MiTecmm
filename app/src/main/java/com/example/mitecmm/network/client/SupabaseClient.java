package com.example.mitecmm.network.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupabaseClient {
    private static final String BD_URL = "Aqui va la url bd";
    private static final String API_KEY = "Aqui va la apikey";
    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request request = chain.request().newBuilder().addHeader("apikey", API_KEY).addHeader(
                        "Authorization", "Bearer " + API_KEY).addHeader("Content-Type", "application/json").build();

                return chain.proceed(request);

            }).build();

            retrofit = new Retrofit.Builder().baseUrl(BD_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

}
