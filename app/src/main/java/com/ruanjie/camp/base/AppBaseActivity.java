package com.ruanjie.camp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.google.gson.JsonParseException;
import com.gyf.barlibrary.ImmersionBar;
import com.pgyersdk.crash.PgyCrashManager;
import com.ruanjie.camp.R;
import com.ruanjie.camp.network.ApiException;
import com.ruanjie.camp.network.RxJava2NullException;
import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.base.IBasePresenter;
import com.softgarden.baselibrary.base.rxbase.RxManager;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by admin on 2018/6/24.
 */

public abstract class AppBaseActivity<P extends IBasePresenter> extends BaseActivity<P> {
    protected boolean isUserImmersionBarUtil = false;//是否使用ImmersionBar
    protected ImmersionBar mImmersionBar;
    protected boolean keyboardEnable =false;
    protected int keyboardMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        rxManager = new RxManager();
        super.onCreate(savedInstanceState);

        PgyCrashManager.register(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        MobclickAgent.onResume(this);
        //注：回调 1
        if (isUserImmersionBarUtil) {
            mImmersionBar = ImmersionBar.with(this)
                    .keyboardEnable(keyboardEnable)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                    .keyboardMode(keyboardMode)  //单独指定软键盘模式
                    .statusBarDarkFont(false);
            mImmersionBar.init();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null && isUserImmersionBarUtil) {
            mImmersionBar.destroy();
        }
        PgyCrashManager.unregister();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            if (!TextUtils.isEmpty(apiException.getMessage())) {
                ToastUtil.s(apiException.getMessage());
            }
            switch (apiException.getStatus()) {
                case 0://正常无错误

                    break;
            }
        } else{
            if (e instanceof ConnectException) {
                 ToastUtil.s(R.string.network_connection_failed);
            } else if (e instanceof UnknownHostException) {
                 ToastUtil.s(R.string.network_request_failed);
            } else if (e instanceof SocketTimeoutException) {
                ToastUtil.s(R.string.network_connection_timeout);
            } else if (e instanceof JsonParseException) {
                ToastUtil.s(R.string.json_failed);
                // e.printStackTrace();
            } else if (e instanceof RxJava2NullException) {//RxJava2不能发送null

            } else {
                super.showError(e);
            }
        }

    }
}
