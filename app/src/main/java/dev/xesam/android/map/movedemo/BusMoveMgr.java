package dev.xesam.android.map.movedemo;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.xesam.android.map.move.AbsMoveMgr;
import dev.xesam.android.map.move.MoveMarker;

/**
 * Created by xe on 16-12-13.
 */

public class BusMoveMgr extends AbsMoveMgr<Bus> {
    public BusMoveMgr(AMap map) {
        super(map);
    }

    @Override
    protected String getKey(Bus item) {
        return item.id;
    }

    @Override
    protected MoveMarker<Bus> onMarkerAdded(Bus bus, boolean performAnimation) {
        LatLng latLng = new LatLng(bus.lat, bus.lng);
        MarkerOptions options = new MarkerOptions()
                .title("移动1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .snippet("详细信息")
                .anchor(0.5f, 0.5f)
                .position(latLng);
        return new MoveMarker<>(mAMap, mAMap.addMarker(options));
    }

    @Override
    protected void onMarkerUpdated(MoveMarker<Bus> moveMarker, Bus old, Bus updated, boolean performAnimation) {
        LatLng endLatLng = new LatLng(updated.lat, updated.lng);
        if (performAnimation) {
            List<LatLng> points = new ArrayList<>();
            LatLng c = moveMarker.getMarker().getPosition();
            points.add(new LatLng((c.latitude + updated.lat) / 2, (c.longitude + updated.lng) / 2));
            points.add(endLatLng);
            moveMarker.setTotalDuration(5_000);
            moveMarker.setMovePoints(points);
            moveMarker.startMove();
        } else {
            moveMarker.directTo(endLatLng);
        }
    }

    @Override
    protected void onMarkerLost(final MoveMarker<Bus> moveMarker, boolean performAnimation) {
        AlphaAnimation dismiss = new AlphaAnimation(1.0f, 0f);
        dismiss.setDuration(500);
        dismiss.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
                moveMarker.getMarker().remove();
            }
        });
        moveMarker.getMarker().setAnimation(dismiss);
        moveMarker.getMarker().startAnimation();
    }

    public void stop() {
        for (Map.Entry<String, MoveMarker<Bus>> entry : mMoveMarkers.entrySet()) {
            entry.getValue().stopMove();
        }
    }

}
