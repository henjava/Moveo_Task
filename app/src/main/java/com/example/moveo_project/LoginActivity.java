package com.example.moveo_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginEmail;
    EditText etLoginPassword;
    Button btLogin;
    Button btRegister;
    FirebaseAuth mAuth;
    Button forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.emailText);
        etLoginPassword = findViewById(R.id.editPassword);
        btLogin = findViewById(R.id.login_btn);
        btRegister = findViewById(R.id.login_register_btn);
        forgotPassword= findViewById(R.id.btforgotPassword);
        mAuth=FirebaseAuth.getInstance();

        btLogin.setOnClickListener(view -> {
            loginUser();
        });
        btRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        forgotPassword.setOnClickListener(view -> {
            if(TextUtils.isEmpty(etLoginEmail.getText().toString()))
            {
                Toast.makeText(LoginActivity.this, "Please Enter your email", Toast.LENGTH_SHORT).show();

            }
            else{
                mAuth.sendPasswordResetEmail(etLoginEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void loginUser() {
        {
            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();
            if (TextUtils.isEmpty(email)) {
                etLoginEmail.setError("Email cannot be empty");
                etLoginEmail.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                etLoginPassword.setError("Password cannot be empty");
                etLoginPassword.requestFocus();
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "User loged in successfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Login error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                });
            }
        }
    }
}
