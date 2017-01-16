package dev.xesam.android.map.move;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

/**
 * Android SDk Animation
 * Created by xesamguo@gmail.com on 17-1-16.
 */

public class AndroidSdkAnim extends MoveAnim {

    private final Marker vMarker;
    private final LatLng mStart;
    private final LatLng mEnd;
    private OnMoveAnimListener mOnMoveAnimListener;

    private ValueAnimator mAnimation;

    public AndroidSdkAnim(Marker marker, LatLng start, LatLng end, OnMoveAnimListener onMoveAnimListener) {
        this.vMarker = marker;
        this.mStart = start;
        this.mEnd = end;
        this.mOnMoveAnimListener = onMoveAnimListener;
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
                if (mOnMoveAnimListener != null) {
                    mOnMoveAnimListener.onAnimStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mCancel && mOnMoveAnimListener != null) {
                    mOnMoveAnimListener.onAnimEnd();
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
}
