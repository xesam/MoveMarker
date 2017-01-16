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
        //根据 bus 创建marker
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

高德 SDk 提供了基于 glAnimation 的 4 种动画： TranslateAnimation， AlphaAnimation， ScaleAnimation， RotateAnimation，
不过这 4 种动画都有以下问题（当前是 4.1.3 版本）：

1. Marker Animation 如果带有 InfoWindow，InfoWindow 的移动会有延迟。（4.1.3 已修正）
2. Marker Animation 的缺陷，不在视界内，并且没有设置 showInfoWindow() 的时候（此时 Marker 已经被移除了），Animation 就不会执行。
3. 无法停止 Marker 的 Animation。

## 再次补充
高德地图的论坛太不给力了，如果不是公司要求，我宁愿用百度地图，都散了吧。

