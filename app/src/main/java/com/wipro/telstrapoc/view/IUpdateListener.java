package com.wipro.telstrapoc.view;

import com.wipro.telstrapoc.model.NoteList;

import retrofit2.Response;

public interface IUpdateListener {
    void onDataReceive(Response<NoteList> response);
    void onFailure(Throwable t);
}
