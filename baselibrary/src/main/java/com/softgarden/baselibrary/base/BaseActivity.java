package com.softgarden.baselibrary.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.mirkowu.statusbarutil.StatusBarUtil;
import com.noober.background.BackgroundLibrary;
import com.softgarden.baselibrary.BuildConfig;
import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.base.rxbase.RxManager;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.InstanceUtil;
import com.softgarden.baselibrary.utils.L;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity 基类  已实现以下功能
 * <p>
 * 1.切换语言
 * 2.切换日夜模式
 * 3.检测横竖屏
 * 4.显示/隐藏Loading弹框
 * 5.ButterKnife 绑定数据
 * 6.控制RxJava生命周期，防止内存泄漏
 * 7.MVP模式 参考 https://github.com/north2016/T-MVP
 * 需要时 可重写createPresenter() {@link BaseActivity#createPresenter()}  并且使用泛型 <P extends BasePresenter> 为当前Presenter实例
 */

public abstract class BaseActivity<P extends IBasePresenter> extends RxAppCompatActivity implements IBaseDisplay {
    public final String TAG = getClass().getSimpleName();

    /*** 通用的 用于传递数据的Key  */
    public static final String KEY_DATA = "data";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LOGIN_EVENT = "login_event";

    public static final int REQUEST_LOGIN = 0x00001234;
    public static final int REQUEST_CODE = 0x00005678;

    protected Unbinder unbinder;
    protected BaseDelegate mBaseDelegate;

    protected RxManager rxManager;

    protected int requestType = 0;//请求类型

    @NonNull
    public BaseDelegate getBaseDelegate() {
        if (mBaseDelegate == null) {
            mBaseDelegate = new BaseDelegate(this);
        }
        return mBaseDelegate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);

        final BaseDelegate delegate = getBaseDelegate();
        delegate.onCreate(savedInstanceState);

        rxManager = new RxManager();
        bindView();
        prepare();
        initPresenter();
        initialize();

        //显示当前的Activity路径
        if (BuildConfig.DEBUG) L.e("当前打开的Activity:  " + getClass().getName());
    }

//    /**
//     * 取消适配
//     */
//    public void cancelAdapterScreen() {
//        ScreenUtil.cancelAdaptScreen(this);
//    }

    protected void bindView() {
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);//ButterKnife
    }

    protected void prepare(){

    }


    @Override
    protected void onResume() {
        getBaseDelegate().onResume();
        super.onResume();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(getBaseDelegate().attachBaseContext(newBase));
    }

    /**
     * 这个可以视情况 重写 (当横竖屏等配置发生改变时)
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseDelegate().onConfigurationChanged(newConfig);
    }

    /**
     * 设置横屏竖屏
     *
     * @param mOrientationPortrait true 竖屏 false 横屏
     */
    public void setOrientationPortrait(boolean mOrientationPortrait) {
        getBaseDelegate().setOrientationPortrait(mOrientationPortrait);
    }

    public boolean isOrientationPortrait() {
        return getBaseDelegate().mOrientationPortrait;
    }


    /**
     * 切换语言 (设置完后要重启Activity才生效 {@link #reload()})
     *
     * @param language
     */
    public void changeLanguage(Locale language) {
        getBaseDelegate().changeLanguage(language);
    }


    /**
     * 是否相同 二种语言 （语言和 国家都相同才算是相同）
     *
     * @param mLanguage
     * @param locale
     * @return
     */
    public boolean isEqualsLanguage(Locale mLanguage, Locale locale) {
        return getBaseDelegate().isEqualsLanguage(mLanguage, locale);
    }


    /**
     * 切换日夜模式
     * <p>
     * 需要注意的两个地方，
     * 一是app或者activity引用的style需要是Theme.AppCompat.DayNight或者它的子style，
     * 二是调用getDelegate().setLocalNightMode()你的Activity必须是继承AppCompatActivity的。
     *
     * @param isNightMode
     */
    @Override
    public void changeDayNightMode(boolean isNightMode) {
        getBaseDelegate().changeDayNightMode(isNightMode);
    }


    /**
     * 重启Activity
     * 此方法会比 recreate() 效果更好
     */
    public void reload() {
        getBaseDelegate().reload();
    }

    /**
     * 权限提示对话框
     */
    public void showPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.prompt_message)
                .setMessage(R.string.permission_lack)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


    /**
     * 设置为亮色模式 状态栏 颜色变黑
     */
    public void setStatusBarLightMode() {
        if (!BaseSPManager.isNightMode()) {
            StatusBarUtil.setStatusBarLightModeWithNoSupport(this, true);
        }
    }

    /**
     * 回复状态栏颜色状态
     */
    public void setStatusBarDarkMode() {
        if (!BaseSPManager.isNightMode()) {
            if (StatusBarUtil.setStatusBarDarkMode(this) == 0) {//不支持 亮色 模式
//                //恢复过来时的 处理
//                StatusBarUtil.setTransparent(this);
            }
        }
    }

    public BaseActivity getActivity() {
        return this;
    }

    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }


    /***********************************  LoadingDialog start   ***********************************/

    /**
     * 显示加载框
     */
    @Override
    public void showProgressDialog() {
        getBaseDelegate().showProgressDialog();
    }

    /**
     * 显示加载框（带文字）
     */
    @Override
    public void showProgressDialog(CharSequence message) {
        getBaseDelegate().showProgressDialog(message);
    }

    /**
     * 隐藏加载框
     */
    @Override
    public void hideProgressDialog() {
        getBaseDelegate().hideProgressDialog();
    }

    /*******************************  LoadingDialog end  *****************************************/

    /**
     * 默认 吐司显示错误，可重写
     *
     * @param throwable
     */
    @Override
    public void showError(Throwable throwable) {
        getBaseDelegate().showError(throwable);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_LOGIN) {
            int eventId = 0;
            if (data != null) eventId = data.getIntExtra(KEY_LOGIN_EVENT, 0);
            backFromLogin(eventId);//从登陆界面返回  登录成功
        }
    }

    /**
     * 登录成功 返回回调
     *
     * @param eventId 一般为点击View的id，可根据id判断接点击事件，从而继续操作流程
     */
    protected void backFromLogin(int eventId) {

    }


    /**
     * 网络请求结束（无论成功还是失败）
     */
    @Override
    public void onRequestFinish() {

    }

    /**
     * 重新登录
     */
    @Override
    public void showReLogin() {

    }

    /**
     * status = 1 data = null情况
     */
    @Override
    public void showSuccessByDataNull() {

    }

    /*********************** MVP 参考 https://github.com/north2016/T-MVP ***************************/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
        for (int i = 0; i < presenterList.size(); i++) {
            presenterList.get(i).detachView();
        }

    }

    protected P mPresenter;

    protected void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
    }

    public P getPresenter() {
        return mPresenter;
    }

    /**
     * 创建Presenter 此处已重写 需要时重写即可
     *
     * @return
     */
    public P createPresenter() {
        if (this instanceof IBaseDisplay
                && this.getClass().getGenericSuperclass() instanceof ParameterizedType
                && ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[0];//获取Presenter的class
            return InstanceUtil.getInstance(mPresenterClass);
        }
        return null;
    }


    public ArrayList<BasePresenter> presenterList = new ArrayList<>();

    public <T extends BasePresenter> T createPresenter(Class<T> aClass) {
        T t = findPresenter(aClass);
        if (t == null) {
            Object instance = InstanceUtil.getInstance(aClass);
            if (instance instanceof BasePresenter) {
                BasePresenter presenter = (BasePresenter) instance;
                presenter.attachView(this);
                presenterList.add(presenter);
                return (T) presenter;
            }
        }
        return t;
    }

    public <T extends BasePresenter> T findPresenter(Class<T> aClass) {
        if (presenterList.isEmpty()) return null;
        for (int i = 0; i < presenterList.size(); i++) {
            BasePresenter act = presenterList.get(i);
            if (act.getClass().equals(aClass))
                return (T) act;
        }
        return null;
    }


    /*********************** MVP 参考 https://github.com/north2016/T-MVP ***************************/

    @LayoutRes
    protected abstract int getLayoutId();


    protected abstract void initialize();


}
