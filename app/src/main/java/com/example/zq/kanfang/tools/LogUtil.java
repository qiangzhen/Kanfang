package com.example.zq.kanfang.tools;

import android.util.Log;

/**
 *
 */
public class LogUtil {
    public static final boolean DEBUG = true;
    public static final String TAG = "TAG";
    private static LogUtil sLogUtil;

    private LogUtil() {
    }

    public static LogUtil getInstance() {
        if (sLogUtil == null) {
            synchronized (LogUtil.class) {
                if (sLogUtil == null) {
                    sLogUtil = new LogUtil();
                }
            }
        }
        return sLogUtil;
    }

    public void debug(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public void info(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public void error(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public void warn(String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }
}
