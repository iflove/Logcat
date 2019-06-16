package com.lazy.logging;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Logcat;
import com.lazy.library.logging.extend.JLog;
import com.lazy.logging.util.StorageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewGroup contentMain = (ViewGroup) findViewById(R.id.content_main);

        final List<Character> characters = Arrays.asList(Logcat.SHOW_VERBOSE_LOG, Logcat.SHOW_DEBUG_LOG, Logcat.SHOW_INFO_LOG, Logcat.SHOW_WARN_LOG, Logcat.SHOW_ERROR_LOG);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

        Logcat.i("{\"list\":[{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":36483768,\"last_recommend\":[{\"mid\":89357,\"time\":1542887019,\"msg\":\"万众期待的高音\",\"uname\":\"时空游客\",\"face\":\"http://i0.hdslb.com/bfs/face/194b37a372d3362e52cf0c620f0f36c3e005cd07.jpg\"}],\"typeid\":22,\"typename\":\"鬼畜调教\",\"title\":\"【高音哥】歌剧2（我的高音，全宇宙最高）\",\"subtitle\":\"\",\"play\":9823,\"review\":58,\"video_review\":114,\"favorites\":214,\"mid\":183410,\"author\":\"XiaoLLK\",\"description\":\"这是一首来自高音哥的宇宙级高音\\n越到后面越高能哦\\nPS：UP主参加了新星计划，希望喜欢这个作品的朋友能多点赞多关注（/TДT)/\\n混音：Gold Rum\\n填词：不鸽木村夫\\n调音、视频：XiaoLLK\",\"create\":\"2018-11-22 18:05\",\"pic\":\"http://i0.hdslb.com/bfs/archive/0ea82cc1bf3d92fe850f3754d183d7343bc4b4e2.jpg\",\"credit\":0,\"coins\":424,\"duration\":\"4:41\"},{\"aid\":36475654,\"last_recommend\":[{\"mid\":29743,\"time\":1542860575,\"msg\":\"\",\"uname\":\"破碎面具\",\"face\":\"http://i0.hdslb.com/bfs/face/1fb9b8842aba749c4ddaf4f1f14fc5602d73cf40.jpg\"}],\"typeid\":25,\"typename\":\"MMD·3D\",\"title\":\"【旗袍改版配布】面具子ROKI（田中姬版）\",\"subtitle\":\"\",\"play\":15570,\"review\":332,\"video_review\":156,\"favorites\":931,\"mid\":29743,\"author\":\"破碎面具\",\"description\":\"借物表：见视频结尾处。\\n\\n前几天说过这个旗袍我又改造了一个低胸版，准备也发一下，于是就发出来了，顺便做个视频演示一下样子。\\nROKI是田中姬的版本~\\n\\n模型配布地址会在视频发布后放在评论里~（毕竟评论里修改起来比视频资料里容易的多）\",\"create\":\"2018-11-22 11:25\",\"pic\":\"http://i1.hdslb.com/bfs/archive/1ed7c5b7509ec382719ef6b5bc6fc2efca82b928.jpg\",\"credit\":0,\"coins\":1486,\"duration\":\"3:57\"},{\"aid\":36076350,\"last_recommend\":[{\"mid\":58426,\"time\":1542616617,\"msg\":\"\",\"uname\":\"残星什么的就是残星\",\"face\":\"http://i0.hdslb.com/bfs/face/56ac36b37662e3746228f30eb4acf2cd332b66a5.jpg\"}],\"typeid\":28,\"typename\":\"原创音乐\",\"title\":\"【KA.U】返校衍生曲《你在哪里》\",\"subtitle\":\"\",\"play\":1832,\"review\":70,\"video_review\":85,\"favorites\":172,\"mid\":18977573,\"author\":\"KAU中文配音社团\",\"description\":\"Sc\\n原著：赤烛游戏《返校》\\n策划/剧本/PV构思/海报构思：西塔【KA.U】\\n编曲/分轨混音：爆豪胜姬\\n作曲：爆豪胜姬，张卫帆（见注释1）\\n作词：不如卿\\n演唱：费囧【KA.U】\\n歌曲人声后期：小仙女后期工作室\\n剧情后期：故安【KA.U】\\nPV：昭乱【初C工作室】\\nPV素材鸣谢：半支烟sama（见注释2）\\n海报：波斯菊【KA.U】\\n\\nCV：\\n方芮欣：纸巾【KA.U】\\n张明辉：江笙【翼之声】\\n殷翠涵：青钺森森【KA.U】\\n魏仲庭：清觞【KA.U】\\n电话男声：浩然【KA.U】\\n\\n\\n方芮欣【独白】：\\n（独自一人\",\"create\":\"2018-11-17 19:00\",\"pic\":\"http://i1.hdslb.com/bfs/archive/1fbe79363dc796542993b1a83b00b4b2fafd39e9.jpg\",\"credit\":0,\"coins\":252,\"duration\":\"5:29\"},{\"aid\":35679755,\"last_recommend\":[{\"mid\":294646,\"time\":1541853272,\"msg\":\"\",\"uname\":\"毛酱·把名字还给我\",\"face\":\"http://i0.hdslb.com/bfs/face/b2345c23456f93d865cdaac3905a6157f1f4ea06.jpg\"}],\"typeid\":31,\"typename\":\"翻唱\",\"title\":\"【翻唱】スキキライ ft. Orion【A. Ikari】\",\"subtitle\":\"\",\"play\":1465,\"review\":44,\"video_review\":4,\"favorites\":50,\"mid\":436256,\"author\":\"A_Ikari\",\"description\":\"可是又不是真的14岁（摊手\\n\\n和往年一样为了不影响大家剁手提前一天发_(:з」∠)_\\n祝大家明天剁手开心（手动doge\\n不说了我去整理购物车了_(:з」∠)_\\n\\n前作：光彼方 → av27346906\",\"create\":\"2018-11-10 16:32\",\"pic\":\"http://i1.hdslb.com/bfs/archive/ea78412a091fa423a3bf5857f7ea90bb30ebcbce.jpg\",\"credit\":0,\"coins\":161,\"duration\":\"4:59\"},{\"aid\":34942885,\"last_recommend\":[{\"mid\":2560,\"time\":1541756023,\"msg\":\"\",\"uname\":\"-林檎消失-\",\"face\":\"http://i1.hdslb.com/bfs/face/3474a5d9ba4c29629cfa725f581a9e6a1c4188b7.jpg\"}],\"typeid\":20,\"typename\":\"宅舞\",\"title\":\"【蓝鹅】✟Happy Halloween✟万圣节快乐~\",\"subtitle\":\"\",\"play\":9853,\"review\":62,\"video_review\":124,\"favorites\":380,\"mid\":89556,\"author\":\"laner\",\"description\":\"大家万圣节快乐~不给糖就捣蛋啦！这次参加了活动，大家喜欢的话麻烦点赞投币收藏呀，APP升级最新版本可以一键一条龙~\\n\\n原创振幅：sm24733261\\n使用音源：sm24734630\\n摄影：兔子命/后期：七分\\n后勤：兔子、小璇\\n\\n↓下面是一些碎碎念\\n第一次尝试夜景，结果不是很好，感谢后期拯救我的死亡黄光！下次录夜景会准备的更加充分的！这个舞也是历经波折，第一次出了意外，这是录的第二次，也是困难重重，不过好在顺利产出啦~第二作没有很大的进步，表情和动作都没能做到更好，以后会更加努力练习。\\n感谢摄影和后勤，冷天\",\"create\":\"2018-10-30 19:17\",\"pic\":\"http://i2.hdslb.com/bfs/archive/4ec30eda41b37633ad512d90041a5d1ab73e1269.jpg\",\"credit\":0,\"coins\":319,\"duration\":\"4:18\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"},{\"aid\":23740206,\"last_recommend\":[{\"mid\":124416,\"time\":1543063004,\"msg\":\"\",\"uname\":\"ここにいるよ\",\"face\":\"http://i0.hdslb.com/bfs/face/1fd42acf7198403375fbf209e094cf3983182187.jpg\"}],\"typeid\":24,\"typename\":\"MAD·AMV\",\"title\":\"【火影AMV】我的2007 // MY2007\",\"subtitle\":\"\",\"play\":2537,\"review\":29,\"video_review\":12,\"favorites\":218,\"mid\":592002,\"author\":\"非属MAGUS提督后宫分部\",\"description\":\"http://amvnews.ru/index.php?go=Files&in=view&id=9606\\n原作者名: tomik\\n原视频标题: MY2007\\n简介: 燃爆\",\"create\":\"2018-05-21 17:09\",\"pic\":\"http://i2.hdslb.com/bfs/archive/39c0039d7b6da05bd1ad6ca992dc6695de23f7cc.jpg\",\"credit\":0,\"coins\":93,\"duration\":\"3:08\"}]}");
        Logcat.i().tag("fmt-json").msg("response body:").fmtJSON("{\"data\":[{\"children\":[],\"courseId\":13,\"id\":408,\"name\":\"鸿洋\",\"order\":190000,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":409,\"name\":\"郭霖\",\"order\":190001,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":410,\"name\":\"玉刚说\",\"order\":190002,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":411,\"name\":\"承香墨影\",\"order\":190003,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":413,\"name\":\"Android群英传\",\"order\":190004,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":414,\"name\":\"code小生\",\"order\":190005,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":415,\"name\":\"谷歌开发者\",\"order\":190006,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":416,\"name\":\"奇卓社\",\"order\":190007,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":417,\"name\":\"美团技术团队\",\"order\":190008,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":420,\"name\":\"GcsSloop\",\"order\":190009,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":421,\"name\":\"互联网侦察\",\"order\":190010,\"parentChapterId\":407,\"userControlSetTop\":false,\"visible\":1}],\"errorCode\":0,\"errorMsg\":\"\"}").out();

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
        Logcat.v().file().msg("{\"code\":0,\"message\":\"success\",\"result\":[{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/a4e0f26fdd24887749ee023c3d6d2f31eafde946.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/3461\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/3e62691b8d95944a355378bf3fdcd1de34549966.jpg\",\"title\":\"完结撒花！\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/43daa400687be05e8b5f2ac43ee2efc431859260.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/191\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/95ad9d56dd263668ee90dbfc38428ad7d64cccf4.jpg\",\"title\":\"请问您今天要来点兔子吗？\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/bec36122392fa877cbd786547bc1460ee03b6611.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/1183\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/62645a31323f50805101d3ff913a022d61a1a9ac.jpg\",\"title\":\"CODE GEASS 反叛的鲁路修\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/2768afe6997c9dab6455c7efac85d14c9c1ff5da.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/2546\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/88a29d2e30288816059865b7bcb07cc6b589d7f2.jpg\",\"title\":\"言叶之庭\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/4b68111b04e9cbd32e4d29c28bfb2bf8b5df8505.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/3151\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/1f42b344f5077799f797fdc14c26ec7ff4d1758d.jpg\",\"title\":\"异邦人：无皇刃谭\"}]}");

        Logcat.v().file().tag(TAG).msg("output file msg").out();
        Logcat.v().file().msg("output file msg").tag("xxa").msg("result = ").msg(2 + 2).out();
        Logcat.v().file().msgs("output file msgs is ", 1, 2, 3).tag("A").file("MyLog.txt").msg("当然你也可指定文件名 ").out();


        Logcat.e().msg("output error msg").out().ln().msg("Do you see?").format("a:%s;b:%s", 2, 3).out();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
