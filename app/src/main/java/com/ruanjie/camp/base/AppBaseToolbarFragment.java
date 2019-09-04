package com.ruanjie.camp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;

import com.google.gson.JsonParseException;
import com.gyf.barlibrary.ImmersionBar;
import com.ruanjie.camp.R;
import com.ruanjie.camp.network.ApiException;
import com.ruanjie.camp.network.RxJava2NullException;
import com.softgarden.baselibrary.base.IBasePresenter;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by moligy on 2018/6/24.
 */

public abstract class AppBaseToolbarFragment<P extends IBasePresenter> extends ToolbarFragment<P> {
    protected boolean isUserImmersionBarUtil = false;//是否使用ImmersionBar
    protected ImmersionBar mImmersionBar;
    protected boolean keyboardEnable =false;
    protected int keyboardMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面，"MainScreen"为页面名称，可自定义
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
    public void onPause() {
        super.onPause();
        //注：回调 2
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null && isUserImmersionBarUtil) {
            mImmersionBar.destroy();
        }
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
