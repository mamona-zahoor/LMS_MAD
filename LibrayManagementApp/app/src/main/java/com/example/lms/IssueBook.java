package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueBook extends AppCompatActivity {
    DatabaseReference Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);


    }
    public void Next(View v)
    {
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
        Toast.makeText(IssueBook.this, "Issued", Toast.LENGTH_LONG).show();


    }
}

