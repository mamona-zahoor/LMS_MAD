package com.example.lms;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class IssuedBooksList extends ArrayAdapter<IssuedBooks>
{
    private Activity context;
    private List<IssuedBooks> issuedBooksList;
    public IssuedBooksList(Activity context, List<IssuedBooks> issuedBooksList)
    {
        super(context, R.layout.list_layout, issuedBooksList);
        this.context = context;
        this.issuedBooksList = issuedBooksList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView BookName = (TextView) ListViewItem.findViewById(R.id.txtBookName);
        TextView IssuedTo = (TextView) ListViewItem.findViewById(R.id.txtIssuedTo);
        TextView IssuedDate = (TextView) ListViewItem.findViewById(R.id.txtIssuedDate);
        TextView ReturnDate = (TextView) ListViewItem.findViewById(R.id.txtReturnDate);

        IssuedBooks Ib = issuedBooksList.get(position);
        BookName.setText(Ib.getISBN());
        IssuedDate.setText(Ib.getIssuedDate());
        IssuedTo.setText(Ib.getIssueTo());
        ReturnDate.setText(Ib.getReturnDate());

        return ListViewItem;

    }

}

