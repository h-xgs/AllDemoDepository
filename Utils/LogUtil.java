package com.hb.android.tapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

/**
 * 日志工具类.
 * <p>若不传入TAG和Context，则TAG默认为"测试"
 * <p>若不传入TAG但传入Context，则TAG默认为"context类名+测试"
 */
public final class LogUtil {
    /**
     * 日志总开关
     */
    public static boolean isDebug = true;

    /**
     * 把日志输出到缓存文件
     */
    public static boolean isOutputLog = false;

    public static void logV(String msg) {
        String t = "测试";
        if (isDebug) {
            Log.v(t, msg);
        }
    }

    public static void logV(Context context, String msg) {
        String t = context.getClass().getSimpleName() + "测试";
        if (isDebug) {
            Log.v(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "v：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logV(Context context, String t, String msg) {
        if (isDebug) {
            Log.v(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "v：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logD(String msg) {
        String t = "测试";
        if (isDebug) {
            Log.d(t, msg);
        }
    }

    public static void logD(Context context, String msg) {
        String t = context.getClass().getSimpleName() + "测试";
        if (isDebug) {
            Log.d(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "d：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logD(Context context, String t, String msg) {
        if (isDebug) {
            Log.d(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "d：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logI(String msg) {
        String t = "测试";
        if (isDebug) {
            Log.i(t, msg);
        }
    }

    public static void logI(Context context, String msg) {
        String t = context.getClass().getSimpleName() + "测试";
        if (isDebug) {
            Log.i(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "i：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logI(Context context, String t, String msg) {
        if (isDebug) {
            Log.i(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "i：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logW(String msg) {
        String t = "测试";
        if (isDebug) {
            Log.w(t, msg);
        }
    }

    public static void logW(Context context, String msg) {
        String t = context.getClass().getSimpleName() + "测试";
        if (isDebug) {
            Log.w(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "w：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logW(Context context, String t, String msg) {
        if (isDebug) {
            Log.w(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "w：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logE(String msg) {
        String t = "测试";
        if (isDebug) {
            Log.e(t, msg);
        }
    }

    public static void logE(Context context, String msg) {
        String t = context.getClass().getSimpleName() + "测试";
        if (isDebug) {
            Log.e(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "e：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    public static void logE(Context context, String t, String msg) {
        if (isDebug) {
            Log.e(t, msg);
        }
        if (isOutputLog) {
            SharedPreferences.Editor editor = context.getSharedPreferences("mLog", Context.MODE_PRIVATE).edit();
            editor.putString(String.valueOf(index), "e：" + t + "：" + new Date() + msg);
            editor.apply();
            index++;
        }
    }

    /**
     * 日志数目
     */
    private static int index = 0;
}
