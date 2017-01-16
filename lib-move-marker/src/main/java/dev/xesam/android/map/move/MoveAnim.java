package dev.xesam.android.map.move;

/**
 * Created by xesamguo@gmail.com on 17-1-16.
 */

public abstract class MoveAnim {

    abstract void start(long duration);

    abstract void stop();

    abstract void onAnimStart();

    abstract void onAnimEnd();
}
