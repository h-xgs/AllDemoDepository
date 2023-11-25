package com.hb.customview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv_one = this.findViewById(R.id.lv_one);
        ListView lv_two = this.findViewById(R.id.lv_two);
        String[] strs1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
                "12", "13", "14", "15"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1, strs1);
        lv_one.setAdapter(adapter1);
        String[] strs2 = {"A", "B", "C", "D", "E", "E", "G", "H", "I", "J", "K", "L",
                "M", "N", "O"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1, strs2);
        lv_two.setAdapter(adapter2);
    }
}