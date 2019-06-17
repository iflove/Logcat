package com.lazy.logcat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Logcat;
import com.lazy.library.logging.extend.JLog;
import com.lazy.logcat.demo.R;
import com.lazy.logcat.util.StorageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewGroup contentMain = findViewById(R.id.content_main);

        final List<Character> characters = Arrays.asList(Logcat.SHOW_VERBOSE_LOG, Logcat.SHOW_DEBUG_LOG, Logcat.SHOW_INFO_LOG, Logcat.SHOW_WARN_LOG, Logcat.SHOW_ERROR_LOG);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化Logcat 配置更多信息
                final Builder builder = Logcat.newBuilder();
                builder.topLevelTag("标签Top");
                builder.autoSaveLogToFile(true);
                builder.showStackTraceInfo(false);
                builder.showFileTimeInfo(false);
                builder.showFilePidInfo(false);
                builder.showFileLogLevel(false);
                builder.showFileLogTag(false);
                builder.showFileStackTraceInfo(false);
                //设置Log 保存的文件夹
                builder.logSavePath(StorageUtils.getDiskCacheDir(MainActivity.this, "log"));

                int logCatLogLevel = Logcat.NOT_SHOW_LOG;
                final List<Character> logLevelCharacterList = new ArrayList<>();
                int childCount = contentMain.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    CheckBox checkBox = (CheckBox) contentMain.getChildAt(i);
                    if (checkBox.isChecked()) {
                        logLevelCharacterList.add(characters.get(i));
                    }
                }

                if (logLevelCharacterList.size() >= 1) {
                    logCatLogLevel = logLevelCharacterList.get(0);
                }
                for (int i = 1; i < logLevelCharacterList.size(); i++) {
                    logCatLogLevel |= logLevelCharacterList.get(i);
                }

                builder.logCatLogLevel(logCatLogLevel);
                builder.fileLogLevel(logCatLogLevel);
                //TODO config ip
                builder.dispatchLog(new JLog("192.168.3.11", 5036));
                Logcat.initialize(MainActivity.this, builder.build());

                printLog();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        printLog();
    }

    private void printLog() {
        //控制台
        Logcat.v("The is verbose log ");
        Logcat.d("The is debug log");
        Logcat.i("The is info log");
        Logcat.w("The is warn log");
        Logcat.e("The is error log");

        Logcat.i("标签Top.2", "[{\"userId\":29338068,\"faces\":[{\"schoolId\":11925956,\"face\":\"eyib96isnrho9ulu83l63qi0\",\"faceToken\":\"d83f17376a0ec2b4de4f09830ccb10bf\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"eyib96isnrho9ulu83l63qi0\",\"faceCode\":\"1.1.0.42$$ee5mto6aarohedxtg1bibw1b\",\"engine\":\"arcsoft\"}],\"name\":\"苏淑玲\"},{\"userId\":29869169,\"faces\":[{\"schoolId\":11925956,\"face\":\"sss8trlcfu4x60a5irky54tt\",\"faceToken\":\"38694fd1b6555653fc956a081a6ceea7\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"sss8trlcfu4x60a5irky54tt\",\"faceCode\":\"1.1.0.42$$w32jl0l5wrp384qj5i32ixpf\",\"engine\":\"arcsoft\"}],\"name\":\"魏福群\"},{\"userId\":29869122,\"faces\":[{\"schoolId\":11925956,\"face\":\"6r3fvufqes7n9bc2xafa3asu\",\"faceToken\":\"3f62ced7660d4d5be4e52a9f73461021\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"6r3fvufqes7n9bc2xafa3asu\",\"faceCode\":\"1.1.0.42$$0mqmu2eusst6ddf8af04igo6\",\"engine\":\"arcsoft\"}],\"name\":\"何淑青\"},{\"userId\":29866652,\"faces\":[{\"schoolId\":11925956,\"face\":\"wt1v4sz4hs23fxigssxe9msk\",\"faceCode\":\"1.1.0.42$$7nzxd5mkhu5mdbbap4xydmnh\",\"engine\":\"arcsoft\"},{\"schoolId\":11925956,\"face\":\"wt1v4sz4hs23fxigssxe9msk\",\"faceToken\":\"f51407c44b8c4b4fca550e6117924f77\",\"engine\":\"baidu\"}],\"name\":\"李琼燕\"},{\"userId\":29335357,\"faces\":[{\"schoolId\":11925956,\"face\":\"9o8xdolbfs4vdylpbflthhfn\",\"faceToken\":\"38641df0a264e8cb0e6e0b44a08d8a2c\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"9o8xdolbfs4vdylpbflthhfn\",\"faceCode\":\"1.1.0.42$$koboaxkdksu84d3uldnde5or\",\"engine\":\"arcsoft\"}],\"name\":\"田洋\"},{\"userId\":29337222,\"faces\":[{\"schoolId\":11925956,\"face\":\"185orkjynsdzfvkbiem7m94r\",\"faceToken\":\"63229f979fe33268bd258f68b6125d7b\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"185orkjynsdzfvkbiem7m94r\",\"faceCode\":\"1.1.0.42$$hhwvi92svs5r7syhh7hah9vd\",\"engine\":\"arcsoft\"}],\"name\":\"谢玉珍\",\"avatar\":\"ankbq4fesss4f25nzqbcgip0\"},{\"userId\":29337230,\"faces\":[{\"schoolId\":11925956,\"face\":\"deq0fk2tnt5yapzm7spwn94w\",\"faceToken\":\"0e9300aa058b677cf240fbd2ccf04f75\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"deq0fk2tnt5yapzm7spwn94w\",\"faceCode\":\"1.1.0.42$$lgm98vq3et9t84ey3uuuiws5\",\"engine\":\"arcsoft\"}],\"name\":\"熊转华\"},{\"userId\":29335443,\"faces\":[{\"schoolId\":11925956,\"face\":\"xmaobzqezrvh7pxjfrc7w2va\",\"faceToken\":\"5c770bac193b8b3cead10a71047df250\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"xmaobzqezrvh7pxjfrc7w2va\",\"faceCode\":\"1.1.0.42$$z0ff3nlvtragacexw2x82oxn\",\"engine\":\"arcsoft\"}],\"name\":\"甘霖\"},{\"userId\":29336603,\"faces\":[{\"schoolId\":11925956,\"face\":\"cudnko12lt4e49byh3x4r7fz\",\"faceToken\":\"a4d9a2dbf1b924559d5180084ce97eaf\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"cudnko12lt4e49byh3x4r7fz\",\"faceCode\":\"1.1.0.42$$0r8hqv1nkt1sdw02xlhlhu1u\",\"engine\":\"arcsoft\"}],\"name\":\"苏婷\"},{\"userId\":29336645,\"faces\":[{\"schoolId\":11925956,\"face\":\"swr3j5niisd5ew5364p1cbxo\",\"faceToken\":\"d1d04f6ab48b700f7e6803da802f60ba\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"swr3j5niisd5ew5364p1cbxo\",\"faceCode\":\"1.1.0.42$$az62alkxjsbmei46151yjdiz\",\"engine\":\"arcsoft\"}],\"name\":\"唐嘉敏\"},{\"userId\":29338094,\"faces\":[{\"schoolId\":11925956,\"face\":\"bm4wzrkhitkg8end4xbudmkc\",\"faceCode\":\"1.1.0.42$$fa4bnf23lsti7psa5qcw013h\",\"engine\":\"arcsoft\"},{\"schoolId\":11925956,\"face\":\"bm4wzrkhitkg8end4xbudmkc\",\"faceToken\":\"897936007aa5e2e59b5ad1eeb36ae2b9\",\"engine\":\"baidu\"}],\"name\":\"贾贝西\"},{\"userId\":29337581,\"faces\":[{\"schoolId\":11925956,\"face\":\"n9py9oedxtv2ev4iu67u0lnx\",\"faceToken\":\"7fac13d063805f9cf59c98ad1a86c5ab\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"n9py9oedxtv2ev4iu67u0lnx\",\"faceCode\":\"1.1.0.42$$mhy2twr4tt25bgl0kx9dqp3s\",\"engine\":\"arcsoft\"}],\"name\":\"刘文杰\"},{\"userId\":29336999,\"faces\":[{\"schoolId\":11925956,\"face\":\"6hkcssqvstewc6qvjgap8ik0\",\"faceToken\":\"95133a4878d3b9b1976d299667ec442c\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"6hkcssqvstewc6qvjgap8ik0\",\"faceCode\":\"1.1.0.42$$p8lji6h4gud77wry3hriattf\",\"engine\":\"arcsoft\"}],\"name\":\"刘佩仪\"},{\"userId\":29335559,\"faces\":[{\"schoolId\":11925956,\"face\":\"n1yavq4bps85crm06gxgy3uj\",\"faceToken\":\"b67edf780261c5ac1299ca28fea2c678\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"n1yavq4bps85crm06gxgy3uj\",\"faceCode\":\"1.1.0.42$$y5ez5lid4rlffr1crbtpee96\",\"engine\":\"arcsoft\"}],\"name\":\"卢骏飞\"},{\"userId\":29336890,\"faces\":[{\"schoolId\":11925956,\"face\":\"q4okotiw9syt8hwuc471h4b2\",\"faceToken\":\"e90b391e79804cead64256ce4ae1d71f\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"q4okotiw9syt8hwuc471h4b2\",\"faceCode\":\"1.1.0.42$$8csfhilvqsx571ck872t3k5z\",\"engine\":\"arcsoft\"}],\"name\":\"付晓慧\"},{\"userId\":29337198,\"faces\":[{\"schoolId\":11925956,\"face\":\"0uewk5q9qttl8k4m956a2jds\",\"faceCode\":\"1.1.0.42$$gsnhdfz83rofcj12fp9ag12y\",\"engine\":\"arcsoft\"},{\"schoolId\":11925956,\"face\":\"0uewk5q9qttl8k4m956a2jds\",\"faceToken\":\"59564a7410128b4e826bae8659bf5ce5\",\"engine\":\"baidu\"}],\"name\":\"范瑞萍\"},{\"userId\":29335262,\"faces\":[{\"schoolId\":11925956,\"face\":\"rgdk29nqttj9cb4hbsbqbf8h\",\"faceToken\":\"17aae7a90ab941032a70b2c2915cf27c\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"rgdk29nqttj9cb4hbsbqbf8h\",\"faceCode\":\"1.1.0.42$$cwpb4arq1s0j9l5f8o8oxsc1\",\"engine\":\"arcsoft\"}],\"name\":\"陈文\",\"avatar\":\"lw278vk54ucganq7xrep9ayd\"},{\"userId\":29868804,\"faces\":[{\"schoolId\":11925956,\"face\":\"mw1p1vop5tmb5wos6b5r0vnx\",\"faceToken\":\"77dae37167735016b1ed04a42fce2e66\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"mw1p1vop5tmb5wos6b5r0vnx\",\"faceCode\":\"1.1.0.42$$g96d53kodrxfgg26qa39u716\",\"engine\":\"arcsoft\"}],\"name\":\"曹娟\"},{\"userId\":29335449,\"faces\":[{\"schoolId\":11925956,\"face\":\"5olxmzcautiqc8m0os1u4l3k\",\"faceToken\":\"ec06d2bb2bfb2f5a608d766961bc2705\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"5olxmzcautiqc8m0os1u4l3k\",\"faceCode\":\"1.1.0.42$$1q1j86ju5rld4o2hwf2ye18r\",\"engine\":\"arcsoft\"}],\"name\":\"廖作友\"},{\"userId\":29338054,\"faces\":[{\"schoolId\":11925956,\"face\":\"gebxvv6p7t3h6ag0e3im2uxr\",\"faceToken\":\"2517dd2985bccdf2c35f65fff7c1c5ee\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"gebxvv6p7t3h6ag0e3im2uxr\",\"faceCode\":\"1.1.0.42$$n9atbo5xlsq0b38y9uaqtd65\",\"engine\":\"arcsoft\"}],\"name\":\"曾莹萍\",\"avatar\":\"owbvmgsl4sy0ae7j4xy17cv2\"},{\"userId\":29868807,\"faces\":[{\"schoolId\":11925956,\"face\":\"sg1agzm3dthldojv7sqvurzc\",\"faceToken\":\"171c92720267904ac237407fce23e587\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"sg1agzm3dthldojv7sqvurzc\",\"faceCode\":\"1.1.0.42$$tiqpdoc2xshd896k8csk7rli\",\"engine\":\"arcsoft\"}],\"name\":\"丁敏如\"},{\"userId\":29869131,\"faces\":[{\"schoolId\":11925956,\"face\":\"mbu3hl4idsjle6ghpfz9luwn\",\"faceToken\":\"aab8f0226b7b6740bc8723ddbed4067a\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"mbu3hl4idsjle6ghpfz9luwn\",\"faceCode\":\"1.1.0.42$$84drjniw9ry1frj64g7fryk3\",\"engine\":\"arcsoft\"}],\"name\":\"黄倩康\"},{\"userId\":29869125,\"faces\":[{\"schoolId\":11925956,\"face\":\"bc9suz9durgva46u4y7jnc6f\",\"faceToken\":\"774ebd095ca6b2d729c24fdd8f47507f\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"bc9suz9durgva46u4y7jnc6f\",\"faceCode\":\"1.1.0.42$$f6pkahvstu04dadv337c5mk3\",\"engine\":\"arcsoft\"}],\"name\":\"张凯林\"},{\"userId\":29337421,\"faces\":[{\"schoolId\":11925956,\"face\":\"8q4zqgk9rrhac97nmuxk6vr3\",\"faceToken\":\"2d1360435f11a6d52d032db5e9f309fd\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"8q4zqgk9rrhac97nmuxk6vr3\",\"faceCode\":\"1.1.0.42$$cvlwsxs2ktjoapu1np2cs15g\",\"engine\":\"arcsoft\"}],\"name\":\"陈永强\"},{\"userId\":29867613,\"faces\":[{\"schoolId\":11925956,\"face\":\"bs186joxqrxjak7h2bq6zsal\",\"faceToken\":\"cdc6c96bc1f34df2d37dac071b655dcc\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"bs186joxqrxjak7h2bq6zsal\",\"faceCode\":\"1.1.0.42$$5ut0utno0t9u81sdwtnwmw93\",\"engine\":\"arcsoft\"}],\"name\":\"凌勇军\"},{\"userId\":29337361,\"faces\":[{\"schoolId\":11925956,\"face\":\"6birjzjmes1watcrbk0dvdok\",\"faceToken\":\"d20a993dc74c4c625cf3d3677db99e8b\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"6birjzjmes1watcrbk0dvdok\",\"faceCode\":\"1.1.0.42$$9hp6gzgdbsqecymxnyb35nbs\",\"engine\":\"arcsoft\"}],\"name\":\"陈文密\"},{\"userId\":29337418,\"faces\":[{\"schoolId\":11925956,\"face\":\"se1fkhvrtr8l7uyt75hb804i\",\"faceToken\":\"3d713814c83dc905c9d7f596e0910200\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"se1fkhvrtr8l7uyt75hb804i\",\"faceCode\":\"1.1.0.42$$4luhp2751rah5w0rjln7g5ht\",\"engine\":\"arcsoft\"}],\"name\":\"张春华\"},{\"userId\":29869119,\"faces\":[{\"schoolId\":11925956,\"face\":\"zvfyy6oy7tff5gav185c4ymt\",\"faceToken\":\"13f03058edc4c57d5c901f772f53f52a\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"zvfyy6oy7tff5gav185c4ymt\",\"faceCode\":\"1.1.0.42$$4138j4ra5t3i4m7x6wlw9fnz\",\"engine\":\"arcsoft\"}],\"name\":\"刘学平\"},{\"userId\":29869182,\"faces\":[{\"schoolId\":11925956,\"face\":\"blgqeab1ftt76r9uomqke0e1\",\"faceToken\":\"f1fde52dc5c30a761a87df726ea1720f\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"blgqeab1ftt76r9uomqke0e1\",\"faceCode\":\"1.1.0.42$$7xp161td5u5f7v7omd38ac4d\",\"engine\":\"arcsoft\"}],\"name\":\"张淑桢\"},{\"userId\":29869140,\"faces\":[{\"schoolId\":11925956,\"face\":\"59cu0yaturjlfkj947dau42m\",\"faceToken\":\"d7714016b11364b9d68ffe9bcf6c3db1\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"59cu0yaturjlfkj947dau42m\",\"faceCode\":\"1.1.0.42$$rusmsbn1frsc9t1svk8sca3d\",\"engine\":\"arcsoft\"}],\"name\":\"李朋河\"},{\"userId\":29867918,\"faces\":[{\"schoolId\":11925956,\"face\":\"f9zfdp5yprxv5mxb692pho11\",\"faceToken\":\"57c0b7d0d5ef0a46b6cdb4cf43314252\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"f9zfdp5yprxv5mxb692pho11\",\"faceCode\":\"1.1.0.42$$8wq5l7mnatp0cf3k9ii8ybrz\",\"engine\":\"arcsoft\"}],\"name\":\"杨明慧\"},{\"userId\":29866637,\"faces\":[{\"schoolId\":11925956,\"face\":\"k5yoib0xzt61flscvp20up3a\",\"faceToken\":\"5f49c8f2b7d5c1b588a27fc20e331a3a\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"k5yoib0xzt61flscvp20up3a\",\"faceCode\":\"1.1.0.42$$7jjax7rz2tcg6aobzb42f980\",\"engine\":\"arcsoft\"}],\"name\":\"黎柱辉\"},{\"userId\":29336295,\"faces\":[{\"schoolId\":11925956,\"face\":\"v7kz08wl6t4i8tygthmjp0bv\",\"faceToken\":\"8639c6872cf54c91ec9705472e00008d\",\"engine\":\"baidu\"},{\"schoolId\":11925956,\"face\":\"v7kz08wl6t4i8tygthmjp0bv\",\"faceCode\":\"1.1.0.42$$uceplk17rs8xfux4dgcx906f\",\"engine\":\"arcsoft\"}],\"name\":\"吴禧\"}]");
        Logcat.i().tag("标签Top.2").msg("response body:").fmtJSON("{\"data\":[{\"children\":[],\"courseId\":13,\"id\":408,\"name\":\"鸿洋\",\"order\":190000,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":409,\"name\":\"郭霖\",\"order\":190001,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":410,\"name\":\"玉刚说\",\"order\":190002,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":411,\"name\":\"承香墨影\",\"order\":190003,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":413,\"name\":\"Android群英传\",\"order\":190004,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":414,\"name\":\"code小生\",\"order\":190005,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":415,\"name\":\"谷歌开发者\",\"order\":190006,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":416,\"name\":\"奇卓社\",\"order\":190007,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":417,\"name\":\"美团技术团队\",\"order\":190008,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":420,\"name\":\"GcsSloop\",\"order\":190009,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":421,\"name\":\"互联网侦察\",\"order\":190010,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1}],\"errorCode\":0,\"errorMsg\":\"\"}").out();

        Logcat.v().msg("msg content").out();
        Logcat.v().msg("msg content").msg("result = ").msg(2 + 2).out();
        Logcat.v().msgs("msgs is content", 1, 2, 3).out();

        Logcat.v().tag(TAG).out();
        Logcat.v().tag(TAG).tags("A", "B").out();
        Logcat.v().tag(TAG).tag("A").tag("B").out();

        Logcat.v().msg("msg content").tag(TAG).msg("result = ").msg(2 + 2).out();
        Logcat.v().msgs("output msgs is ", 1, 2, 3).tag(TAG).out();

        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logcat.v().file().msg("output file msg not main thread").out();
            }
        }).start();
        Logcat.v().file().msg("output file msg").out();
        Logcat.v().file().msg("output file msg").msg("result = ").msg(2 + 2).out();
        Logcat.v().file().msgs("output file msgs is ", 1, 2, 3).out();

        Logcat.v().file().tag(TAG).msg("output file msg").out();
        Logcat.v().file().msg("output file msg").tag("xxa").msg("result = ").msg(2 + 2).out();
        Logcat.v().file().msgs("output file msgs is ", 1, 2, 3).tag("A").file("MyLog.txt").msg("当然你也可指定文件名 ").out();


        Logcat.e().msg("output error msg").out().ln().msg("Do you see?").format("a:%s;b:%s", 2, 3).out();
    }


}
