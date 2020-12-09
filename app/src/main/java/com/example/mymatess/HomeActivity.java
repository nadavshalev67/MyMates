package com.example.mymatess;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerViewDates mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initRecyclerView();
    }


    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_dates);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new RecyclerViewDates(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }
}