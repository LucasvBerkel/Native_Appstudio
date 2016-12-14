package com.example.lucas.lucasvanberkel_pset6.db;

import com.google.firebase.auth.FirebaseAuth;


public class FbHelper {

    public FirebaseAuth getFireBaseAuth(){
        return FirebaseAuth.getInstance();
    }
}