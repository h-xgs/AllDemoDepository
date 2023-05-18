package com.hb.example.viewpager2demo.utils;

import com.hb.example.viewpager2demo.R;

import java.util.ArrayList;
import java.util.List;

public class VideoUriStringUtil {

    public static List<String> getVideoUriStringsData() {
        ArrayList<String> stringDataList = new ArrayList<>();
        stringDataList.add("android.resource://com.hb.example.viewpager2demo/" + R.raw.knc_simplelife);
        stringDataList.add("android.resource://com.hb.example.viewpager2demo/" + R.raw.knc_simplelife);

        return stringDataList;
    }

}
