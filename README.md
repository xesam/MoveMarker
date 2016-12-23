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
1. 地图 Animation 的缺陷，不在视界内，并且没有设置 showInfoWindow() 的时候，Animation 就不会执行。
1. 如果添加当前视界之外的 Marker，高德地图是不会执行动画的。如果不对这种情况进行修正，那么当视界外的点进入视界之内的时候，会触发动画的异常执行。

