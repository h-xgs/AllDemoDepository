package com.hb.android.locationdemo;

import android.util.Log;

public class LogUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;

    private static final String TAG = "测试";

    // 让level等于NOTHING就可以把所有日志都屏蔽掉
    private static final int level = VERBOSE;

    public static void v(String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (level <= DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (level <= INFO) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (level <= WARN) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (level <= ERROR) {
            Log.e(TAG, msg);
        }
    }

}
