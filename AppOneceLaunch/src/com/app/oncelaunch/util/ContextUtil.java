package com.app.oncelaunch.util;

/*
 * 获取Context的工具类
 * manifest中<application>中加入Android：name="mypackage.ContextUtil",这样我们就可以在任何一个类下面获取Context，
 * 例如：Context c=ContextUtil.getInstance();
 */
		

import android.app.Application;

public class ContextUtil extends Application {
    private static ContextUtil instance;

    public static ContextUtil getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}