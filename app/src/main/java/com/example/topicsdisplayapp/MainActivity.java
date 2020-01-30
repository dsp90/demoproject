package com.example.topicsdisplayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private SwipeRefreshLayout swipeRefreshList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        StoriesViewModel viewModel = ViewModelProviders.of(this).get(StoriesViewModel.class);
        viewModel.getHits(1);
    }

    private void initViews() {
        rvList = findViewById(R.id.list_post);
        swipeRefreshList = findViewById(R.id.swipe_ref_lyt);
    }
}
