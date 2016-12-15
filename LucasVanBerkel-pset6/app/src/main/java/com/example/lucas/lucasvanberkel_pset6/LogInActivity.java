package com.example.lucas.lucasvanberkel_pset6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.lucasvanberkel_pset6.db.FbHelper;
import com.example.lucas.lucasvanberkel_pset6.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is the first activity of the app. Here the user logs in into his own account saved by firebase.
 * If the user does not have an account registered, he/she can chose to register his/her own account,
 * using her own emailadress and password. If login or sign up is succesfull, then the user will
 * be navigated to his/her own favorite list.
 */
public class LogInActivity extends AppCompatActivity {

    TextView emailTextField;
    TextView emailField;
    TextView passwordTextField;
    TextView passwordField;
    Button loginButton;
    Button signupButton;
    Button toggleButton;

    boolean login;

    String email;
    String password;

    private FirebaseAuth mFirebaseAuth;


    // Basic onCreate method, finds the views in the activity and sets the keyboard automatically
    // to the email field.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Log in");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fill in your userinformation", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FbHelper fbHelper = new FbHelper();

        mFirebaseAuth = fbHelper.getFireBaseAuth();

        emailTextField = (TextView) findViewById(R.id.loginEmail);
        emailField = (TextView) findViewById(R.id.loginEmailText);
        passwordTextField = (TextView) findViewById(R.id.loginPassword);
        passwordField = (TextView) findViewById(R.id.loginPasswordText);
        signupButton = (Button) findViewById(R.id.signupButton);
        login = true;
        
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        
        toggleButton = (Button) findViewById(R.id.switchButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSignup();
            }
        });
        
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(emailField, InputMethodManager.SHOW_IMPLICIT);
    }

    // The two methods which inflates and activates the menu toolbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_about:
                Util.aboutDialog(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to switch between login and sign up mode
    private void toggleSignup() {
        if(login) {
            loginButton.setVisibility(View.INVISIBLE);
            signupButton.setVisibility(View.VISIBLE);
            emailTextField.setText(R.string.signupEmail);
            passwordTextField.setText(R.string.signupPassword);
            toggleButton.setText(R.string.toggleButtonLogIn);
            login = false;
        } else {
            signupButton.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            emailTextField.setText(R.string.loginEmail);
            passwordTextField.setText(R.string.loginPassword);
            toggleButton.setText(R.string.toggleButtonSignUp);
            login = true;
        }
    }

    // Method activated when user wants to sign up, code is more less same as in the android guide
    private void signUp() {
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        password = password.trim();
        email = email.trim();

        if (password.isEmpty() || email.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
            builder.setMessage("Email and password are required")
                    .setTitle("Sign up failed")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                                builder.setMessage(task.getException().getMessage())
                                        .setTitle("Sign up failed")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
        }
    }

    // Method activated when user wants to login, code is more less same as in the android guide
    private void submit() {
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        email = email.trim();
        password = password.trim();

        if (email.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
            builder.setMessage("Email and password are required")
                    .setTitle("Login failed")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            mFirebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("LOG_Login", "signInWithEmail:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()){
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.w("LOG_Login", "signInWithEmail", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }
}
