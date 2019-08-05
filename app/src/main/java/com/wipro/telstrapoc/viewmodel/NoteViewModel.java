package com.wipro.telstrapoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.wipro.telstrapoc.ApiClient;
import com.wipro.telstrapoc.view.IUpdateListener;

public class NoteViewModel extends AndroidViewModel {

    public NoteViewModel(@NonNull Application application ) {
        super(application);
    }

    public void setupNetworkClient(IUpdateListener listener) {
        ApiClient.setupNetworkClient(getApplication().getApplicationContext());
        ApiClient.setUIListener(listener);
    }
}
