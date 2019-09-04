package com.ruanjie.camp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.ruanjie.camp.R;
import com.softgarden.baselibrary.base.BaseActivity;


/**
 * Created by liujinghui on 6/15/16.
 */
public class ProgressUtil {

    ProgressDialog progressDialog;
    Context context;

    public ProgressUtil(Context context) {
        this.context = context;
    }

    public void show(String message) {
        try {
            init(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        try {
            init(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(final String message) {
        ((BaseActivity) context).runOnUiThread(() -> {
                progressDialog = new ProgressDialog(context, R.style.my_progress_dialog_theme);
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressDialog.setMessage(message);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
        });
    }
}
