package dev.xesam.android.map.move;

import android.support.annotation.Nullable;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * 多个点构成的运动路径
 * Created by xe on 16-12-13.
 */

public class MovePath {
    private List<MoveSpan> mMoveSpen;
    private final long mTotalDuration;

    public MovePath(List<LatLng> points, long duration) {
        mMoveSpen = new ArrayList<>();
        mTotalDuration = duration;
        float totalDistance = 0f;
        for (int i = 1; i < points.size(); i++) {
            LatLng start = points.get(i - 1);
            LatLng end = points.get(i);
            float spanDistance = AMapUtils.calculateLineDistance(start, end);
            totalDistance += spanDistance;
            MoveSpan moveSpan = new MoveSpan();
            moveSpan.distance = spanDistance;
            moveSpan.end = end;
            moveSpan.rotate = getRotate(start, end);
            mMoveSpen.add(moveSpan);
        }
        long assignedDuration = 0;
        for (int i = 0, size = mMoveSpen.size(); i < size; i++) {
            MoveSpan moveSpan = mMoveSpen.get(i);
            if (i == size - 1) {
                moveSpan.duration = mTotalDuration - assignedDuration;
            } else {
                long spanDuration = (long) (mTotalDuration * (moveSpan.distance / totalDistance));
                assignedDuration += spanDuration;
                moveSpan.duration = spanDuration;
            }
        }
    }

    private float getRotate(LatLng from, LatLng to) {
        double fromLat = from.latitude;
        double fromLng = from.longitude;
        double toLat = to.latitude;
        double toLng = to.longitude;
        return (float) (Math.atan2(toLng - fromLng, toLat - fromLat) / Math.PI * 180);
    }

    public int getSpanCount() {
        return mMoveSpen.size();
    }

    @Nullable
    public MoveSpan getSpan(int index) {
        if (index >= getSpanCount()) {
            return null;
        }
        return mMoveSpen.get(index);
    }
}
