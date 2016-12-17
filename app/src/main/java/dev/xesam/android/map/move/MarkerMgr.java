package dev.xesam.android.map.move;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xe on 16-12-13.
 */

public class MarkerMgr {
    private Map<String, MoveMarker3<Bus>> mMoveMarkers = new HashMap<>();

    AMap mAMap;

    public MarkerMgr(AMap map) {
        this.mAMap = map;
    }

    public void update(List<Bus> buses) {
        Map<String, Bus> newBuses = new HashMap<>();
        for (Bus bus : buses) {
            newBuses.put(bus.id, bus);
        }
        Set<String> newew = newBuses.keySet();
        Set<String> now = mMoveMarkers.keySet();

        Set<String> added = new HashSet<>();
        added.addAll(newew);
        added.removeAll(now);

        List<Bus> addedBuses = new ArrayList<>();
        for (String id : added) {
            addedBuses.add(newBuses.get(id));
        }
        onMarkerAdded(addedBuses);

        Set<String> update = new HashSet<>();
        update.addAll(now);
        update.retainAll(newew);

        List<Bus> updatedBuses = new ArrayList<>();
        for (String id : update) {
            updatedBuses.add(newBuses.get(id));
        }
        onMarkerUpdated(updatedBuses);

        Set<String> lost = new HashSet<>();
        lost.addAll(now);
        lost.removeAll(newew);

        List<MoveMarker3<Bus>> lostMarkers = new ArrayList<>();
        for (String id : lost) {
            lostMarkers.add(mMoveMarkers.get(id));
        }
        onMarkerLost(lostMarkers);
    }

    /**
     * 新增 marker
     */
    protected void onMarkerAdded(List<Bus> buses) {
        for (Bus bus : buses) {
            LatLng latLng = new LatLng(bus.lat, bus.lng);
            MarkerOptions options = new MarkerOptions()
                    .title("移动1")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_car))
                    .snippet("详细信息")
                    .anchor(0.5f, 0.5f)
                    .position(latLng);
            MoveMarker3<Bus> moveMarker3 = new MoveMarker3<>(mAMap.addMarker(options));
            moveMarker3.setData(bus);
            mMoveMarkers.put(bus.id, moveMarker3);
        }
    }

    /**
     * 更新 marker
     */
    protected void onMarkerUpdated(List<Bus> buses) {
        for (Bus bus : buses) {
            LatLng latLng = new LatLng(bus.lat, bus.lng);
            MoveMarker3<Bus> moveMarker3 = mMoveMarkers.get(bus.id);
            moveMarker3.stopMove();
            List<LatLng> points = new ArrayList<>();
            points.add(latLng);
            moveMarker3.setTotalDuration(1_000);
            moveMarker3.setTargetPoints(points);
            moveMarker3.startMove();
        }
    }

    /**
     * 丢失 marker
     */
    protected void onMarkerLost(List<MoveMarker3<Bus>> markers) {
        for (final MoveMarker3<Bus> moveMarker3 : markers) {
            mMoveMarkers.remove(moveMarker3.getData().id);
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
}
