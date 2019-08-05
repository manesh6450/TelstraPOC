package com.wipro.telstrapoc.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.wipro.telstrapoc.ApiClient;
import com.wipro.telstrapoc.R;
import com.wipro.telstrapoc.model.NoteList;
import com.wipro.telstrapoc.viewmodel.NoteViewModel;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NoteAdaptor adapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        swipeRefreshLayout = findViewById(R.id.simpleSwipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        noteViewModel.setupNetworkClient(listener);
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
        if (!isNetworkConnected()) {
            showSnackBar();
        }
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, this.getString(R.string.interrupted_connection), Snackbar.LENGTH_LONG)
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
