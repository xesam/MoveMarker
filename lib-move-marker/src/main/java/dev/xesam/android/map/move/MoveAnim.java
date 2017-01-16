package dev.xesam.android.map.move;

/**
 * Created by xesamguo@gmail.com on 17-1-16.
 */

public abstract class MoveAnim {

    public static final int TYPE_MAP_SDK = 0;
    public static final int TYPE_ANDROID_SDK = 1;

    interface OnMoveAnimListener {

        void onAnimStart();

        void onAnimEnd();
    }

    abstract void start(long duration);

    abstract void stop();
}
