package com.wipro.architectureexample;

import com.wipro.architectureexample.model.NoteList;
import com.wipro.architectureexample.view.IUpdateListener;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("facts.json")
    Call<NoteList> getData();
}
