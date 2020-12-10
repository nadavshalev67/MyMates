package com.example.mymatess.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HomeActivity extends AppCompatActivity implements DateChangeListener, View.OnClickListener {
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DataApplication mDataApplication = new DataApplication();
    private TextView countPeopleTextView;
    private ImageView pieChartImageView;

    private RecyclerView mDatesRecyclerView;
    private RecyclerViewDates mDatesAdapter;

    private RecyclerView mPeopleDatesRecyclerView;
    private RecyclerViewPeople mPeopleAdapter;

    private PieChart mPieChartView;
    PieData pieData;
    PieDataSet pieDataSet;
    Date mDate = null;
    private Button addMeButton;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        addMeButton = findViewById(R.id.add_me_button);
        addMeButton.setOnClickListener(this);
        mPeopleDatesRecyclerView = findViewById(R.id.recycler_view_list_of_people);
        mDatesRecyclerView = findViewById(R.id.recycler_view_dates);
        countPeopleTextView = findViewById(R.id.count_people);
        countPeopleTextView.setOnClickListener(this);
        pieChartImageView = findViewById(R.id.piechart_image);
        pieChartImageView.setOnClickListener(this);
        mPieChartView = findViewById(R.id.pieChartView);
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
                mDate = mDatesAdapter.getFirstPlace();
                generatePieData(mDatesAdapter.getFirstPlace());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private PieData generatePieData(Date date) {
        HashMap<String, Integer> mapOfHobbies = mDataApplication.getAllHobbies(date);
        pieDataSet = new PieDataSet(genereateEntries(mapOfHobbies), "");
        pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);
        mPieChartView.setData(pieData);
        mPieChartView.notifyDataSetChanged();
        mPieChartView.invalidate();
        return pieData;
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
        mPeopleDatesRecyclerView.setVisibility(View.VISIBLE);
        mPieChartView.setVisibility(View.INVISIBLE);
    }

    private void initRecylerPerson() {
        mPeopleDatesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPeopleAdapter = new RecyclerViewPeople(this, mPeopleDatesRecyclerView);
        mPeopleDatesRecyclerView.setAdapter(mPeopleAdapter);
    }

    private void initRecyclerDates() {
        mDatesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDatesAdapter = new RecyclerViewDates(this, mDatesRecyclerView, this);
        mDatesRecyclerView.setAdapter(mDatesAdapter);
    }


    @Override
    public void onDateChangeListener(Date date) {
        mDate = date;
        ArrayList<String> allPeople = mDataApplication.getAllPeople(date);
        mPeopleAdapter.setNewList(allPeople);
        String numberOfPeople = allPeople == null ? "0" : String.valueOf(allPeople.size());
        countPeopleTextView.setText(numberOfPeople);
        generatePieData(date);
        mPeopleDatesRecyclerView.setVisibility(View.VISIBLE);
        mPieChartView.setVisibility(View.INVISIBLE);

    }

    private Date parseDate(String date) {
        String[] arry = date.split("-");
        int day = Integer.parseInt(arry[0]);
        int month = Integer.parseInt(arry[1]);
        int year = Integer.parseInt(arry[2]);
        return new Date(day, month, year, false);
    }

    private ArrayList genereateEntries(HashMap<String, Integer> map) {
        ArrayList pieEntries = new ArrayList<>();
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String key = (String) pair.getKey();
                Integer value = (Integer) pair.getValue();
                pieEntries.add(new PieEntry(value.floatValue(), key));
            }
        }
        return pieEntries;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.count_people: {
                if (mPeopleDatesRecyclerView != null && mPieChartView != null) {
                    mPeopleDatesRecyclerView.setVisibility(View.VISIBLE);
                    mPieChartView.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.piechart_image: {
                if (mPeopleDatesRecyclerView != null && mPieChartView != null) {
                    mPeopleDatesRecyclerView.setVisibility(View.INVISIBLE);
                    mPieChartView.setVisibility(View.VISIBLE);
                }
                break;
            }
            case R.id.add_me_button: {
                if (mDate == null) {
                    return;

                }
                final DatabaseReference ref = database.getReference("dates").child(mDate.toString());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> postValues = new HashMap<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            postValues.put(snapshot.getKey(), snapshot.getValue());
                        }

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String uuid = (String) snapshot.getValue();
                            if (TextUtils.equals(uuid, firebaseUser.getUid())) {
                                Toast.makeText(getApplicationContext(), "You are already registered to this date", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        postValues.put(String.valueOf(mDate.hashCode()), firebaseUser.getUid());
                        ref.setValue(postValues);
                        Toast.makeText(getApplicationContext(), "You have been added - calander is refreshed", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }
    }
}