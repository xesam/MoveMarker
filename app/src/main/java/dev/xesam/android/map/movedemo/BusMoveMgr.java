package dev.xesam.android.map.movedemo;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.xesam.android.map.move.AbsMoveMgr;
import dev.xesam.android.map.move.MoveMarker;
import dev.xesam.android.map.move.R;

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
    protected MoveMarker<Bus> onMarkerAdded(Bus bus) {
        LatLng latLng = new LatLng(bus.lat, bus.lng);
        MarkerOptions options = new MarkerOptions()
                .title("移动1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .snippet("详细信息")
                .anchor(0.5f, 0.5f)
                .position(latLng);
        return new MoveMarker<>(mAMap.addMarker(options));
    }

    @Override
    protected void onMarkerUpdated(MoveMarker<Bus> moveMarker, Bus updated) {
        List<LatLng> points = new ArrayList<>();
        LatLng c = moveMarker.getMarker().getPosition();
        points.add(new LatLng((c.latitude + updated.lat) / 2, updated.lng + new Random().nextDouble()));
        LatLng latLng = new LatLng(updated.lat, updated.lng);
        points.add(latLng);
        moveMarker.setTotalDuration(5_000);
        moveMarker.setTargetPoints(points);
    }

    @Override
    protected void onMarkerLost(final MoveMarker<Bus> moveMarker) {
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

}
