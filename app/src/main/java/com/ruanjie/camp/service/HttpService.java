package com.ruanjie.camp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ruanjie.camp.serve.CampNanoHttpPD;
import com.ruanjie.camp.utils.LogUtil;

import java.io.IOException;

/**
 * @author Moligy
 * @date 2019/8/30.
 */
public class HttpService extends Service {
    private CampNanoHttpPD mHttpServer = null;//这个是HttpServer的句柄。

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LogUtil.d("HttpService","onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("HttpService","onStartCommand");
        //在这里开启HTTP Server。
        try {
            mHttpServer = new CampNanoHttpPD(8080);
            mHttpServer.start();
            LogUtil.d("HttpService","CampNanoHttpPD开启");
        } catch (IOException e) {
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.d("HttpService","onDestroy");
        //在这里关闭HTTP Server
        if(mHttpServer != null){
            mHttpServer.stop();
            LogUtil.d("HttpService","CampNanoHttpPD停止");
        }
    }
}
