package com.example.mymatess.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymatess.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText mEmail, mPassword;
    TextView mForgotYourPassword;
    Button mLogin, mRegister;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login_button);
        mLogin.setOnClickListener(this);
        mRegister = findViewById(R.id.register_button);
        mRegister.setOnClickListener(this);
        mForgotYourPassword = findViewById(R.id.forgot_yourt_password);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Log.d("UserLoginNull", "null user");
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button: {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(password, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(password, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                break;
            }
            case R.id.forgot_yourt_password: {

                break;
            }
            case R.id.register_button: {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            }

        }
    }
}