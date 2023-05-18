package com.hb.android.videodemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class StaticHandler<T> extends Handler {

    private static final String TAG = "StaticHandler";

    protected WeakReference<T> ref;

    public StaticHandler(T t) {
        super(Looper.getMainLooper());
        ref = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        final T t = ref.get();
        if (t == null) {
            return;
        }
        handleMessage(msg, t);
    }

    protected void handleMessage(Message msg, T t) {

    }

}
