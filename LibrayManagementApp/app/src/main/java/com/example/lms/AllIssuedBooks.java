package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllIssuedBooks extends AppCompatActivity {
    DatabaseReference Db, Db2;
    ListView listViewIssued;
    List<IssuedBooks> IssuedBooksList;
    final ArrayList<String> BooksIds = new ArrayList<>();
    final ArrayList<String> BooksNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_issued_books);
        listViewIssued = (ListView) findViewById(R.id.listViewIssued);
        IssuedBooksList = new ArrayList<>();
        Db = FirebaseDatabase.getInstance().getReference("IssuedBooks");
        Db2 = FirebaseDatabase.getInstance().getReference("allbooks");

        Log.d("TAG", "Huhhh");
        Db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BooksNames.add(dataSnapshot1.child("bookName").getValue().toString());
                    BooksIds.add(dataSnapshot1.child("id").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        IssuedBooksList.clear();
        Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    IssuedBooks Ib = new IssuedBooks();
                    Ib = dataSnapshot1.getValue(IssuedBooks.class);
                    IssuedBooksList.add(Ib);
                }
                Toast.makeText(getApplicationContext(), "array length is" + IssuedBooksList.size(), Toast.LENGTH_SHORT).show();
                IssuedBooksList adapter = new IssuedBooksList(AllIssuedBooks.this, IssuedBooksList, BooksNames, BooksIds);
                listViewIssued.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void IssueBook(View v)
    {
        Intent I = new Intent(AllIssuedBooks.this, IssueBook.class);
        startActivity(I);
    }
    public void ReturnBook(View v)
    {
        Intent I = new Intent(AllIssuedBooks.this, ReturnBook.class);
        startActivity(I);
    }
    @Override
    protected void onStart() {
        super.onStart();

            /*   Db.addValueEventListener((new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       IssuedBooksList.clear();
                       for (DataSnapshot data : dataSnapshot.getChildren()) {
                           IssuedBooks Ib = data.getValue(IssuedBooks.class);
                           IssuedBooksList.add(Ib);

                       }
                       IssuedBooksList adapter = new IssuedBooksList(AllIssuedBooks.this, IssuedBooksList);
                       listViewIssued.setAdapter(adapter);
                   }
       */

    }
}
