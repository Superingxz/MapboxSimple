package com.ruanjie.camp.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.plugins.annotation.Line;
import com.mapbox.mapboxsdk.plugins.annotation.LineManager;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.china.constants.ChinaStyle;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.pluginscalebar.ScaleBarPlugin;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.camp.R;
import com.ruanjie.camp.SP;
import com.ruanjie.camp.utils.LogUtil;
import com.ruanjie.camp.utils.SPManager;
import com.softgarden.baselibrary.base.IBasePresenter;
import com.softgarden.baselibrary.utils.BaseSPManager;
import com.softgarden.baselibrary.utils.EmptyUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;
import static com.ruanjie.camp.Constants.DEFAULT_INTERVAL_IN_MILLISECONDS;
import static com.ruanjie.camp.Constants.DEFAULT_MAX_WAIT_TIME;
import static com.ruanjie.camp.Constants.id0;
import static com.ruanjie.camp.Constants.id1;
import static com.ruanjie.camp.Constants.id2;
import static com.ruanjie.camp.Constants.id3;
import static com.ruanjie.camp.Constants.id_ins;
import static com.ruanjie.camp.Constants.id_ins0;
import static com.ruanjie.camp.Constants.id_ins1;
import static com.ruanjie.camp.Constants.id_ins2;
import static com.ruanjie.camp.Constants.id_ins3;
import static com.ruanjie.camp.Constants.id_ins4;
import static com.ruanjie.camp.Constants.id_ins5;
import static com.ruanjie.camp.Constants.id_ins6;
import static com.ruanjie.camp.Constants.id_ins7;
import static com.ruanjie.camp.Constants.id_ins8;
import static com.ruanjie.camp.utils.CMapboxUtil.updateLocationCamera;

/**
 * @author Moligy
 * @date 2019/7/5.
 */
public abstract class BaseMapboxActivity<P extends IBasePresenter> extends AppBaseToolbarActivity<P>
        implements OnMapReadyCallback, MapboxMap.OnMapClickListener, OnSymbolClickListener,
        PermissionsListener, LocationEngineCallback<LocationEngineResult>, MapboxMap.OnCameraMoveListener {
    private static final String ID_ICON_LOCATION = "camp_marker1";

    private static final String ID_ICON_MARKER1 = "camp_marker1";
    private static final String ID_ICON_MARKER2 = "camp_marker2";
    private static final String ID_ICON_MARKER3 = "camp_marker3";

    @BindView(R.id.mMapView)
    protected MapView mapView;

    protected int type;//image1 image2

    protected Location mLastLocation;

    protected List<LatLng> mLatLngs = new ArrayList<>();
    protected List<Line> mLines = new ArrayList<>();
    protected List<Feature> features = new ArrayList<>();
    protected List<SymbolLayer> mSymbolLayers = new ArrayList<>();

    protected MapboxMap mapboxMap;
    protected GeoJsonSource source;
    protected FeatureCollection featureCollection;

    protected double latitude = 23.14604146813636d;
    protected double longitude = 113.361669650864d;

    protected PermissionsManager permissionsManager;
    protected LocationEngine locationEngine;
    protected LineManager lineManager;
    protected SymbolManager symbolManager;
    protected Style mStyle;

    protected Symbol symbol_location;
    protected MarkerViewManager mMarkerViewManager;

//    protected MapboxNavigation navigation;
    protected LocalizationPlugin localizationPlugin;
    protected ScaleBarPlugin scaleBarPlugin;

    protected String map_source= "map_source";
    protected String layerid_unclustered_points = "unclustered-points";
    private String quake_triangle_icon_id = "quake-triangle-icon-id";

    //    protected String local_uri = "asset://local_style_file.json";
    protected String local_uri = "asset://mapboxStyle-cn.json";
//    protected String local_uri = "asset://mapboxStyle-en.json";

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initialize() {
        super.initialize();
        mapView.getMapAsync(this);

//        MapboxNavigationOptions options = MapboxNavigationOptions.builder().isDebugLoggingEnabled(true).build();
//        navigation = new MapboxNavigation(getApplicationContext(), mapbox_token, options);
//        navigation.addNavigationEventListener(this);
//        navigation.addMilestoneEventListener(this);

        permissionsManager = new PermissionsManager(this);
    }

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void loadData();

    protected abstract void mapStyleChange();

    protected void setPermissions(){

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
//        SetStyle(mapboxMap);
        setChinaStyle(mapboxMap);
    }

    @SuppressLint("useChinaStyleVersion")
    private void SetStyle(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            mStyle = mapboxMap.getStyle();
            if (mStyle != null) {
                lineManager = new LineManager(mapView, mapboxMap, mStyle);
                symbolManager = new SymbolManager(mapView, mapboxMap, mStyle);
                mMarkerViewManager = new MarkerViewManager(mapView, mapboxMap);
                enableLocationComponent(mapboxMap,mStyle);

                symbolManager.addClickListener(this);
                mapboxMap.addOnMapClickListener(this);
                mapboxMap.addOnCameraMoveListener(this);

                initData();
                initView();
                initListener();
                loadData();
            }
        });
    }

    @SuppressLint("useChinaStyleVersion")
    private void setChinaStyle(@NonNull MapboxMap mapboxMap) {
//        mapboxMap.setStyle(new Style.Builder().fromUrl(ChinaStyle.MAPBOX_STREETS_CHINESE), style -> {
//            mStyle = mapboxMap.getStyle();
//            ChangeMapStyle(mStyle);
//        });

        mapboxMap.setStyle(new Style.Builder().fromUri(local_uri), mLocalStyle -> {
            mStyle = mapboxMap.getStyle();
            ChangeMapStyle(mStyle);
            updateLocationCamera(mapboxMap, Double.valueOf(SP.getLocationLatitude()), Double.valueOf(SP.getLocationLongtude()));
        });
    }

    private void ChangeMapStyle(Style mStyle) {
        if (mStyle != null) {
            LogUtil.d("mStyle", mStyle.getUri());
            lineManager = new LineManager(mapView, mapboxMap, mStyle);
            symbolManager = new SymbolManager(mapView, mapboxMap, mStyle);
            mMarkerViewManager = new MarkerViewManager(mapView, mapboxMap);
            localizationPlugin = new LocalizationPlugin(mapView, mapboxMap, mStyle);
            localizationPlugin.setMapLanguage(SPManager.getLanguage());
            scaleBarPlugin = new ScaleBarPlugin(mapView, mapboxMap);
            initMapboxImage(mStyle);
            initLayerIcons(mStyle);
            enableLocationComponent(mapboxMap,mStyle);

            symbolManager.addClickListener(this);
            mapboxMap.addOnMapClickListener(this);
            mapboxMap.addOnCameraMoveListener(this);

            initData();
            initView();
            initListener();
            loadData();
        }
    }

    @SuppressLint("useChinaStyleVersion")
    protected void MapTypeChange() {
        Style mapboxMapStyle = mapboxMap.getStyle();
        if (mapboxMapStyle != null) {
            if (mapboxMapStyle.getUri().equals(ChinaStyle.MAPBOX_STREETS_CHINESE)) {
                mapboxMap.setStyle(Style.SATELLITE_STREETS, style -> {
                    mStyle = mapboxMap.getStyle();
                    ChangeMapStyle(mStyle);
                });
            } else if (mapboxMapStyle.getUri().equals(Style.SATELLITE_STREETS)) {
                mapboxMap.setStyle(new Style.Builder().fromUrl(ChinaStyle.MAPBOX_STREETS_CHINESE), style -> {
                    mStyle = mapboxMap.getStyle();
                    ChangeMapStyle(mStyle);
                });
            }
        }
    }

    protected void initMapboxImage(Style mStyle){

    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onAnnotationClick(Symbol symbol) {
        LogUtil.d(TAG,"点击" + symbol.getId());
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(MapboxMap mapboxMap, Style style) {

        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .accuracyColor(Color.RED)
                    // .foregroundDrawable(R.drawable.mapbox_compass_icon)
                    .build();

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, style)
                            //  .locationComponentOptions(customLocationComponentOptions)
                            .build();


            // Activate with a built LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }

        setPermissions();
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    protected void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        if (locationEngine != null) {
            locationEngine.requestLocationUpdates(request, this, getMainLooper());
            locationEngine.getLastLocation(this);
        }
    }

    protected void stopLocationEngine(){
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(this);
        }
    }

    protected void initMapboxImage1(Style mStyle) {
        mStyle.addImage(id0, getResources().getDrawable(R.drawable.camp_marker1));
        mStyle.addImage(id1, getResources().getDrawable(R.drawable.camp_marker2));
        mStyle.addImage(id2, getResources().getDrawable(R.drawable.camp_marker3));
        mStyle.addImage(id_ins, getResources().getDrawable(R.drawable.symbol_ins8));
        mStyle.addImage(id_ins0, getResources().getDrawable(R.drawable.symbol_ins8));
        mStyle.addImage(id_ins1, getResources().getDrawable(R.drawable.symbol_ins1));
        mStyle.addImage(id_ins2, getResources().getDrawable(R.drawable.symbol_ins2));
        mStyle.addImage(id_ins3, getResources().getDrawable(R.drawable.symbol_ins3));
        mStyle.addImage(id_ins4, getResources().getDrawable(R.drawable.symbol_ins4));
        mStyle.addImage(id_ins5, getResources().getDrawable(R.drawable.symbol_ins5));
        mStyle.addImage(id_ins6, getResources().getDrawable(R.drawable.symbol_ins6));
        mStyle.addImage(id_ins7, getResources().getDrawable(R.drawable.symbol_ins7));
        mStyle.addImage(id_ins8, getResources().getDrawable(R.drawable.symbol_ins8));
    }

    protected void initMapboxImage2(Style mStyle) {
        mStyle.addImage(id1, getResources().getDrawable(R.drawable.camp_marker1));
        mStyle.addImage(id2, getResources().getDrawable(R.drawable.camp_marker2));
        mStyle.addImage(id3, getResources().getDrawable(R.drawable.camp_marker3));
        mStyle.addImage(id_ins, getResources().getDrawable(R.drawable.symbol_ins8));
        mStyle.addImage(id_ins0, getResources().getDrawable(R.drawable.symbol_ins8));
        mStyle.addImage(id_ins1, getResources().getDrawable(R.drawable.symbol_ins1));
        mStyle.addImage(id_ins2, getResources().getDrawable(R.drawable.symbol_ins2));
        mStyle.addImage(id_ins3, getResources().getDrawable(R.drawable.symbol_ins3));
        mStyle.addImage(id_ins4, getResources().getDrawable(R.drawable.symbol_ins4));
        mStyle.addImage(id_ins5, getResources().getDrawable(R.drawable.symbol_ins5));
        mStyle.addImage(id_ins6, getResources().getDrawable(R.drawable.symbol_ins6));
        mStyle.addImage(id_ins7, getResources().getDrawable(R.drawable.symbol_ins7));
        mStyle.addImage(id_ins8, getResources().getDrawable(R.drawable.symbol_ins8));
    }

    protected void clearSymbolLayer() {
        if (EmptyUtil.isNotEmpty(mSymbolLayers)) {
            for (int i = 0; i < mSymbolLayers.size(); i++) {
                SymbolLayer symbolLayer = mSymbolLayers.get(i);
                mStyle.removeLayer(symbolLayer);
            }
            mSymbolLayers.clear();
        }

        if (mStyle != null) {
            mStyle.removeSource(map_source);
        }
    }

    protected void initMapCluster() {
        if (mStyle != null) {
            addUnClusteredLayer(mStyle, "{type}", map_source);
            addClusteredLayer(mStyle, map_source);
        } else if (mapboxMap != null) {
            mStyle = mapboxMap.getStyle();
            if (mStyle != null) {
                addUnClusteredLayer(mStyle, "{type}", map_source);
                addClusteredLayer(mStyle, map_source);
            }
        }
    }

    protected void initMapSource(Style mStyle) {
        clearSymbolLayer();

        if (mStyle == null) {
            return;
        }
        featureCollection = FeatureCollection.fromFeatures(features);
        addGeoJsonSource(mStyle, featureCollection, map_source);
    }

    private void initLayerIcons(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("single-quake-icon-id", BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.camp_marker1)));
        loadedMapStyle.addImage("quake-triangle-icon-id", BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.map_layer_circle)));
    }

    protected boolean addClustered(){

        return false;
    }

    protected boolean addClusteredLayer(@NonNull Style loadedMapStyle,String sourceId) {
        Source source = loadedMapStyle.getSource(sourceId);
        if (source == null) {
            return false;
        }

        // Use the earthquakes GeoJSON source to create three point ranges.
        int[] layers = new int[]{150, 100, 50, 20, 10, 0};

        for (int i = 0; i < layers.length; i++) {
            //Add clusters' SymbolLayers images
            SymbolLayer symbolLayer = new SymbolLayer("cluster-" + i, sourceId);

            symbolLayer.setProperties(
                    iconImage(quake_triangle_icon_id)
            );
            Expression pointCount = toNumber(get("point_count"));

            // Add a filter to the cluster layer that hides the icons based on "point_count"
            symbolLayer.setFilter(
                    i == 0
                            ? all(has("point_count"),
                            gte(pointCount, literal(layers[i]))
                    ) : all(has("point_count"),
                            gt(pointCount, literal(layers[i])),
                            lt(pointCount, literal(layers[i - 1]))
                    )
            );
            loadedMapStyle.addLayer(symbolLayer);
            mSymbolLayers.add(symbolLayer);
        }

        //Add a SymbolLayer for the cluster data number point count
        SymbolLayer countLayer = new SymbolLayer("count", sourceId).withProperties(
                textField(Expression.toString(get("point_count"))),
                textSize(18f),
                textColor(Color.WHITE),
                textIgnorePlacement(true),
                // The .5f offset moves the data numbers down a little bit so that they're
                // in the middle of the triangle cluster image
                textOffset(new Float[]{0f, .0f}),
                textAllowOverlap(true)
        );
        loadedMapStyle.addLayer(countLayer);
        mSymbolLayers.add(countLayer);
        return true;
    }

    protected void addUnClusteredLayer(@NonNull Style loadedMapStyle, String id, String sourceId) {
        //Creating a SymbolLayer icon layer for single data/icon points
        SymbolLayer mag = new SymbolLayer(layerid_unclustered_points, sourceId).withProperties(
                iconImage(id),
                iconSize(
                        division(
                                get("mag"), literal(4.0f)
                        )
                )
        );
        loadedMapStyle.addLayer(mag);
        mSymbolLayers.add(mag);
    }

    private boolean addGeoJsonSource(@NonNull Style loadedMapStyle, FeatureCollection data, String sourceId) {
        Source source = loadedMapStyle.getSource(sourceId);
        if (source != null) {
            return false;
        }

        // Add a new source from the GeoJSON data and set the 'cluster' option to true.
        GeoJsonSource geoJsonSource = new GeoJsonSource(sourceId,
                data,
                new GeoJsonOptions()
                        .withCluster(true)
                        .withClusterMaxZoom(15)
                        .withClusterRadius(50)
        );
        loadedMapStyle.addSource(
                // Point to GeoJSON data. This example visualizes all M1.0+ earthquakes from
                // 12/22/15 to 1/21/16 as logged by USGS' Earthquake hazards program.
                geoJsonSource
        );
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        if (mapboxMap != null) {
            Layer mapText = mapboxMap.getStyle().getLayer("country-label");
            if (mapText != null) {
                Locale mLanguage = BaseSPManager.getLanguage();
                if (isEqualsLanguage(Locale.ENGLISH, mLanguage)) {
                    mapText.setProperties(textField("{name_en}"));
                } else if (isEqualsLanguage(Locale.SIMPLIFIED_CHINESE, mLanguage)) {
                    mapText.setProperties(textField("{name_zh}"));
                }
            }
        }

        if (localizationPlugin != null) {
            localizationPlugin.setMapLanguage(SPManager.getLanguage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }

        if (mMarkerViewManager != null) {
            mMarkerViewManager.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

//    @Override
//    public void onRunning(boolean running) {
//
//    }
//
//    @Override
//    public void onMilestoneEvent(RouteProgress routeProgress, String instruction, Milestone milestone) {
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public void onSuccess(LocationEngineResult result) {
        mLastLocation = result.getLastLocation();
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        LogUtil.e(TAG, exception.getMessage());
    }

    protected void loadOfflineMapDb(Context context, String path) {
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
}
