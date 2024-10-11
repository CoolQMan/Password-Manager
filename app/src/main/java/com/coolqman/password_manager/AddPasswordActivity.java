package com.coolqman.password_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText etWebsite, etPassword, etUsername;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        etWebsite = findViewById(R.id.etWebsite);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername); // Initialize username EditText
        btnSave = findViewById(R.id.btnSave);

        // Check if we're editing an existing password
        Intent intent = getIntent();
        if (intent.hasExtra("website") && intent.hasExtra("username") && intent.hasExtra("password")) {
            String website = intent.getStringExtra("website");
            String password = intent.getStringExtra("password");
            String username = intent.getStringExtra("username"); // Get username
            etWebsite.setText(website);
            etPassword.setText(password);
            etUsername.setText(username); // Set username in EditText
            btnSave.setText("Update Password"); // Change button text to indicate update
        }

        btnSave.setOnClickListener(v -> {
            String website = etWebsite.getText().toString().trim();
            String username = etUsername.getText().toString().trim(); // Get username
            String password = etPassword.getText().toString().trim();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("website", website);
            resultIntent.putExtra("username", username); // Send username back
            resultIntent.putExtra("password", password);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }

}
