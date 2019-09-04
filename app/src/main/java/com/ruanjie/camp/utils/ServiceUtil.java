package com.ruanjie.camp.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * @author Moligy
 * @date 2019/5/19.
 */
public class ServiceUtil {
    /**
     * 判断自己些的一个Service是否已经运行
     */
    public static boolean isServiceWorked(Context context, String SerbiceName) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(200);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(SerbiceName)) {
                return true;
            }
        }
        return false;
    }
}
