package com.wipro.telstrapoc;

import android.content.Context;

import com.wipro.telstrapoc.model.NoteList;
import com.wipro.telstrapoc.view.IUpdateListener;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    private static Retrofit retrofit = null;
    private static ApiInterface apiInterface;
    private static IUpdateListener uiListener;


    private ApiClient() {
    }

    public static Retrofit getRetrofit(Context mContext) {

        if (null == retrofit) {

            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(mContext.getCacheDir(), cacheSize);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void setupNetworkClient(Context mContext) {
        apiInterface = ApiClient.getRetrofit(mContext).create(ApiInterface.class);
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
