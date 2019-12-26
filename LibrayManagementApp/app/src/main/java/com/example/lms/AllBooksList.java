package com.example.lms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllBooksList extends AppCompatActivity {

    ListView lst;
    ArrayList<AllBooks>BookList;
    ArrayList<Authors> authorList;
    ArrayList<String> keys;

    DatabaseReference booksreff,authorreff;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books_list);


        lst = findViewById(R.id.lstbooks);
        BookList = new ArrayList<>();
        authorList = new ArrayList<>();
        keys = new ArrayList<>();


        booksreff = FirebaseDatabase.getInstance().getReference("allbooks");
        authorreff = FirebaseDatabase.getInstance().getReference("Authors");


        booksreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    AllBooks book = new AllBooks();
                    book = dataSnapshot1.getValue(AllBooks.class);

                    BookList.add(book);

                }

                authorreff.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            Authors auth = new Authors();
                            auth = dataSnapshot1.getValue(Authors.class);


                            authorList.add(auth);
                        }
                        AllBooksArrayAdapter adapter = new AllBooksArrayAdapter(AllBooksList.this, BookList,authorList);
                        lst.setAdapter(adapter);

                    }




                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
