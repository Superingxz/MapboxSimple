package com.ruanjie.camp;

import android.os.Environment;

import java.io.File;

/**
 * @author by DELL
 * @date on 2018/2/6
 * @describe
 */

public class Constants {
    public static final String MD5_KEY = "77788f4699214cu7yyyyyc2a1d513he06";
    public static final String WX_APPID = "";
    public static final int COUNT_DOWN = 5;//倒计时时间
    public static final int GET_CODE_TIME = 60; //获取验证码的倒数时间
    public static String URL;
    public static final String mapbox_token = "pk.eyJ1Ijoic3VwZXJpbmd4eiIsImEiOiJjanhxOG5wbmcwcTR0M21vN3p2aDgwM2k3In0.Nfmz3qsLFUrkJZ0He4RMuA";

    public static String SHARE_URL = "https://www.pgyer.com/camping";

    public static final String HX_PROFIX = "app_";//环信id前缀

    public static final String CS_ROOT = Environment.getExternalStorageDirectory().getPath() + File.separator + "camp" + File.separator + "download" + File.separator;

    public static final String CS_DATA_FOLDER = "data";
    public static final String CS_DATA_FILENAME = "data.json";

    public static final String id0 = "0";
    public static final String id1 = "1";
    public static final String id2 = "2";
    public static final String id3 = "3";
    public static final String id4 = "4";
    public static final String id5 = "5";
    public static final String id6 = "6";
    public static final String id_pos = "00";
    public static final String id_pos_start = "001";
    public static final String id_pos_end = "002";

    public static final String id_ins = "0*";
    public static final String id_ins0 = "00";
    public static final String id_ins1 = "01";
    public static final String id_ins2 = "02";
    public static final String id_ins3 = "03";
    public static final String id_ins4 = "04";
    public static final String id_ins5 = "05";
    public static final String id_ins6 = "06";
    public static final String id_ins7 = "07";
    public static final String id_ins8 = "08";

    public static final String CS_ZIP = CS_ROOT + "zip" + File.separator;      //下载的Zip存放路径
    public static final String CS_ROOT_DOWNLOAD = Environment.getExternalStorageDirectory().getPath() + File.separator + "camp" + File.separator + "download" + File.separator;
    public static final String CS_ROOT_CAMP = Environment.getExternalStorageDirectory().getPath() + File.separator + "camp" + File.separator;
    public static final String CS_ROOT_MAPBOX = Environment.getExternalStorageDirectory().getPath() + File.separator + "camp" + File.separator + "mapbox" + File.separator;

    /**
     * 后台服务 位置定时器 默认时间 1*10秒
     **/
    public static final int SERVICE_LOCATION_TIME = 10;

    /**
     * 后台服务 位置定时器 默认延迟时间 0秒
     **/
    public static final int SERVICE_LOCATION_DELAY_TIME = 0;

    /**
     * 后台服务 位置定时器 默认时间 1*10秒
     **/
    public static final int SERVICE_ORDER_REFRESH_TIME = 5;

    public static long DEFAULT_INTERVAL_IN_MILLISECONDS = 10000L;
    public static long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 10;

    /**
     * 缓存文件夹路径 参数配置
     */
    public final static class CACHE_PATH {

        public final static String SD_DATA = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/camp/data";
        //文件存储位置
        public final static String SD_DOWNLOAD = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/camp/download";
        //日志存储位置
        public final static String SD_LOG = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/camp/log";
        //图片存储位置
        public final static String SD_IMAGE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/camp/image";
        //图片缓存位置
        public final static String SD_IMAGECACHE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/camp/imagecache";
    }
}
