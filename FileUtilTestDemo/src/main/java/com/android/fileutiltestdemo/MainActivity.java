package com.android.fileutiltestdemo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.fileutiltestdemo.Utils.FileUtil;
import com.android.fileutiltestdemo.Utils.PermissionsUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity测试";
    private static final int REQUEST_CODE_PICK = 1;

    // 文件名
    private String fileName1 = "图片1.png";
    private String fileName2 = "图片2.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 申请权限
        PermissionsUtil.checkAndApplyPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});

        // 从data目录下面读取文件
        InputStream is = null;
        try {
            is = openFileInput(fileName1);
            // 把输入流解析为 Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            // 显示图片到 ImageView
            ((ImageView) findViewById(R.id.v_image)).setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        findViewById(R.id.btn_choose_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_PICK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "onActivityResult not ok.");
            return;
        }
        if (requestCode == REQUEST_CODE_PICK) {
            // 获取选取返回的图片资源, Uri格式
            Uri uri;
            if (data != null) {
                uri = data.getData();
                Log.w(TAG, "选取的图片的uri = " + uri);
                // ((ImageView) findViewById(R.id.v_image)).setImageURI(uri);

                // 通过Uri把选择的文件复制到data目录下去
                // FileUtil.copyToAppFilesByUri(this, uri, fileName1);
                // 查询图片的详细信息，没有需求可以忽略
                FileUtil.queryFileDetailFromUri(this, uri);
                // 通过文件的Uri获得它的真实路径
                String path = FileUtil.getFilePathByUri(this, uri);
                Log.w(TAG, "选取的图片的path = " + path);

                // 通过Path把选择的文件复制到data目录下去
                // FileUtil.copyToAppFilesByPath(this, path, fileName2);
            }
        }
    }

}
