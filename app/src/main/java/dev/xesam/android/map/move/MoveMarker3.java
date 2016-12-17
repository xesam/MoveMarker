package dev.xesam.android.map.move;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;

import java.util.List;

/**
 * Created by xe on 16-12-13.
 */

public class MoveMarker3<D> {
    private D mData;
    private Marker vMarker;

    private MovePath mMovePath;
    private long mTotalDuration;

    public MoveMarker3(Marker marker) {
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
    }

    private void startMoveSpan(final MovePath movePath, final int index) {
        movePath.mRunningSpanIndex = index;
        final MoveSpan moveSpan = movePath.getSpan(index);
        if (moveSpan == null) {
            return;
        }
        Animation animation = new TranslateAnimation(moveSpan.end);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                vMarker.setRotateAngle(360f - moveSpan.rotate);
            }

            @Override
            public void onAnimationEnd() {
                startMoveSpan(movePath, index + 1);
            }
        });
        animation.setDuration(moveSpan.duration);
        vMarker.setAnimation(animation);
        vMarker.startAnimation();
    }

    /**
     * 设置运动总时间
     */
    public void setTotalDuration(long duration) {
        mTotalDuration = duration;
    }

    /**
     * 当前运动到第几个Span
     */
    public MoveSpan getRunningSpan() {
        return mMovePath.getRunningSpan();
    }

    /**
     * 开始移动
     */
    public void startMove() {
        startMoveSpan(mMovePath, 0);
    }

    /**
     * 停止移动
     */
    public void stopMove() {
        Animation animation = new TranslateAnimation(vMarker.getPosition());
        animation.setDuration(5);
        vMarker.setAnimation(animation);
        vMarker.startAnimation();
    }
}
