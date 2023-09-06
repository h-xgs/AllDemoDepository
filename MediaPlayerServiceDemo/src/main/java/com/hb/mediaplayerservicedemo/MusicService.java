package com.hb.mediaplayerservicedemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class MusicService extends Service {

    private static final String TAG = "MusicService";

    private static final String CHANNEL_ID = "MusicServiceChannel";
    private static final int NOTIFICATION_ID = 3001;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private RemoteViews notify_music;

    private final IBinder mBinder = new Binder() {
        public MusicService getService() {
            return MusicService.this;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    private String mSong;
    private final String PAUSE_EVENT = "com.hb.mediaplayerservicedemo.pause";
    private boolean bPlay = true;

    private long mBaseTime;
    private long mPauseTime = 0;
    private int mProcess = 0;
    private final Handler mHandler = new Handler();
    private final Runnable mPlay = new Runnable() {
        @Override
        public void run() {
            if (bPlay) {
                if (mProcess < 100) {
                    mProcess += 2;
                } else {
                    mProcess = 0;
                }
                mHandler.postDelayed(this, 1000);
            }
            getNotify(MusicService.this, mSong, bPlay, mProcess, mBaseTime);
        }
    };

    private void getNotify(Context ctx, String song, boolean isPlay, int progress, long time) {
        Log.d(TAG, "getNotify");
        Intent pIntent = new Intent(PAUSE_EVENT);
        PendingIntent nIntent = PendingIntent.getBroadcast(ctx, R.string.app_name, pIntent, PendingIntent.FLAG_IMMUTABLE);
        if (isPlay) {
            notify_music.setTextViewText(R.id.btn_play, "暂停");
            notify_music.setTextViewText(R.id.tv_play, song + " 正在播放");
            notify_music.setChronometer(R.id.chr_play, time, "%s", true);
        } else {
            notify_music.setTextViewText(R.id.btn_play, "继续");
            notify_music.setTextViewText(R.id.tv_play, song + "暂停播放");
            notify_music.setChronometer(R.id.chr_play, time, "%s", false);
        }
        notify_music.setProgressBar(R.id.pb_play, 100, progress, false);
        notify_music.setOnClickPendingIntent(R.id.btn_play, nIntent);
        // 更新通知栏内容
        mBuilder.setCustomBigContentView(notify_music);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void init() {
        notify_music = new RemoteViews(getPackageName(), R.layout.notify_music);
        mNotificationManager = getSystemService(NotificationManager.class);
        // 创建通知信道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            mNotificationManager.createNotificationChannel(serviceChannel);
        }
        // 创建一个 Intent 对象，指向应用程序的启动活动
        Intent launchIntent = new Intent(this, MainActivity.class);
        launchIntent.setAction(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, PendingIntent.FLAG_MUTABLE);
        // 创建前台服务的通知
        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setCustomBigContentView(notify_music)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.tt_s)
                .setOnlyAlertOnce(true);
        startForeground(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        mBaseTime = SystemClock.elapsedRealtime();
        bPlay = intent.getBooleanExtra("is_play", true);
        mSong = intent.getStringExtra("song");
        Log.d(TAG, "bPlay=" + bPlay + ", mSong=" + mSong);
        init();
        mHandler.postDelayed(mPlay, 200);
        return START_NOT_STICKY;

        /*START_STICKY
            粘性的服务。如果服务进程被杀掉，就保留服务的状态为开始状态，但不保留传送的Intent对象。随后系统尝试重新创建服务，由于服务状态为开始状态，因此创建服务后一定会调用onStartCommand方法。如果在此期间没有任何启动命令传送给服务，参数Intent就为空值
        START_NOT_STICKY
            非粘性的服务。使用这个返回值时，如果服务被异常杀掉，系统就不会自动重启该服务
        START_REDELIVER_INTENT
            重传 Intent的服务。使用这个返回值时，如果服务被异常杀掉，系统就会自动重启该服务，并传入Intent的原值
        START_STICKY_COMPATIBILITY
            START_STICKY 的兼容版本，不保证服务被杀掉后一定能重启*/
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        IntentFilter filter = new IntentFilter(PAUSE_EVENT);
        registerReceiver(pauseReceiver, filter);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(pauseReceiver);
        stopForeground(true);
        super.onDestroy();
    }

    private final BroadcastReceiver pauseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                bPlay = !bPlay;
                if (bPlay) {
                    mHandler.postDelayed(mPlay, 200);
                    if (mPauseTime > 0) {
                        long gap = SystemClock.elapsedRealtime() - mPauseTime;
                        mBaseTime += gap;
                    }
                } else {
                    mPauseTime = SystemClock.elapsedRealtime();
                }
            }
        }
    };

}
