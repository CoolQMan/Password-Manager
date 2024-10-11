package com.coolqman.password_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {

    private List<Password> passwordList;
    private OnItemClickListener listener;
    private boolean[] passwordVisibility;

    public PasswordAdapter(List<Password> passwordList, OnItemClickListener listener) {
        this.passwordList = passwordList;
        this.listener = listener;
        passwordVisibility = new boolean[passwordList.size()];
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password, parent, false);
        return new PasswordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        Password password = passwordList.get(position);
        holder.websiteTextView.setText(password.getWebsite());
        holder.usernameTextView.setText("Username: " + password.getUsername());
        holder.passwordTextView.setText("Password: ********");

        // Set long click listener to delete password
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(position);
            return true; // Indicate that the long click was handled
        });

        // Set click listener to reveal password (regular click)
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(position);
        });

        // Add delete button click listener here if you have a specific delete button in the item layout
    }


    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    static class PasswordViewHolder extends RecyclerView.ViewHolder {
        TextView websiteTextView;
        TextView usernameTextView; // Added for displaying username
        TextView passwordTextView; // TextView to display password (hidden)

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            websiteTextView = itemView.findViewById(R.id.websiteTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView); // Initialize username TextView
            passwordTextView = itemView.findViewById(R.id.passwordTextView); // Initialize password TextView
        }
    }

    // Create an interface for item click listener
    public interface OnItemClickListener {
        void onItemLongClick(int position);
        void onItemClick(int position); // Method for handling regular clicks
    }
}
