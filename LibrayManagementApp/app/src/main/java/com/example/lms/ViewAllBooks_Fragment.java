package com.example.lms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllBooks_Fragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference dbreference;
    ArrayList<AllBooks> allBooksArrayList = new ArrayList<>();

    public ViewAllBooks_Fragment() {
        database = FirebaseDatabase.getInstance();
        dbreference = database.getReference("allbooks");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewbooks_fragment, container, false);
//        try {
//            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.viewallbooks_recyclerview);
//            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//            recyclerView.setHasFixedSize(true);
//
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            // recyclerAdapter_viewContact = new RecyclerAdapter_ViewContact(getContext(), nameList,emailList,phoneList);
//            final RecyclerAdapter_AllBooks recyclerAdapter_allBooks = new RecyclerAdapter_AllBooks(getContext(), allBooksArrayList);
//            recyclerView.setAdapter(recyclerAdapter_allBooks);
//
//            dbreference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        AllBooks allBooks = dataSnapshot1.getValue(AllBooks.class);
//                        allBooksArrayList.add(allBooks);
//
//                    }
//                    recyclerAdapter_allBooks.notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        } catch (Exception ex) {
//            Toast.makeText(getContext(), "ereror book view" + ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
        // recyclerAdapter_viewContact = new RecyclerAdapter_ViewContact(getContext(), nameList,emailList,phoneList);
        //  arrayList = new ArrayList<>();
        // recyclerAdapter_allmembers = new RecyclerAdapter_Allmembers(getContext(), arrayList);
        // recyclerView.setAdapter(recyclerAdapter_allmembers);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
