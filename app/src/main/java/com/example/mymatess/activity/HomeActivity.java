package com.example.mymatess.activity;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymatess.Date;
import com.example.mymatess.R;
import com.example.mymatess.interfaces.DateChangeListener;
import com.example.mymatess.recylers.RecyclerViewDates;


public class HomeActivity extends AppCompatActivity implements DateChangeListener {
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
        mAdapter = new RecyclerViewDates(this, mRecyclerView, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDateChangeListener(Date date) {

    }
}