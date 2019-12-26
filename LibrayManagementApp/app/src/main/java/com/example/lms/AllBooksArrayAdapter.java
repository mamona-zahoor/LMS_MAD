package com.example.lms;

import android.app.Activity;
import android.app.AlertDialog;
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

import java.util.List;

public class AllBooksArrayAdapter extends ArrayAdapter <AllBooks> {

    private Activity context;
    private List<AllBooks> BookList;
    private List<Authors> Authorlist;


    public AllBooksArrayAdapter(Activity context,List<AllBooks> BookList,List<Authors> Authorlist ){
        super(context, R.layout.allbookcell,BookList);
        this.context = context;
        this.BookList = BookList;
        this.Authorlist = Authorlist;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewitem = inflater.inflate(R.layout.allbookcell,null,true);
        TextView BokkName = listViewitem.findViewById(R.id.txtBookName);
        TextView Status = listViewitem.findViewById(R.id.txtStatus);
        TextView Price = listViewitem.findViewById(R.id.txtPrice);
        TextView Edition = listViewitem.findViewById(R.id.txtEdition);
        TextView Author = listViewitem.findViewById(R.id.txtAuthor);
        TextView ISBN = listViewitem.findViewById(R.id.txtISBN);

        ImageView img = listViewitem.findViewById(R.id.imgbook);
        AllBooks book = BookList.get(position);
        Authors author = Authorlist.get(position);
        String j = book.getBookName();

        BokkName.setText(j);
        Status.setText("Status : "+book.getAvailability());
        Price.setText("Price : "+book.getPrice());
        ISBN.setText("ISBN : "+book.getISBN());
        Edition.setText("Edition : "+book.getEdition());
        // Status.setText(book.getAvailability());
        Author.setText("Author : "+author.getAuthorName());
        return  listViewitem;
    }
}
