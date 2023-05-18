package com.hb.android.videodemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VideoActivity extends Activity {

    private static final int MSG_SEEK = 4000;
    private static final int MSG_SHOW_PREVIEW = 4001;
    private static final int MSG_UPDATE_SEEK_BAR = 4002;

    VideoView videoView;
    SeekBar seekBar;
    /**
     * 视频总时长
     */
    TextView textViewTime;
    /**
     * 视频的当前播放位置
     */
    TextView textViewCurrentPosition;
    ImageView playControlView;
    VideoHandler videoHandler;
    Uri videoUri;
    /**
     * 是否循环播放视频
     */
    boolean isLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        // 设置屏幕不休眠
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        videoView = findViewById(R.id.videoView);
        seekBar = findViewById(R.id.seekBar);
        textViewTime = findViewById(R.id.textViewTime);
        textViewCurrentPosition = findViewById(R.id.textViewCurrentPosition);
        playControlView = findViewById(R.id.playControlView);
        videoHandler = new VideoHandler(this);

        try {
            // 获取传递过来的视频的 uri
            videoUri = this.getIntent().getParcelableExtra("path");
            videoView.setVideoURI(videoUri);
            showPreview();

            // 获取传递过来的isLoop，判断视频是否重复播放
            isLoop = getIntent().getBooleanExtra("loop", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // 当进度条停止修改的时候触发
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 取得当前进度条的刻度
                int progress = seekBar.getProgress();
                Message msg = new Message();
                msg.what = MSG_SEEK;
                msg.obj = progress;
                videoHandler.sendMessage(msg);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });

        // 播放器准备就绪
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(videoView.getDuration());
                seekBar.setProgress(0);
                textViewTime.setText(time(videoView.getDuration()));
                videoView.setBackground(null);
            }
        });

        // 视频播放完毕
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                textViewCurrentPosition.setText(time(videoView.getDuration()));
                playControlView.setVisibility(View.VISIBLE);
                seekBar.setProgress(0);
                textViewCurrentPosition.setText(time(0L));

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
        videoHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 1000);
    }

    private void play() {
        videoView.setBackground(null);
        videoView.start();
    }

    /**
     * 根据视频播放进度设置进度条
     */
    private void watchPlayDurationAndSetSeekBar() {
        if (videoView.isPlaying()) {
            int current = videoView.getCurrentPosition();
            seekBar.setProgress(current);
            textViewCurrentPosition.setText(time(videoView.getCurrentPosition()));
        }
    }

    /**
     * 格式化时间
     *
     * @param millionSeconds 毫秒数
     * @return 返回格式化后的时间 mm:ss
     */
    private String time(long millionSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
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
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
            playControlView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.seekTo(seekBar.getProgress());
        if (!videoView.isPlaying()) {
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
        videoHandler.removeCallbacksAndMessages(null);
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

    class VideoHandler extends StaticHandler<com.hb.android.videodemo.VideoActivity> {

        public VideoHandler(com.hb.android.videodemo.VideoActivity videoActivity) {
            super(videoActivity);
        }

        @Override
        protected void handleMessage(Message msg, com.hb.android.videodemo.VideoActivity videoActivity) {
            super.handleMessage(msg, videoActivity);
            switch (msg.what) {
                case MSG_SHOW_PREVIEW:
                    showPreview();
                    break;
                case MSG_SEEK:
                    // 设置当前播放的位置
                    videoView.seekTo((int) msg.obj);
                    break;
                case MSG_UPDATE_SEEK_BAR:
                    // 更新进度条
                    watchPlayDurationAndSetSeekBar();
                    videoHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 1000);
                    break;
                default:
                    break;
            }
        }
    }

}