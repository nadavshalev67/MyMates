package com.example.mymatess;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }

    private void initSpinner() {
        final String[] Departments = new String[]{"blabla", "blabla"};//this come from firebase!!
        final Spinner spinner = findViewById(R.id.spinner_codespinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.textview, Departments));
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
}