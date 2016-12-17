package dev.xesam.android.map.move;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;

import java.util.List;

/**
 * Created by xe on 16-12-13.
 */

public class MoveMarker2 {
    private Marker vMarker;

    private MovePath mMovePath;
    private long mTotalDuration;

    public MoveMarker2(Marker marker) {
        this.vMarker = marker;
    }

    public Marker getMarker() {
        return vMarker;
    }

    /**
     * 设置运动路劲
     */
    public void setPathPoints(List<LatLng> points) {
        stopMove();
        mMovePath = new MovePath(points, mTotalDuration);
    }

    private void moveSpan(final MovePath movePath, final int index) {
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
                moveSpan(movePath, index + 1);
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
        moveSpan(mMovePath, 0);
    }

    /**
     * 停止移动
     */
    public void stopMove() {
        //没有停止方法，变通一下
        Animation animation = new TranslateAnimation(vMarker.getPosition());
        animation.setDuration(5);
        vMarker.setAnimation(animation);
        vMarker.startAnimation();
    }
}
