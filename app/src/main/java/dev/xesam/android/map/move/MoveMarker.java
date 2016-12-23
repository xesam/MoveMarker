package dev.xesam.android.map.move;

import android.animation.ValueAnimator;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import java.util.List;

/**
 * Created by xe on 16-12-13.
 */

public class MoveMarker<D> {
    private D mData;

    private final AMap mAMap;
    private final Marker vMarker;

    private MovePath mMovePath;
    private static final int SPAN_NOT_START = 0;
    private int mRunningIndex = SPAN_NOT_START;
    private long mTotalDuration;
    private boolean mRunning = false;

    public MoveMarker(AMap map, Marker marker) {
        this.mAMap = map;
        this.vMarker = marker;
    }

    public Marker getMarker() {
        return vMarker;
    }

    public D getData() {
        return mData;
    }

    public void setData(D d) {
        mData = d;
    }

    /**
     * 设置目标点，不包括当前 Marker 所在点
     */
    public void setTargetPoints(List<LatLng> points) {
        stopMove();
        LatLng current = vMarker.getPosition();
        points.add(0, current);
        mMovePath = new MovePath(points, mTotalDuration);
        mRunningIndex = SPAN_NOT_START;
    }

    private ValueAnimator animator;

    private void startMoveSpan(final MovePath movePath, final int index) {
        final MoveSpan moveSpan = movePath.getSpan(index);
        if (moveSpan == null) {
            mRunningIndex = SPAN_NOT_START;
            mRunning = false;
            return;
        }
        mRunningIndex = index;

        final LatLng start = vMarker.getPosition();
        final LatLng to = moveSpan.end;

        final float latDelta = (float) (to.latitude - start.latitude);
        final float lngDelta = (float) (to.longitude - start.longitude);

        if (animator != null) {
            animator.cancel();
        }

        if (latDelta != 0) {
            animator = ValueAnimator.ofFloat((float) start.latitude, (float) to.latitude);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float lat = (float) animation.getAnimatedValue();
                    float lng = (float) (start.longitude + lngDelta * (lat - start.latitude) / latDelta);
                    LatLng newPos = new LatLng(lat, lng);
                    vMarker.setPosition(newPos);
                }
            });
            animator.setDuration(moveSpan.duration);
            vMarker.setRotateAngle(360f - moveSpan.rotate + mAMap.getCameraPosition().bearing);
            animator.start();
        } else if (lngDelta != 0) {
            animator = ValueAnimator.ofFloat((float) start.longitude, (float) to.longitude);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float lng = (float) animation.getAnimatedValue();
                    float lat = (float) (start.latitude + latDelta * (lng - start.longitude) / lngDelta);
                    LatLng newPos = new LatLng(lat, lng);
                    vMarker.setPosition(newPos);
                }
            });
            animator.setDuration(moveSpan.duration);
            vMarker.setRotateAngle(360f - moveSpan.rotate + mAMap.getCameraPosition().bearing);
            animator.start();
        }
    }

    /**
     * 设置运动总时间
     */
    public void setTotalDuration(long duration) {
        mTotalDuration = duration;
    }

    /**
     * 获取当前运动的完整轨迹
     */
    public MovePath getRunningPath() {
        return mMovePath;
    }

    /**
     * 当前运动到第几个Span
     */
    public int getRunningIndex() {
        return mRunningIndex;
    }

    /**
     * 开始移动
     */
    public void startMove() {
        if (mRunning) {
            return;
        }
        mRunning = true;
        startMoveSpan(mMovePath, 0);
    }

    /**
     * 停止移动
     */
    public void stopMove() {
        if (!mRunning) {
            return;
        }
        if (animator != null) {
            animator.cancel();
        }
        mRunning = false;
    }
}
