package com.example.mymatess.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymatess.Hobbie;
import com.example.mymatess.R;
import com.example.mymatess.recylers.RecylerViewHobbies;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private RecylerViewHobbies mAdapter;
    private RecyclerView mRecyclerView;
    private Spinner spinner, groupTeam;
    private EditText fullName;
    private Button mContinueButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        mContinueButton = findViewById(R.id.continue_button);
        fullName = findViewById(R.id.full_name_et);
        groupTeam = findViewById(R.id.spinner);
        mContinueButton.setOnClickListener(this);
        initSpinner();
        initRecyclerView();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button: {
                String email = firebaseUser.getEmail();
                String fullname = fullName.getText().toString();
                String groupteam = groupTeam.getSelectedItem().toString();
                HashMap<String, String> hashMap = new HashMap<>();
                HashMap<String, String> hashMapHobbies = new HashMap<>();
                int i = 1;
                for (Hobbie temp : mAdapter.getList()) {
                    if (temp.isChecked) {
                        hashMapHobbies.put(String.valueOf(i), temp.name);
                        i++;
                    }
                }
                hashMap.put("fullName", fullname);
                hashMap.put("email", email);
                hashMap.put("groupTeam", groupteam);
                if (fullname.equals("")) {
                    Toast.makeText(ProfileActivity.this, "full name Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                database.getReference("/Profile").child(firebaseUser.getUid()).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference("/Profile").child(firebaseUser.getUid()).child("hobbies").setValue(hashMapHobbies).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });

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