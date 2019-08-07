package com.example.parentportal.Utils;

import android.content.Context;

import com.example.parentportal.RetrofitCalls;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestController {

    private final static String BASE_URL = "http://10.0.2.2:3000/api/";
    private static RetrofitCalls retrofitCalls;
    private RequestController instance;
    private static Retrofit retrofit;
    private Context context;

    private RequestController(){}

    private static Retrofit createRetrofit() {

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;

    }

    public static Retrofit getRetrofit() {

        if(retrofit == null) {
            return createRetrofit();
        }

        return retrofit;

    }
//    public RequestController(Context context) {
//        this.context = context;
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        retrofitCalls = retrofit.create(RetrofitCalls.class);
//
//    }
//
//    public RequestController getInstance(Context context) {
//
//        if(instance == null) {
//            instance = new RequestController(context);
//        }
//
//        return instance;
//
//    }
//
//
}
//