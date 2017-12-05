# Logcat

[ ![Download](https://api.bintray.com/packages/lazy/maven/library/images/download.svg?version=1.3.0) ](https://bintray.com/lazy/maven/library/1.3.0/link) [![CircleCI](https://img.shields.io/circleci/project/github/RedSparr0w/node-csgo-parser.svg)]() [![MyGet tenant](https://img.shields.io/dotnet.myget/dotnet-coreclr/dt/Microsoft.DotNet.CoreCLR.svg)]()

这是一个Android 上 效率极高的 Log 工具，主要功能为控制不同级别的Log输出,Log信息保存到文件、打印行号、函数调用、Json解析、点击跳转、多标签Tag 支持无限长字符串打印，无Logcat4000字符限制等功能

 打印行号、函数调用、Json解析、点击跳转 参照[KLog](https://github.com/ZhaoKaiQiang/KLog) of [ZhaoKaiQiang](https://github.com/ZhaoKaiQiang).
 
 ---
## Gradle

```
dependencies {
    compile 'com.lazy.logging:library:1.3.0'
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
} else {
  builder.logCatLogLevel(Logcat.SHOW_INFO_LOG | Logcat.SHOW_WARN_LOG | Logcat.SHOW_ERROR_LOG);
}
//设置输出文件日志等级
builder.fileLogLevel(Logcat.NOT_SHOW_LOG);
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

//文件log 格式
V/Logcat->newTag 2017-04-15_21:10:17
fileName:MainActivity.java
className:com.lazy.logging.MainActivity
methodName:OnCreate
lineNumber:78
output file msg result =  4 
```
### 5.Sample Usage

![][ScreenShot-2017-12-05.png]

[ScreenShot-2017-12-05.png]: https://github.com/iflove/Logcat/blob/master/ScreenShot/ScreenShot-2017-12-05.png


Thanks [KLog](https://github.com/ZhaoKaiQiang/KLog)

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
