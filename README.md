# MoveMarker

高德地图 Marker 按照轨迹移动

## 使用

```java

public class BusMoveMgr extends AbsMoveMgr<Bus> {
    public BusMoveMgr(AMap map) {
        super(map);
    }

    @Override
    protected String getKey(Bus item) {
        return item.id;
    }

    @Override
    protected MoveMarker<Bus> onMarkerAdded(Bus bus) {
        //根据 bus 信息创建marker
        return new MoveMarker<>(mAMap, mAMap.addMarker(options));
    }

    @Override
    protected void onMarkerUpdated(MoveMarker<Bus> moveMarker, Bus updated) {
        //marker 有更新
    }

    @Override
    protected void onMarkerLost(final MoveMarker<Bus> moveMarker) {
        //对应的 marker 丢失
        moveMarker.getMarker().remove();
    }

}

````

## 补充
1. Marker Animation 的缺陷，不在视界内，并且没有设置 showInfoWindow() 的时候，Animation 就不会执行。
2. 无法停止 Marker 的 Animation。

