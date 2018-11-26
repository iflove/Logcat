package com.lazy.library.logging;

import android.support.annotation.NonNull;

import com.lazy.library.logging.extend.JLog;

import java.io.File;

public class Builder {
    public String logSavePath = "";
    public String topLevelTag = "";
    public Character logCatLogLevel;
    public Character fileLogLevel;
    public JLog jLog;

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
}