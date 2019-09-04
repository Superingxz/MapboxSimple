package com.ruanjie.camp;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.mapbox.mapboxsdk.Mapbox;
import com.pgyersdk.crash.PgyCrashManager;
import com.softgarden.baselibrary.BaseApplication;

/**
 * @author Moligy
 * @date 2019/7/8.
 */
public class App extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupDataBase(this);

        //init demo helper
        PgyCrashManager.register(this);

        initUmeng();

        initMapbox();
    }

    private void setupDataBase(Context context) {

    }

    private void initUmeng() {

    }

    private void initMapbox() {
        Mapbox.getInstance(this, "pk.eyJ1Ijoic3VwZXJpbmd4eiIsImEiOiJjanhxOG5wbmcwcTR0M21vN3p2aDgwM2k3In0.Nfmz3qsLFUrkJZ0He4RMuA");
    }
}
