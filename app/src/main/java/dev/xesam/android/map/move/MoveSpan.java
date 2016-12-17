package dev.xesam.android.map.move;

import com.amap.api.maps.model.LatLng;

/**
 * 两点构成的单段运动路径
 * Created by xe on 16-12-13.
 */

public class MoveSpan {
    public float distance;
    public long duration;
    public LatLng end;
    public float rotate;
}
