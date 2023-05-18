package com.hb.android.videodemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * 请求码
     */
    private static final int CHOOSE_VIDEO = 2;
    /**
     * 视频路径的 uri
     */
    private Uri videoUri = null;
    /**
     * 是否循环播放
     */
    private CheckBox checkBoxIsLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxIsLoop = findViewById(R.id.cb_is_loop);
        findViewById(R.id.play_default_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开始播放内置的视频
                playDefaultVideo();
            }
        });

        findViewById(R.id.play_default_video2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开始播放内置的视频（使用VideoActivity2）
                String uri = "android.resource://" + getPackageName() + "/" + R.raw.knc_simplelife;
                videoUri = Uri.parse(uri);
                Intent it = new Intent(MainActivity.this, VideoActivity2.class);
                it.putExtra("path", videoUri);
                it.putExtra("loop", checkBoxIsLoop.isChecked());
                try {
                    startActivity(it);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.play_select_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选择视频
                selectVideo();
            }
        });
    }

    /**
     * 播放默认内置的视频
     */
    private void playDefaultVideo() {
        // 加载此APP的res文件夹的raw里面内置的视频的 uri
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.knc_simplelife;
        videoUri = Uri.parse(uri);

        Intent it = new Intent(MainActivity.this, VideoActivity.class);
        it.putExtra("path", videoUri);
        it.putExtra("loop", checkBoxIsLoop.isChecked());
        try {
            startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从相册选择的视频
     */
    private void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        // 打开相册选择视频
        startActivityForResult(intent, CHOOSE_VIDEO);
    }

    /**
     * 开始播放选择的视频
     */
    private void playSelectVideo() {
        Intent it = new Intent(MainActivity.this, VideoActivity.class);
        it.putExtra("path", videoUri);
        it.putExtra("loop", checkBoxIsLoop.isChecked());
        try {
            startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_VIDEO:
                if (resultCode == RESULT_OK) {
                    videoUri = data.getData();
                    playSelectVideo();
                } else {
                    Toast.makeText(this, "未选择视频", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}
