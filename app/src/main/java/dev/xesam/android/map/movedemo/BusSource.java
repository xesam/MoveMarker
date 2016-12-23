package dev.xesam.android.map.movedemo;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xe on 16-12-16.
 */

public abstract class BusSource {

    private List<Bus> getBuses1() {
        final List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("1", 39, 115));
        buses.add(new Bus("2", 39, 116));
        buses.add(new Bus("3", 40, 116));
        return buses;
    }

    private List<Bus> getBuses2() {
        final List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("1", 39, 116));
        buses.add(new Bus("2", 40, 116));
        buses.add(new Bus("3", 40, 115));
        buses.add(new Bus("4", 39, 115));
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
                switch (count % 2) {
                    case 0:
                        onBusesLoaded(getBuses1());
                        break;
                    case 1:
                        onBusesLoaded(getBuses2());
                        break;
                }
                count++;
                start();
            }
        }, 6_000);
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    abstract void onBusesLoaded(List<Bus> buses);
}
