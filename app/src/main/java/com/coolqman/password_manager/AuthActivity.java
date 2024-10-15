package com.coolqman.password_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView forgotPassword, registerButton;
    private ImageButton togglePasswordVisibility;
    private boolean isPasswordVisible = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setTitle("Password Manager");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.RegisterBtn);
        togglePasswordVisibility = findViewById(R.id.btnTogglePasswordVisibility);
        forgotPassword = findViewById(R.id.forgotPasswordLink);

        loginButton.setOnClickListener(view -> validateAndLogin());
        registerButton.setOnClickListener(view -> validateAndRegister());
        togglePasswordVisibility.setOnClickListener(view -> togglePasswordVisibility());
        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            if(!emailEditText.getText().toString().isEmpty()){
                intent.putExtra("email", emailEditText.getText().toString());
            }
            startActivity(intent);
        });
    }

    private void validateAndLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AuthActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if(Objects.equals(Objects.requireNonNull(task.getException()).getMessage(), "The email address is badly formatted.")){
                            Toast.makeText(AuthActivity.this, "Login Failed: Enter valid Email Address!", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(AuthActivity.this, "Login Failed: Wrong Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void validateAndRegister() {
        Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
        String email = emailEditText.getText().toString().trim();
        if(!email.isEmpty()){
            intent.putExtra("email", email);
        }
        startActivityForResult(intent, 4);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            finish();
        }
    }
}
