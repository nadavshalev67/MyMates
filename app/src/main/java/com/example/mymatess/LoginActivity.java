package com.example.mymatess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEmail, mPassword;
    TextView mForgotYourPassword;
    Button mLogin, mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login_button);
        mLogin.setOnClickListener(this);
        mRegister = findViewById(R.id.register_button);
        mForgotYourPassword = findViewById(R.id.forgot_yourt_password);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button: {

                break;
            }
        }
    }
}