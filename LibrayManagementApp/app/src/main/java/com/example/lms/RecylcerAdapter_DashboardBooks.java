package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecylcerAdapter_DashboardBooks extends RecyclerView.Adapter<RecylcerAdapter_DashboardBooks.ViewHolder> {
    Context context;
    ArrayList<ImageuploadInfo> arrayList;

    public RecylcerAdapter_DashboardBooks(Context applicatiocontext, ArrayList<ImageuploadInfo> booksArrayList) {
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
    public RecylcerAdapter_DashboardBooks.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View convertView = layoutInflater.inflate(R.layout.customlayout_bookitem_dashboard, parent, false);
        return new RecylcerAdapter_DashboardBooks.ViewHolder(convertView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookname.setText(arrayList.get(position).imageName);
        Picasso.get().load(arrayList.get(position).imageURL)
                .into(holder.bookimage , new Callback() {
                    @Override
                    public void onSuccess() {
                     //   Toast.makeText(context, "ssuccess", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {
                 //       Toast.makeText(context, "ereror image response book view" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                String updatedImageUrl;
//                                if (uri.contains("https")){
//                                    updatedImageUrl = uri.replace("https", "http");
//                                }else{
//                                    updatedImageUrl = uri.replace("http", "https");
//                                }
//                                loadImage(imageView, updatedImageUrl);
                    }
                });
       // holder.bookimage.setImageResource();
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerAdapter_AllBooks.ViewHolder holder, int position) {
//
//        holder.bookname.setText(arrayList.get(position).bookName);
//      //  holder.edition.setText(arrayList.get(position).edition);
//     //   holder.bookavailability.setText(arrayList.get(position).availability);
//    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bookname;//, bookavailability, edition;
        public ImageView bookimage;
        public CardView layout;


        public ViewHolder(View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.customdashboardlayout_bookname);
            bookimage=itemView.findViewById(R.id.customdashboardlayout_img_dashboard);
            //  edition = itemView.findViewById(R.id.custombooklayout_edition);
            // bookavailability = itemView.findViewById(R.id.custombooklayout_availablestatus);
            layout = itemView.findViewById(R.id.dashboardViewItem_parentlayout);

        }


    }
}