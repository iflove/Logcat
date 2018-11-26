package com.lazy.library.logging;

public enum LogLevel {

    Verbose(Logcat.SHOW_VERBOSE_LOG),
    Debug(Logcat.SHOW_DEBUG_LOG),
    Info(Logcat.SHOW_INFO_LOG),
    Warn(Logcat.SHOW_WARN_LOG),
    Error(Logcat.SHOW_ERROR_LOG);

    LogLevel(int value) {
        this.value = value;
    }

    final int value;
}