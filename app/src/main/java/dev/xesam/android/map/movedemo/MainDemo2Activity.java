package dev.xesam.android.map.movedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.PolylineOptions;

import java.util.List;

public class MainDemo2Activity extends BaseMapActivity {

    private BusSource mBusSource = new BusSource() {

        @Override
        void onBusesLoaded(List<Bus> buses) {
            mMarkerMgr.update(buses);
        }
    };

    private BusMoveMgr mMarkerMgr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarkerMgr = new BusMoveMgr(mAMap);

        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.a_infowindow, null, false);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(marker.getPosition().toString());
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(39.912013, 116.401131))
                .add(new LatLng(39.911613, 116.401689))
                .add(new LatLng(39.91126, 116.401518))
                .add(new LatLng(39.910803, 116.401185))
                .add(new LatLng(39.912013, 116.401131))
                .width(10)
                .color(Color.RED);
        mAMap.addPolyline(polylineOptions);

        mAMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                mMapCtrl.centerZoom(new LatLng(39.911613, 116.401689), 18f, false);
                mBusSource.start();
            }
        });

        findViewById(R.id.action_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMarkerMgr.stop();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBusSource != null) {
            mBusSource.stop();
        }
    }
}
