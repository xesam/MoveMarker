package dev.xesam.android.map.movedemo;

import android.support.annotation.DrawableRes;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;

/**
 * Created by xe on 16-11-15.
 */

public class MapCtrl {

    private AMap mAMap;

    public MapCtrl(AMap aMap) {
        mAMap = aMap;
    }

    public MapCtrl(MapView mapView) {
        this(mapView.getMap());
    }

    public MapCtrl uiMyLocation(boolean enable) {
        mAMap.getUiSettings().setMyLocationButtonEnabled(enable);
        return this;
    }

    public MapCtrl uiZoom(boolean enable) {
        mAMap.getUiSettings().setZoomControlsEnabled(enable);
        return this;
    }

    public MapCtrl uIRotate(boolean enable) {
        mAMap.getUiSettings().setRotateGesturesEnabled(enable);
        return this;
    }

    public MapCtrl uIScale(boolean enable) {
        mAMap.getUiSettings().setScaleControlsEnabled(enable);
        return this;
    }

    public MapCtrl uiLocationIcon(@DrawableRes int icon) {
        mAMap.setMyLocationStyle(new MyLocationStyle().myLocationIcon(BitmapDescriptorFactory.fromResource(icon)));
        return this;
    }

    public MapCtrl gestureScroll(boolean enable) {
        mAMap.getUiSettings().setScrollGesturesEnabled(enable);
        return this;
    }

    public MapCtrl gestureZoom(boolean enable) {
        mAMap.getUiSettings().setZoomGesturesEnabled(enable);
        return this;
    }

    public MapCtrl gestureTilt(boolean enable) {
        mAMap.getUiSettings().setTiltGesturesEnabled(enable);
        return this;
    }

    public MapCtrl gestureRotate(boolean enable) {
        mAMap.getUiSettings().setRotateGesturesEnabled(enable);
        return this;
    }

    public MapCtrl center(LatLng latLng, boolean animate) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latLng);
        if (animate) {
            mAMap.animateCamera(cameraUpdate);
        } else {
            mAMap.moveCamera(cameraUpdate);
        }
        return this;
    }

    public MapCtrl zoomIn(boolean animate) {
        if (animate) {
            mAMap.animateCamera(CameraUpdateFactory.zoomIn());
        } else {
            mAMap.moveCamera(CameraUpdateFactory.zoomIn());
        }
        return this;
    }

    public MapCtrl zoomOut(boolean animate) {
        if (animate) {
            mAMap.animateCamera(CameraUpdateFactory.zoomOut());
        } else {
            mAMap.moveCamera(CameraUpdateFactory.zoomOut());
        }
        return this;
    }

    public float zoom() {
        return mAMap.getCameraPosition().zoom;
    }

    public MapCtrl zoom(float zoom, boolean animate) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(zoom);
        if (animate) {
            mAMap.animateCamera(cameraUpdate);
        } else {
            mAMap.moveCamera(cameraUpdate);
        }
        return this;
    }

    public MapCtrl centerZoom(LatLng latLng, float zoom, boolean animate) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        if (animate) {
            mAMap.animateCamera(cameraUpdate);
        } else {
            mAMap.moveCamera(cameraUpdate);
        }
        return this;
    }
}
