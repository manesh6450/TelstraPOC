package com.wipro.architectureexample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.wipro.architectureexample.ApiClient;
import com.wipro.architectureexample.view.IUpdateListener;

public class NoteViewModel extends AndroidViewModel {

    public NoteViewModel(@NonNull Application application ) {
        super(application);
    }

    public void setupNetworkClient(IUpdateListener listener) {
        ApiClient.setupNetworkClient();
        ApiClient.setUIListener(listener);
    }
}
