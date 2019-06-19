package com.lazy.logcat.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;


public class StorageUtils {

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() throws IllegalArgumentException {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取SDCARD总存储空间
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * SDCARD是否存在
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 将数字大小转为“MB”、“KB”、“GB”格式
     *
     * @param size
     * @return
     */
    public static String getSize(long size) {
        if (size < 0)
            return null;

        String result = null;
        if (size > 1024 * 1024 * 1024) {
            float f = (float) size / (1024 * 1024 * 1024);
            String s = String.valueOf(f);
            if (s.length() - s.indexOf(".") > 2)
                result = s.substring(0, s.indexOf(".") + 3);
            else
                result = s;
            return result + "GB";
        } else if (size > 1024 * 1024) {
            float f = (float) size / (1024 * 1024);
            String s = String.valueOf(f);
            if (s.length() - s.indexOf(".") > 2)
                result = s.substring(0, s.indexOf(".") + 3);
            else
                result = s;
            return result + "MB";
        } else if (size > 1024) {
            float f = (float) size / 1024;
            String s = String.valueOf(f);
            if (s.length() - s.indexOf(".") > 2)
                result = s.substring(0, s.indexOf(".") + 3);
            else
                result = s;
            return result + "KB";
        } else if (size < 1024) {
            return String.valueOf(size) + "B";
        } else
            return null;
    }


    public static long getFreeSpace(String dir) {
        StatFs state = null;
        try {
            state = new StatFs(dir);
            long blockSize = state.getBlockSize();
            long availableCount = state.getAvailableBlocks();
            long free = availableCount * blockSize;
            if (free > 0) {
                return free;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File externalCacheDir = context.getExternalCacheDir();
            // context.getExternalCacheDir() maybe null
            if (externalCacheDir == null) {
                cachePath = context.getCacheDir().getPath();
            } else {
                cachePath = externalCacheDir.getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File file = new File(cachePath + File.separator + uniqueName);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        return file;
    }

    public static File getDiskDir(String uniqueName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File storageDirectory = Environment.getExternalStorageDirectory();
            if (storageDirectory != null) {
                File file = new File(storageDirectory + File.separator + uniqueName);
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdir();
                }
                return file;
            }
        }
        return null;
    }

}
