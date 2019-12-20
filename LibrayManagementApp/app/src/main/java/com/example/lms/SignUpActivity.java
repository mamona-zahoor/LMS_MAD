package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
FirebaseDatabase firebaseDatabase;
    EditText firstname,lastname;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("users");

    }
    public void navDash(View view){
        try {
            firstname = (EditText) findViewById(R.id.signupact_firstname);
           lastname = (EditText) findViewById(R.id.signupact_lastname);
            EditText txtUsername = (EditText) findViewById(R.id.signupact_email);
            EditText txtPassword = (EditText) findViewById(R.id.signup_password);

            auth.createUserWithEmailAndPassword(txtUsername.getText().toString(), txtPassword.getText().toString())
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "create account success" + auth.getUid(), Toast.LENGTH_SHORT).show();
                                postSignUpUserInfo(auth.getUid());
                            } else {
                                Toast.makeText(SignUpActivity.this, "signUp error" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//            DatabaseReference databaseReference=firebaseDatabase.getReference("users");
//            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                        if(!dataSnapshot1.hasChild("userrole")){
//                            onNewChild(dataSnapshot1.getKey());
//
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        }catch (Exception ex){
            Toast.makeText(SignUpActivity.this, "error" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
//    public void onNewChild(String childpath){
//        Toast.makeText(getApplicationContext(),"call",Toast.LENGTH_SHORT).show();
//        DatabaseReference databaseReference=firebaseDatabase.getReference("users");
//        databaseReference.child(childpath).child("userrole").setValue("user").addOnCompleteListener(SignUpActivity.this,
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(getApplicationContext(),"good",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//    }
    public void postSignUpUserInfo(String userId){
       // final Boolean[] isChildExist = {false};

//        databaseReference.child("id").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.getValue()!=null){
//                    isChildExist[0] =true;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        Map<String,Object> map=new HashMap<>();
       // map.put("id",userId);
        map.put("name",firstname.getText()+ " "+lastname.getText());
        map.put("userrole","user");

        databaseReference.child(userId).setValue(map).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "error in signup "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}