package com.bridgelabz.fundoo.common.firebase_auth_service;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FirebaseAuthManager {
    FirebaseAuth firebaseAuth;

    public void signIn(final String emailid, final String password) {


        firebaseAuth = FirebaseAuth.getInstance();


        firebaseAuth.signInWithEmailAndPassword(emailid, password).addOnCompleteListener
                (new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Sucessfully Login.... ", Toast.LENGTH_SHORT).show();

//                            FirebaseAuthManager firebaseAuthManager = new FirebaseAuthManager();
//                            firebaseAuthManager.signIn(emailid, password);


                        } else {

                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void createUser(String emailid, String pwd) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailid, pwd).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Sucessfully Register.... ",
                                            Toast.LENGTH_SHORT).show();
                                }
                        else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                           Toast.LENGTH_SHORT).show();
                            }
                    }
                });
    }
}





