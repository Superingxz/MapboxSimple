package com.softgarden.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softgarden.baselibrary.BuildConfig;
import com.softgarden.baselibrary.base.rxbase.RxManager;
import com.softgarden.baselibrary.utils.InstanceUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.softgarden.baselibrary.base.BaseActivity.KEY_LOGIN_EVENT;
import static com.softgarden.baselibrary.base.BaseActivity.REQUEST_LOGIN;

/**
 * Fragment 基类  已实现以下功能
 * <p>
 * 1.显示/隐藏Loading弹框
 * 2.ButterKnife 绑定数据
 * 3.控制RxJava生命周期，防止内存泄漏
 * 4.MVP模式 参考 https://github.com/north2016/T-MVP
 * 需要时 可重写createPresenter() {@link BaseActivity#createPresenter()}  并且使用泛型 <P extends BasePresenter> 为当前Presenter实例
 */
public abstract class BaseFragment<P extends IBasePresenter> extends RxFragment implements IBaseDisplay {

    protected Unbinder unbinder;

    protected RxManager rxManager;

    protected int requestType = 0;//请求类型

    protected AppCompatActivity mActivity;
    protected Context mContext;

    private int position;
    private String title;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (AppCompatActivity) context;
        mContext = context;
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(getLayoutId(), container, false);
        bindView(mView);
        return mView;
    }

    protected void bindView(View mView) {
        unbinder = ButterKnife.bind(this, mView);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rxManager = new RxManager();
        initPresenter();
        initialize();
    }


    /**
     * 切换日夜模式
     *
     * @param isNightMode
     */
    @Override
    public void changeDayNightMode(boolean isNightMode) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).changeDayNightMode(isNightMode);
    }

    public boolean isEqualsLanguage(Locale mLanguage, Locale locale) {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).isEqualsLanguage(mLanguage, locale);
        } else {
            return false;
        }
    }

    /**
     * 显示加载框
     */
    @Override
    public void showProgressDialog() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    /**
     * 显示加载框（带文字）
     */
    @Override
    public void showProgressDialog(CharSequence message) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog(message);
    }

    /**
     * 隐藏加载框
     */
    @Override
    public void hideProgressDialog() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideProgressDialog();
    }

    /**
     * 默认 吐司显示错误，可重写
     *
     * @param throwable
     */
    @Override
    public void showError(Throwable throwable) {
        ToastUtil.s(throwable.getMessage());
        if (BuildConfig.DEBUG) throwable.printStackTrace();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        throw new RuntimeException("getActivity is not instanceof BaseActivity");
    }

    public void startActivity(Class<? extends Activity> cls) {
        this.startActivity(new Intent(getActivity(), cls));
    }

    public void startActivityForResult(Class<? extends Activity> cls, int request) {
        this.startActivityForResult(new Intent(getActivity(), cls), request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }

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
    protected P mPresenter;


    protected void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.detachView();
        for (int i = 0; i < presenterList.size(); i++) {
            presenterList.get(i).detachView();
        }
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
