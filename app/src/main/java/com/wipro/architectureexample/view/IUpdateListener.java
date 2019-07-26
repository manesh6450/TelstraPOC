package com.wipro.architectureexample.view;

import com.wipro.architectureexample.model.NoteList;

import retrofit2.Response;

public interface IUpdateListener {
    void onDataReceive(Response<NoteList> response);
    void onFailure(Throwable t);
}
