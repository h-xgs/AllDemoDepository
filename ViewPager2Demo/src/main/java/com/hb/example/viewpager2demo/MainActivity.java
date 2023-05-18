package com.hb.example.viewpager2demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;

import com.hb.example.viewpager2demo.adapter.MyFragmentAdapter;
import com.hb.example.viewpager2demo.adapter.MyViewAdapter;
import com.hb.example.viewpager2demo.fragment.TextFragment;
import com.hb.example.viewpager2demo.fragment.VideoFragment;
import com.hb.example.viewpager2demo.utils.VideoUriStringUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 page = findViewById(R.id.page);
        page.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        List<Fragment> fragments = new ArrayList<>();
        for (String uri : VideoUriStringUtil.getVideoUriStringsData()) {
            fragments.add(VideoFragment.newInstance(uri));
        }
        page.setAdapter(new MyFragmentAdapter(this, fragments));
    }

}