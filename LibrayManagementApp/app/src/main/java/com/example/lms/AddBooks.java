package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBooks extends AppCompatActivity {

    EditText BookName;
    EditText ISBN;
    Spinner BookStatus;
    EditText Price;
    EditText Edition;

    Button btn;

    DatabaseReference dbreff;


    public static final String BOOKID = "com.example.lms.bookId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        BookName = findViewById(R.id.txtbookname);
        ISBN = findViewById(R.id.txtisbn);
        //BookStatus = findViewById(R.id.availability);
        btn = (Button)findViewById(R.id.btnsave);
        Price = findViewById(R.id.txtPrice);
        Edition = findViewById(R.id.txtEdition);






        dbreff = FirebaseDatabase.getInstance().getReference("allbooks");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        String name = BookName.getText().toString();
                        String number = ISBN.getText().toString();
                        // String status = BookStatus.getSelectedItem().toString();
                        String price = Price.getText().toString();
                        String edition = Edition.getText().toString();

                        String id = dbreff.push().getKey();
                        if(name == "")
                        {
                            Toast.makeText(AddBooks.this, "Please Enter Book Name", Toast.LENGTH_SHORT).show();

                        }
                        else if(number == "")
                        {
                            Toast.makeText(AddBooks.this, "Please Enter Book Number", Toast.LENGTH_SHORT).show();
                        }
                        else if(price == "")
                        {
                            Toast.makeText(AddBooks.this, "Please Enter Book Price", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            AllBooks book = new AllBooks(name,number,price,edition,id,"Available");

                            dbreff.child(id).setValue(book);
                            Toast.makeText(AddBooks.this, "Saved", Toast.LENGTH_LONG).show();
                        }


                }
                catch (Exception e)
                {

                }




            }
        });



    }
}
