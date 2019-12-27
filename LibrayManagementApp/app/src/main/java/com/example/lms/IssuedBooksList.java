package com.example.lms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class IssuedBooksList extends ArrayAdapter<IssuedBooks>
{
    DatabaseReference Db;
    Context context;
    private List<IssuedBooks> issuedBooksList;
    private List<String> BooksNames;
    private List<String> BooksIds;
    public IssuedBooksList(Context context, List<IssuedBooks> issuedBooksList, List<String> BooksNames, List<String> BooksIds)
    {
        super(context, R.layout.list_layout, issuedBooksList);
        this.context = context;
        this.issuedBooksList = issuedBooksList;
        this.BooksNames = BooksNames;
        this.BooksIds = BooksIds;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View ListViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView BookName = (TextView) ListViewItem.findViewById(R.id.txtBookName);
        TextView IssuedTo = (TextView) ListViewItem.findViewById(R.id.txtIssuedTo);
        TextView IssuedDate = (TextView) ListViewItem.findViewById(R.id.txtIssuedDate);
        TextView ReturnDate = (TextView) ListViewItem.findViewById(R.id.txtReturnDate);
        IssuedBooks Ib = issuedBooksList.get(position);
        for(int index = 0; index < BooksIds.size(); index++)
        {
            if(BooksIds.get(index).equals(Ib.getBookId()))
            {
                BookName.setText(BooksNames.get(index));
                break;
            }
        }

        IssuedDate.setText(Ib.getIssuedDate());
        IssuedTo.setText(Ib.getIssueTo());
        ReturnDate.setText(Ib.getReturnDate());

        return ListViewItem;

    }

}

