package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IssueBook extends AppCompatActivity {
    DatabaseReference Db, Db2;
    String Name;
    boolean exists;
    FirebaseAuth firebaseAuth;
    public static EditText ISBN;
    final ArrayList<String> UserEmails = new ArrayList<>();
    String BookId, UserId;
    final ArrayList<String> UserNames = new ArrayList<>();
    final ArrayList<String> BooksIds = new ArrayList<>();
    final ArrayList<String> BooksISBNs = new ArrayList<>();
    final ArrayList<String> BooksNames = new ArrayList<>();

    String UserName;
    boolean WrongDate = false;

    boolean WrongEmail = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        ImageButton scanQRbtn=findViewById(R.id.cmdScan);
        scanQRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IssueBook.this, QRScannerActivity.class);
                startActivity(intent);
            }
        });
        Db = FirebaseDatabase.getInstance().getReference("users");
        Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    UserEmails.add(dataSnapshot1.child("Email").getValue().toString());
                    UserNames.add(dataSnapshot1.child("name").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("LMS", "Before adapter");

        Db2 = FirebaseDatabase.getInstance().getReference("allbooks");
        Db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BooksNames.add(dataSnapshot1.child("bookName").getValue().toString());
                    BooksISBNs.add(dataSnapshot1.child("isbn").getValue().toString());
                    BooksIds.add(dataSnapshot1.child("id").getValue().toString());
                }
                Toast.makeText(getApplicationContext(), "books array length is" + BooksISBNs.size(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    public void IssueConfirmation(View v) {


        final EditText TextISBN = findViewById(R.id.txtISBN);
        final EditText IssueTo = findViewById(R.id.txtIssueTo);
        final CalendarView Return = findViewById(R.id.cdrReturn);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //    awesomeValidation.addValidation(this, R.id.txtISBN, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        awesomeValidation.addValidation(this, R.id.txtIssuedTo, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        //Vaidation for wrong return date
        if (dateFormat.format(date).compareTo(dateFormat.format(Return.getDate())) >= 0) {
            Toast.makeText(IssueBook.this, "Invalid return date!", Toast.LENGTH_SHORT).show();
            WrongDate = true;
        }
        int indexOfEmail = 0;
        for (String e : UserEmails) {
            if (e.equals(IssueTo.getText().toString())) {
                UserName = UserNames.get(indexOfEmail);
                WrongEmail = false;
                break;
            }
            indexOfEmail++;

        }
        if(WrongEmail)
        {
            Toast.makeText(IssueBook.this, "This user is not registered!", Toast.LENGTH_SHORT).show();
        }
        if (/*awesomeValidation.validate() && */!WrongDate && !WrongEmail) {
            AlertDialog.Builder builder = new AlertDialog.Builder(IssueBook.this);
            builder.setTitle(R.string.app_name);
            firebaseAuth = FirebaseAuth.getInstance();
            String j = TextISBN.getText().toString();
            for (int index = 0; index < BooksISBNs.size(); index++)
            {
                if (BooksISBNs.get(index).equals(TextISBN.getText().toString())) {
                    Name = BooksNames.get(index);
                    BookId = BooksIds.get(index);
                }
            }

            builder.setMessage("Book : " + Name + " will be issued to " + UserName);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Db = FirebaseDatabase.getInstance().getReference("IssuedBooks");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String Id = Db.push().getKey();
                    IssuedBooks IssuedBookObj = new IssuedBooks(BookId, Id,
                            IssueTo.getText().toString(),
                            dateFormat.format(date),
                            dateFormat.format(Return.getDate()));
                    Db.child(Id).setValue(IssuedBookObj);
                    Db2 = FirebaseDatabase.getInstance().getReference("allbooks").child(BookId);
                    Db2.child("availability").setValue("Unavailable");


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

