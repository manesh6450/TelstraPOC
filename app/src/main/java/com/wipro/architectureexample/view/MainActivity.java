package com.wipro.architectureexample.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                fetchData();
            }
        });
        fetchData();
    }

    private void fetchData() {
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<NoteList> call = apiInterface.getData();

        call.enqueue(new Callback<NoteList>() {
            @Override
            public void onResponse(Call<NoteList> call, Response<NoteList> response) {
                String toolbarTitle = response.body().getMainTitle();
                getSupportActionBar().setTitle(toolbarTitle);
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
