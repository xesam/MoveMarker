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

import dev.xesam.android.map.movedemo.Bus;

/**
 * Created by xe on 16-12-13.
 */

public class MarkerMgr {
    private Map<String, MoveMarker3<Bus>> mMoveMarkers = new HashMap<>();

    protected AMap mAMap;

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
        onMarkersAdded(addedBuses);

        Set<String> update = new HashSet<>();
        update.addAll(now);
        update.retainAll(newew);

        List<Bus> updatedBuses = new ArrayList<>();
        for (String id : update) {
            updatedBuses.add(newBuses.get(id));
        }
        onMarkersUpdated(updatedBuses);

        Set<String> lost = new HashSet<>();
        lost.addAll(now);
        lost.removeAll(newew);

        List<MoveMarker3<Bus>> lostMarkers = new ArrayList<>();
        for (String id : lost) {
            lostMarkers.add(mMoveMarkers.get(id));
        }
        onMarkersLost(lostMarkers);
    }

    /**
     * 新增 marker
     */
    protected void onMarkersAdded(List<Bus> buses) {
        for (Bus bus : buses) {
            MoveMarker3<Bus> moveMarker3 = onMarkerAdded(bus);
            moveMarker3.setData(bus);
            mMoveMarkers.put(bus.id, moveMarker3);
        }
    }

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

    /**
     * 更新 marker
     */
    protected void onMarkersUpdated(List<Bus> buses) {
        for (Bus bus : buses) {
            MoveMarker3<Bus> moveMarker3 = mMoveMarkers.get(bus.id);
            moveMarker3.stopMove();
            onMarkerUpdated(moveMarker3, bus);
            moveMarker3.startMove();
        }
    }

    protected void onMarkerUpdated(MoveMarker3<Bus> moveMarker3, Bus updated) {
        List<LatLng> points = new ArrayList<>();
        LatLng latLng = new LatLng(updated.lat, updated.lng);
        points.add(latLng);
        moveMarker3.setTotalDuration(1_000);
        moveMarker3.setTargetPoints(points);
    }

    /**
     * 丢失 marker
     */
    protected void onMarkersLost(List<MoveMarker3<Bus>> markers) {
        for (final MoveMarker3<Bus> moveMarker3 : markers) {
            moveMarker3.stopMove();
            mMoveMarkers.remove(moveMarker3.getData().id);
            onMarkerLost(moveMarker3);
        }
    }

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
