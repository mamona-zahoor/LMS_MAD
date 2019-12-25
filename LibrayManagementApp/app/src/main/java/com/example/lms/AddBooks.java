package com.example.lms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddBooks extends AppCompatActivity {

    EditText BookName;
    EditText ISBN;
    EditText author;
    Spinner BookStatus;
    EditText Price;
    EditText Edition;
    Uri imguri;

    Button btn;
    ImageButton Image;


    DatabaseReference dbreff,AuthorsRef;
    private static final int CAME_REQUEST = 1;
    private StorageReference sr;

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
        Image = findViewById(R.id.camera);
        author = findViewById(R.id.txtAuthor);





        dbreff = FirebaseDatabase.getInstance().getReference("allbooks");
        AuthorsRef = FirebaseDatabase.getInstance().getReference("Authors");
        sr = FirebaseStorage.getInstance().getReference();

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // intent.setType("image/*");

                startActivityForResult(intent.createChooser(intent,"SElect Picture"),CAME_REQUEST);



            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        String name = BookName.getText().toString();
                        String number = ISBN.getText().toString();
                        // String status = BookStatus.getSelectedItem().toString();
                        String price = Price.getText().toString();
                        String edition = Edition.getText().toString();
                        String Author = author.getText().toString();


                        String id = dbreff.push().getKey();
                        String authorid = AuthorsRef.push().getKey();
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
                            AllBooks book = new AllBooks(name,number,price,edition,id,"Available",imguri);
                            Authors a = new Authors(authorid,id,Author);

                            dbreff.child(id).setValue(book);
                            AuthorsRef.child(authorid).setValue(a);
                            Toast.makeText(AddBooks.this, "Saved", Toast.LENGTH_LONG).show();
                        }


                }
                catch (Exception e)
                {
                    Toast.makeText(AddBooks.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }




            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAME_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
                imguri = data.getData();
                StorageReference filepath = sr.child("Photos").child(imguri.getLastPathSegment());
                filepath.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddBooks.this,"Uploading finished",Toast.LENGTH_LONG).show();
                    }
                });

        }



    }



    private String getExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap m = MimeTypeMap.getSingleton();
        return m.getExtensionFromMimeType(cr.getType(uri));

    }




}
