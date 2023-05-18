package com.hb.example.viewpager2demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VideoActivity extends Activity {

    /**
     * 记录当前进度
     */
    private int current;
    private VideoView videoView;
    private ImageView playControlView;
    private Uri videoUri;
    /**
     * 是否循环播放视频
     */
    boolean isLoop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        // 设置屏幕不休眠
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        videoView = findViewById(R.id.videoView);
        playControlView = findViewById(R.id.playControlView);
        current = 0;

        /*try {
            // 获取传递过来的视频的 uri
            videoUri = this.getIntent().getParcelableExtra("path");
            videoView.setVideoURI(videoUri);
            showPreview();
            // 获取传递过来的isLoop，判断视频是否重复播放
            isLoop = getIntent().getBooleanExtra("loop", false);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String uri = "android.resource://" + getPackageName() + "/" + R.raw.knc_simplelife;
        videoUri = Uri.parse(uri);
        videoView.setVideoURI(videoUri);
        showPreview();

        // 播放器准备就绪
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 设置进度控制器
                videoView.setMediaController(new MediaController(VideoActivity.this));
                videoView.setBackground(null);
            }
        });

        // 视频播放完毕
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playControlView.setVisibility(View.VISIBLE);

                // 判断是否循环播放
                if (isLoop) {
                    playControlView.setVisibility(View.GONE);
                    play();
                }
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // "播放出错"
                playControlView.setVisibility(View.GONE);
                Toast.makeText(VideoActivity.this, "视频播放出错", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 点击视频进入暂停
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    playControlView.setVisibility(View.VISIBLE);
                } else if (playControlView.getVisibility() == View.VISIBLE) {
                    playControlView.setVisibility(View.GONE);
                    videoView.start();
                }
            }
        });

        // 暂停时视频中间会出现一个播放按钮，按下它时，会继续播放，并隐藏此按钮
        playControlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playControlView.setVisibility(View.GONE);
                play();
            }
        });

        play();
    }

    private void play() {
        videoView.setBackground(null);
        videoView.start();
    }

    /**
     * 获取选择的视频的第一张封面图片
     *
     * @param uri 视频的uri路径
     * @return
     */
    protected Bitmap getFirstFrameDrawable(Uri uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(this, uri);
            return mmr.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 显示选择的视频的封面图片
     */
    private void showPreview() {
        Bitmap firstFrame = getFirstFrameDrawable(videoUri);
        if (firstFrame != null) {
            BitmapDrawable bd = new BitmapDrawable(getResources(), firstFrame);
            videoView.setBackground(bd);
        }
    }

    @Override
    protected void onPause() {
        if (videoView.isPlaying()) {
            current = videoView.getCurrentPosition();
            Log.w("mtest", "VideoActivity onPause: current" + current);
            videoView.pause();
            playControlView.setVisibility(View.VISIBLE);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!videoView.isPlaying()) {
            Log.w("mtest", "VideoActivity onResume: current" + current);
            videoView.seekTo(current);
            videoView.start();
            playControlView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }

}