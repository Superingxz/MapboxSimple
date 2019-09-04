package com.ruanjie.camp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Moligy
 * @date 2019/8/28.
 */
public class AssetsUtil {
    public static void loadAssetsFile(Context context, String dbFold, String dbname){
        File dbFoldFile = new File(dbFold);
        if (!dbFoldFile.exists()) {
            dbFoldFile.mkdir();
        }

        try {
            File newFile = new File(dbFold, dbname);
            if (newFile.exists()) {
                newFile.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(newFile);
            InputStream in = context.getAssets().open(dbname);
            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, readBytes);
            }
            in.close();
            out.close();
        }catch (IOException e) {
            LogUtil.d("loadAssetsFile",e.getMessage());
        }
    }

    public static void loadAssetsFile(Context context, String dbFold,String filename, String assetname){
        File dbFoldFile = new File(dbFold);
        if (!dbFoldFile.exists()) {
            dbFoldFile.mkdir();
        }

        try {
            File newFile = new File(dbFold, filename);
            FileOutputStream out = new FileOutputStream(newFile);
            InputStream in = context.getAssets().open(assetname);
            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, readBytes);
            }
            in.close();
            out.close();
        }catch (IOException e) {
            LogUtil.d("loadAssetsFile",e.getMessage());
        }
    }

    /**
     * 从asset路径下读取对应文件转String输出
     * @param mContext
     * @return
     */
    public static String getJson(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }

    /**
     * 从assets目录下拷贝整个文件夹，不管是文件夹还是文件都能拷贝
     *
     * @param context
     *            上下文
     * @param rootDirFullPath
     *            文件目录，要拷贝的目录如assets目录下有一个SBClock文件夹：SBClock
     * @param targetDirFullPath
     *            目标文件夹位置如：/sdcrad/SBClock
     */
    public static void copyFolderFromAssets(Context context, String rootDirFullPath, String targetDirFullPath) {
        LogUtil.d("copyFolderFromAssets", "copyFolderFromAssets " + "rootDirFullPath-" + rootDirFullPath + " targetDirFullPath-" + targetDirFullPath);
        try {
            String[] listFiles = context.getAssets().list(rootDirFullPath);// 遍历该目录下的文件和文件夹
            for (String string : listFiles) {// 看起子目录是文件还是文件夹，这里只好用.做区分了
                LogUtil.d("copyFolderFromAssets", "name-" + rootDirFullPath + "/" + string);
                if (isFileByName(string)) {// 文件
                    copyFileFromAssets(context, rootDirFullPath + "/" + string, targetDirFullPath + "/" + string);
                } else {// 文件夹
                    String childRootDirFullPath = rootDirFullPath + "/" + string;
                    String childTargetDirFullPath = targetDirFullPath + "/" + string;
                    new File(childTargetDirFullPath).mkdirs();
                    copyFolderFromAssets(context, childRootDirFullPath, childTargetDirFullPath);
                }
            }
        } catch (IOException e) {
            LogUtil.d("copyFolderFromAssets", "copyFolderFromAssets " + "IOException-" + e.getMessage());
            LogUtil.d("copyFolderFromAssets", "copyFolderFromAssets " + "IOException-" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从assets目录下拷贝文件
     *
     * @param context
     *            上下文
     * @param assetsFilePath
     *            文件的路径名如：SBClock/0001cuteowl/cuteowl_dot.png
     * @param targetFileFullPath
     *            目标文件路径如：/sdcard/SBClock/0001cuteowl/cuteowl_dot.png
     */
    public static void copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {
        LogUtil.d("copyFileFromAssets", "copyFileFromAssets ");
        InputStream assestsFileImputStream;
        try {
            assestsFileImputStream = context.getAssets().open(assetsFilePath);
            FileHelper.copyFile(assestsFileImputStream, targetFileFullPath);
        } catch (IOException e) {
            LogUtil.d("copyFileFromAssets", "copyFileFromAssets " + "IOException-" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean isFileByName(String string) {
        if (string.contains(".")) {
            return true;
        }
        return false;
    }

    /**
     * 判断   SD卡是否可用
     */
    public static boolean isSDCardAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)){
            return false;
        }else{
            return true;
        }
    }
}
