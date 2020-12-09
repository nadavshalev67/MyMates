package com.example.mymatess.activity;


import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymatess.R;
import com.example.mymatess.interfaces.DateChangeListener;
import com.example.mymatess.model.DataApplication;
import com.example.mymatess.model.Date;
import com.example.mymatess.model.PeopleDates;
import com.example.mymatess.model.Profile;
import com.example.mymatess.recylers.RecyclerViewDates;
import com.example.mymatess.recylers.RecyclerViewPeople;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HomeActivity extends AppCompatActivity implements DateChangeListener {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    private DataApplication mDataApplication = new DataApplication();
    private TextView countPeopleTextView;
    //Dates(of the top of the screen)
    private RecyclerView mDatesRecyclerView;
    private RecyclerViewDates mDatesAdapter;

    private RecyclerView mPeopleDatesRecyclerView;
    private RecyclerViewPeople mPeopleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        countPeopleTextView = findViewById(R.id.count_people);
        retriveAllDataFromFireBase();

    }

    private void retriveAllDataFromFireBase() {
        DatabaseReference ref = database.getReference("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchProfiles(dataSnapshot);
                fetchPeopleDates();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void fetchPeopleDates() {
        DatabaseReference ref = database.getReference("dates");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PeopleDates> peopleDates = new ArrayList();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ArrayList<String> uuidList = new ArrayList<>();
                    String date = snapshot.getKey();
                    HashMap<Object, String> hashMap = (HashMap<Object, String>) snapshot.getValue();
                    Iterator it = hashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        uuidList.add((String) pair.getValue());
                    }
                    peopleDates.add(new PeopleDates(parseDate(date), uuidList));

                }
                mDataApplication.setPeopleDates(peopleDates);
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchProfiles(DataSnapshot dataSnapshot) {
        ArrayList<Profile> profiles = new ArrayList();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String userUUID = snapshot.getKey();
            String email = (String) ((HashMap) snapshot.getValue()).get("email");
            String fullName = (String) ((HashMap) snapshot.getValue()).get("fullName");
            String groupTeam = (String) ((HashMap) snapshot.getValue()).get("groupTeam");
            ArrayList<String> hobbies = (ArrayList<String>) ((HashMap) snapshot.getValue()).get("hobbies");
            profiles.add(new Profile(userUUID, email, fullName, groupTeam, hobbies));
        }
        mDataApplication.setProfiles(profiles);
    }


    private void initRecyclerView() {
        initRecyclerDates();
        initRecylerPerson();

    }

    private void initRecylerPerson() {
        mPeopleDatesRecyclerView = findViewById(R.id.recycler_view_list_of_people);
        mPeopleDatesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPeopleAdapter = new RecyclerViewPeople(this, mPeopleDatesRecyclerView);
        mPeopleDatesRecyclerView.setAdapter(mPeopleAdapter);
    }

    private void initRecyclerDates() {
        mDatesRecyclerView = findViewById(R.id.recycler_view_dates);
        mDatesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDatesAdapter = new RecyclerViewDates(this, mDatesRecyclerView, this);
        mDatesRecyclerView.setAdapter(mDatesAdapter);
    }


    @Override
    public void onDateChangeListener(Date date) {
        ArrayList<String> allPeople = mDataApplication.getAllPeople(date);
        mPeopleAdapter.setNewList(allPeople);
        String numberOfPeople = allPeople == null ? "0" : String.valueOf(allPeople.size());
        countPeopleTextView.setText(numberOfPeople);
    }

    private Date parseDate(String date) {
        String[] arry = date.split("-");
        int day = Integer.parseInt(arry[0]);
        int month = Integer.parseInt(arry[1]);
        int year = Integer.parseInt(arry[2]);
        return new Date(day, month, year, false);
    }
}