package com.hb.poihandleexcel;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

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
                }
            }
        });

        // 读取
        findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = Environment.getExternalStorageDirectory().getPath();
                String fileResultPath = filePath + File.separator + fileName + ".xlsx";
                List list = ExcelFileUtils.readExcel(MainActivity.this, fileResultPath);
                for (Object mb : list) {
                    Log.w("htest", "mb：" + mb);
                }
            }
        });

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