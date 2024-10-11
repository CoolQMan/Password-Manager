package com.coolqman.password_manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PasswordAdapter.OnItemClickListener {

    public static final int RESULT_DELETED = 3;

    private RecyclerView recyclerView;
    private PasswordAdapter adapter;
    private List<Password> passwordList;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAddPassword);

        passwordList = new ArrayList<>();
        adapter = new PasswordAdapter(passwordList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPasswordActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onItemClick(int position) {
        // Reveal password on item click
        Password password = passwordList.get(position);
        Toast.makeText(this, "Password: " + password.getPassword(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position) {
        // Open edit screen on long click
        Password password = passwordList.get(position);
        Intent intent = new Intent(MainActivity.this, EditPasswordActivity.class);
        intent.putExtra("website", password.getWebsite());
        intent.putExtra("username", password.getUsername());
        intent.putExtra("password", password.getPassword());
        intent.putExtra("position", position);
        startActivityForResult(intent, 2); // Different request code for editing
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // New password added
            String website = data.getStringExtra("website");
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");

            Password newPassword = new Password(website, username, password);
            passwordList.add(newPassword);
            adapter.notifyDataSetChanged();
        } else if (requestCode == 2) {
            int position = data.getIntExtra("position", -1); // Get the position
            if (resultCode == RESULT_OK && position != -1) {
                // Password updated
                String website = data.getStringExtra("website");
                String username = data.getStringExtra("username");
                String password = data.getStringExtra("password");

                passwordList.set(position, new Password(website, username, password));
                adapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_DELETED && position != -1) {
                // Handle deletion
                passwordList.remove(position); // Remove from the list
                adapter.notifyDataSetChanged(); // Notify adapter
                Toast.makeText(this, "Password deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
