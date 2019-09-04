package com.ruanjie.camp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.ruanjie.camp.bean.UserBean;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.EmptyUtil;
import com.softgarden.baselibrary.utils.SPUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by DELL
 * @date on 2018/7/31
 * @describe
 */
public class SPManager extends BaseSPManager {
    public static final String CARMEN_FEATURE = "CarmenFeature";
    public static final String USERBEAN = "userBean";
    public static final String SearchHistory = "SearchHistory";
    public static final String PHONE = "phone";
    public static final String Password = "Password";
    public static final String Location = "location_info";
    public static final String Draft = "Draft";
    public static final String UnMsgCount = "UnMsgCount";
    public static final String OuYiId = "OuYiId";
    public static final String CacheMediaId = "cacheMediaId";

    public static final String MAP_FILTER_OPTION_NUM = "map_filter_option";//地图筛选条件数目

    /*** 用户资料*/
    public static UserBean getUserBean() {
        return (UserBean) SPUtil.getSerializableObject(USERBEAN);
    }


    public static void saveUserBean(UserBean bean) {
        SPUtil.putSerializableObject(USERBEAN, bean);
    }

    /*** 用户资料*/
    public static CarmenFeature getCarmenFeature() {
        return (CarmenFeature) SPUtil.getSerializableObject(CARMEN_FEATURE);
    }


    public static void saveCarmenFeature(CarmenFeature bean) {
        SPUtil.putSerializableObject(CARMEN_FEATURE, bean);
    }

    public static String getUid() {
        if (getUserBean() != null) {
            return getUserBean().getUser_id();
        } else {
            return "0";
        }
    }

    public static void clearUserBean() {
        SPUtil.putSerializableObject(USERBEAN, null);
        putCacheMediaId(0L);//更换用户 从0开始
    }

    /*** 搜索历史*/
    public static List<String> getSearchHistory() {
        String json = (String) SPUtil.get(SearchHistory + SPManager.getUid(), "");
        if (EmptyUtil.isEmpty(json)) return null;
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    public static void saveSearchHistory(String content) {
        if (EmptyUtil.isEmpty(content)) return;
        List<String> list = getSearchHistory();
        if (EmptyUtil.isEmpty(list)) {
            list = new ArrayList<>();
        }
        if (list.contains(content)) {
            list.remove(content);
        }
        list.add(content);

        SPUtil.put(SearchHistory + SPManager.getUid(), new Gson().toJson(list));
    }

    public static void clearSearchHistory() {
        SPUtil.put(SearchHistory + SPManager.getUid(), null);
    }

    /*** 手机号码 传递太多繁琐 直接存到本地*/
    public static void putPhone(String phone) {
        SPUtil.put(PHONE, phone);
    }

    public static String getPhone() {
        return (String) SPUtil.get(PHONE, "");
    }

    /*** 偶易号 */
    public static void putOuYiId(int value) {
        SPUtil.put(OuYiId, value);
    }

    public static int getOuYiId() {
        return (int) SPUtil.get(OuYiId, 0);
    }

    /*** 首页加载视频 缓存id */
    public static void putCacheMediaId(long value) {
        SPUtil.put(CacheMediaId, value);

    }
    public static long getCacheMediaId() {
        return (long) SPUtil.get(CacheMediaId, 0L);
    }

    /*** 未读消息  仅保存的极光的 IM的自己有查询 */
    public static void putUnMsgCount(int value) {
        SPUtil.put(UnMsgCount, value);
    }

    public static int getUnMsgCount() {
        return (int) SPUtil.get(UnMsgCount, 0);
    }

    /*** 定位信息 直接存到本地*/

    public static void putMapFilterNum(int value) {
        SPUtil.put(MAP_FILTER_OPTION_NUM, value);
    }

    public static int getMapFilterNum() {
        return (int) SPUtil.get(MAP_FILTER_OPTION_NUM, 0);
    }
}
