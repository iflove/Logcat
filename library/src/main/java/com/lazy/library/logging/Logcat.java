package com.lazy.library.logging;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright  2016
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * <ol>
 * <li>仅输出Log ->控制台   {@link Logcat#v(Object, String...)}</li>
 * <li>仅输出 LogTransaction->文件  {@link Logcat#vv(Object, String...)}</li>
 * <li>输出Log ->控制台,且输出 LogTransaction->文件  {@link Logcat#vvv(Object, String...)}</li>
 * <li>仅输出 LogTransaction->文件,并指定Log 文件名  {@link Logcat#fv(Object, String, String...)}</li>
 * <li>输出Log ->控制台,且输出Log->文件,并指定Log 文件名  {@link Logcat#fvv(Object, String, String...)}</li>
 * </ol>
 * 1.具有 Android LogTransaction 的功能
 * <p/>
 * 2.具有写Log 到文件,同时能设置Log 的文件夹,LogTransaction 文件名的功能
 * <p/>
 * 3.能控制Log的等级输出
 * <p/>
 *
 * @author Lazy
 */
//@SuppressWarnings("ALL")
public final class Logcat {
    //1 级 Tag
    public static final String TAG = "Logcat";

    // 日志类型标识符(优先级：由低到高排列，取值越小优先级越高)
    public static final char SHOW_VERBOSE_LOG = 0x01;
    public static final char SHOW_DEBUG_LOG = 0x01 << 1;
    public static final char SHOW_INFO_LOG = 0x01 << 2;
    public static final char SHOW_WARN_LOG = 0x01 << 3;
    public static final char SHOW_ERROR_LOG = 0x01 << 4;
    //增加json 数据输出处理
    public static final char SHOW_JSON_LOG = 0x01 << 5;
    //位运算
    public static final char OPERATION_BIT = 0; //不显示 某级别Log
    public static final char NOT_SHOW_LOG = 0; //不显示 LogTransaction

    //日志级别
    public static final String V = "V/";
    public static final String D = "D/";
    public static final String I = "I/";
    public static final String W = "W/";
    public static final String E = "E/";
    public static final String JSON = "JSON/";

    //Tag 分割符号
    public static final String TAG_SEPARATOR = "->";
    public static final String DEFAULT_LOG_DIR = "logs";

    public static final char SHOW_ALL_LOG =
            SHOW_VERBOSE_LOG |
                    SHOW_DEBUG_LOG |
                    SHOW_INFO_LOG |
                    SHOW_WARN_LOG |
                    SHOW_ERROR_LOG |
                    SHOW_JSON_LOG;

    public static final char SHOW_FILE_LOG = 0x01;

    //    // 默认为五种日志类型均在 LogCat 中输出显示
    private static char m_cLogCatShowLogType = SHOW_ALL_LOG;

    // 默认为五种日志类型均在 日志文件 中输出保存

    // 以下注释不要删除，以便日后开启指定日志类型输出到日志文件中
    private static char m_cFileSaveLogType = SHOW_ALL_LOG;

    // 存放日志文件的目录全路径
    public static String sLogFolderPath = "";
    //Application Context 防止内存泄露
    private static Context mContext;
    //换行符
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    //文件 separator
    private static final String FILE_SEPARATOR = File.separator;


    private static final int JSON_INDENT = 3;
    private static final String LOGFILE_SUFFIX = ".log";

    //日志打印日期
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    //默认文件Log 文件名
    private static SimpleDateFormat fileSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //单线程 用写文件 防止 anr
    private static ExecutorService mSingleExecutors = Executors.newSingleThreadExecutor();
    public static final int INDEX = 5;

    @IntDef({SHOW_VERBOSE_LOG, SHOW_DEBUG_LOG, SHOW_INFO_LOG,
            SHOW_WARN_LOG, SHOW_ERROR_LOG, SHOW_JSON_LOG, NOT_SHOW_LOG})
    @Retention(RetentionPolicy.SOURCE)
    private @interface LockLevel {
    }

    private Logcat() {
        throw new UnsupportedOperationException();
    }

    public static void initialize(@NonNull Context context) {
        mContext = context.getApplicationContext();
        initialize(context, defaultConfig());
    }

    /**
     * @param context
     * @param config
     */
    public static void initialize(@NonNull Context context, @NonNull Config config) {
        mContext = context.getApplicationContext();
        if (config.logSavePath == null || config.logSavePath.trim().equals("")) {
            defaultConfig();
        } else {
            checkSaveLogPath(config.logSavePath);
        }
        if (config.logCatLogLevel != null) {
            m_cLogCatShowLogType = config.logCatLogLevel;
        }
        if (config.fileLogLevel != null) {
            m_cFileSaveLogType = config.fileLogLevel;
            if (m_cFileSaveLogType == NOT_SHOW_LOG)
                mSingleExecutors = null; //Recycle
        }

    }

    public static Config defaultConfig() {
        Builder builder = newBuilder();

        // 非循环，只是为了减少分支缩进深度
        do {
            String state = Environment.getExternalStorageState();
            // 未安装 SD 卡
            if (!Environment.MEDIA_MOUNTED.equals(state)) {
                Log.w(TAG, "Not mount SD card!");
                break;
            }

            // SD 卡不可写
            if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                Log.w(TAG, "Not allow write SD card!");
                break;
            }

            File externalCacheDir = mContext.getExternalCacheDir();
            // context.getExternalCacheDir() maybe null
            if (externalCacheDir != null) {
                builder.logSavePath = externalCacheDir.getAbsolutePath() + FILE_SEPARATOR + DEFAULT_LOG_DIR;
            } else {
                Log.e(TAG, "externalCacheDir is null!");
                builder.fileLogLevel(OPERATION_BIT);
                break;
            }

            // 只有存在外部 SD 卡且可写入的情况下才允许保存日志文件到指定目录路径下
            // 没有指定日志文件存放位置的话，就写到默认位置，即 当前应用 SD 卡根目录下的 Cache/LogTransaction 目录中
            String strSaveLogPath = builder.logSavePath;

            checkSaveLogPath(strSaveLogPath);
        } while (false);

        Config config = new Config(builder);
        return config;
    }

    private static void checkSaveLogPath(@NonNull String strSaveLogPath) {
        if (sLogFolderPath.trim().equals("")) {
            File fileSaveLogFolderPath = new File(strSaveLogPath);
            // 保存日志文件的路径不存在的话，就创建它
            if (!fileSaveLogFolderPath.exists()) {
                boolean mkdirs = fileSaveLogFolderPath.mkdirs();
                if (mkdirs) {
                    Log.i(TAG, "Create log folder success!");
                } else {
                    Log.i(TAG, "Create log folder failed!");
                }
            }

            // 指定日志文件保存的路径，文件名由内部按日期时间形式
            sLogFolderPath = strSaveLogPath;
        }
    }

    // 2017/4/15

    public static LogTransaction level(LogLevel logLevel) {
        return new LogStackRecord(logLevel);
    }

    public static LogTransaction v() {
        return new LogStackRecord(LogLevel.Verbose);
    }

    public static LogTransaction d() {
        return new LogStackRecord(LogLevel.Debug);
    }

    public static LogTransaction i() {
        return new LogStackRecord(LogLevel.Info);
    }

    public static LogTransaction w() {
        return new LogStackRecord(LogLevel.Warn);
    }

    public static LogTransaction e() {
        return new LogStackRecord(LogLevel.Error);
    }

    public static LogTransaction json() {
        return new LogStackRecord(LogLevel.Json);
    }


    //1.控制台Log
    public static void v(String tag, Object msg) {
        consoleLog(SHOW_VERBOSE_LOG, msg, tag);
    }

    public static void d(String tag, Object msg) {
        consoleLog(SHOW_DEBUG_LOG, msg, tag);
    }

    public static void i(String tag, Object msg) {
        consoleLog(SHOW_INFO_LOG, msg, tag);
    }

    public static void w(String tag, Object msg) {
        consoleLog(SHOW_WARN_LOG, msg, tag);
    }

    public static void e(String tag, Object msg) {
        consoleLog(SHOW_ERROR_LOG, msg, tag);
    }

    public static void json(String tag, String msg) {
        consoleLog(SHOW_JSON_LOG, msg, tag);
    }

    public static void v(Object msg, final String... tag) {
        consoleLog(SHOW_VERBOSE_LOG, msg, tag);
    }

    public static void d(Object msg, final String... tag) {
        consoleLog(SHOW_DEBUG_LOG, msg, tag);
    }

    public static void i(Object msg, final String... tag) {
        consoleLog(SHOW_INFO_LOG, msg, tag);
    }

    public static void w(Object msg, final String... tag) {
        consoleLog(SHOW_WARN_LOG, msg, tag);
    }

    public static void e(Object msg, final String... tag) {
        consoleLog(SHOW_ERROR_LOG, msg, tag);
    }

    public static void json(String msg, final String... tag) {
        consoleLog(SHOW_JSON_LOG, msg, tag);
    }

    //2.仅输出到默认Log文件

    public static void vv(final Object msg, final String... tag) {
        writeLog(SHOW_VERBOSE_LOG, msg, null, tag);
    }

    /**
     * @param msg
     * @param tag
     */
    public static void dd(final Object msg, final String... tag) {
        writeLog(SHOW_DEBUG_LOG, msg, null, tag);
    }

    /**
     * @param msg
     * @param tag
     */
    public static void ii(final Object msg, final String... tag) {
        writeLog(SHOW_INFO_LOG, msg, null, tag);
    }

    /**
     * @param msg
     * @param tag
     */
    public static void ww(final Object msg, final String... tag) {
        writeLog(SHOW_WARN_LOG, msg, null, tag);
    }

    /**
     * @param msg
     * @param tag
     */
    public static void ee(final Object msg, final String... tag) {
        writeLog(SHOW_ERROR_LOG, msg, null, tag);
    }

    /**
     * @param msg
     * @param tag
     */
    public static void fjson(final String msg, final String... tag) {
        writeLog(SHOW_JSON_LOG, msg, null, tag);
    }

    //3.控制台 + 文件Log

    /**
     * 3.控制台 + 文件Log
     *
     * @param msg
     * @param tag
     */
    public static void vvv(final Object msg, final String... tag) {
        //控制台
        consoleLog(SHOW_VERBOSE_LOG, msg, tag);
        //文件Log
        writeLog(SHOW_VERBOSE_LOG, msg, null, tag);
    }

    public static void ddd(final Object msg, final String... tag) {
        consoleLog(SHOW_DEBUG_LOG, msg, tag);

        writeLog(SHOW_DEBUG_LOG, msg, null, tag);
    }


    public static void iii(final Object msg, final String... tag) {
        consoleLog(SHOW_INFO_LOG, msg, tag);

        writeLog(SHOW_INFO_LOG, msg, null, tag);
    }


    public static void www(final Object msg, final String... tag) {
        consoleLog(SHOW_WARN_LOG, msg, tag);

        writeLog(SHOW_WARN_LOG, msg, null, tag);
    }


    public static void eee(final Object msg, final String... tag) {
        consoleLog(SHOW_ERROR_LOG, msg, tag);
        writeLog(SHOW_ERROR_LOG, msg, null, tag);
    }

    /**
     * 3.控制台 + 文件Log
     *
     * @param msg
     * @param tag
     */
    public static void cfjson(final Object msg, final String... tag) {
        //控制台log
        consoleLog(SHOW_JSON_LOG, msg, tag);
        //文件Log
        writeLog(SHOW_JSON_LOG, msg, null, tag);
    }


    //4.指定Log文件名,输出Log 到文件

    /**
     * 4.指定Log文件名,仅输出Log 到文件
     *
     * @param msg
     * @param logFileName
     * @param tag
     */
    public static void fv(final Object msg, @Nullable final String logFileName, final String... tag) {
        writeLog(SHOW_VERBOSE_LOG, msg, logFileName, tag);
    }

    public static void fd(final Object msg, @Nullable final String logFileName, final String... tag) {
        writeLog(SHOW_DEBUG_LOG, msg, logFileName, tag);
    }


    public static void fi(final Object msg, @Nullable final String logFileName, final String... tag) {
        writeLog(SHOW_INFO_LOG, msg, logFileName, tag);
    }


    public static void fw(final Object msg, @Nullable final String logFileName, final String... tag) {
        writeLog(SHOW_WARN_LOG, msg, logFileName, tag);
    }


    public static void fe(final Object msg, @Nullable final String logFileName, final String... tag) {
        writeLog(SHOW_ERROR_LOG, msg, logFileName, tag);
    }

    /**
     * 4.指定Log文件名,仅输出Log 到文件
     *
     * @param msg
     * @param logFileName 日志文件名
     * @param tag
     */
    public static void ffjson(final String msg, @Nullable final String logFileName, final String... tag) {
        writeLog(SHOW_JSON_LOG, msg, logFileName, tag);
    }

    //4.控制台 + 指定Log文件名,且输出Log 到文件

    /**
     * 4.控制台 + 指定Log文件名,且输出Log 到文件
     *
     * @param msg
     * @param logFileName
     * @param tag
     */
    public static void fvv(final Object msg, @Nullable final String logFileName, final String... tag) {
        consoleLog(SHOW_VERBOSE_LOG, msg, tag);

        writeLog(SHOW_VERBOSE_LOG, msg, logFileName, tag);
    }

    public static void fdd(final Object msg, @Nullable final String logFileName, final String... tag) {
        consoleLog(SHOW_DEBUG_LOG, msg, tag);

        writeLog(SHOW_DEBUG_LOG, msg, logFileName, tag);
    }


    public static void fii(final Object msg, @Nullable final String logFileName, final String... tag) {
        consoleLog(SHOW_INFO_LOG, msg, tag);

        writeLog(SHOW_INFO_LOG, msg, logFileName, tag);
    }


    public static void fww(final Object msg, @Nullable final String logFileName, final String... tag) {
        consoleLog(SHOW_WARN_LOG, msg, tag);

        writeLog(SHOW_WARN_LOG, msg, logFileName, tag);
    }


    public static void fee(final Object msg, @Nullable final String logFileName, final String... tag) {
        consoleLog(SHOW_ERROR_LOG, msg, tag);

        writeLog(SHOW_ERROR_LOG, msg, logFileName, tag);

    }

    /**
     * 4.控制台 + 指定Log文件名,且输出Log 到文件
     *
     * @param msg
     * @param logFileName
     * @param tag
     */
    public static void fcfjson(final String msg, @Nullable final String logFileName, final String... tag) {
        consoleLog(SHOW_JSON_LOG, msg, tag);

        writeLog(SHOW_JSON_LOG, msg, logFileName, tag);
    }


    /**
     * 将msg 写入日志文件
     *
     * @param msg
     */
    private static void saveLog2File(String msg) {
        // 得到当前日期时间的指定格式字符串
        String strDateTimeFileName = fileSimpleDateFormat.format(new Date());
        saveLog2File(msg, strDateTimeFileName + LOGFILE_SUFFIX);
    }

    /**
     * 将msg 写入日志文件
     *
     * @param msg
     * @param logFileName log 文件名
     */
    private static void saveLog2File(String msg, String logFileName) {
        FileWriter objFilerWriter = null;
        BufferedWriter objBufferedWriter = null;

        do { // 非循环，只是为了减少分支缩进深度
            String state = Environment.getExternalStorageState();
            // 未安装 SD 卡
            if (!Environment.MEDIA_MOUNTED.equals(state)) {
                Log.d(TAG, "Not mount SD card!");
                break;
            }

            // SD 卡不可写
            if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                Log.d(TAG, "Not allow write SD card!");
                break;
            }

            File rootPath = new File(sLogFolderPath);
            if (rootPath.exists()) {


                File fileLogFilePath = new File(sLogFolderPath, logFileName);
                // 如果日志文件不存在，则创建它
                if (true != fileLogFilePath.exists()) {
                    try {
                        fileLogFilePath.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                // 如果执行到这步日志文件还不存在，就不写日志到文件了
                if (true != fileLogFilePath.exists()) {
                    Log.d(TAG, "Create log file failed!");
                    break;
                }

                try {
                    objFilerWriter = new FileWriter(fileLogFilePath, //
                            true);          // 续写不覆盖
                } catch (IOException e1) {
                    Log.d(TAG, "New FileWriter Instance failed");
                    e1.printStackTrace();
                    break;
                }

                objBufferedWriter = new BufferedWriter(objFilerWriter);

                try {
                    objBufferedWriter.write(msg);
                    objBufferedWriter.flush();
                } catch (IOException e) {
                    Log.d(TAG, "objBufferedWriter.write or objBufferedWriter.flush failed");
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "LogTransaction savePaht invalid!");
            }


        } while (false);

        if (null != objBufferedWriter) {
            try {
                objBufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (null != objFilerWriter) {
            try {
                objFilerWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param type
     * @param objectMsg
     * @param tagArgs
     */
    private static void printLog(int type, Object objectMsg, @Nullable String... tagArgs) {
        //当前线程的堆栈情况
        int index = 4;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final StackTraceElement stackTraceElement = stackTrace[index];
        printLog(stackTraceElement, type, objectMsg, tagArgs);
    }

    /**
     * @param stackTraceElement
     * @param type
     * @param objectMsg
     * @param tagArgs
     */
    private static void printLog(final StackTraceElement stackTraceElement, int type, Object objectMsg, @Nullable String... tagArgs) {
        String msg;
        if (m_cLogCatShowLogType == OPERATION_BIT) {
            return;
        }

        String fileName = stackTraceElement.getFileName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        StringBuilder tagBuilder = new StringBuilder();
        tagBuilder.append(TAG);
        if (tagArgs == null) {
            tagBuilder.append(TAG_SEPARATOR);
            tagBuilder.append(fileName);
        } else {
            for (String tagArg : tagArgs) {
                tagBuilder.append(TAG_SEPARATOR);
                tagBuilder.append(tagArg);
            }
        }

        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(fileName).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");

        if (objectMsg == null) {
            msg = "null";
        } else {
            msg = objectMsg.toString();
        }
        if (msg != null && type != SHOW_JSON_LOG) {
            stringBuilder.append(msg);
        }

        String logStr = stringBuilder.toString();

        switch (type) {
            case SHOW_VERBOSE_LOG:
                Log.v(tagBuilder.toString(), logStr);
                break;
            case SHOW_DEBUG_LOG:
                Log.d(tagBuilder.toString(), logStr);
                break;
            case SHOW_INFO_LOG:
                Log.i(tagBuilder.toString(), logStr);
                break;
            case SHOW_WARN_LOG:
                Log.w(tagBuilder.toString(), logStr);
                break;
            case SHOW_ERROR_LOG:
                Log.e(tagBuilder.toString(), logStr);
                break;
            case SHOW_JSON_LOG: {
                if (TextUtils.isEmpty(msg)) {
                    Log.d(tagBuilder.toString(), "Empty or Null json content");
                    return;
                }

                String message = null;

                try {
                    if (msg.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(msg);
                        message = jsonObject.toString(JSON_INDENT);
                    } else if (msg.startsWith("[")) {
                        JSONArray jsonArray = new JSONArray(msg);
                        message = jsonArray.toString(JSON_INDENT);
                    }
                } catch (JSONException e) {
                    e("JSONException/" + tagBuilder.toString(), e.getCause().getMessage() + LINE_SEPARATOR + msg);
                    return;
                }

                printLine(JSON + tagBuilder.toString(), true);
                message = logStr + LINE_SEPARATOR + message;
                String[] lines = message.split(LINE_SEPARATOR);
                StringBuilder jsonContent = new StringBuilder();
                for (String line : lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR);
                }
                Log.d(JSON + tagBuilder.toString(), jsonContent.toString());
                printLine(JSON + tagBuilder.toString(), false);
            }
            break;
        }

    }

    /**
     * 写Log 到文件
     *
     * @param logLevel
     * @param msg
     * @param logFileName
     * @param tag
     */
    private static void writeLog(@LockLevel final int logLevel, final Object msg, @Nullable final String logFileName, final String... tag) {
        if (OPERATION_BIT != (logLevel &
                m_cFileSaveLogType)) {
            //当前主线程的堆栈情况
            final StackTraceElement stackTraceElement = getStackTraceElement(INDEX);
            mSingleExecutors.execute(new Runnable() {
                @Override
                public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    fileLog(stackTraceElement, logLevel, msg, logFileName, tag);
                }
            });
        }
    }

    public static void writeLog(@LockLevel final int logLevel, int INDEX, final Object msg, @Nullable final String logFileName, final String... tag) {
        if (OPERATION_BIT != (logLevel &
                m_cFileSaveLogType)) {
            //当前主线程的堆栈情况
            final StackTraceElement stackTraceElement = getStackTraceElement(INDEX);
            mSingleExecutors.execute(new Runnable() {
                @Override
                public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    fileLog(stackTraceElement, logLevel, msg, logFileName, tag);
                }
            });
        }
    }

    /**
     * 输出控制台日志
     *
     * @param logLevel
     * @param msg
     * @param tag
     */
    private static void consoleLog(@LockLevel final int logLevel, Object msg, String... tag) {
        //当前线程的堆栈情况
        consoleLog(logLevel, getStackTraceElement(INDEX), msg, tag);
    }

    public static void consoleLog(@LockLevel final int logLevel, int INDEX, Object msg, String[] tag) {
        //当前线程的堆栈情况
        consoleLog(logLevel, getStackTraceElement(INDEX), msg, tag);
    }

    /**
     * 输出控制台日志
     *
     * @param logLevel
     * @param msg
     * @param tag
     */
    private static void consoleLog(@LockLevel final int logLevel, final StackTraceElement stackTraceElement, Object msg, String[] tag) {
        if (OPERATION_BIT != (logLevel &
                m_cLogCatShowLogType)) {
            printLog(stackTraceElement, logLevel, msg, tag);
        }
    }

    /**
     * @param stackTraceElement
     * @param type
     * @param objectMsg
     * @param tagArgs
     */
    private static void fileLog(StackTraceElement stackTraceElement, int type, Object objectMsg, @Nullable String... tagArgs) {
        fileLog(stackTraceElement, type, objectMsg, null, tagArgs);
    }

    private static StackTraceElement getStackTraceElement(int index) {
        //当前线程的堆栈情况
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[index];
    }

    /**
     * @param stackTraceElement
     * @param type
     * @param objectMsg
     * @param logFileName
     * @param tagArgs
     */
    private static void fileLog(StackTraceElement stackTraceElement, int type, Object objectMsg, @Nullable String logFileName, @Nullable String... tagArgs) {
        String msg;
        if (m_cFileSaveLogType == OPERATION_BIT) {
            return;
        }

        String fileName = stackTraceElement.getFileName();
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        StringBuilder tagBuilder = new StringBuilder();
        tagBuilder.append(TAG);
        if (tagArgs == null) {
            tagBuilder.append(TAG_SEPARATOR);
            tagBuilder.append(className);
        } else {
            for (String tagArg : tagArgs) {
                tagBuilder.append(TAG_SEPARATOR);
                tagBuilder.append(tagArg);
            }
        }


        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder stringBuilder = new StringBuilder();

        // 得到当前日期时间的指定格式字符串

        String strDateTimeLogHead = simpleDateFormat.format(new Date());

        // 将标签、日期时间头、日志信息体结合起来
        stringBuilder
                .append(tagBuilder.toString())
                .append(" ")
                .append(strDateTimeLogHead)
                .append(LINE_SEPARATOR)
                .append("fileName:")
                .append(fileName)
                .append(LINE_SEPARATOR)
                .append("className:")
                .append(className)
                .append(LINE_SEPARATOR)
                .append("methodName:")
                .append(methodName)
                .append(LINE_SEPARATOR)
                .append("lineNumber:")
                .append(lineNumber)
                .append(LINE_SEPARATOR);


        if (objectMsg == null) {
            msg = "null";
        } else {
            msg = objectMsg.toString();
        }
        if (msg != null && type != SHOW_JSON_LOG) {
            stringBuilder.append(msg);
        }
        stringBuilder.append(LINE_SEPARATOR + LINE_SEPARATOR);


        switch (type) {
            case SHOW_VERBOSE_LOG:
                if (TextUtils.isEmpty(logFileName)) {
                    saveLog2File(V + stringBuilder.toString());
                } else {
                    saveLog2File(V + stringBuilder.toString(), logFileName);
                }

                break;
            case SHOW_DEBUG_LOG:
                if (TextUtils.isEmpty(logFileName)) {
                    saveLog2File(D + stringBuilder.toString());
                } else {
                    saveLog2File(D + stringBuilder.toString(), logFileName);
                }
                break;
            case SHOW_INFO_LOG:
                if (TextUtils.isEmpty(logFileName)) {
                    saveLog2File(I + stringBuilder.toString());
                } else {
                    saveLog2File(I + stringBuilder.toString(), logFileName);
                }

                break;
            case SHOW_WARN_LOG:
                if (TextUtils.isEmpty(logFileName)) {
                    saveLog2File(W + stringBuilder.toString());
                } else {
                    saveLog2File(W + stringBuilder.toString(), logFileName);
                }
                break;
            case SHOW_ERROR_LOG:
                if (TextUtils.isEmpty(logFileName)) {
                    saveLog2File(E + stringBuilder.toString());
                } else {
                    saveLog2File(E + stringBuilder.toString(), logFileName);
                }
                break;
            case SHOW_JSON_LOG: {
                if (TextUtils.isEmpty(msg)) {
                    Log.d(tagBuilder.toString(), "Empty or Null json content");
                    return;
                }

                String message = null;

                try {
                    if (msg.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(msg);
                        message = jsonObject.toString(JSON_INDENT);
                    } else if (msg.startsWith("[")) {
                        JSONArray jsonArray = new JSONArray(msg);
                        message = jsonArray.toString(JSON_INDENT);
                    }
                } catch (JSONException e) {
                    e("JSONException/" + tagBuilder.toString(), e.getCause().getMessage() + LINE_SEPARATOR + msg);
                    return;
                }

                stringBuilder.append(JSON);
                stringBuilder.append(LINE_SEPARATOR);
                stringBuilder.append("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
                message = stringBuilder.toString() + LINE_SEPARATOR + message;
                String[] lines = message.split(LINE_SEPARATOR);
                StringBuilder jsonContent = new StringBuilder();
                for (String line : lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR);
                }
                jsonContent.append("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
                if (TextUtils.isEmpty(logFileName)) {
                    saveLog2File(jsonContent.toString());
                } else {
                    saveLog2File(stringBuilder.toString(), logFileName);
                }

            }
            break;
        }

    }

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }


    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }
}
