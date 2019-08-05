package com.wipro.telstrapoc;

import com.wipro.telstrapoc.model.NoteList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("facts.json")
    Call<NoteList> getData();
}
