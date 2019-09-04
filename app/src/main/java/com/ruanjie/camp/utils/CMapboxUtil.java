package com.ruanjie.camp.utils;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

/**
 * @author Moligy
 * @date 2019/7/11.
 */
public class CMapboxUtil {
    /**
     * 把地图画面移动到定位地点(使用moveCamera方法没有动画效果)
     *
     * @param latitude
     * @param longitude
     */
    public static void moveMapCamera(MapboxMap mapboxMap, double latitude, double longitude) {
        if (null != mapboxMap) {
            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
        }
    }

    public static void updateLocationCamera(MapboxMap mapboxMap, double latitude, double longitude) {
        if (mapboxMap == null) {
            return;
        }

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)) // Sets the new camera position
                .zoom(25) // Sets the zoom
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);
    }
}
