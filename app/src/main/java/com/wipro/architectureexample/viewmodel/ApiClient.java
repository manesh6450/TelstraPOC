package com.wipro.architectureexample.viewmodel;

import com.wipro.architectureexample.model.NoteList;
import com.wipro.architectureexample.view.IUpdateListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL= "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    public static Retrofit retrofit = null;
    private static ApiInterface apiInterface;
    private static IUpdateListener uiListener;


    private ApiClient() {
    }

    public static Retrofit getRetrofit(){
        if(null == retrofit){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void setupNetworkClient() {
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    }

    public static void setUIListener(IUpdateListener listener) {
        uiListener = listener;
    }

    public static void getData() {
        Call<NoteList> call = apiInterface.getData();
        call.enqueue(new Callback<NoteList>() {
            @Override
            public void onResponse(Call<NoteList> call, Response<NoteList> response) {
                uiListener.onDataReceive(response);
            }

            @Override
            public void onFailure(Call<NoteList> call, Throwable t) {
                uiListener.onFailure(t);
            }
        });
    }
}
