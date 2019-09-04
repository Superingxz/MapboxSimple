package com.ruanjie.camp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.camp.R;
import com.ruanjie.camp.activity.LocationComponentActivity;
import com.ruanjie.camp.activity.MapboxSimpleActivity;
import com.ruanjie.camp.base.AppBaseToolbarFragment;
import com.ruanjie.camp.utils.LogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ruanjie.camp.Constants.CS_ROOT_DOWNLOAD;

/**
 * @author Moligy
 * @date 2019/7/11.
 */
public class HomeFragment extends AppBaseToolbarFragment {
    @BindView(R.id.tv_map_simple)
    AppCompatTextView tvMapSimple;
    @BindView(R.id.tv_map_marker)
    AppCompatTextView tvMapMarker;
    @BindView(R.id.tv_map_polyline)
    AppCompatTextView tvMapPolyline;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("Mapbox Main");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mapbox_main;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.tv_map_simple,
            R.id.tv_map_location_component,
            R.id.tv_map_marker,
            R.id.tv_map_polyline,
            R.id.tv_test_mapbox_download_file})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_map_simple:
                MapboxSimpleActivity.start(mContext);
                break;
            case R.id.tv_map_location_component:
                LocationComponentActivity.start(mContext);
                break;
            case R.id.tv_map_marker:
                break;
            case R.id.tv_map_polyline:
                break;
            case R.id.tv_test_mapbox_download_file:
                // url is the URL to download.
                AsyncHttpClient.getDefaultInstance().executeFile(new AsyncHttpGet(file_url_pbf),
                        CS_ROOT_DOWNLOAD + "download.pbf", new AsyncHttpClient.FileCallback() {
                            @Override
                            public void onCompleted(Exception e, AsyncHttpResponse source, File result) {
                                if (e != null) {
                                    Log.d("errorLog", e.getMessage());
                                }
                                if (result != null) {
                                    LogUtil.d("FileCallback", result.getAbsolutePath());
                                }
                            }
                        });
                break;
        }
    }

    //192.168.137.1041
    private String image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567077894577&di=e9d31dae261063301b417fa25efa3dd3&imgtype=0&src=http%3A%2F%2Fku.90sjimg.com%2Felement_origin_min_pic%2F18%2F07%2F05%2Ff9d1fc8b6b2067bb4e6382d6c8c9715c.jpg";
    private String file_url = "http://localhost:8080/mapbox/sprite/sprite@2x.png";
    private String file_url_pbf = "http://localhost:8080/mapbox/font/Arial%20Unicode%20MS%20Regular/0-255.pbf";
    private String file_url2 = "http://localhost:5050/sprite.png";
}
