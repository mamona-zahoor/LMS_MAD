package com.example.lms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllBooksArrayAdapter extends ArrayAdapter <AllBooks> {

    private Activity context;
    private List<AllBooks> BookList;
    private List<Authors> Authorlist;
    private List<ImageuploadInfo>Images;
    String s ="";
    DatabaseReference dbreff,authreff,ImageRef;



    public AllBooksArrayAdapter(Activity context,List<AllBooks> BookList,List<Authors> Authorlist,List<ImageuploadInfo>imageuploadInfoList ){
        super(context, R.layout.allbookcell,BookList);
        this.context = context;
        this.BookList = BookList;
        this.Authorlist = Authorlist;
        Images = imageuploadInfoList;
        //  s = S;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        dbreff = FirebaseDatabase.getInstance().getReference("allbooks");
        ImageRef = FirebaseDatabase.getInstance().getReference("Images");
        authreff = FirebaseDatabase.getInstance().getReference("Authors");
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewitem = inflater.inflate(R.layout.allbookcell,null,true);
        final TextView BokkName = listViewitem.findViewById(R.id.txtBookName);
        TextView Status = listViewitem.findViewById(R.id.txtStatus);
        TextView Price = listViewitem.findViewById(R.id.txtPrice);
        TextView Edition = listViewitem.findViewById(R.id.txtEdition);
        TextView Author = listViewitem.findViewById(R.id.txtAuthor);
        TextView ISBN = listViewitem.findViewById(R.id.txtISBN);
        Button btnUpload = listViewitem.findViewById(R.id.btnUpdate);

        Button btnDelete = listViewitem.findViewById(R.id.btnDlt);

        ImageView img = listViewitem.findViewById(R.id.imageView);
        final AllBooks book = BookList.get(position);
        final Authors author = Authorlist.get(position);
//        ImageuploadInfo imgObj = Images.get(position);
        String j = book.getBookName();

        BokkName.setText(j);
        Status.setText("Status : "+book.getAvailability());
        Price.setText("Price : "+book.getPrice());
        ISBN.setText("ISBN : "+book.getISBN());
        Edition.setText("Edition : "+book.getEdition());
        // String URL = imgObj.getImageURL();
        //img.setImageURI(URL);

        //Picasso.with(this.context).load(URL).into(img);

        // Status.setText(book.getAvailability());
        Author.setText("Author : "+author.getAuthorName());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbreff.child(book.getId()).setValue(null);
                authreff.child(author.getAuthorId()).setValue(null);
                ImageRef.orderByChild("bookId").equalTo(book.getId().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            s = datas.child("bookId").getKey().toString();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

                //ImageRef.orderByChild("bookId").equalTo(book.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                //  String d = ImageRef.child("bookId").getKey();




                ImageRef.child(s).setValue(null);
                //  Toast.makeText(context,"")

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateAllBooks.class);
                intent.putExtra("key",book.getId());
                intent.putExtra("name",book.getBookName());
                intent.putExtra("isbn",book.getISBN());
                intent.putExtra("price",book.getPrice());
                intent.putExtra("status",book.getAvailability());
                intent.putExtra("Edition",book.getEdition());
                intent.putExtra("authorid",author.getAuthorId());
                intent.putExtra("author",author.getAuthorName());

                context.startActivity(intent);
            }
        });




        return  listViewitem;
    }
}
