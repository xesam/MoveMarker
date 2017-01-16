package dev.xesam.android.map.move;

import android.view.animation.LinearInterpolator;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;

/**
 * Created by xesamguo@gmail.com on 17-1-16.
 */

public class MapSdkAnim extends MoveAnim {

    private final Marker vMarker;
    private final LatLng mStart;
    private final LatLng mEnd;

    private Animation mAnimation;

    public MapSdkAnim(Marker marker, LatLng start, LatLng end) {
        this.vMarker = marker;
        this.mStart = start;
        this.mEnd = end;
    }

    @Override
    public void start(long duration) {
        mAnimation = new TranslateAnimation(mEnd);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                onAnimStart();
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

    @Override
    void onAnimStart() {

    }

    @Override
    void onAnimEnd() {

    }
}
