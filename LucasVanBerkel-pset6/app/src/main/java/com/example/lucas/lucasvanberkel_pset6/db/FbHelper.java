package com.example.lucas.lucasvanberkel_pset6.db;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This class looks like it is redundant. Idea behind creating this class is to (in future times) migrate
 * the sql-database from its local origin to the real-time firebase-database
 */
public class FbHelper {

    public FirebaseAuth getFireBaseAuth(){
        return FirebaseAuth.getInstance();
    }
}