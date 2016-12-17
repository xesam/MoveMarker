package dev.xesam.android.map.movedemo;

import android.os.Bundle;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import dev.xesam.android.map.move.MoveMarker2;
import dev.xesam.android.map.move.R;


public class MainDemoActivity extends BaseMapActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private Marker mMove1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<LatLng> points = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                points.add(new LatLng(40, 116 + i * 0.02));
            } else {
                points.add(new LatLng(40 + i * 0.02, 116));
            }
        }

        LatLng first = points.get(0);

        MarkerOptions move1 = new MarkerOptions()
                .title("移动1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_car))
                .snippet("详细信息")
                .anchor(0.5f, 0.5f)
                .position(first);
        mMove1 = mAMap.addMarker(move1);

        mMove1.setInfoWindowEnable(true);
        mMove1.showInfoWindow();

//        Animation animation = new TranslateAnimation(points.get(2));
//        animation.setDuration(3000);
//        mMove1.setAnimation(animation);
//        mMove1.startAnimation();

        mMapCtrl.centerZoom(first, 13, true);

        final MoveMarker2 moveMarker = new MoveMarker2(mMove1);
        moveMarker.setTotalDuration(16_000);
        moveMarker.setPathPoints(points);
        moveMarker.startMove();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                moveMarker.stopMove();
//            }
//        }, 5000);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<LatLng> points2 = new ArrayList<>();
//                for (int i = 0; i < 4; i++) {
//                    LatLng latLng;
//                    if (i % 2 == 0) {
//                        latLng = new LatLng(40.001 + i * 0.02, 116.001);
//                    } else {
//                        latLng = new LatLng(40.001, 116.001 + i * 0.02);
//                    }
//                    points2.add(latLng);
//                    mAMap.addMarker(new MarkerOptions()
//                            .title("移动1")
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.poi_marker_1))
//                            .snippet("详细信息")
//                            .position(latLng));
//                }
//                moveMarker.stopMove();
//                points2.add(0, moveMarker.getMarker().getPosition());
//                moveMarker.setTotalDuration(2_000);
//                moveMarker.setPathPoints(points2);
////                moveMarker.startMove();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        moveMarker.startMove();
//                    }
//                }, 2000);
//            }
//        }, 8_000);

//
//        MarkerOptions move2 = new MarkerOptions()
//                .title("移动1")
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
//                .snippet("详细信息")
//                .position(points2.get(0));
//        Marker mMove2 = mAMap.addMarker(move2);
//        new MoveMarker(mMove2).start(points2);


//        mAMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                marker.setTitle("infowindow clicked");
//            }
//        });
//
//        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                View view = getLayoutInflater().inflate(R.layout.a_infowindow, null, false);
//                TextView tv = (TextView) view.findViewById(R.id.tv);
//                if (moveMarker != null) {
//                    tv.setText(moveMarker.getRunningSpan() + ":" + marker.getPosition().toString());
//                }
//                return view;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                return null;
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
