package com.ruanjie.camp.serve;

import com.google.gson.Gson;
import com.ruanjie.camp.App;
import com.ruanjie.camp.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

import static com.ruanjie.camp.Constants.CS_ROOT_CAMP;

/**
 * @author Moligy
 * @date 2019/8/30.
 */
public class CampNanoHttpPD extends NanoHTTPD {
    public CampNanoHttpPD(int port){
        super(port);
        LogUtil.d("CampNanoHttpPD", "\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    public CampNanoHttpPD(){
        super(8080);
        LogUtil.d("CampNanoHttpPD", "\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> header, Map<String, String> parameters,
                          Map<String, String> files) {
        LogUtil.d("CampNanoHttpPD访问地址:", uri);
        LogUtil.d("CampNanoHttpPDparameters",new Gson().toJson(parameters));
        LogUtil.d("CampNanoHttpPDfiles",new Gson().toJson(files));
        String mimeType = "file/*";
        String pathname = CS_ROOT_CAMP + uri;

        //判断本地文件是否存在
        LogUtil.d("CampNanoHttpPDLocal", String.format("本地文件:%s%s", pathname,
                new File(pathname).exists() ? "存在" : "不存在"));
        //判断Asset资源文件是否存在
        LogUtil.d("CampNanoHttpPDAsset", String.format("Asset文件:%s%s", "file:///android_assets" + uri,
                new File("file:///android_assets" + uri).exists() ? "存在" : "不存在"));

        File file = new File(pathname);
        FileInputStream fis = null;
        InputStream assetFis = null;
        try {
            fis = new FileInputStream(file);
            assetFis = App.getInstance().getAssets().open(uri.substring(1));
            mimeType = URLConnection.guessContentTypeFromStream(fis);
        } catch (FileNotFoundException e) {
            LogUtil.d("CampNanoHttpPD", e.getMessage());
        } catch (IOException e) {
            LogUtil.d("CampNanoHttpPD", e.getMessage());
        }
        return new Response(Response.Status.OK, mimeType, fis);
    }
}
