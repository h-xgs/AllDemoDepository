package com.hb.poihandleexcel;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView rt = findViewById(R.id.result);
        fileName = getResources().getString(R.string.app_name) + "TestData";

        // 下载
        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionsUtil.checkAndApplyPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                    List<MyBean> list = new ArrayList<>();
                    list.add(new MyBean("1", "a1", ExcelFileUtils.getNowTime()));
                    list.add(new MyBean("2", "a2", ExcelFileUtils.getNowTime()));
                    list.add(new MyBean("3", "a3", ExcelFileUtils.getNowTime()));
                    list.add(new MyBean("4", "a4", ExcelFileUtils.getNowTime()));
                    list.add(new MyBean("5", "a5", ExcelFileUtils.getNowTime()));
                    ExcelFileUtils.download(MainActivity.this, list, fileName);
                    rt.setText("");
                }
            }
        });

        // 读取
        findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = Environment.getExternalStorageDirectory().getPath();
                String fileResultPath = filePath + File.separator + fileName + ".xlsx";
                List list = ExcelFileUtils.readExcel(fileResultPath);
                if (list.size() == 0) {
                    rt.setText("没有数据");
                    return;
                }
                StringBuilder s = new StringBuilder();
                for (Object mb : list) {
                    Log.w("htest", "mb：" + mb);
                    s.append(mb).append("\n");
                    rt.setText(s);
                }
            }
        });


        String i ="_iD inTegER prImArY KeY autoInCREmeNT NOt NulL。";
        Log.w("htest MainActivity", "onCreate: " + TextUtils.toCamelCaseNoSpace(i));
        Log.w("htest MainActivity", "onCreate: " + TextUtils.toCamelCase(i));
        Log.w("htest MainActivity", "onCreate: " + TextUtils.toUpperCase(i));
        Log.w("htest MainActivity", "onCreate: " + TextUtils.toLowerCase(i));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionsUtil.checkAuthorized(this, permissions, grantResults)) {
            List<MyBean> list = new ArrayList<>();
            list.add(new MyBean("1", "a1", ExcelFileUtils.getNowTime()));
            list.add(new MyBean("2", "a2", ExcelFileUtils.getNowTime()));
            list.add(new MyBean("3", "a3", ExcelFileUtils.getNowTime()));
            list.add(new MyBean("4", "a4", ExcelFileUtils.getNowTime()));
            list.add(new MyBean("5", "a5", ExcelFileUtils.getNowTime()));
            ExcelFileUtils.download(MainActivity.this, list, fileName);
        }
    }
}