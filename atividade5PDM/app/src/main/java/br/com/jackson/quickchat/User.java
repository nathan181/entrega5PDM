package br.com.jackson.quickchat;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String name;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {//construtor para o cadastro
        this.name = name;
        this.email = email;
    }
}