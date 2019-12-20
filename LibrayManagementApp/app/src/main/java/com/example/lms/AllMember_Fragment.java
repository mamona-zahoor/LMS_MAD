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

public class AllMember_Fragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerAdapter_Allmembers recyclerAdapter_allmembers;
    ArrayList<LoginUser_ModelClass> arrayList;

    public AllMember_Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_allmembers, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.allmembers_recyclerview);


        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerAdapter_viewContact = new RecyclerAdapter_ViewContact(getContext(), nameList,emailList,phoneList);
        arrayList = new ArrayList<>();
        recyclerAdapter_allmembers = new RecyclerAdapter_Allmembers(getContext(), arrayList);
        recyclerView.setAdapter(recyclerAdapter_allmembers);
//
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (!dataSnapshot1.child("userrole").getValue().equals("admin")) {
                        LoginUser_ModelClass loginUser_modelClass = new LoginUser_ModelClass();
                        loginUser_modelClass = dataSnapshot1.getValue(LoginUser_ModelClass.class);
                        arrayList.add(loginUser_modelClass);
                    }
                }
                Toast.makeText(getContext(), "array length is" + arrayList.size(), Toast.LENGTH_SHORT).show();
                recyclerAdapter_allmembers.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }
}
