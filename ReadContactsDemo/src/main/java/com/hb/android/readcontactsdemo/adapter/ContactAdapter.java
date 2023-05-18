package com.hb.android.readcontactsdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hb.android.readcontactsdemo.R;
import com.hb.android.readcontactsdemo.bean.ContactInfo;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private Context context;
    private List<ContactInfo> contactInfoList;

    public ContactAdapter(Context context, List<ContactInfo> contactInfoList) {
        this.context = context;
        this.contactInfoList = contactInfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(contactInfoList.get(position).getContactName());
        holder.tv_phone.setText(contactInfoList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactInfoList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
        }
    }

}