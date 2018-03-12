package com.aidan.envelopetracker.tools;

import android.util.Log;

import com.aidan.envelopetracker.models.Constants;
/**
 * Created by s352431 on 2018/2/13.
 */


// TODO: Auto-generated Javadoc

/**
 * The Class LogHelper.
 */
public class LogHelper {

    /**
     * Gets the caller caller class name.
     *
     * @return the caller caller class name
     */
    public static StackTraceElement getCallerCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(LogHelper.class.getName())
                    && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                return ste;// ste.getClassName()+ste.getMethodName()+ste.getLineNumber();
            }
        }
        return null;
    }

    /**
     * Log.
     *
     * @param log the log
     */
    public static void log(String log) {
        if (!Constants.isRelease()) {
            StackTraceElement ste = getCallerCallerClassName();
            if (ste != null)
                Log.e(ste.getClassName() + " ",
                        "at line:" + ste.getLineNumber() + " " + log);
        }
    }
}
