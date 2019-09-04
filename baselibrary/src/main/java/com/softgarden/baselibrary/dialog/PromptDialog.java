package com.softgarden.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.EmptyUtil;
import com.softgarden.baselibrary.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * 提示窗  继承自Dialog
 * @author by DELL
 * @date on 2018/1/5
 * @describe
 */

public class PromptDialog extends Dialog implements View.OnClickListener {

    LinearLayout llRoot;
    ImageView ivIcon;
    TextView tvTitle;
    TextView tvContent;
    TextView tvPositive;
    TextView tvNegative;
    LinearLayout mCustomContainer;
    LinearLayout mCustomBottomContainer;
    List<View> mCustomViews = new ArrayList<>();
    List<View> mCustomBottomViews = new ArrayList<>();

    private int rootBg;
    private int icon;
    private String title;
    private String content;
    private String positiveLabel;
    private String negativeLabel;
    private int positiveTextColor;
    private int negativeTextColor;
    private int positiveBgColor;
    private int negativeBgColor;
    private OnButtonClickListener listener;

    public <T extends View> T $(@IdRes int id) {
        return findViewById(id);
    }

    public PromptDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    public PromptDialog(Context context, int themeId) {
        super(context, themeId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt);

        getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtil.getScreenWidth(getContext()) * 0.75);//设定宽度为屏幕宽度的0.75
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        initView();
    }


    private void initView() {
        llRoot = $(R.id.ll_root);
        ivIcon = $(R.id.ivIcon);
        tvTitle = $(R.id.tvTitle);
        tvContent = $(R.id.tvContent);
        tvPositive = $(R.id.tvPositive);
        tvNegative = $(R.id.tvNegative);
        mCustomContainer = $(R.id.custom_container);
        mCustomBottomContainer = $(R.id.custom_bottom_container);
        tvPositive.setOnClickListener(this);
        tvNegative.setOnClickListener(this);

        if (rootBg > 0) {
            llRoot.setBackgroundResource(rootBg);
        }
        ivIcon.setImageResource(icon);
        tvTitle.setText(title);
        tvContent.setText(content);
        ivIcon.setVisibility(icon > 0 ? VISIBLE : GONE);
        tvTitle.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
        tvContent.setVisibility(TextUtils.isEmpty(content) ? GONE : VISIBLE);

        tvPositive.setText(positiveLabel);
        tvNegative.setText(negativeLabel);
        tvPositive.setVisibility(TextUtils.isEmpty(positiveLabel) ? GONE : VISIBLE);
        tvNegative.setVisibility(TextUtils.isEmpty(negativeLabel) ? GONE : VISIBLE);

        if (positiveTextColor != 0)
            tvPositive.setTextColor(ContextUtil.getColor(positiveTextColor));
        if (negativeTextColor != 0)
            tvNegative.setTextColor(ContextUtil.getColor(negativeTextColor));
        if (negativeBgColor != 0)
            tvNegative.setBackgroundColor(ContextUtil.getColor(negativeBgColor));
        if (positiveBgColor != 0)
            tvPositive.setBackgroundColor(ContextUtil.getColor(positiveBgColor));

        if (EmptyUtil.isNotEmpty(mCustomViews)) {
            for (int i = 0; i < mCustomViews.size(); i++) {
                mCustomContainer.addView(mCustomViews.get(i));
            }
        }

        if (EmptyUtil.isNotEmpty(mCustomBottomViews)) {
            for (int i = 0; i < mCustomBottomViews.size(); i++) {
                mCustomBottomContainer.addView(mCustomBottomViews.get(i));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int i = v.getId();
            if (i == R.id.tvPositive) {
                listener.onButtonClick(this, true);
            } else if (i == R.id.tvNegative) {
                listener.onButtonClick(this, false);
            }
        }
        dismiss();
    }

    public PromptDialog setRootBg(@DrawableRes int root_bg) {
        this.rootBg = root_bg;
        return this;
    }

    public PromptDialog setIcon(@DrawableRes int icon) {
        this.icon = icon;
        return this;
    }

    public PromptDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 注意：该方法已弃用
     * 因为返回值类型不同 未能重写该方法
     * 设置标题请使用{@link #setTitle(String)} 方法
     */
    @Deprecated
    @Override
    public void setTitle(int titleId) {
        //super.setTitle(titleId);
    }


    public PromptDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public PromptDialog setContent(@StringRes int id) {
        return setContent(ContextUtil.getString(id));
    }

    public PromptDialog addCustomView(View customView){
        this.mCustomViews.add(customView);
        return this;
    }

    public PromptDialog addCustomBottomView(View customBottomView){
        this.mCustomBottomViews.add(customBottomView);
        return this;
    }

    /**
     * 左边的 （默认取消）
     *
     * @param negativeLabel
     * @return
     */
    public PromptDialog setNegativeButton(@StringRes int negativeLabel, @ColorRes int textColorInt) {
        return setNegativeButton(ContextUtil.getString(negativeLabel), textColorInt);
    }

    public PromptDialog setNegativeButton(String negativeLabel, @ColorRes int textColorInt) {
        this.negativeLabel = negativeLabel;
        this.negativeTextColor = textColorInt;
        return this;
    }

    public PromptDialog setNegativeButton(String negativeLabel, @ColorRes int textColorInt,@ColorRes int textBgColor) {
        this.negativeLabel = negativeLabel;
        this.negativeTextColor = textColorInt;
        this.negativeBgColor = textBgColor;
        return this;
    }


    public PromptDialog setNegativeButton(@StringRes int negativeLabel) {
        return setNegativeButton(ContextUtil.getString(negativeLabel));
    }

    public PromptDialog setNegativeButton(String negativeLabel) {
        this.negativeLabel = negativeLabel;
        return this;
    }

    /**
     * 右边的 （默认确定）
     *
     * @return
     */
    public PromptDialog setPositiveButton(@StringRes int positiveLabel, @ColorRes int textColorInt) {
        return setPositiveButton(ContextUtil.getString(positiveLabel), textColorInt);
    }

    public PromptDialog setPositiveButton(String positiveLabel, @ColorRes int textColorInt) {
        this.positiveLabel = positiveLabel;
        this.positiveTextColor = textColorInt;
        return this;
    }

    public PromptDialog setPositiveButton(String positiveLabel, @ColorRes int textColorInt,@ColorRes int textBgColorInt) {
        this.positiveLabel = positiveLabel;
        this.positiveTextColor = textColorInt;
        this.positiveBgColor = textBgColorInt;
        return this;
    }

    public PromptDialog setPositiveButton(@StringRes int positiveLabel) {
        return setPositiveButton(ContextUtil.getString(positiveLabel));
    }

    public PromptDialog setPositiveButton(String positiveLabel) {
        this.positiveLabel = positiveLabel;
        return this;
    }


    public PromptDialog setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void show() {
        super.show();
    }


    public interface OnButtonClickListener {

        /**
         * 当窗口按钮被点击
         * @param dialog
         * @param isPositiveClick true :PositiveButton点击, false :NegativeButton点击
         */
        void onButtonClick(PromptDialog dialog, boolean isPositiveClick);
    }


}
