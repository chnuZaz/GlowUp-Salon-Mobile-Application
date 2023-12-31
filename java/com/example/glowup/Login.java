package com.example.glowup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends Activity {

    TextInputEditText inputEmail, inputPassword;
    Button btnLogIn;
    TextView signUp;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        inputEmail= findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogIn =findViewById(R.id.btnLogIn);
        signUp =findViewById(R.id.Signup);
        ProgressBar ProgressBar=findViewById(R.id.ProgressBar);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,Signup.class);
                startActivity(intent);
                finish();

            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email,Password;
                Email= String.valueOf(inputEmail.getText());
                Password=String.valueOf(inputPassword.getText());

                if(TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
                    ProgressBar.setVisibility(View.GONE);
                    return;
                }
                if(TextUtils.isEmpty(Password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG).show();
                    ProgressBar.setVisibility(View.GONE);
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(Email,Password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                ProgressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successfull",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Main2.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        // User does not exist
                                        Toast.makeText(getApplicationContext(), "user not found.", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        // Invalid password or email format
                                        Toast.makeText(getApplicationContext(), "invalid user name or password.", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        // General error, log and handle accordingly
                                        Log.e("AuthenticationError", "Authentication failed: " + e.getMessage());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });



            }

        });


    }
}
