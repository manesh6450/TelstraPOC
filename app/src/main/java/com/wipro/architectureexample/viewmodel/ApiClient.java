package com.wipro.architectureexample.viewmodel;

import com.wipro.architectureexample.model.NoteList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL= "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if(null == retrofit){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
