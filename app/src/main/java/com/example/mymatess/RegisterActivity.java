package com.example.mymatess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymatess.activity.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mContinueButton;
    EditText mEmail, mPassword,mConfirmPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mAuth = FirebaseAuth.getInstance();


        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mContinueButton = findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button: {
                String email= mEmail.getText().toString();
                String password= mPassword.getText().toString();
                String confirmPassword= mConfirmPassword.getText().toString();

                if(!email.substring(email.length()-10).equals("@fyber.com")){
                    Toast.makeText(RegisterActivity.this, "Email Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(confirmPassword) ){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, udate UI with the signed-in user's information
                                        Log.d("createUserWithEmail", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                                        startActivity(intent);
//                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("createUserWithEmail", "createUserWithEmail:success");
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
//                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });
                    }else{
                        Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }


                break;
            }
        }
    }
}

