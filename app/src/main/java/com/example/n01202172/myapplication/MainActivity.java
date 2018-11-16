package com.example.n01202172.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView message;
    private Button signin;
    private Button register;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        message = findViewById(R.id.loginmessage);


        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //handleLogin();
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        signin = findViewById(R.id.signIn);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chkUser = username.getText().toString();
                if (chkUser.trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter a username", Toast.LENGTH_LONG).show();
                    return;
                }
                String chkPass = password.getText().toString();
                if (chkPass.trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter a Password", Toast.LENGTH_LONG).show();
                    return;
                }
                loginUser(String.valueOf(username.getText()), String.valueOf(password.getText()));
            }
        });
    }
    private void testUser(){
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            message.setText("Already Logged in");
        }
    }
    private void loginUser(String email, String password){
        // TODO: Login with Email and Password on Firebase.
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MapleLeaf", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            message.setText("User "+ user.getEmail() + " is now Logged In");
                            Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("MapleLeaf", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }


}

