package dev.xesam.android.map.move;

import com.amap.api.maps.AMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xe on 16-12-13.
 */

public abstract class AbsMarkerMgr<D> {
    protected Map<String, MoveMarker3<D>> mMoveMarkers = new HashMap<>();

    protected AMap mAMap;

    public AbsMarkerMgr(AMap map) {
        this.mAMap = map;
    }

    protected abstract String getKey(D item);

    public void update(List<D> items) {
        Map<String, D> newItems = new HashMap<>();
        for (D item : items) {
            newItems.put(getKey(item), item);
        }
        Set<String> newer = newItems.keySet();
        Set<String> current = mMoveMarkers.keySet();

        Set<String> added = new HashSet<>();
        added.addAll(newer);
        added.removeAll(current);

        List<D> addedItems = new ArrayList<>();
        for (String id : added) {
            addedItems.add(newItems.get(id));
        }
        onMarkersAdded(addedItems);

        Set<String> update = new HashSet<>();
        update.addAll(current);
        update.retainAll(newer);

        List<D> updatedItems = new ArrayList<>();
        for (String id : update) {
            updatedItems.add(newItems.get(id));
        }
        onMarkersUpdated(updatedItems);

        Set<String> lost = new HashSet<>();
        lost.addAll(current);
        lost.removeAll(newer);

        List<MoveMarker3<D>> lostMarkers = new ArrayList<>();
        for (String id : lost) {
            lostMarkers.add(mMoveMarkers.get(id));
        }
        onMarkersLost(lostMarkers);
    }

    /**
     * 新增 marker
     */
    protected void onMarkersAdded(List<D> items) {
        for (D item : items) {
            MoveMarker3<D> moveMarker3 = onMarkerAdded(item);
            moveMarker3.setData(item);
            mMoveMarkers.put(getKey(item), moveMarker3);
        }
    }

    protected abstract MoveMarker3<D> onMarkerAdded(D item);

    /**
     * 更新 marker
     */
    protected void onMarkersUpdated(List<D> items) {
        for (D item : items) {
            MoveMarker3<D> moveMarker3 = mMoveMarkers.get(getKey(item));
            moveMarker3.stopMove();
            onMarkerUpdated(moveMarker3, item);
            moveMarker3.startMove();
        }
    }

    protected abstract void onMarkerUpdated(MoveMarker3<D> moveMarker3, D updated);

    /**
     * 丢失 marker
     */
    protected void onMarkersLost(List<MoveMarker3<D>> markers) {
        for (final MoveMarker3<D> moveMarker3 : markers) {
            moveMarker3.stopMove();
            D item = moveMarker3.getData();
            mMoveMarkers.remove(getKey(item));
            onMarkerLost(moveMarker3);
        }
    }

    protected abstract void onMarkerLost(final MoveMarker3<D> moveMarker3);

}
