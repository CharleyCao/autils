package cn.caoleix.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lei.cao
 * @date 2019/6/17 10:14
 * @description 日志管理类
 */
public class CLogger {

    private static final String DEFAULT_TAG = CLogger.class.getSimpleName() + "_";
    private static final String LOG_FILE_PATH = Environment.getExternalStorageDirectory() + "/CLogger/logs/";

    private static boolean logSwitch = true;
    private static boolean autoSaveLogSwitch = false;
    private static Level autoSaveLogLevel = Level.error;

    public static void d(String tag, String msg) {
        if (TextUtils.isEmpty(tag))
            tag = DEFAULT_TAG;
        if (logSwitch) {
            String final_tag = DEFAULT_TAG.equals(tag) ? tag : DEFAULT_TAG + tag;
            Log.d(final_tag, msg);
        }
        if (autoSaveLogSwitch)
            save(msg);
    }

    public static void i(String tag, String msg) {
        if (TextUtils.isEmpty(tag))
            tag = DEFAULT_TAG;
        if (logSwitch) {
            String final_tag = DEFAULT_TAG.equals(tag) ? tag : DEFAULT_TAG + tag;
            Log.i(final_tag, msg);
        }
        if (autoSaveLogSwitch && autoSaveLogLevel != Level.debug)
            save(msg);
    }

    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(tag))
            tag = DEFAULT_TAG;
        if (logSwitch) {
            String final_tag = DEFAULT_TAG.equals(tag) ? tag : DEFAULT_TAG + tag;
            Log.e(final_tag, msg);
        }
        if (autoSaveLogSwitch && autoSaveLogLevel == Level.error)
            save(msg);
    }

    public static void d(String msg) {
        d((String)null, msg);
    }

    public static void i(String msg) {
        i((String)null, msg);
    }

    public static void e(String msg) {
        e((String)null, msg);
    }

    public static void d(Throwable e) {
        d((String)null, exception2String(e));
    }

    public static void i(Throwable e) {
        i((String)null, exception2String(e));
    }

    public static void e(Throwable e) {
        e((String)null, exception2String(e));
    }

    public static boolean save(Throwable e) {
        return save(exception2String(e));
    }

    public static String exception2String(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter errors = new StringWriter();
        PrintWriter printWriter = new PrintWriter(errors);
        e.printStackTrace(printWriter);
        printWriter.close();
        return errors.toString();
    }

    /**
     * 保存日志到SD卡的文件中
     * @param log 日志信息
     *                  例如：
     *                      14.txt              表示当天14-15点的日志信息
     * @return 是否保存成功
     */
    public static boolean save(String log) {
        String parentPath = LOG_FILE_PATH + today() + "/";
        File parent = new File(parentPath);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        File logFile = new File(parent,  hour() + ".txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
//                saveLogToCloud(exception2String(e));
                return false;
            }
        }
        return appendLogToFile(logFile, log);
    }

    //添加日志信息到文件
    private static boolean appendLogToFile(File file, String log) {
        try {
            FileWriter writer = new FileWriter(file, true);

            writer.write("-------------------------------START--------------------------------\n");
            writer.write(now() + "\n");
            writer.write(log + "\n");
            writer.write("-------------------------------END--------------------------------\n\n");

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
//            saveLogToCloud(exception2String(e));
            return false;
        }
        return true;
    }

    private static String today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return sdf.format(new Date());
    }

    private static int hour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public enum Level {
        info, debug, error
    }

}
