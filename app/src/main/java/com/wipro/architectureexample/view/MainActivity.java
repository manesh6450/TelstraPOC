package com.wipro.architectureexample.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wipro.architectureexample.R;
import com.wipro.architectureexample.model.NoteList;
import com.wipro.architectureexample.viewmodel.ApiClient;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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
        setupNetworkClient();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                fetchData();
            }
        });
        fetchData();
    }

    private void setupNetworkClient(){
        ApiClient.setupNetworkClient();
        ApiClient.setUIListener(listener);
    }

    private IUpdateListener listener = new IUpdateListener() {
        @Override
        public void onDataReceive(Response<NoteList> response) {
            String toolbarTitle = response.body().getMainTitle();
            getSupportActionBar().setTitle(toolbarTitle);
            adapter = new NoteAdaptor(response.body().getNotes());
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("onFailure", t.getMessage());
        }
    };

    private void fetchData() {
        ApiClient.getData();
    }
}
