package com.coolqman.password_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    private ImageButton togglePasswordVisibility;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setTitle("Password Manager");

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        togglePasswordVisibility = findViewById(R.id.btnTogglePasswordVisibility);

        togglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.baseline_show_24); // Change to hide icon
                } else {
                    // Show password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.baseline_hide_24); // Change to show icon
                }
                // Move the cursor to the end of the text
                etPassword.setSelection(etPassword.length());
                // Toggle the visibility state
                isPasswordVisible = !isPasswordVisible;
            }
        });

        // Set up login button listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });
    }

    // Validate login credentials
    private void validateLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Hardcoded email and password
        String validEmail = "test@admin.com";
        String validPassword = "12345678";

        // Check if entered credentials match
        if (email.equals(validEmail) && password.equals(validPassword)) {
            // Navigate to the List Screen if successful
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the login screen
        } else {
            // Show error message if credentials are incorrect
            Toast.makeText(AuthActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
