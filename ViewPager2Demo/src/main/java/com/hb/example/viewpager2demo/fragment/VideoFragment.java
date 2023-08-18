package com.hb.example.viewpager2demo.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hb.example.viewpager2demo.R;

public class VideoFragment extends Fragment {

    private VideoView videoView;
    private ImageView playControlView;
    private Uri videoUri;
    /**
     * 记录当前进度
     */
    private int current;
    /**
     * 是否循环播放视频
     */
    boolean isLoop = false;

    /**
     * 创建一个VideoFragment对象，并从activity传入视频uri的值
     *
     * @param uri 视频uri的值
     * @return 返回一个VideoFragment对象
     */
    public static VideoFragment newInstance(String uri) {
        Bundle args = new Bundle();
        args.putString("videoUri", uri);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_video, container, false);
        videoView = view.findViewById(R.id.videoView);
        playControlView = view.findViewById(R.id.playControlView);
        current = 0;

        if (getArguments() == null) {
            String uri = "android.resource://com.hb.example.viewpager2demo/" + R.raw.knc_simplelife;
            videoUri = Uri.parse(uri);
        }
        String uri = getArguments().getString("videoUri");
        videoUri = Uri.parse(uri);
        Log.w("mtest", "VideoFragment onCreateView: videoUri = " + videoUri);
        videoView.setVideoURI(videoUri);

        showPreview();
        // 播放器准备就绪
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 设置进度控制器
                // videoView.setMediaController(new MediaController(getActivity()));
                videoView.setBackground(null);
            }
        });

        // 视频播放完毕
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playControlView.setVisibility(View.VISIBLE);
                current = 0;

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
                Toast.makeText(getActivity(), "视频播放出错", Toast.LENGTH_SHORT).show();
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
        return view;
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
            mmr.setDataSource(getContext(), uri);
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
    public void onPause() {
        if (videoView.isPlaying()) {
            current = videoView.getCurrentPosition();
            Log.w("mtest", "VideoFragment onPause: current = " + current);
            videoView.pause();
            playControlView.setVisibility(View.VISIBLE);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!videoView.isPlaying()) {
            Log.w("mtest", "VideoFragment onResume: current = " + current);
            videoView.seekTo(current);
            videoView.start();
            playControlView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }

}