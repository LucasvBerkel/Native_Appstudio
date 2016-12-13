package com.example.lucas.lucasvanberkel_pset6.db;



import com.example.lucas.lucasvanberkel_pset6.classes.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FbHelper {
    private DatabaseReference mUsers;

    public FbHelper() {
        this.mUsers = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void addUser(User user){
        Map<String, String> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("firstname", user.getFirstname());
        data.put("lastname", user.getLastname());
        data.put("address", user.getAddress());
        mUsers.child(user.getUsername()).setValue(data);
    }
}