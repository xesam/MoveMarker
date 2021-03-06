package dev.xesam.android.map.movedemo;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xe on 16-12-16.
 */

public abstract class BusSource {


    private int count = 0;
    private Handler mHandler;

    public BusSource() {
        mHandler = new Handler();
    }

    private List<Bus> getBuses1() {
        final List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("1", 39.912013, 116.401131));
        buses.add(new Bus("2", 39.911613, 116.401689));
        buses.add(new Bus("3", 39.91126, 116.401518));
        return buses;
    }

    private List<Bus> getBuses2() {
        final List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("1", 39.911613, 116.401689));
        buses.add(new Bus("2", 39.91126, 116.401518));
        buses.add(new Bus("3", 39.910803, 116.401185));
        buses.add(new Bus("4", 39.910803, 116.401185));
        return buses;
    }

    private void load() {
        switch (count % 2) {
            case 0:
                onBusesLoaded(getBuses1());
                break;
            case 1:
                onBusesLoaded(getBuses2());
                break;
        }
        count++;
    }

    private void start0() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load();
                start0();
            }
        }, 5_000);
    }

    public void start() {
        load();
        start0();
    }

    public void stop() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    abstract void onBusesLoaded(List<Bus> buses);
}
