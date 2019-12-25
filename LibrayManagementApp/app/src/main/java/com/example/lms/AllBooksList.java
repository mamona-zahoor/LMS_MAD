package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllBooksList extends AppCompatActivity {

    ListView lst;
    ArrayList<AllBooks>BookList;
    ArrayList<Authors> authorList;

    DatabaseReference booksreff,authorreff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books_list);

        lst = findViewById(R.id.lstbooks);
        BookList = new ArrayList<>();

        booksreff = FirebaseDatabase.getInstance().getReference("allbooks");
        authorreff = FirebaseDatabase.getInstance().getReference("Authors");



    }


    @Override
    protected void onStart() {
        super.onStart();
        booksreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot bookSnap: dataSnapshot.getChildren())
                {
                    Log.d("msg","inside");
                    // authorList.clear();
                    BookList.clear();
                    AllBooks Book = bookSnap.getValue(AllBooks.class);
                    // Authors auth = bookSnap.getValue(Authors.class);
                    BookList.add(Book);
                    //  authorList.add(auth);
                }

                AllBooksArrayAdapter adapter = new AllBooksArrayAdapter(AllBooksList.this,BookList,authorList);
                lst.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllBooksList.this, "Please Enter Book Number", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
