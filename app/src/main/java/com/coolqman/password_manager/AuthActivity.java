package com.coolqman.password_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.coolqman.password_manager.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private ImageButton togglePasswordVisibility;
    private boolean isPasswordVisible = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth); // Update with your layout name
        setTitle("Password Manager");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find views by ID
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        togglePasswordVisibility = findViewById(R.id.btnTogglePasswordVisibility);

        // Set up button click listeners
        loginButton.setOnClickListener(view -> validateAndLogin());
        registerButton.setOnClickListener(view -> validateAndRegister());
        togglePasswordVisibility.setOnClickListener(view -> togglePasswordVisibility());
    }

    private void validateAndLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Perform basic validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AuthActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if(task.getException().getMessage() == "The email address is badly formatted."){
                            Toast.makeText(AuthActivity.this, "Login Failed: Enter valid Email Address!", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(AuthActivity.this, "Login Failed: Wrong Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void validateAndRegister() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Perform basic validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AuthActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AuthActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            togglePasswordVisibility.setImageResource(R.drawable.baseline_show_24); // Change to hide icon
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            togglePasswordVisibility.setImageResource(R.drawable.baseline_hide_24); // Change to show icon
        }
        passwordEditText.setSelection(passwordEditText.length());
        isPasswordVisible = !isPasswordVisible;
    }
}
