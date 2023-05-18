package com.hb.example.viewpager2demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hb.example.viewpager2demo.R;

import java.util.List;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.ViewPagerHolder> {

    List<Integer> datas;

    public MyViewAdapter(List<Integer> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new ViewPagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerHolder holder, int position) {
        holder.textView.setText("页面" + position);
        holder.textView.setBackgroundColor(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewPagerHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewPagerHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.i_text);
        }
    }

}
