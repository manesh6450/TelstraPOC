package com.wipro.architectureexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteList {

    @SerializedName("rows")
    private List<Note> notes;

    @SerializedName("title")
    private String mainTitle;

    public NoteList(List<Note> notes, String mainTitle) {
        this.notes = notes;
        this.mainTitle = mainTitle;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public String getMainTitle() {
        return mainTitle;
    }
}
