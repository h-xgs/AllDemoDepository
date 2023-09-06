package com.hb.mediaplayerservicedemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText et_song;
    private Button btn_send_service;
    private boolean bPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_song = findViewById(R.id.et_song);
        btn_send_service = findViewById(R.id.btn_send_service);
        btn_send_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mtest", "onClick");
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                intent.putExtra("is_play", bPlay);
                intent.putExtra("song", et_song.getText().toString());
                if (bPlay) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    btn_send_service.setText("停止播放音乐");
                } else {
                    stopService(intent);
                    btn_send_service.setText("开始播放音乐");
                }
                bPlay = !bPlay;
            }
        });
    }

}