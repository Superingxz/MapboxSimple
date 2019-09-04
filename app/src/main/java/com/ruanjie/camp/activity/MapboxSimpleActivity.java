package com.ruanjie.camp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.camp.R;
import com.ruanjie.camp.SP;
import com.ruanjie.camp.base.BaseMapboxActivity;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.ToastUtil;

import java.io.File;
import java.util.Locale;

import static com.ruanjie.camp.Constants.CS_ROOT_MAPBOX;
import static com.ruanjie.camp.utils.AssetsUtil.loadAssetsFile;

/**
 * @author Moligy
 * @date 2019/7/8.
 */
public class MapboxSimpleActivity extends BaseMapboxActivity {
    protected static final String ID_ICON_LOCATION = "camp_marker1";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            final String pathEn = CS_ROOT_MAPBOX + "data" + File.separator + "world-en.db";
            final String pathZh = CS_ROOT_MAPBOX + "data" + File.separator + "world-cn.db";
            switch (msg.what) {
                case 0:
                    loadOfflineMapDb(MapboxSimpleActivity.this,pathZh);
                    break;
                case 1:
                    loadOfflineMapDb(MapboxSimpleActivity.this,pathEn);
                    break;
                case 2://font sprite

                    break;
            }
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, MapboxSimpleActivity.class);
//        starter.putExtra("");
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("Mapbox Simple");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mapbox;
    }

    @Override
    protected void initialize() {
        super.initialize();
        initView();
        loadMapboxDb();
        loadData();
    }

    /**
     * 加载离线全球地图
     */
    private void loadMapboxDb() {
        final String pathEn = CS_ROOT_MAPBOX + "data" + File.separator + "world-en.db";
        final String pathZh = CS_ROOT_MAPBOX + "data" + File.separator + "world-cn.db";
        //加载Assets Db
        new Thread(() -> {
            Locale mLanguage = BaseSPManager.getLanguage();
            if (isEqualsLanguage(Locale.ENGLISH, mLanguage)) {//英文版
                if (!new File(pathEn).exists()) {
                    loadAssetsFile(this, CS_ROOT_MAPBOX, "world-en.db");
                    mHandler.sendEmptyMessage(1);
                } else {
                    loadOfflineMapDb(MapboxSimpleActivity.this,pathEn);
                }
            } else if (isEqualsLanguage(Locale.SIMPLIFIED_CHINESE, mLanguage)) {//中文版
                if (!new File(pathZh).exists()) {
                    loadAssetsFile(this, CS_ROOT_MAPBOX, "world-cn.db");
                    mHandler.sendEmptyMessage(0);
                } else {
                    loadOfflineMapDb(MapboxSimpleActivity.this,pathZh);
                }
            }
        }).start();
    }

    @Override
    protected void initData() {
        mStyle.addImage(ID_ICON_LOCATION,getResources().getDrawable(R.drawable.camp_marker1));
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
        if (symbolManager != null) {
            symbolManager.addClickListener(this);
        }
    }

    @Override
    protected void loadData() {
        SymbolOptions symbolOptions = new SymbolOptions()
                .withLatLng(new LatLng(Double.valueOf(SP.getLocationLatitude()), Double.valueOf(SP.getLocationLongtude())))
                .withIconImage(ID_ICON_LOCATION)
                .withIconSize(1.3f)
                .withSymbolSortKey(10.0f)
                .withData(new JsonElement() {
                    @Override
                    public JsonElement deepCopy() {
                        return null;
                    }
                });

        if (symbolManager != null) {
            symbol_location = symbolManager.create(symbolOptions);
        }
    }

    @Override
    protected void mapStyleChange() {

    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    @Override
    public void onAnnotationClick(Symbol symbol) {
        ToastUtil.s("点击" + symbol.getIconImage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
