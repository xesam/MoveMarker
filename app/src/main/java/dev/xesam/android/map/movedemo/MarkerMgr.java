package dev.xesam.android.map.movedemo;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;

import java.util.ArrayList;
import java.util.List;

import dev.xesam.android.map.move.AbsMarkerMgr;
import dev.xesam.android.map.move.MoveMarker3;
import dev.xesam.android.map.move.R;

/**
 * Created by xe on 16-12-13.
 */

public class MarkerMgr extends AbsMarkerMgr<Bus> {
    public MarkerMgr(AMap map) {
        super(map);
    }

    @Override
    protected String getKey(Bus item) {
        return item.id;
    }

    @Override
    protected MoveMarker3<Bus> onMarkerAdded(Bus bus) {
        LatLng latLng = new LatLng(bus.lat, bus.lng);
        MarkerOptions options = new MarkerOptions()
                .title("移动1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .snippet("详细信息")
                .anchor(0.5f, 0.5f)
                .position(latLng);
        return new MoveMarker3<>(mAMap.addMarker(options));
    }

    @Override
    protected void onMarkerUpdated(MoveMarker3<Bus> moveMarker3, Bus updated) {
        List<LatLng> points = new ArrayList<>();
        LatLng c = moveMarker3.getMarker().getPosition();
        points.add(new LatLng((c.latitude + updated.lat) / 2, 116.2));
        LatLng latLng = new LatLng(updated.lat, updated.lng);
        points.add(latLng);
        moveMarker3.setTotalDuration(5_000);
        moveMarker3.setTargetPoints(points);
    }

    @Override
    protected void onMarkerLost(final MoveMarker3<Bus> moveMarker3) {
        AlphaAnimation dismiss = new AlphaAnimation(1.0f, 0f);
        dismiss.setDuration(500);
        dismiss.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
                moveMarker3.getMarker().remove();
            }
        });
        moveMarker3.getMarker().setAnimation(dismiss);
        moveMarker3.getMarker().startAnimation();
    }

}
