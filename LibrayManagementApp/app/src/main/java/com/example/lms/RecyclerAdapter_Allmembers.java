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

public class RecyclerAdapter_Allmembers extends RecyclerView.Adapter<RecyclerAdapter_Allmembers.ViewHolder> {
    Context context;
    ArrayList<LoginUser_ModelClass> arrayList;

    public RecyclerAdapter_Allmembers(Context applicatiocontext, ArrayList<LoginUser_ModelClass> userArrayList) {
        context = applicatiocontext;
        arrayList = userArrayList;
    }

//    @NonNull
//    @Override
//    public RecyclerAdapter_Allmembers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View convertView = layoutInflater.inflate(R.layout.customlayout_memberitem, parent, false);
        return new ViewHolder(convertView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_Allmembers.ViewHolder holder, int position) {

        holder.membername.setText(arrayList.get(position).name);
        holder.memberrole.setText(arrayList.get(position).userrole);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView membername, memberrole;
        public RelativeLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            membername = itemView.findViewById(R.id.customlayout_membername);
            memberrole = itemView.findViewById(R.id.customlayout_memberrole);
            layout = itemView.findViewById(R.id.allmembers_recyclerview);

        }


    }
}
