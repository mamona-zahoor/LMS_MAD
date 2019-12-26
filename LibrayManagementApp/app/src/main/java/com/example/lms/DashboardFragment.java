package com.example.lms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference dbreference;
    ArrayList<ImageuploadInfo> allBooksArrayList = new ArrayList<>();

    public DashboardFragment() {
        database = FirebaseDatabase.getInstance();
        dbreference = database.getReference("Images");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        try {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.viewallbooks_recyclerview_dashboard);
            //  recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            //  recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            final RecylcerAdapter_DashboardBooks recyclerAdapter_allBooks = new RecylcerAdapter_DashboardBooks(getContext(), allBooksArrayList);
            recyclerView.setAdapter(recyclerAdapter_allBooks);
            dbreference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ImageuploadInfo allBooks = dataSnapshot1.getValue(ImageuploadInfo.class);
                        allBooksArrayList.add(allBooks);
                    }
                    Toast.makeText(getContext(), "data is" + allBooksArrayList.get(0).imageURL, Toast.LENGTH_SHORT).show();
                    recyclerAdapter_allBooks.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), "ereror book view" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
