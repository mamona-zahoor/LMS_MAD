package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAllBooks extends AppCompatActivity {
    boolean check;
    EditText BookName;
    EditText ISBN;
    EditText author;
    Spinner BookStatus;
    EditText Price;
    EditText Edition;
    Uri imguri;
    String imgid;
    Button btn,btndelete;
    ImageButton Image;
    DatabaseReference dbreff,AuthorsRef,imagesreff;

    String bookid,authorid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        check = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_all_books);
        dbreff = FirebaseDatabase.getInstance().getReference("allbooks");
        // ImageRef = FirebaseDatabase.getInstance().getReference("Images");
        AuthorsRef = FirebaseDatabase.getInstance().getReference("Authors");
        BookName = findViewById(R.id.txtbookname);
        ISBN = findViewById(R.id.txtisbn);
        BookStatus = findViewById(R.id.availability);
        btn = (Button)findViewById(R.id.btnUpdate);
        Price = findViewById(R.id.txtPrice);
        Edition = findViewById(R.id.txtEdition);
        //  btndelete = findViewById(R.id.btnDelete);
        author = findViewById(R.id.txtAuthor);


        bookid = getIntent().getStringExtra("key");
        authorid = getIntent().getStringExtra("authorid");
        BookName.setText(getIntent().getStringExtra("name"));
        ISBN.setText(getIntent().getStringExtra("isbn"));
        Price.setText(getIntent().getStringExtra("price"));
        Edition.setText(getIntent().getStringExtra("Edition"));
        author.setText(getIntent().getStringExtra("author"));
        // BookStatus.(getIntent().getStringExtra("name"));
        BookStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                check = false;
                Toast.makeText(UpdateAllBooks.this,"Select Status first",Toast.LENGTH_LONG).show();
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BookStatus.getSelectedItem().toString().trim() == "Status")
                {
                    Toast.makeText(UpdateAllBooks.this,"Select Status first",Toast.LENGTH_LONG).show();
                }
                else if(check == true)
                {
                    AllBooks book = new AllBooks();
                    Authors auth = new Authors();
                    book.setBookName(BookName.getText().toString());
                    book.setEdition(Edition.getText().toString());
                    book.setISBN(ISBN.getText().toString());
                    book.setPrice(Price.getText().toString());
                    auth.setAuthorName(author.getText().toString());
                    book.setAvailability(BookStatus.getSelectedItem().toString());
                    book.setId(bookid);
                    auth.setBookId(bookid);
                    auth.setAuthorId(authorid);

                    dbreff.child(bookid).setValue(book);
                    AuthorsRef.child(authorid).setValue(auth);
                    Toast.makeText(UpdateAllBooks.this,"Updated Successfully",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(UpdateAllBooks.this,AllBooksList.class);
                    UpdateAllBooks.this.startActivity(intent);

                }



            }
        });


    }



}
