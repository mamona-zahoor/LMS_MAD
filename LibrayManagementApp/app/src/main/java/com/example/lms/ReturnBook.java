package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReturnBook extends AppCompatActivity {
    DatabaseReference Db, Db2;
    String BookId, BookName;
    final ArrayList<String> UserNames = new ArrayList<>();
    final ArrayList<String> BooksIds = new ArrayList<>();
    final ArrayList<String> BooksISBNs = new ArrayList<>();
    final ArrayList<String> BooksNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);
        Db = FirebaseDatabase.getInstance().getReference("users");
        Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
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

    public void ReturnBook(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReturnBook.this);
        builder.setTitle(R.string.app_name);
        EditText ISBNRet = (EditText) findViewById(R.id.txtISBNToReturn);
        for(int index = 0 ; index < BooksIds.size(); index++)
        {
            if(BooksISBNs.get(index).equals(ISBNRet.getText().toString())) {
                BookId = BooksIds.get(index);
                BookName = BooksNames.get(index);
                break;
            }

        }
        Db = FirebaseDatabase.getInstance().getReference("IssuedBooks");
        builder.setMessage("Do you want to return book : "+ BookName);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            Db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if(dataSnapshot1.child("bookId").getValue().equals(BookId))
                            {
                                Db.child(dataSnapshot1.child("id").getValue().toString()).removeValue();
                                Toast.makeText(getApplicationContext(), "Book Returned!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            }
                        }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
        });
        Db2 = FirebaseDatabase.getInstance().getReference("allbooks").child(BookId);
        Db2.child("availability").setValue("Available");

        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();



        }
    }



