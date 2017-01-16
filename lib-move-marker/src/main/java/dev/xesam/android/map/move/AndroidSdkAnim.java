package dev.xesam.android.map.move;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

/**
 * Created by xesamguo@gmail.com on 17-1-16.
 */

public class AndroidSdkAnim extends MoveAnim {

    private final Marker vMarker;
    private final LatLng mStart;
    private final LatLng mEnd;

    private ValueAnimator mAnimation;

    public AndroidSdkAnim(Marker marker, LatLng start, LatLng end) {
        this.vMarker = marker;
        this.mStart = start;
        this.mEnd = end;
    }

    @Override
    void start(long duration) {
        if (mAnimation != null) {
            mAnimation.cancel();
        }
        mAnimation = ValueAnimator.ofObject(new MoveEvaluator(), mStart, mEnd);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LatLng newPos = (LatLng) animation.getAnimatedValue();
                vMarker.setPosition(newPos);
            }
        });
        mAnimation.addListener(new Animator.AnimatorListener() {
            private boolean mCancel = false;

            @Override
            public void onAnimationStart(Animator animation) {
                onAnimStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mCancel) {
                    onAnimEnd();
                    ;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCancel = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimation.setDuration(duration);
        mAnimation.start();
    }

    @Override
    void stop() {
        if (mAnimation != null) {
            mAnimation.cancel();
        }
    }

    @Override
    void onAnimStart() {

    }

    @Override
    void onAnimEnd() {

    }
}
