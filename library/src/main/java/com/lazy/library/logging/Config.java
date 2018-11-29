package com.lazy.library.logging;

import com.lazy.library.logging.extend.JLog;

class Config {
    // 存放日志文件的目录全路径
    public String logSavePath = "";
    public Character logCatLogLevel;
    public Character fileLogLevel;
    public String topLevelTag;
    public JLog jLog;

    public Config(Builder builder) {
        this.logSavePath = builder.logSavePath;
        this.logCatLogLevel = builder.logCatLogLevel;
        this.fileLogLevel = builder.fileLogLevel;
        this.topLevelTag = builder.topLevelTag;
        this.jLog = builder.jLog;
    }


}