package com.example.lms;


import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    //LoginUser_ModelClass loginUser_modelClass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        //   loginUser_modelClass = LoginUser_ModelClass.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
    }

    public void signUpNav(View view) {
        Intent newIntent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(newIntent);
    }

    public void navDash(View view) {
        EditText emailtxt = (EditText) findViewById(R.id.loginact_email);
        EditText passwordtxt = (EditText) findViewById(R.id.loginact_password);
        auth.signInWithEmailAndPassword(emailtxt.getText().toString(), passwordtxt.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "good" + auth.getUid(), Toast.LENGTH_SHORT).show();
                            getFurtherInfo();

                        } else {
                            Toast.makeText(LoginActivity.this, "sign in error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void getFurtherInfo() {
        Toast.makeText(getApplicationContext(), "call",Toast.LENGTH_SHORT).show();
        databaseReference.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    //   LoginUser_ModelClass loginUser_modelClass = LoginUser_ModelClass.getInstance();
                    LoginUser_ModelClass loginUser_modelClass = dataSnapshot.getValue(LoginUser_ModelClass.class);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    intent.putExtra("name", loginUser_modelClass.name);
                    intent.putExtra("userrole", loginUser_modelClass.userrole);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "kn" + loginUser_modelClass.name + "role is" + loginUser_modelClass.userrole,
                            Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, "sign in  i error" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "database error " + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}