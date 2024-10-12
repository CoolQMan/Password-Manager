package com.coolqman.password_manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditPasswordActivity extends AppCompatActivity {

    private EditText etWebsite, etUsername, etPassword;
    private Button btnSave;
    private ImageButton btnDelete;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        etWebsite = findViewById(R.id.etWebsite);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        // Get data from intent for editing
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        if (intent.hasExtra("website") && intent.hasExtra("username") && intent.hasExtra("password")) {
            String website = intent.getStringExtra("website");
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");

            etWebsite.setText(website);
            etUsername.setText(username);
            etPassword.setText(password);
        }

        // Handle save button click
        btnSave.setOnClickListener(v -> {
            String website = etWebsite.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Check if any field is empty
            if (website.isEmpty()) {
                Toast.makeText(EditPasswordActivity.this, "Please fill in the Website field", Toast.LENGTH_SHORT).show();
            } else if (username.isEmpty()) {
                Toast.makeText(EditPasswordActivity.this, "Please fill in the Username field", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(EditPasswordActivity.this, "Please fill in the Password field", Toast.LENGTH_SHORT).show();
            } else {
                // All fields are filled, save the data and return to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("website", website);
                resultIntent.putExtra("username", username);
                resultIntent.putExtra("password", password);
                resultIntent.putExtra("position", position);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // Handle delete button click
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Password")
                    .setMessage("Are you sure you want to delete this password?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("position", getIntent().getIntExtra("position", -1)); // Pass the position back
                        setResult(MainActivity.RESULT_DELETED, resultIntent); // Use the custom deletion code
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public void onBackPressed() {
        // Simply set the result as canceled to indicate no changes were made
        Intent resultIntent = new Intent();
        resultIntent.putExtra("position", position);
        setResult(RESULT_CANCELED, resultIntent);
        finish();
        super.onBackPressed(); // This will navigate back to MainActivity without closing it
    }
}