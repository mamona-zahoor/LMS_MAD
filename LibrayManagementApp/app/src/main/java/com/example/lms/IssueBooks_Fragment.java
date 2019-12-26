package com.example.lms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueBooks_Fragment extends Fragment {
    DatabaseReference Db;
    public IssueBooks_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view=inflater.inflate(R.layout.activity_issue_book,container,false);
       Button nextButton=(Button) view.findViewById(R.id.cmdNext);
       nextButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Next(view);
           }
       });

       return view;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void Next(View view)
    {
        EditText ISBN = view.findViewById(R.id.txtISBN);
        EditText IssueTo = view.findViewById(R.id.txtIssueTo);
        CalendarView Return = view.findViewById(R.id.cdrReturn);
        Db = FirebaseDatabase.getInstance().getReference("IssuedBooks");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String Id = Db.push().getKey();
        IssuedBooks IssuedBookObj = new IssuedBooks(ISBN.getText().toString(), Id,
                IssueTo.getText().toString(),
                dateFormat.format(date),
                dateFormat.format(Return.getDate()));
        Db.child(Id).setValue(IssuedBookObj);
        Toast.makeText(getContext(), "Issued", Toast.LENGTH_LONG).show();


    }
}
