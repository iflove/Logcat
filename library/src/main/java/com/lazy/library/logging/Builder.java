package com.lazy.library.logging;

import android.support.annotation.NonNull;

import com.lazy.library.logging.extend.JLog;

import java.io.File;

public class Builder {
    String logSavePath = "";
    String topLevelTag = "";
    Character logCatLogLevel;
    Character fileLogLevel;
    boolean autoSaveLogToFile;
    boolean showStackTraceInfo = true;
    boolean showFileTimeInfo = true;
    boolean showFilePidInfo = true;
    boolean showFileLogLevel = true;
    boolean showFileLogTag = true;
    boolean showFileStackTraceInfo = true;
    JLog jLog;

    public Config build() {
        return new Config(this);
    }

    public Builder logSavePath(@NonNull String logSavePath) {
        this.logSavePath = logSavePath;
        return this;
    }

    public Builder logSavePath(@NonNull File logSavePath) {
        this.logSavePath = logSavePath.getAbsolutePath();
        return this;
    }

    public Builder logCatLogLevel(Character logCatLogLevel) {
        this.logCatLogLevel = logCatLogLevel;
        return this;
    }

    public Builder fileLogLevel(Character fileLogLevel) {
        this.fileLogLevel = fileLogLevel;
        return this;
    }

    public Builder logCatLogLevel(int logCatLogLevel) {
        this.logCatLogLevel = (char) logCatLogLevel;
        return this;
    }

    public Builder fileLogLevel(int fileLogLevel) {
        this.fileLogLevel = (char) fileLogLevel;
        return this;
    }

    public Builder topLevelTag(@NonNull String tag) {
        this.topLevelTag = tag;
        return this;
    }

    public Builder dispatchLog(@NonNull JLog jLog) {
        this.jLog = jLog;
        return this;
    }

    public Builder autoSaveLogToFile(boolean save) {
        this.autoSaveLogToFile = save;
        return this;
    }

    public Builder showStackTraceInfo(boolean show) {
        this.showStackTraceInfo = show;
        return this;
    }

    public Builder showFileTimeInfo(boolean show) {
        this.showFileTimeInfo = show;
        return this;
    }

    public Builder showFilePidInfo(boolean show) {
        this.showFilePidInfo = show;
        return this;
    }

    public Builder showFileLogLevel(boolean show) {
        this.showFileLogLevel = show;
        return this;
    }

    public Builder showFileLogTag(boolean show) {
        this.showFileLogTag = show;
        return this;
    }

    public Builder showFileStackTraceInfo(boolean show) {
        this.showFileStackTraceInfo = show;
        return this;
    }
}