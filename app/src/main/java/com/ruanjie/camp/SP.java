package com.ruanjie.camp;

import com.softgarden.baselibrary.BaseApplication;
import com.softgarden.baselibrary.utils.SPUtil;

/**
 * Desc sp管理类
 * Author feng
 * Date   2017/7/19.
 */

public class SP {
    private static final String IS_FIRST_ENTRY = "isFirstEntry";//是否第一次进去app
    private static final String IS_FIRST_LOGIN = "is_first_login";//是否第一次登陆
    private static final String IS_OTHER_LOGIN = "otherLogin";//信账号在其他设备登录
    private static final String ENTRY_TIME = "entry_time";//进入app次数
    private static final String BASE_INFO = "baseInfo";
    private static final String LANGUAGE = "language";//语言环境
    private static final String MAC = "mac";

    private static final String ID="id";//id
    private static final String USER_ID = "userId"; //用户id
    private static final String APP_ID = "appId"; //用户id

    private static final String IMUSERNAME = "IMUSERNAME";//环信账号
    private static final String IMPASSWORD = "IMPASSWORD";//环信密码

    private static final String EASE_DISTURB= "no_disturb";//是否免干扰

    private static final String IS_OPEN_PUSH= "is_open_push";//是否开启消息推送

    private static final String TOURIST_ID = "touristId";//游客id
    //    private static final String PHONE = "phone"; //用户id
    private static final String USER_NAME = "userName"; //用户名
    private static final String USER_REALNAME = "realname";//真实姓名
    private static final String USER_NICKNAME = "nickname";//昵称
    private static final String IS_LOGIN = "isLogin"; //是否已登录
    private static final String IS_VIP = "is_vip";//是否是会员
    private static final String IS_PROPRIETARY = "is_proprietary";//是否自营 1:是,0:否
    private static final String TOKEN = "token";
    private static final String HEAD_IMG = "headImg"; //头像
    private static final String MOBILE = "mobile";//手机号码
    private static final String EMAIL = "email";//邮箱
    private static final String SCORE = "score";//分数
    private static final String SEX = "sex";//性别
    private static final String SIGNATURE = "signature.";//签名
    private static final String MERCHANT_ID = "merchant_id";//店铺id
    private static final String IDENTITY = "identity";//身份
    private static final String DRIVER_CHECK_STATUS = "driver_check_status";//车辆审核状态(0:未审核,1:已审核,2:不通过,9:未提交)
    private static final String CHECK_STATUS = "check_status";//认证状态:0:未验证,1=待审核,2=通过,3=拒绝
    private static final String AUTH = "auth";//认证(-1：未认证，0：待审核，1：审核通过，2：审核不通过)
    private static final String LEVEL = "level";//等级(-1：，0: 1: 2: 3: 4:)
    private static final String LEVEL_VIP = "level_vip";//等级(-1：，0:1:2:3:4:5:6:7)
    private static final String LEVEL_VIP_NAME = "level_vip_name";//等级(-1：，0:1:2:3:4:5:6:7)
    private static final String VIP_END_TIME = "vip_end_time";//VIP结束时间
    private static final String IS_BIND_WECHAT = "is_bind_wechat";//绑定微信
    private static final String IS_BIND_QQ = "is_bind_qq";//绑定QQ

    private static final String SELECT_CITY_ID = "select_city_id"; //选择城市的id
    private static final String LOCATION_LONGITUDE = "location_longitude";//定位经度
    private static final String LOCATION_LATITUDE = "";//定位纬度
    private static final String LOCATION_ADDRESS = "location_address";//定位地址
    private static final String SELECT_CITY = "select_city"; //手动选择城市 优先取这个
    private static final String SELECT_CITY_CODE = "select_city_code"; //手动选择城市编码 优先取这个
    private static final String LOCATION_PROVINCE = "location_province"; //定位省份
    private static final String LOCATION_PROVINCE_CODE = "location_province_code"; //定位省份
    private static final String LOCATION_CITY = "location_city"; //定位城市
    private static final String LOCATION_CITY_DISTRICT = "location_city_district"; //定位城市区
    private static final String LOCATION_DISTRICT = "location_city_district"; //定位城市区
    private static final String LOCATION_DISTRICT_CODE = "location_city_district_code"; //定位城市区

    public static int getEntryTime() {
        return (int) SPUtil.get(ENTRY_TIME, 0);
    }

    public static void setEntryTime(int entryTime) {
        SPUtil.put(ENTRY_TIME, entryTime);
    }

    public static boolean isFirstLogin() {
        return (boolean) SPUtil.get(IS_FIRST_LOGIN, true);
    }

    public static void setFirstLogin(boolean isFirstLogin) {
        SPUtil.put(IS_FIRST_LOGIN, isFirstLogin);
    }



    public static String getLanguage() {
        return (String) SPUtil.get(LANGUAGE, "");
    }

    public static void setMac(String mac) {
        SPUtil.put(MAC, mac);
    }

    public static String getMac() {
        return (String) SPUtil.get(MAC, "");
    }

    public static void setLanguage(String language) {
        SPUtil.put(LANGUAGE, language);
    }

    public static String getId() {
        return (String) SPUtil.get(ID, "");
    }

    public static void setId(String userId) {
        SPUtil.put(USER_ID, userId);
    }

    public static String getUserID() {
        return (String) SPUtil.get(USER_ID, "");
    }

    public static void seteUserID(String userId) {
        SPUtil.put(USER_ID, userId);
    }

    public static int getCheckStatus() {
        return (int) SPUtil.get(CHECK_STATUS, 0);
    }

    public static void setCheckStatus(String checkStatus) {
        SPUtil.put(CHECK_STATUS, checkStatus);
    }

    public static int getDriverCheckStatus() {
        return (int) SPUtil.get(DRIVER_CHECK_STATUS, 0);
    }

    public static void setDriverCheckStatus(int driverCheckStatus) {
        SPUtil.put(DRIVER_CHECK_STATUS, driverCheckStatus);
    }

    public static int getAuth() {
        return (int) SPUtil.get(AUTH, -1);
    }

    public static void setAuth(int auth) {
        SPUtil.put(AUTH, auth);
    }

    public static int getLevel() {
        return (int) SPUtil.get(LEVEL, 1);
    }

    public static void setLevel(int level) {
        SPUtil.put(LEVEL, level);
    }

    public static int getLevelVip() {
        return (int) SPUtil.get(LEVEL_VIP, 0);
    }

    public static void setLevelVip(int levelVip) {
        SPUtil.put(LEVEL_VIP, levelVip);
    }

    public static void setLevelVipName(String levelVipName) {
        SPUtil.put(LEVEL_VIP_NAME, levelVipName);
    }

    public static String getLevelVipName() {
        return (String) SPUtil.get(LEVEL_VIP_NAME, "");
    }

    public static void setVipEndTime(Long vipEndTime) {
        SPUtil.put(VIP_END_TIME, vipEndTime);
    }

    public static long getVipEndTime() {
        return (Long) SPUtil.get(VIP_END_TIME, 0L);
    }

    public static void setIsBindWechat(int isBindWechat) {
        SPUtil.put(IS_BIND_WECHAT, isBindWechat);
    }

    public static int getIsBindWechat() {
        return (int) SPUtil.get(IS_BIND_WECHAT, 0);
    }

    public static void setIsBindQq(int isBindQq) {
        SPUtil.put(IS_BIND_QQ, isBindQq);
    }

    public static int getIsBindQQ() {
        return (int) SPUtil.get(IS_BIND_QQ, 0);
    }

    public static String getAppId() {
        return (String) SPUtil.get(APP_ID, "");
    }

    public static void setAppId(String appId) {
        SPUtil.put(APP_ID, appId);
    }

    public static String getImUserName() {
        return (String) SPUtil.get(IMUSERNAME, "");
    }

    public static void setImUserName(String imUserName) {
        SPUtil.put(IMUSERNAME, imUserName);
    }

    public static String getImPassword() {
        return (String) SPUtil.get(IMPASSWORD, "");
    }

    public static void setImPassword(String imPassword) {

        SPUtil.put(IMPASSWORD, imPassword);
    }

    public static boolean isEaseAvoidDisturb() {
        return (boolean) SPUtil.get(EASE_DISTURB, false);
    }

    public static void setEaseAvoidDisturb(boolean isEaseDisturb) {
        SPUtil.put(EASE_DISTURB, isEaseDisturb);
    }

    public static String getTouristID() {
        return (String) SPUtil.get(TOURIST_ID, "");
    }

    public static void setTouristID(String touristID) {
        SPUtil.put(TOURIST_ID, touristID);
    }

    public static boolean IsFirstEntry() {
        return (boolean) SPUtil.get(IS_FIRST_ENTRY, false);
    }

    public static void setIsFirstEntry(boolean isFirstEntry) {
        SPUtil.put(IS_FIRST_ENTRY, isFirstEntry);
    }

    public static boolean getIsLogin() {
        return (boolean) SPUtil.get(IS_LOGIN, false);
    }

    public static void setIsLogin(boolean isLogin) {
        SPUtil.put(IS_LOGIN, isLogin);
    }

    public static boolean getIsOtherLogin() {
        return (boolean) SPUtil.get(IS_OTHER_LOGIN, false);
    }

    public static void setIsOtherLogin(boolean isOtherLogin) {
        SPUtil.put(IS_OTHER_LOGIN, isOtherLogin);
    }

    public static int getIsProprietary() {
        return (int) SPUtil.get(IS_PROPRIETARY, 0);
    }

    public static void setIsProprietary(int isProprietary) {
        SPUtil.put(IS_PROPRIETARY, isProprietary);
    }

    public static boolean IsOpenPush() {
        return (boolean) SPUtil.get(IS_OPEN_PUSH, true);
    }

    public static void setIsOpenPush(boolean isOpenPush) {
        SPUtil.put(IS_OPEN_PUSH, isOpenPush);
    }

    public static String getToken() {
        return (String) SPUtil.get(TOKEN, "");
    }

    public static void setToken(String token) {
        SPUtil.put(TOKEN, token);
    }

    public static String getUserName() {
        return (String) SPUtil.get(USER_NAME, "");
    }

    public static void setUserName(String userName) {
        SPUtil.put(USER_NAME, userName);
    }

    public static String getRealName() {
        return (String) SPUtil.get(USER_REALNAME, "");
    }

    public static void setRealName(String realname) {
        SPUtil.put(USER_REALNAME, realname);
    }

    public static String getUserNickname() {
        return (String) SPUtil.get(USER_NICKNAME, "");
    }

    public static void setUserNickname(String mobile) {
        SPUtil.put(USER_NICKNAME, mobile);
    }

    public static String getMobile() {
        return (String) SPUtil.get(MOBILE, "");
    }

    public static void setMobile(String mobile) {
        SPUtil.put(MOBILE, mobile);
    }

    public static String getEmail() {
        return (String) SPUtil.get(EMAIL, "");
    }

    public static void setEmail(String email) {
        SPUtil.put(EMAIL, email);
    }


    public static String getHeadImg() {
        return (String) SPUtil.get(HEAD_IMG, "");
    }

    public static void setHeadImg(String headImg) {
        SPUtil.put(HEAD_IMG, headImg);
    }

    public static String getScore() {
        return (String) SPUtil.get(SCORE, "");
    }

    public static void setScore(String score) {
        SPUtil.put(SCORE, score);
    }

    public static String getSex() {
        return (String) SPUtil.get(SEX, "");
    }

    public static void setSex(String sex) {
        SPUtil.put(SEX, sex);
    }

    public static String getSignature() {
        return (String) SPUtil.get(SIGNATURE, "");
    }

    public static void setSignature(String signature) {
        SPUtil.put(SIGNATURE, signature);
    }

    /**
     * 手动选择的城市 -- 名称
     *
     * @return
     */
    public static String getSelectCity() {
        return (String) SPUtil.get(SELECT_CITY, "");
    }

    public static void putSelectCity(String selectCity) {
        SPUtil.put(SELECT_CITY, selectCity);
    }

    /**
     * 手动选择的城市 -- 名称
     *
     * @return
     */
    public static String getSelectCityCode() {
        return (String) SPUtil.get(SELECT_CITY_CODE, "");
    }

    public static void putSelectCityCode(String selectCityCode) {
        SPUtil.put(SELECT_CITY_CODE, selectCityCode);
    }

    /**
     * 手动选择的城市  -- id
     *
     * @return
     */
    public static String getSelectCityId() {
        return (String) SPUtil.get(SELECT_CITY_ID, "");
    }

    public static void putSelectCityId(String selectCityId) {
        SPUtil.put(SELECT_CITY_ID, selectCityId);
    }

    public static String getLocationLongtude() {
        String longtude = (String) SPUtil.get(LOCATION_LONGITUDE, "0.00");
        return longtude;
    }

    public static void putLocationLongtude(String locationLongtude) {
        SPUtil.put(LOCATION_LONGITUDE, locationLongtude);
    }

    public static String getLocationLatitude() {
        String latitude = (String) SPUtil.get(LOCATION_LATITUDE, "0.00");
        return latitude;
    }

    public static void putLocationLatitude(String locationLatitude) {
        SPUtil.put(LOCATION_LATITUDE, locationLatitude);
    }

    public static String getLocationAddress() {
        String address = (String) SPUtil.get(LOCATION_ADDRESS, "");
        return address;
    }

    public static void putLocationAddress(String locationAddress) {
        SPUtil.put(LOCATION_ADDRESS, locationAddress);
    }

    /**
     * 自动定位的省
     *
     * @return
     */
    public static String getLocationProvince() {
        return (String) SPUtil.get(LOCATION_PROVINCE, "");
    }

    public static void putLocationProvince(String locationProvince) {
        SPUtil.put(LOCATION_PROVINCE, locationProvince);
    }

    /**
     * 自动定位的省
     *
     * @return
     */
    public static String getLocationProvinceCode() {
        return (String) SPUtil.get(LOCATION_PROVINCE_CODE, "");
    }

    public static void putLocationProvinceCode(String locationProvinceCode) {
        SPUtil.put(LOCATION_PROVINCE_CODE, locationProvinceCode);
    }


    /**
     * 自动定位的城市
     *
     * @return
     */
    public static String getLocationCity() {
        return (String) SPUtil.get(LOCATION_CITY, "");
    }

    public static void putLocationCity(String locationCity) {
        SPUtil.put(LOCATION_CITY, locationCity);
    }

    /**
     * 自动定位的城市  -- 的区域
     *
     * @return
     */
    public static String getLocationCityDistrict() {
        return (String) SPUtil.get(LOCATION_CITY_DISTRICT, BaseApplication.getInstance().getString(R.string.location_city));
    }

    public static void putLocationCityDistrict(String locationCityDistrict) {
        SPUtil.put(LOCATION_CITY_DISTRICT, locationCityDistrict);
    }

    /**
     * 自动定位的城市  -- 的区域
     *
     * @return
     */
    public static String getLocationDistrict() {
        return (String) SPUtil.get(LOCATION_DISTRICT, "");
    }

    public static void putLocationDistrict(String locationDistrict) {
        SPUtil.put(LOCATION_DISTRICT, locationDistrict);
    }

    /**
     * 自动定位的城市  -- 的区域
     *
     * @return
     */
    public static String getLocationDistrictCode() {
        return (String) SPUtil.get(LOCATION_DISTRICT_CODE, "");
    }

    public static void putLocationDistrictCode(String locationDistrictCode) {
        SPUtil.put(LOCATION_DISTRICT_CODE, locationDistrictCode);
    }

    public static String getInfo() {
        return (String) SPUtil.get(BASE_INFO, "");
    }

    public static void setInfo(String info) {
        SPUtil.put(BASE_INFO, info);
    }
}
