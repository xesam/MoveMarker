package dev.xesam.android.map.move;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xe on 16-12-16.
 */

public abstract class BusSource {

    private List<Bus> getBuses1() {
        final List<Bus> buses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bus bus = new Bus();
            if (i % 2 == 0) {
                bus.id = i + "";
                bus.lat = 40;
                bus.lng = 116 + i * 0.05;
            } else {
                bus.id = i + "";
                bus.lat = 40 + i * 0.05;
                bus.lng = 116;
            }
            buses.add(bus);
        }
        return buses;
    }

    private List<Bus> getBuses2() {
        final List<Bus> buses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Bus bus = new Bus();
            if (i % 2 == 0) {
                bus.id = i + "";
                bus.lat = 40.01;
                bus.lng = 116 + i * 0.05;
            } else {
                bus.id = i + "";
                bus.lat = 40 + i * 0.05;
                bus.lng = 116.01;
            }
            buses.add(bus);
        }
        return buses;
    }

    private List<Bus> getBuses3() {
        final List<Bus> buses = new ArrayList<>();
        for (int i = 2; i < 5; i++) {
            Bus bus = new Bus();
            if (i % 2 == 0) {
                bus.id = i + "";
                bus.lat = 39.99;
                bus.lng = 116 + i * 0.05;
            } else {
                bus.id = i + "";
                bus.lat = 40 + i * 0.05;
                bus.lng = 115.99;
            }
            buses.add(bus);
        }
        return buses;
    }

    private int count = 0;
    private Handler mHandler;

    public void start() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (count % 3) {
                    case 0:
                        onBusesLoaded(getBuses1());
                        break;
                    case 1:
                        onBusesLoaded(getBuses2());
                        break;
                    default:
                        onBusesLoaded(getBuses3());
                }
                count++;
                start();
            }
        }, 2000);
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    abstract void onBusesLoaded(List<Bus> buses);
}
