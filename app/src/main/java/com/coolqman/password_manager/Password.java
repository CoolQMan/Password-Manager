package com.coolqman.password_manager;

public class Password {
    private String website;
    private String password;
    private String username;
    private boolean isVisibile;

    public Password(String website, String username, String password) {
        this.website = website;
        this.password = password;
        this.username = username;
        this.isVisibile = false;
    }

    // Getters and Setters
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
        return this.isVisibile;
    }

    public void setVisibile(boolean isVisibile){
        this.isVisibile = isVisibile;
    }
}

