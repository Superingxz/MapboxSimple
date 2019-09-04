package com.ruanjie.camp.network;


import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.ruanjie.camp.MainActivity;
import com.ruanjie.camp.utils.NetworkUtil;
import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.base.BaseFragment;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.utils.ToastUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ruanjie.camp.utils.LoginUtil.Logout;


/**
 * RxJava2 转换器 用于网络加载数据 已实现功能有：
 * <p>
 * 1.检测有无网络
 * 2.加载网络时显示加载框 结束是隐藏
 * 3.控制RxJava生命周期，防止内存泄漏
 */
public class NetworkTransformer<T> implements ObservableTransformer<BaseBean<T>, T> {
    private IBaseDisplay mView;
    private boolean showLoading;
    private boolean showErrorInfo;

    public NetworkTransformer(IBaseDisplay mView) {
        this(mView, true);
    }

    public NetworkTransformer(IBaseDisplay mView, boolean showLoading) {
        if (mView == null) throw new RuntimeException("IBaseDisplay is not NULL");
        this.mView = mView;
        this.showLoading = showLoading;
    }

    public NetworkTransformer(IBaseDisplay mView, boolean showLoading, boolean showErrorInfo) {
        if (mView == null) throw new RuntimeException("IBaseDisplay is not NULL");
        this.mView = mView;
        this.showLoading = showLoading;
        this.showErrorInfo = showErrorInfo;
    }

    @Override
    public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (!NetworkUtil.isConnected(mView.getContext())) {
                        NetworkUtil.showNoNetWorkDialog(mView.getContext());
                        disposable.dispose();
                    } else {
                        if (showLoading) mView.showProgressDialog();
                    }
                })
                .doFinally(() -> {
                    if (showLoading) mView.hideProgressDialog();
                })
                .map(baseBean -> {
                    if (baseBean.status == 1) {
                        if (!TextUtils.isEmpty(baseBean.info) && showErrorInfo) {
                            ToastUtil.s(baseBean.info);
                        }
                        return baseBean;
                    } else {
                        if (baseBean.status == -1) {
                            mView.showReLogin();
                            Logout();
                            if (mView instanceof BaseActivity) {
                                BaseActivity activity = (BaseActivity) this.mView;
                                //跳到登录界面
//                                LoginTypeActivity.start(activity);
                                if (activity != null && !activity.isFinishing()) {
                                    if (!(activity instanceof MainActivity)) {
                                        activity.finish();
                                    }
                                }
                            }

                            if (mView instanceof BaseFragment) {
                                BaseFragment fragment = (BaseFragment) this.mView;
                                FragmentActivity activity = fragment.getActivity();
                                //跳到登录界面
                                if (activity != null && !activity.isFinishing()) {
                                    if (!(activity instanceof MainActivity)) {
                                        activity.finish();
                                    }
                                }
                            }
                        }
                        throw new ApiException(baseBean.status, baseBean.info);
                    }
                })
                .map(baseBean -> {
                    if (baseBean.data == null) {
                        baseBean.data = (T) "";
                    }
                    return baseBean;
                })
                .map(tBaseBean -> {
                    if (tBaseBean.data != null) {
                        return tBaseBean.data;
                    } else {
                        mView.showSuccessByDataNull();
                        //返回空数据时 抛出一个异常让CallBack处理
                        throw new RxJava2NullException();
                    }
                })
                .compose(mView.bindToLifecycle());

    }

}
