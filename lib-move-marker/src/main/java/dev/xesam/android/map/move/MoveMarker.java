package dev.xesam.android.map.move;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import java.util.List;

/**
 * Android SDk Animation
 * Created by xe on 16-12-13.
 */

public class MoveMarker<D> {
    private D mData;

    private final AMap mAMap;
    private final Marker vMarker;
    private final int mAnimType;

    private MovePath mMovePath;
    private static final int SPAN_NOT_START = 0;
    private int mRunningIndex = SPAN_NOT_START;
    private long mTotalDuration;
    private boolean mRunning = false;

    public MoveMarker(AMap map, Marker marker) {
        this(map, marker, MoveAnim.TYPE_MAP_SDK);
    }

    public MoveMarker(AMap map, Marker marker, int animType) {
        this.mAMap = map;
        this.vMarker = marker;
        this.mAnimType = animType;
    }

    public Marker getMarker() {
        return vMarker;
    }

    /**
     * 不执行动画，直接去往某个地方
     */
    public void directTo(LatLng end) {
        stopMove();
        vMarker.setPosition(end);
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
    public void setMovePoints(List<LatLng> points) {
        stopMove();
        LatLng current = vMarker.getPosition();
        points.add(0, current);
        mMovePath = new MovePath(points, mTotalDuration);
        mRunningIndex = SPAN_NOT_START;
    }

    private MoveAnim mMoveAnim;

    private MoveAnim createMoveAnim(LatLng end, MoveAnim.OnMoveAnimListener onMoveAnimListener) {
        if (mAnimType == MoveAnim.TYPE_ANDROID_SDK) {
            return new AndroidSdkAnim(vMarker, vMarker.getPosition(), end, onMoveAnimListener);
        } else {
            return new MapSdkAnim(vMarker, vMarker.getPosition(), end, onMoveAnimListener);
        }
    }

    private void startMoveSpan(final MovePath movePath, final int index) {
        final MoveSpan moveSpan = movePath.getSpan(index);
        if (moveSpan == null) {
            mRunningIndex = SPAN_NOT_START;
            mRunning = false;
            return;
        }
        mRunningIndex = index;
        mMoveAnim = createMoveAnim(moveSpan.end, new MoveAnim.OnMoveAnimListener() {
            @Override
            public void onAnimStart() {
                vMarker.setRotateAngle(360f - moveSpan.rotate + mAMap.getCameraPosition().bearing);
            }

            @Override
            public void onAnimEnd() {
                startMoveSpan(movePath, index + 1);
            }
        });
        mMoveAnim.start(moveSpan.duration);
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
        if (mMoveAnim != null) {
            mMoveAnim.stop();
        }
        mRunning = false;
    }
}
