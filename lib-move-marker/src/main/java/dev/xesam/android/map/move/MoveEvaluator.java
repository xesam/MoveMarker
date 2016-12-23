package dev.xesam.android.map.move;

import android.animation.TypeEvaluator;

import com.amap.api.maps.model.LatLng;

/**
 * Created by xesamguo@gmail.com on 16-12-23.
 */

public class MoveEvaluator implements TypeEvaluator<LatLng> {
    @Override
    public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {

        final float latDelta = (float) (endValue.latitude - startValue.latitude);
        final float lngDelta = (float) (endValue.longitude - startValue.longitude);
        float lat = latDelta * fraction;
        float lng = lngDelta * fraction;
        return new LatLng(startValue.latitude + lat, startValue.longitude + lng);
    }
}
