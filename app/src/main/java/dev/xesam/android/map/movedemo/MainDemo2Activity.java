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

import dev.xesam.android.map.move.R;

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

        mMapCtrl.centerZoom(new LatLng(40.06, 116.09), 8.5f, false);

        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(39, 115))
                .add(new LatLng(39, 116))
                .add(new LatLng(40, 116))
                .add(new LatLng(40, 115))
                .add(new LatLng(39, 115))
                .width(10)
                .color(Color.RED);
        mAMap.addPolyline(polylineOptions);

        mBusSource.start();
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
