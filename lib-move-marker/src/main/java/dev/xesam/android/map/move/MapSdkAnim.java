package dev.xesam.android.map.move;

import android.os.Handler;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;

/**
 * Map SDk Animation
 * Created by xesamguo@gmail.com on 17-1-16.
 */

public class MapSdkAnim extends MoveAnim {

    private final Marker vMarker;
    private final LatLng mStart;
    private final LatLng mEnd;
    private OnMoveAnimListener mOnMoveAnimListener;

    private Animation mAnimation;

    public MapSdkAnim(Marker marker, LatLng start, LatLng end, OnMoveAnimListener onMoveAnimListener) {
        this.vMarker = marker;
        this.mStart = start;
        this.mEnd = end;
        this.mOnMoveAnimListener = onMoveAnimListener;
    }

    @Override
    public void start(long duration) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mOnMoveAnimListener != null) {
                    vMarker.setPosition(mEnd);
                    mOnMoveAnimListener.onAnimEnd();
                }
            }
        }, duration);

        mAnimation = new TranslateAnimation(mEnd);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                if (mOnMoveAnimListener != null) {
                    mOnMoveAnimListener.onAnimStart();
                }
            }

            @Override
            public void onAnimationEnd() {
            }
        });
        mAnimation.setDuration(duration);
        vMarker.setAnimation(mAnimation);
        vMarker.startAnimation();
    }

    @Override
    void stop() {
        Animation animation = new TranslateAnimation(vMarker.getPosition());
        animation.setDuration(5);
        vMarker.setAnimation(animation);
        vMarker.startAnimation();
    }
}
