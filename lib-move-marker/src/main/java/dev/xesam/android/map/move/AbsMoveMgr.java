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

public abstract class AbsMoveMgr<D> {
    protected Map<String, MoveMarker<D>> mMoveMarkers = new HashMap<>();

    protected AMap mAMap;

    public AbsMoveMgr(AMap map) {
        this.mAMap = map;
    }

    protected abstract String getKey(D item);

    public void update(List<D> items, boolean performAnimation) {
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

        Set<String> update = new HashSet<>();
        update.addAll(current);
        update.retainAll(newer);

        List<D> updatedItems = new ArrayList<>();
        for (String id : update) {
            updatedItems.add(newItems.get(id));
        }

        Set<String> lost = new HashSet<>();
        lost.addAll(current);
        lost.removeAll(newer);

        List<MoveMarker<D>> lostMarkers = new ArrayList<>();
        for (String id : lost) {
            lostMarkers.add(mMoveMarkers.get(id));
        }

        onMarkersAdded(addedItems, performAnimation);
        onMarkersUpdated(updatedItems, performAnimation);
        onMarkersLost(lostMarkers, performAnimation);
    }

    /**
     * 新增 marker
     */
    protected void onMarkersAdded(List<D> items, boolean performAnimation) {
        for (D item : items) {
            MoveMarker<D> moveMarker = onMarkerAdded(item, performAnimation);
            moveMarker.setData(item);
            mMoveMarkers.put(getKey(item), moveMarker);
        }
    }

    protected abstract MoveMarker<D> onMarkerAdded(D item, boolean performAnimation);

    /**
     * 更新 marker
     */
    protected void onMarkersUpdated(List<D> items, boolean performAnimation) {
        for (D item : items) {
            MoveMarker<D> moveMarker = mMoveMarkers.get(getKey(item));
            moveMarker.stopMove();
            onMarkerUpdated(moveMarker, moveMarker.getData(), item, performAnimation);
            moveMarker.setData(item);
        }
    }

    protected abstract void onMarkerUpdated(MoveMarker<D> moveMarker, D old, D updated, boolean performAnimation);

    /**
     * 丢失 marker
     */
    protected void onMarkersLost(List<MoveMarker<D>> markers, boolean performAnimation) {
        for (final MoveMarker<D> moveMarker : markers) {
            moveMarker.stopMove();
            D item = moveMarker.getData();
            mMoveMarkers.remove(getKey(item));
            onMarkerLost(moveMarker, performAnimation);
        }
    }

    protected abstract void onMarkerLost(final MoveMarker<D> moveMarker, boolean performAnimation);

}
