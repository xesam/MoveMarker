package dev.xesam.android.map.movedemo;

/**
 * Created by xe on 16-12-13.
 */

public class Bus {
    public String id;
    public double lat;
    public double lng;

    public Bus(String id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id='" + id + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
