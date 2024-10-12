package com.coolqman.password_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText etWebsite, etPassword, etUsername;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        setTitle("Password Manager");

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etWebsite = findViewById(R.id.etWebsite);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();

        btnSave.setOnClickListener(v -> {
            String website = etWebsite.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Check if any field is empty
            if (website.isEmpty()) {
                Toast.makeText(AddPasswordActivity.this, "Please fill in the Website field", Toast.LENGTH_SHORT).show();
            } else if (username.isEmpty()) {
                Toast.makeText(AddPasswordActivity.this, "Please fill in the Username field", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(AddPasswordActivity.this, "Please fill in the Password field", Toast.LENGTH_SHORT).show();
            } else {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("website", website);
                resultIntent.putExtra("username", username);
                resultIntent.putExtra("password", password);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
