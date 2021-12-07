package com.example.moveo_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    EditText etRegEmail;
    EditText etRegPassword;
    Button btnRegister;
    EditText etFirstName;
    EditText etLastName;
    EditText pinCode;
    Button btBack;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.register_email_editText);
        etRegPassword = findViewById((R.id.register_password_editText));
        etFirstName = findViewById(R.id.register_firstName_editText);
        etLastName = findViewById(R.id.register_lastName_editText);
        pinCode = findViewById(R.id.register_pin_input);
        btBack = findViewById(R.id.register_back_btn);

        btnRegister = findViewById((R.id.register_btn));
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(view -> {
            createUser();
        });
        btBack.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

    }
    private void createUser() {
        String firstname = etFirstName.getText().toString();
        String lastname = etLastName.getText().toString();
        String pincode = pinCode.getText().toString();
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(firstname)) {
            etFirstName.setError("First name cannot be empty");
            etFirstName.requestFocus();
        } else if (TextUtils.isEmpty(lastname)) {
            etLastName.setError("Last name  cannot be empty");
            etLastName.requestFocus();
        } else if (TextUtils.isEmpty(pincode)) {
            pinCode.setError("Pin code cannot be empty");
            pinCode.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(email,firstname,lastname,pincode);

                        Toast.makeText(RegisterActivity.this, "User register successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

