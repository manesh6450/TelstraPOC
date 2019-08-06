package com.wipro.telstrapoc.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
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
        noteViewModel.setupNetworkClient();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                noteViewModel.fetchData();
            }
        });
        noteViewModel.fetchData();
        noteViewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSnackBar(s);
            }
        });
        noteViewModel.getDataResponse().observe(this, new Observer<Response<NoteList>>() {
            @Override
            public void onChanged(Response<NoteList> noteListResponse) {
                String toolbarTitle = noteListResponse.body().getMainTitle();
                getSupportActionBar().setTitle(toolbarTitle);
                adapter = new NoteAdaptor(noteListResponse.body().getNotes());
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteViewModel.fetchData();
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        noteViewModel.fetchData();
                    }
                });
        snackbar.show();
    }

}
