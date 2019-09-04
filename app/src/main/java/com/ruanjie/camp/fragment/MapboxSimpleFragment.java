package com.ruanjie.camp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.utils.MapFragmentUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Moligy
 * @date 2019/7/11.
 */
public class MapboxSimpleFragment extends Fragment implements OnMapReadyCallback {
    private final List<OnMapReadyCallback> mapReadyCallbackList = new ArrayList();
    private OnMapViewReadyCallback mapViewReadyCallback;
    private MapboxMap mapboxMap;

    private MapView map;

    public MapboxSimpleFragment() {
    }

    public static MapboxSimpleFragment newInstance() {
        return new MapboxSimpleFragment();
    }

    @NonNull
    public static MapboxSimpleFragment newInstance(@Nullable MapboxMapOptions mapboxMapOptions) {
        MapboxSimpleFragment mapFragment = new MapboxSimpleFragment();
        mapFragment.setArguments(MapFragmentUtils.createFragmentArgs(mapboxMapOptions));
        return mapFragment;
    }

    public void onInflate(@NonNull Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        this.setArguments(MapFragmentUtils.createFragmentArgs(MapboxMapOptions.createFromAttributes(context, attrs)));
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof com.mapbox.mapboxsdk.maps.MapFragment.OnMapViewReadyCallback) {
            this.mapViewReadyCallback = (OnMapViewReadyCallback)context;
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Context context = inflater.getContext();
        this.map = new MapView(context, MapFragmentUtils.resolveArgs(context, this.getArguments()));
        return this.map;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.map.onCreate(savedInstanceState);
        this.map.getMapAsync(this);
        if (this.mapViewReadyCallback != null) {
            this.mapViewReadyCallback.onMapViewReady(this.map);
        }

    }

    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        Iterator var2 = this.mapReadyCallbackList.iterator();

        while(var2.hasNext()) {
            OnMapReadyCallback onMapReadyCallback = (OnMapReadyCallback)var2.next();
            onMapReadyCallback.onMapReady(mapboxMap);
        }

    }

    public void onStart() {
        super.onStart();
        this.map.onStart();
    }

    public void onResume() {
        super.onResume();
        this.map.onResume();
    }

    public void onPause() {
        super.onPause();
        this.map.onPause();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.map != null && !this.map.isDestroyed()) {
            this.map.onSaveInstanceState(outState);
        }

    }

    public void onStop() {
        super.onStop();
        this.map.onStop();
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (this.map != null && !this.map.isDestroyed()) {
            this.map.onLowMemory();
        }

    }

    public void onDestroyView() {
        super.onDestroyView();
        this.map.onDestroy();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mapReadyCallbackList.clear();
    }

    public void getMapAsync(@NonNull OnMapReadyCallback onMapReadyCallback) {
        if (this.mapboxMap == null) {
            this.mapReadyCallbackList.add(onMapReadyCallback);
        } else {
            onMapReadyCallback.onMapReady(this.mapboxMap);
        }

    }

    public interface OnMapViewReadyCallback {
        void onMapViewReady(MapView var1);
    }
}
