package com.wipro.architectureexample.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wipro.architectureexample.viewmodel.ApiClient;
import com.wipro.architectureexample.viewmodel.ApiInterface;
import com.wipro.architectureexample.R;
import com.wipro.architectureexample.model.NoteList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private NoteAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<NoteList> call = apiInterface.getData();

        call.enqueue(new Callback<NoteList>() {
            @Override
            public void onResponse(Call<NoteList> call, Response<NoteList> response) {
                String MainTitle = response.body().getMainTitle();
                getSupportActionBar().setTitle(MainTitle);
                adapter = new NoteAdaptor(response.body().getNotes());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NoteList> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }
}
