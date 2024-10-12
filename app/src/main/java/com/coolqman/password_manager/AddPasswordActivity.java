package com.coolqman.password_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText etWebsite, etPassword, etUsername;
    private Button btnSave;
    private ImageButton togglePasswordVisibility;

    private boolean isPasswordVisible = false;

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
