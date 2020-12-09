package com.example.mymatess;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RecylerViewHobbies mAdapter;
    private RecyclerView mRecyclerView;
    private Spinner spinner;
    private Button mContinueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        mContinueButton = findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(this);
        initSpinner();

        initRecyclerView();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button: {

                break;
            }

        }
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        final ArrayList<String> teams = new ArrayList();
        DatabaseReference ref = database.getReference("groupTeams");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String team = snapshot.getKey();
                    teams.add(team);
                }
                spinner.setAdapter(new ArrayAdapter<String>(ProfileActivity.this, R.layout.spinner_layout, R.id.textview, teams));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner.setSelection(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        final ArrayList<Hobbie> hobbies = new ArrayList();
        mRecyclerView = findViewById(R.id.recycler_view_hobbies);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecylerViewHobbies(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        DatabaseReference ref = database.getReference("hobbies");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hobbies.add(new Hobbie(snapshot.getKey()));
                }
                mAdapter.setNewList(hobbies);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}