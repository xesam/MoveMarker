package dev.xesam.android.map.movedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

import dev.xesam.android.map.move.R;

/**
 * Created by xe on 16-11-18.
 */

public class BaseMapActivity extends AppCompatActivity {
    protected MapView vMapView;
    protected AMap mAMap;
    protected MapCtrl mMapCtrl;

    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        vMapView = (MapView) findViewById(R.id.map);
        vMapView.onCreate(savedInstanceState);// 必须要写
        mAMap = vMapView.getMap();
        mMapCtrl = new MapCtrl(mAMap);
    }

    protected void onResume() {
        super.onResume();
        vMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        vMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vMapView.onDestroy();
    }

}
