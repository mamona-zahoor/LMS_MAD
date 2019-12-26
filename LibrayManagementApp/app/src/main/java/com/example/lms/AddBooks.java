package com.example.lms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AddBooks extends AppCompatActivity {

    EditText BookName;
    EditText ISBN;
    EditText author;
    Spinner BookStatus;
    EditText Price;
    EditText Edition;
    Uri imguri;
    String imgid;
    Button btn;
    ImageButton Image;


    //  ProgressDialog Pg;
    ImageView bookpic, QRimagePreview;


    DatabaseReference dbreff, AuthorsRef;
    DatabaseReference ImageRef;
    private static final int CAME_REQUEST = 1;
    private StorageReference sr;
    boolean check = true;

    public static final String BOOKID = "com.example.lms.bookId";
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        BookName = findViewById(R.id.txtbookname);
        ISBN = findViewById(R.id.txtisbn);
        //BookStatus = findViewById(R.id.availability);
        btn = (Button) findViewById(R.id.btnsave);
        Price = findViewById(R.id.txtPrice);
        Edition = findViewById(R.id.txtEdition);
        Image = findViewById(R.id.camera);
        author = findViewById(R.id.txtAuthor);
        //   Pg = new ProgressDialog(AddBooks.this);
        QRimagePreview = findViewById(R.id.qrimagepreview);
        bookpic = findViewById(R.id.imgbook);
        String regex = "^[a-zA-Z0-9]+$";
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txtbookname, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        awesomeValidation.addValidation(this, R.id.txtisbn, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        awesomeValidation.addValidation(this, R.id.txtAuthor, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        awesomeValidation.addValidation(this, R.id.txtPrice, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        awesomeValidation.addValidation(this, R.id.txtEdition, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror);
        QRimagePreview = findViewById(R.id.qrimagepreview);
Bitmap bitmap;

        dbreff = FirebaseDatabase.getInstance().getReference("allbooks");
        ImageRef = FirebaseDatabase.getInstance().getReference("Images");
        AuthorsRef = FirebaseDatabase.getInstance().getReference("Authors");
        sr = FirebaseStorage.getInstance().getReference();
//        if (awesomeValidation.validate()) {
//            QRCodeWriter qrCodeWriter=new QRCodeWriter();
//            try {
//                BitMatrix bitMatrix=qrCodeWriter.encode(ISBN.getText().toString(),BarcodeFormat.QR_CODE,200,200);
//                bitmap=Bitmap.createBitmap(200,200,Bitmap.Config.RGB_565);
//                for(int x=0;x<200;x++){
//                    for(int y=0;y<200;y++){
//                        bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK:Color.WHITE);
//                    }
//                }
//                QRimagePreview.setImageBitmap(bitmap);
//            } catch (WriterException e) {
//                e.printStackTrace();
//            }
            Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(intent.ACTION_GET_CONTENT);

                    startActivityForResult(intent, CAME_REQUEST);


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
                        if (name == "") {
                            Toast.makeText(AddBooks.this, "Please Enter Book Name", Toast.LENGTH_SHORT).show();

                        } else if (number == "") {
                            Toast.makeText(AddBooks.this, "Please Enter Book Number", Toast.LENGTH_SHORT).show();
                        } else if (price == "") {
                            Toast.makeText(AddBooks.this, "Please Enter Book Price", Toast.LENGTH_SHORT).show();
                        } else {
                            UploadImage(name, id);
                            if (check == true) {
                                AllBooks book = new AllBooks(name, number, price, edition, id, "Available", imgid);
                                Authors a = new Authors(authorid, id, Author);

                                dbreff.child(id).setValue(book);
                                AuthorsRef.child(authorid).setValue(a);
                                Toast.makeText(AddBooks.this, "Saved.. wait Image is Uploading", Toast.LENGTH_LONG).show();
                            }

                        }


                    } catch (Exception e) {
                        Toast.makeText(AddBooks.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
    //    }

    }


  /*  @Override
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



   */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAME_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imguri = data.getData();

         //   Picasso.with(this).load(imguri).into(bookpic);
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadImage(final String s, final String bookId) {
        check = true;

        if (imguri != null) {

            //  Pg.setTitle("Image is Uploading...");
            //  Pg.show();

            //generate QR of ISBN and store image in firebase
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Bitmap bitmap;
            try {
                BitMatrix bitMatrix = qrCodeWriter.encode(ISBN.getText().toString(), BarcodeFormat.QR_CODE, 200, 200);
                bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
                for (int x = 0; x < 200; x++) {
                    for (int y = 0; y < 200; y++) {
                        bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                QRimagePreview.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            ///////////////////////////////////////////////
            StorageReference storageReference2 = sr.child(System.currentTimeMillis() + "." + GetFileExtension(imguri));
            storageReference2.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // String TempImageName = txtdata.getText().toString().trim();

                            Toast.makeText(AddBooks.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                            //  Pg.dismiss();


                            //   Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            imgid = ImageRef.push().getKey();

                            @SuppressWarnings("VisibleForTests")
                            ImageuploadInfo imageUploadInfo = new ImageuploadInfo(s, taskSnapshot.getStorage().getDownloadUrl().toString(), bookId);
                            imgid = ImageRef.push().getKey();
                            ImageRef.child(imgid).setValue(imageUploadInfo);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBooks.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            // Pg.setProgress((int) progress);
                        }
                    });
        } else {

            Toast.makeText(AddBooks.this, "Please Select Image", Toast.LENGTH_LONG).show();
            check = false;

        }
    }


}
