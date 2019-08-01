package com.wipro.architectureexample.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.wipro.architectureexample.R;
import com.wipro.architectureexample.model.NoteList;
import com.wipro.architectureexample.viewmodel.ApiClient;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NoteAdaptor adapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        swipeRefreshLayout = findViewById(R.id.simpleSwipeRefreshLayout);
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

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    private void setupNetworkClient() {
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
        if(isNetworkConnected()) {
            ApiClient.getData();
        }
        else{
            showSnackBar();
        }
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Interrupted Connection", Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchData();
                    }
                });
        snackbar.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
