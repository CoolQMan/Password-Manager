package com.coolqman.password_manager;

public class Password {

    private int id;
    private String website;
    private String password;
    private String username;
    private boolean isVisible;
    private long timestamp;


    public Password(int id, String website, String username, String password, long timestamp) {

        this.id = id;
        this.website = website;
        this.password = password;
        this.username = username;
        this.isVisible = false;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordVisible(){
        return this.isVisible;
    }

    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }
}

