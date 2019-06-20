# Logcat
[![](https://jitpack.io/v/iflove/Logcat.svg)](https://jitpack.io/#iflove/Logcat) [![CircleCI](https://img.shields.io/circleci/project/github/RedSparr0w/node-csgo-parser.svg)]() [![MyGet tenant](https://img.shields.io/dotnet.myget/dotnet-coreclr/dt/Microsoft.DotNet.CoreCLR.svg)]()

这是一个Android 上 效率极高的 Log 工具，主要功能为控制不同级别的Log输出,Log信息保存到文件、打印行号、函数调用、Json解析、点击跳转、多标签Tag 支持无限长字符串打印，无Logcat4000字符限制等功能

 打印行号、函数调用、Json解析、点击跳转 参照[KLog](https://github.com/ZhaoKaiQiang/KLog) of [ZhaoKaiQiang](https://github.com/ZhaoKaiQiang).

---
## Gradle

```groovy
dependencies {
    implementation 'com.github.iflove:Logcat:2.1.1'
}
```

### 1.开始使用 Logcat

你只需要在 Application 里面调用Logcat.initialize一次即可完成初始化
```java
//初始化Logcat
Logcat.initialize(this);
```

配置更多信息
```java
Builder builder = Logcat.newBuilder();
//设置Log 保存的文件夹
builder.logSavePath(StorageUtils.getDiskCacheDir(this, "log"));
//设置输出日志等级
if (BuildConfig.DEBUG) {
    builder.logCatLogLevel(Logcat.SHOW_ALL_LOG);
    //设置输出文件日志等级
    builder.fileLogLevel(Logcat.SHOW_ALL_LOG);
} else {
    builder.logCatLogLevel(Logcat.SHOW_INFO_LOG | Logcat.SHOW_WARN_LOG | Logcat.SHOW_ERROR_LOG);
    //设置输出文件日志等级
    builder.fileLogLevel(Logcat.SHOW_INFO_LOG | Logcat.SHOW_WARN_LOG | Logcat.SHOW_ERROR_LOG);
}
//不显示日志
//builder.fileLogLevel(Logcat.NOT_SHOW_LOG);

builder.topLevelTag(TAG_TOP_1);
//删除过了几天无用日志条目
builder.deleteUnusedLogEntriesAfterDays(7);
//输出到Java控制台服务端
if (isMainProcess) {
    builder.dispatchLog(new JLog("192.168.3.11", 5036));
}
//是否自动保存日志到文件中
builder.autoSaveLogToFile(true);
//是否显示打印日志调用堆栈信息
builder.showStackTraceInfo(true);
//是否显示文件日志的时间
builder.showFileTimeInfo(true);
//是否显示文件日志的进程以及Linux线程
builder.showFilePidInfo(true);
//是否显示文件日志级别
builder.showFileLogLevel(true);
//是否显示文件日志标签
builder.showFileLogTag(true);
//是否显示文件日志调用堆栈信息
builder.showFileStackTraceInfo(true);
//添加该标签,日志将被写入文件
builder.addTagToFile(TAG_APP_EVENT);
Logcat.initialize(this, builder.build());
```

### 2.示例

```java
//控制台
Logcat.v("The is verbose log");
Logcat.d("The is debug log");
Logcat.i("The is info log");
Logcat.w("The is warn log");
Logcat.e("The is error log");

```

### 3.LogTransaction 为Logcat 提供灵活的链式调用api 

```java
msg(@NonNull final Object msg);// 打印 msg
msgs(@NonNull final Object... msg);// n ... msg
tag(@NonNull final String tag);// 打印 tag
tags(@NonNull final String... tags); //n ... tag
file(); // log默认输出到文件
file(@NonNull final String fileName); //指定文件名
ln(); //换行
format(@NonNull final String format, Object... args); //格式化
out(); //输出log
```

### 4.Logcat log 文件

```java
--默认log文件夹 sdcard/Android/data/you.pakeage/cache/logs 下

//默认文件log 格式
11-29 22:25:32.363 5523-1/com.lazy.logging V/Logcat[ (MainActivity.java:104)#PrintLog ] output file msg 
```

### 5.JLog

安卓开发者都知道，Android系统的单条日志打印长度是有限的, 底层Logger  `Logger.h` 文件中限制了输出日志字符的大小，具体原因就不造了。

`Logger.h`

```c
#define LOGGER_ENTRY_MAX_LEN        (4*1024)  
#define LOGGER_ENTRY_MAX_PAYLOAD    \\  
    (LOGGER_ENTRY_MAX_LEN - sizeof(struct logger_entry))

```

开发也没法子通过API来改变这一值。只能采取分段打印的办法输出日志信息。但是我发现了Java 的`System.out.println()` 是没有限制的，但是在安卓平台上这会转变为安卓INFO 级的日志，而且无需你分段打印的输出日志(是不是很😑)。在实际项目中，一般就也只有请求HTTP接口，而接口又是返回一个比较大JSON，超过4000 字符，安卓的Log API 就好截断，就无法看到完整的响应数据了。采取分段输出，虽然能看到完整的响应数据，但是每达4000字符时突然的换行以及不对齐，稍微不慎就会拷错，拷多json 字符串处理用工具解析查看。当然每次调试看请求数据也是可以的不过很是低效的。所以我做了个大胆的尝试（加入JLog），通过 `socket` 把日志传送到Java的控制中打印。

####  JLogServer

Run JLogServer 步骤: 下载JLogServer.java 到你的项目,用as 直接run main(),JLogServer 可以配置相应的端口，也可以用adb 端口映射。

![][20181129-0.png]

[20181129-0.png]: https://github.com/iflove/Logcat/blob/master/ScreenShot/20181129-0.png



#### Logcat dispatchLog

```java
...
builder.dispatchLog(new JLog("192.168.3.15", 5036));
```



#### 6.Future

JLogServer.java 是否能作为一个idea intellij plugin ?




### 7.Sample Usage

![][ScreenShot-2017-12-05.png]

[ScreenShot-2017-12-05.png]: https://github.com/iflove/Logcat/blob/master/ScreenShot/ScreenShot-2017-12-05.png

超长的Log完美输出

![][20181129-1.png]

[20181129-1.png]: https://github.com/iflove/Logcat/blob/master/ScreenShot/20181129-1.png

文件Log输出格式

![][20181129-3.png]

[20181129-3.png]: https://github.com/iflove/Logcat/blob/master/ScreenShot/20181129-3.png

## License

```
Copyright  2016 Lazy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

**About me**  
Email: 13532605287@163.com  
