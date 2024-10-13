package com.coolqman.password_manager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_DELETED = 3;

    private RecyclerView recyclerView;
    private PasswordAdapter adapter;
    private List<Password> passwordList;
    private FloatingActionButton fab;


    private TextView userHint, addHint;
    private ImageView arrow;
    private SearchView searchView;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Password Manager");

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAddPassword);
        userHint = findViewById(R.id.userHint);
        addHint = findViewById(R.id.addHint);
        arrow = findViewById(R.id.arrow);
        searchView = findViewById(R.id.searchView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        passwordList = new ArrayList<>();
        //Loading all passwords from db
        dbHelper = new DatabaseHelper(this, uid);
        passwordList = dbHelper.getAllPasswords();

        //Checking if password list is empty
        checkPasswordList();

        adapter = new PasswordAdapter(passwordList, new PasswordAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Password password = passwordList.get(position);
                Intent intent = new Intent(MainActivity.this, EditPasswordActivity.class);
                intent.putExtra("website", password.getWebsite());
                intent.putExtra("username", password.getUsername());
                intent.putExtra("password", password.getPassword());
                intent.putExtra("position", position);
                startActivityForResult(intent, 2);// Different request code for editing
            }

            @Override
            public void onItemClick(int position) {
                Password password = passwordList.get(position);
                password.setVisible(!password.isPasswordVisible());
                adapter.notifyItemChanged(position);
            }
        });

        checkPasswordList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPasswordActivity.class);
            startActivityForResult(intent, 1);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the RecyclerView items
                adapter.getFilter().filter(newText);
                return true;
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // New password added
            String website = data.getStringExtra("website");
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");

            int id = passwordList.isEmpty() ? 1 : passwordList.get(passwordList.size() - 1).getId() + 1;

            Password newPassword = new Password(id, website, username, password, System.currentTimeMillis());
            dbHelper.addPassword(newPassword);
            passwordList.add(newPassword);
            adapter.updateList(passwordList);
            adapter.notifyDataSetChanged();
        } else if (requestCode == 2) {
            int position = data.getIntExtra("position", -1); // Get the position
            if (resultCode == RESULT_OK && position != -1) {
                // Password updated
                String website = data.getStringExtra("website");
                String username = data.getStringExtra("username");
                String password = data.getStringExtra("password");

                int id = passwordList.get(position).getId();

                Password updatedPassword = new Password(id, website, username, password, System.currentTimeMillis());
                dbHelper.updatePassword(updatedPassword, position);
                passwordList.set(position, updatedPassword);
                adapter.updateList(passwordList);
                adapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_DELETED && position != -1) {
                // Handle deletion
                Password deletedPassword = passwordList.get(position);
                dbHelper.deletePassword(deletedPassword.getId());
                passwordList.remove(position); // Remove from the list
                adapter.updateList(passwordList);
                adapter.notifyDataSetChanged(); // Notify adapter
                Toast.makeText(this, "Password deleted", Toast.LENGTH_SHORT).show();
            }
        }
        checkPasswordList();
    }


    private void checkPasswordList(){
        if(dbHelper.getAllPasswords().isEmpty()){
            userHint.setVisibility(View.VISIBLE);
            addHint.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        } else{
            userHint.setVisibility(View.GONE);
            addHint.setVisibility(View.GONE);
            arrow.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        View view = getCurrentFocus();
        searchView.clearFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        if(id == R.id.action_refresh){
            refreshPasswordList();
            return true;
        } else if(id == R.id.action_delete_all){
            confirmDeleteAllPasswords();
            return true;
        } else if(id == R.id.action_hide_all){
            hideAllPasswords();
            return true;
        } else{
            return super.onOptionsItemSelected(item);
        }
    }


    private void refreshPasswordList() {
        // Create a list of views to animate
        ViewGroup viewGroup = (ViewGroup) recyclerView.getParent();

        // Get the current item count
        int itemCount = adapter.getItemCount();

        // Create an ArrayList to store current views before clearing them
        List<View> viewsToAnimate = new ArrayList<>();

        // Loop through the current items and get their views
        for (int i = 0; i < itemCount; i++) {
            View itemView = recyclerView.getChildAt(i);
            if (itemView != null) {
                viewsToAnimate.add(itemView);
            }
        }

        // Start the slide-out animation for each view
        for (View view : viewsToAnimate) {
            Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);
            slideOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Remove the view from the parent once the animation ends
                    viewGroup.removeView(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            view.startAnimation(slideOut);
        }

        // Delay the loading of new passwords to ensure the animation finishes
        new Handler().postDelayed(() -> {
            // Clear the current password list
            passwordList.clear();

            // Fetch the updated list of passwords from the database
            List<Password> newPasswords = dbHelper.getAllPasswords();

            // Check if newPasswords is not null before adding
            if (newPasswords != null) {
                passwordList.addAll(newPasswords);
            }

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }, 300); // Delay must match the duration of the slide-out animation
        checkPasswordList();
    }


    private void hideAllPasswords() {
        for (Password password : passwordList) {
            password.setVisible(false); // Assuming you have a setVisible method in your Password class
        }
        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    private void confirmDeleteAllPasswords() {
        new AlertDialog.Builder(this)
                .setTitle("Delete All Passwords")
                .setMessage("Are you sure you want to delete all passwords?")
                .setPositiveButton("Yes", (dialog, which) -> confirmFinalDeletion()) // Call final confirmation
                .setNegativeButton("No", null) // Dismiss the dialog
                .show();
    }

    private void confirmFinalDeletion() {
        new AlertDialog.Builder(this)
                .setTitle("Final Confirmation")
                .setMessage("This action can NOT be undone. Are you sure you want to proceed?")
                .setPositiveButton("Yes", (dialog, which) -> deleteAllPasswords()) // Proceed with deletion
                .setNegativeButton("No", null) // Dismiss the dialog
                .show();
    }

    private void deleteAllPasswords() {
        // Delete all passwords from the database
        dbHelper.deleteAllPasswords(); // Assuming you have a method in your DB helper to delete all

        // Clear the password list
        passwordList.clear();

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();

        // Optionally, show a toast message to confirm deletion
        Toast.makeText(this, "All passwords have been deleted.", Toast.LENGTH_SHORT).show();

        checkPasswordList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear focus from the search view
        searchView.clearFocus();
    }


}
