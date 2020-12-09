package com.example.mymatess;


import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerViewDates mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initRecyclerView();
    }


    @Override
    public void onClick(View view) {

    }

    private void initRecyclerView() {
        final ArrayList<Hobbie> hobbies = new ArrayList();
        mRecyclerView = findViewById(R.id.recycler_view_dates);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new RecyclerViewDates(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

    }
}