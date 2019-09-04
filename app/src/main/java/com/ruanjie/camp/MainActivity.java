package com.ruanjie.camp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.ruanjie.camp.base.ToolbarActivity;
import com.ruanjie.camp.bean.SpecialTab;
import com.ruanjie.camp.bean.SpecialTabRound;
import com.ruanjie.camp.fragment.HomeFragment;
import com.ruanjie.camp.fragment.MapboxSimpleFragment;
import com.ruanjie.camp.service.HttpService;
import com.ruanjie.camp.service.LocationService;
import com.ruanjie.camp.utils.LogUtil;
import com.softgarden.baselibrary.base.FragmentBaseSimplePagerAdapter;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.EmptyUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.baselibrary.widget.NoScrollViewPager;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

import static com.ruanjie.camp.Constants.CS_ROOT_MAPBOX;
import static com.ruanjie.camp.utils.AssetsUtil.loadAssetsFile;
import static com.ruanjie.camp.utils.FileHelper.copyFolderFromAssets;
import static com.ruanjie.camp.utils.ServiceUtil.isServiceWorked;


/**
 * @author Moligy
 * @date 2019/5/6.
 */
public class MainActivity extends ToolbarActivity implements ViewPager.OnPageChangeListener {
    private NavigationController navigationController;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, int position) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.putExtra("position", position);
        context.startActivity(starter);
    }

    @BindView(R.id.mTabLayout)
    PageNavigationView mTabLayout;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;

    List<Fragment> fragmentList;

    private int prePosition = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            final String pathEn = CS_ROOT_MAPBOX + "data" + File.separator + "world-en.db";
            final String pathZh = CS_ROOT_MAPBOX + "data" + File.separator + "world-cn.db";
            switch (msg.what) {
                case 0:
                    loadOfflineMapDb(MainActivity.this,pathZh);
                    break;
                case 1:
                    loadOfflineMapDb(MainActivity.this,pathEn);
                    break;
                case 2://font sprite

                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected void initialize() {
        initView();
        loadMapboxDb();
        UpdataMapboxLocal();
    }

    private void initView() {
        StatusBarUtil.setImmersiveTransparentStatusBar(this);

        HomeFragment homeFragment = HomeFragment.newInstance();
        MapboxSimpleFragment mapboxSimpleFragment = MapboxSimpleFragment.newInstance();

        FragmentBaseSimplePagerAdapter adapter = new FragmentBaseSimplePagerAdapter(getSupportFragmentManager(),
                homeFragment,mapboxSimpleFragment);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getCount());

        navigationController = mTabLayout.custom()
                .addItem(newItem(R.drawable.home_select, R.drawable.home_select, getString(R.string.home)))
                .addItem(newItem(R.drawable.home_select, R.drawable.home_select, getString(R.string.test)))
                .build();
        navigationController.setupWithViewPager(mViewPager);

        navigationController.addSimpleTabItemSelectedListener((index, old) -> {
            StatusBarUtil.setStatusBarLightMode(getActivity(), StatusBarUtil.setStatusBarLightMode(getActivity()));

//            if (index == 0) {
//                StatusBarUtil.setStatusBarDarkMode(getActivity(), StatusBarUtil.setStatusBarDarkMode(getActivity()));
//            } else {
//                StatusBarUtil.setStatusBarLightMode(getActivity(), StatusBarUtil.setStatusBarLightMode(getActivity()));
//
//            }

            switch (index) {
                case 0:
//                    if (TextUtils.isEmpty(SP.getUserID())) {
//                        mViewPager.setCurrentItem(old);
//                        LoginActivity.star(this);
//                    }
                    break;
                case 1:
//                    if (TextUtils.isEmpty(SP.getUserID())) {
//                        mViewPager.setCurrentItem(old);
//                        LoginActivity.star(this);
//                    }
                    break;
                case 2:
//                    if (TextUtils.isEmpty(SP.getUserID())) {
//                        mViewPager.setCurrentItem(old);
//                        LoginActivity.star(this);
//                    }
                    break;
                case 3:
//                    if (TextUtils.isEmpty(SP.getUserID())) {
//                        mViewPager.setCurrentItem(old);
//                        LoginActivity.star(this);
//                    }
                    break;
            }
        });

        mViewPager.addOnPageChangeListener(this);

        LogUtil.d("service:", "LocationService");
        if (!isServiceWorked(this, "com.ruanjie.camp.service.LocationService")) {
            LogUtil.d("service:", "LocationService");
            startService(new Intent(this, LocationService.class));
        }

        LogUtil.d("service:", "HttpService");
        if (!isServiceWorked(this, "com.ruanjie.camp.service.HttpService")) {
            LogUtil.d("service:", "HttpService");
            startService(new Intent(this, HttpService.class));
        }
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
                    loadOfflineMapDb(MainActivity.this,pathEn);
                }
            } else if (isEqualsLanguage(Locale.SIMPLIFIED_CHINESE, mLanguage)) {//中文版
                if (!new File(pathZh).exists()) {
                    loadAssetsFile(this, CS_ROOT_MAPBOX, "world-cn.db");
                    mHandler.sendEmptyMessage(0);
                } else {
                    loadOfflineMapDb(MainActivity.this,pathZh);
                }
            }
        }).start();
    }

    private void UpdataMapboxLocal() {
        loadAssetsFile(this,CS_ROOT_MAPBOX, "mapboxStyle-cn.json");
        loadAssetsFile(this,CS_ROOT_MAPBOX, "mapboxStyle-en.json");

        copyFolderFromAssets(MainActivity.this, "mapbox" + File.separator + "font",
                CS_ROOT_MAPBOX  + File.separator + "font");
//        FileHelper.copyFolderFromAssets(MainActivity.this, "sprite",
//                CS_ROOT_MAPBOX + "sprite");
        loadAssetsFile(this, CS_ROOT_MAPBOX + "sprite", "sprite.json", "sprite/sprite.json");
        loadAssetsFile(this, CS_ROOT_MAPBOX + "sprite", "sprite.png", "sprite/sprite.png");
        loadAssetsFile(this, CS_ROOT_MAPBOX + "sprite", "sprite@2x.json", "sprite/sprite@2x.json");
        loadAssetsFile(this, CS_ROOT_MAPBOX + "sprite", "sprite@2x.png", "sprite/sprite@2x.png");
        loadAssetsFile(this, CS_ROOT_MAPBOX + "sprite" + File.separator + "sprite", "sprite@2x.json", "sprite/sprite@2x.json");
        loadAssetsFile(this, CS_ROOT_MAPBOX + "sprite" + File.separator + "sprite", "sprite@2x.png", "sprite/sprite@2x.png");
    }

    private void loadOfflineMapDb(Context context,String path) {
        File file = new File(path);
        if (file.exists()) {
            OfflineManager.getInstance(context).mergeOfflineRegions(path, new OfflineManager.MergeOfflineRegionsCallback() {
                @Override
                public void onMerge(OfflineRegion[] offlineRegions) {
                    if (offlineRegions.length > 0) {
                        List<OfflineRegion> offlineRegionsList = Arrays.asList(offlineRegions);
                        if (EmptyUtil.isNotEmpty(offlineRegionsList)) {
                            for (int i = 0; i < offlineRegionsList.size(); i++) {
                                OfflineRegion offlineRegion = offlineRegionsList.get(i);
                                long id = offlineRegion.getID();
                                LogUtil.d("onMerge", String.valueOf(id));
                            }
                        }
                    }
                }

                @Override
                public void onError(String error) {
                    LogUtil.d("onError", error);
                }
            });
        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SP.getIsLogin()) {
            loadData();
        }
    }

    private void loadData() {

    }


    /**
     * 再按一次退出程序
     */
    private long currentBackPressedTime = 0;
    private static int BACK_PRESSED_INTERVAL = 5000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtil.s("再按一次，退出应用！");
                return true;
            } else {
                finish(); // 退出
            }
            return false;

        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 正常tab
     */
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        SpecialTab mainTab = new SpecialTab(this);
        mainTab.initialize(drawable, checkedDrawable, text);
        mainTab.setTextDefaultColor(getResources().getColor(R.color.grayLightText));
        mainTab.setTextCheckedColor(getResources().getColor(R.color.blueLightText));
        return mainTab;
    }

    /**
     * 圆形tab
     */
    private BaseTabItem newRoundItem(int drawable, int checkedDrawable, String text) {
        SpecialTabRound mainTab = new SpecialTabRound(this);
        mainTab.initialize(drawable, checkedDrawable, text);
        mainTab.setTextDefaultColor(getResources().getColor(R.color.grayLightText));
        mainTab.setTextCheckedColor(getResources().getColor(R.color.blueLightText));
        return mainTab;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setCurrentItem(int position) {
        navigationController.setSelect(position);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("position", 0);
        mViewPager.setCurrentItem(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isServiceWorked(this,"com.ruanjie.camp.service.LocationService")) {
            stopService(new Intent(this, LocationService.class));
        }
        if (isServiceWorked(this, "com.ruanjie.camp.service.HttpService")) {
            stopService(new Intent(this, HttpService.class));
        }
    }

}
