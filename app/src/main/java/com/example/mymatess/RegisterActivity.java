package com.example.mymatess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mContinueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mContinueButton = findViewById(R.id.continue_button);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button: {
                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            }

        }
    }
}