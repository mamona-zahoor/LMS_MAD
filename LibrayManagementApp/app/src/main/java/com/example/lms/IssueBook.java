package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueBook extends AppCompatActivity {
    DatabaseReference Db;
    String Name;
    boolean exists;
    FirebaseAuth firebaseAuth;
   public static EditText ISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        ImageButton scanQRbtn=findViewById(R.id.cmdScan);
        ISBN = findViewById(R.id.txtISBN);
        scanQRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IssueBook.this, QRScannerActivity.class);
                startActivity(intent);
            }
        });


    }

    public void Next(View v)
    {
    }

    public void IssueConfirmation(View v) {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txtISBN, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        awesomeValidation.addValidation(this, R.id.txtIssuedTo, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        if (awesomeValidation.validate()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(IssueBook.this);
            builder.setTitle(R.string.app_name);
            firebaseAuth = FirebaseAuth.getInstance();

            String j = ISBN.getText().toString();
            EditText IssueTo = findViewById(R.id.txtIssueTo);
            Db = FirebaseDatabase.getInstance().getReference("allbooks");
            Db.orderByChild("isbn").equalTo(ISBN.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        Name = datas.child("bookName").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });
            //To Validate EMail
            firebaseAuth.fetchSignInMethodsForEmail(IssueTo.getText().toString())
                    .addOnSuccessListener(IssueBook.this, new OnSuccessListener<SignInMethodQueryResult>() {
                        @Override
                        public void onSuccess(@NonNull SignInMethodQueryResult task) {

                            boolean isNewUser = task.getSignInMethods().isEmpty();

                            if (isNewUser) {
                                Log.e("TAG", "Is New User!");
                            } else {
                                Log.e("TAG", "Is Old User!");
                                exists = true;
                            }

                        }
                    });
            builder.setMessage("Book : " + Name);
            if (exists) {
                builder.setMessage("User : " + "Exists");
            } else {
                builder.setMessage("User : " + " NOt Exists");

            }//builder.setIcon(R.drawable.ic_launcher);

            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditText ISBN = findViewById(R.id.txtISBN);
                    EditText IssueTo = findViewById(R.id.txtIssueTo);
                    CalendarView Return = findViewById(R.id.cdrReturn);
                    Db = FirebaseDatabase.getInstance().getReference("IssuedBooks");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String Id = Db.push().getKey();
                    IssuedBooks IssuedBookObj = new IssuedBooks(ISBN.getText().toString(), Id,
                            IssueTo.getText().toString(),
                            dateFormat.format(date),
                            dateFormat.format(Return.getDate()));
                    Db.child(Id).setValue(IssuedBookObj);

                    Toast.makeText(IssueBook.this, "Book issued!", Toast.LENGTH_SHORT).show();


                }
            });
            builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}

