package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
    DatabaseReference Db;
    ListView listViewIssued;
    List<IssuedBooks> IssuedBooksList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_issued_books);
        listViewIssued = (ListView) findViewById(R.id.listViewIssued);
        IssuedBooksList = new ArrayList<>();
        Db = FirebaseDatabase.getInstance().getReference("Issuedbooks");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    IssuedBooks Ib = new IssuedBooks();
                    Ib = dataSnapshot1.getValue(IssuedBooks.class);
                    IssuedBooksList.add(Ib);

                }
                //  Toast.makeText(getContext(), "array length is" + arrayList.size(), Toast.LENGTH_SHORT).show();
                //  IssuedBooksList.otifyDataSetChanged();
            }

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
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
