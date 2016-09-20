# Logging
这是一个Android 上 效率极高的 Log 工具，主要功能为控制不同级别的Log输出,Log信息保存到文件、打印行号、函数调用、Json解析、点击跳转、多标签Tab 等功能

 打印行号、函数调用、Json解析、点击跳转 参照[KLog](https://github.com/ZhaoKaiQiang/KLog) of [ZhaoKaiQiang](https://github.com/ZhaoKaiQiang).

###1.开始使用 Logging
你只需要在 Application 里面调用Logcat.initialize一次即可完成初始化
```java
//初始化Logcat
Logcat.initialize(this);
```
配置更多信息
```java
Builder builder = Logcat.newBuilder();
builder.logSavePath(StorageUtils.getDiskCacheDir(this,"loggg")); //设置Log 保存的文件夹
builder.logCatLogLevel(Logcat.SHOW_INFO_LOG| Logcat.SHOW_ERROR_LOG);//设置日志等级
builder.fileLogLevel(Logcat.NOT_SHOW_LOG); //不显示Log
Logcat.initialize(this, builder.build());
```

###2.开始使用Logcat

```java
//控制台
Logcat.v("The is verbose log");
Logcat.d("The is debug log");
Logcat.i("The is info log");
Logcat.w("The is warn log");
Logcat.e("The is error log");

//写入Log 文件
Logcat.vv("file: The is verbose log")
Logcat.dd("file: The is debug log");
Logcat.ii("file: The is info log");
Logcat.ww("file: The is warn log");
Logcat.ee("file: The is error log");

//控制台+写入Log 文件
Logcat.vvv("All: The is verbose log");
Logcat.ddd("All: The is debug log");
Logcat.iii("All: The is info log");
Logcat.www("All: The is warn log");
Logcat.eee("All: The is error log");

//写入Log 文件,且指定 Log文件名
Logcat.fv("Hello World!", "MyLog.txt");
//控制台+写入Log 文件,且指定 Log文件名
Logcat.fvv("Hello World!!", "MyLog.txt");

```

Thanks [KLog](https://github.com/ZhaoKaiQiang/KLog)

##License

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
Email: 13168639883@163.com  
