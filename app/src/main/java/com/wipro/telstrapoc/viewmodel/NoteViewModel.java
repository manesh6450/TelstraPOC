package com.wipro.telstrapoc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.wipro.telstrapoc.network.ApiClient;
import com.wipro.telstrapoc.network.ApiInterface;
import com.wipro.telstrapoc.R;
import com.wipro.telstrapoc.model.NoteList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteViewModel extends AndroidViewModel {

    public Context mContext;
    public MutableLiveData<String> showSnackBarMessage;
    public MutableLiveData<Response<NoteList>> responseMutableLiveData;
    private ApiInterface apiInterface;

    public NoteViewModel(@NonNull Application application ) {
        super(application);
        mContext = application.getApplicationContext();
        showSnackBarMessage = new MutableLiveData<>();
        responseMutableLiveData = new MutableLiveData<>();
    }

    public void setupNetworkClient() {
        setupNetworkClient(getApplication().getApplicationContext());
    }

    public void fetchData() {
        getData();
        if (!isNetworkConnected()) {
            showSnackBarMessage.setValue(mContext.getString(R.string.interrupted_connection));
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void setupNetworkClient(Context mContext) {
        apiInterface = ApiClient.getRetrofit(mContext).create(ApiInterface.class);
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return showSnackBarMessage;
    }

    public MutableLiveData<Response<NoteList>> getDataResponse() {
        return responseMutableLiveData;
    }

    public void getData() {
        Call<NoteList> call = apiInterface.getData();
        call.enqueue(new Callback<NoteList>() {
            @Override
            public void onResponse(Call<NoteList> call, Response<NoteList> response) {
                responseMutableLiveData.setValue(response);
            }

            @Override
            public void onFailure(Call<NoteList> call, Throwable t) {
                showSnackBarMessage.setValue(mContext.getString(R.string.failed_response));
            }
        });
    }
}
