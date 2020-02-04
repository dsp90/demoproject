package com.example.topicsdisplayapp.views;

import android.os.Bundle;
import android.widget.Toast;

import com.example.topicsdisplayapp.R;
import com.example.topicsdisplayapp.adapter.HitsAdapter;
import com.example.topicsdisplayapp.viewmodels.StoriesViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private SwipeRefreshLayout swipeRefreshList;
    private HitsAdapter adapter;
    private StoriesViewModel storiesViewModel;
    private Toolbar toolbar;
    private long count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storiesViewModel = ViewModelProviders.of(MainActivity.this)
                .get(StoriesViewModel.class);
        initViews();
        initAdapter();
        subscribeObservers();
        loadData();
    }

    private void subscribeObservers() {
        storiesViewModel.getHits().observe(MainActivity.this, listResource -> {
            if (listResource != null){
                switch (listResource.status){
                    case LOADING:
                        break;
                    case ERROR:
                        adapter.setHits(listResource.data);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                        break;
                    case SUCCESS:
                        adapter.setHits(listResource.data);
                        break;
                }
            }
        });
    }

    void loadData(){
        rvList.smoothScrollToPosition(0);
        storiesViewModel.getStoryHits(1);
//        count = storiesViewModel.getSelectedStories(true);
        updateCount(count);
    }

    private void updateCount(long count) {
        toolbar.setTitle( count == 0 ? getString(R.string.app_name) : count + " Selected");
    }

    private void initAdapter() {
        adapter = new HitsAdapter(hit -> {
            storiesViewModel.updateModel(hit);
            updateCount(count++);
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!rvList.canScrollVertically(1)){
                    storiesViewModel.getNextPage();
                }
            }
        });
        rvList.setAdapter(adapter);
    }

    private void initViews() {
        rvList = findViewById(R.id.list_post);
        swipeRefreshList = findViewById(R.id.swipe_ref_lyt);
        toolbar = findViewById(R.id.my_toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
