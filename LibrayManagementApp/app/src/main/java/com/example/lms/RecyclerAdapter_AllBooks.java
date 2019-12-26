package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter_AllBooks extends RecyclerView.Adapter<RecyclerAdapter_AllBooks.ViewHolder> {
    Context context;
    ArrayList<AllBooks> arrayList;

    public RecyclerAdapter_AllBooks(Context applicatiocontext, ArrayList<AllBooks> booksArrayList) {
        context = applicatiocontext;
        arrayList = booksArrayList;
    }

//    @NonNull
//    @Override
//    public RecyclerAdapter_Allmembers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }

    @NonNull
    @Override
    public RecyclerAdapter_AllBooks.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View convertView = layoutInflater.inflate(R.layout.customlayout_bookview, parent, false);
        return new ViewHolder(convertView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_AllBooks.ViewHolder holder, int position) {

        holder.bookname.setText(arrayList.get(position).getBookName());
        holder.edition.setText(arrayList.get(position).getEdition());
        holder.bookavailability.setText(arrayList.get(position).getAvailability());
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bookname, bookavailability,edition;
        public RelativeLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.custombooklayout_bookname);
            edition = itemView.findViewById(R.id.custombooklayout_edition);
            bookavailability = itemView.findViewById(R.id.custombooklayout_availablestatus);
            layout = itemView.findViewById(R.id.customlayout_bookviewparent);

        }


    }
}