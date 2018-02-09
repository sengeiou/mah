package com.modiwu.mah.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.utils.Utils;

/**
 * Created by Obl on 2018/1/9.
 * top.jplayer.zhenggejia
 */

public class BaseApplication extends Application {
    public static Context AppContext;
    public final static String APP_ID = "wx510d1dded9bba6cb";
    private static Handler mMainThreadHandler;

    /**
     * 得到主线程里面的创建的一个hanlder
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        Utils.init(this);
        //主线程的Handler
        mMainThreadHandler = new Handler();
        BaseInitApplication.init(AppContext);
    }


}
