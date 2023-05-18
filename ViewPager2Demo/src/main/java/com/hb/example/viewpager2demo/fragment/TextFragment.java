package com.hb.example.viewpager2demo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hb.example.viewpager2demo.R;

public class TextFragment extends Fragment {

    String title;

    public static TextFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        TextFragment fragment = new TextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 向Fragment传递数据方法一，接受数据
        if (getArguments() != null) {
            this.title = getArguments().getString("title");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_text, container, false);
        TextView textView = view.findViewById(R.id.i_text);
        textView.setText(title);
        textView.setBackgroundColor(Color.parseColor("#30ff0000"));
        return view;
    }

}
