package com.ruanjie.camp.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ruanjie.camp.SP;
import com.ruanjie.camp.utils.LogUtil;
import com.ruanjie.camp.utils.SPManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ruanjie.camp.Constants.DEFAULT_INTERVAL_IN_MILLISECONDS;
import static com.ruanjie.camp.Constants.DEFAULT_MAX_WAIT_TIME;
import static com.ruanjie.camp.Constants.mapbox_token;

/**
 * @author Moligy
 * @date 2019/7/9.
 */
public class LocationService extends Service implements LocationEngineCallback<LocationEngineResult>, PermissionsListener {
    private static final String TAG = "DldLocationService";
    private IBinder binder = new LocationService.LocalBinder();

    protected PermissionsManager permissionsManager;

    private Timer timerCheckUpdatedz = null;
    private LocationEngine locationEngine;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    // 定义内容类继承Binder
    public class LocalBinder extends Binder {

        // 返回本地服务

        LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public void onCreate() {
        initLocation();

        if (timerCheckUpdatedz == null)
            timerCheckUpdatedz = new Timer();

        super.onCreate();
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        timerCheckUpdatedz.schedule(timerTaskUpdatedz,//监听方法
//                Constants.SERVICE_LOCATION_DELAY_TIME * 1000,//开始时 延迟时间
//                Constants.SERVICE_LOCATION_TIME * 1000);//执行间隔时间
        //START_STICKY:兼容模式service异常关闭后系统自动重启
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    /**
     * 定时上传地址
     */
    private TimerTask timerTaskUpdatedz = new TimerTask() {
        public void run() {
            /*provider：有三种定位Provider供用户选择，分别是:LocationManagerProxy.GPS_PROVIDER，代表使用手机GPS定位；LocationManagerProxy.NETWORK_PROVIDER，代表使用手机网络定位；LocationProviderProxy.AMapNetwork，代表高德网络定位服务，混合定位。
             *minTime：位置变化的通知时间，单位为毫秒。如果为-1，定位只定位一次。时间最短是2000毫秒
             *minDistance:位置变化通知距离，单位为米。
             *listener:定位监听者
             */
            startLocation();
//            if (RxPermissionsUtil.checkLocationCoarse(DldLocationService.this)) {
//                startLocation();
//            }else {
//                ToastUtil.s("获取定位信息失败,请先前往设置-应用管理-" + getResources().getString(R.string.app_name) + "权限-您的位置");
//            }
        }
    };

    private void startLocation() {

    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation() {
        // 停止定位
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void initLocation() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Get an instance of the component
            LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                    .setPriority(LocationEngineRequest.PRIORITY_NO_POWER)
                    .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
                    .build();


            locationEngine = LocationEngineProvider.getBestLocationEngine(this);

            locationEngine.requestLocationUpdates(request, this, getMainLooper());
        }
    }

    PermissionsListener permissionsListener = new PermissionsListener() {
        @Override
        public void onExplanationNeeded(List<String> permissionsToExplain) {

        }

        @Override
        public void onPermissionResult(boolean granted) {
            if (granted) {

                // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location

                LocationEngine locationEngine = LocationEngineProvider.getBestLocationEngine(LocationService.this);

                LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                        .setPriority(LocationEngineRequest.PRIORITY_NO_POWER)
                        .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
                        .build();

                if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationEngine.getLastLocation(LocationService.this);
            } else {
                // User denied the permission

            }
        }
    };

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public void onSuccess(LocationEngineResult result) {
        Location lastLocation = result.getLastLocation();
        if (lastLocation != null) {
            getAddressInfoByLatLong(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
        }
    }

    private void getAddressInfoByLatLong(final LatLng latLng) {
        SP.putLocationLatitude(String.valueOf(latLng.getLatitude()));
        SP.putLocationLongtude(String.valueOf(latLng.getLongitude()));

        try {
            // Build a Mapbox geocoding request
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(mapbox_token)
                    .query(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()))
                    .autocomplete(true)
                    .languages(SPManager.getLanguage())
//                    .geocodingTypes(GeocodingCriteria.TYPE_COUNTRY,
//                            GeocodingCriteria.TYPE_ADDRESS,
//                            GeocodingCriteria.TYPE_REGION,
//                            GeocodingCriteria.TYPE_POSTCODE,
//                            GeocodingCriteria.TYPE_DISTRICT,
//                            GeocodingCriteria.MODE_PLACES,
//                            GeocodingCriteria.TYPE_LOCALITY,
//                            GeocodingCriteria.TYPE_NEIGHBORHOOD)
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call,
                                       Response<GeocodingResponse> response) {
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    LogUtil.e("Geocoding Failure: " + throwable.getMessage());
                }
            });
        } catch (ServicesException servicesException) {
            LogUtil.e("Error geocoding: " + servicesException.toString());
        }
    }

    @Override
    public void onFailure(@NonNull Exception exception) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocation();
    }
}
