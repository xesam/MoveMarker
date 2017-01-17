# MoveMarker

高德地图 Marker 按照轨迹移动

## 使用

第一步，添加依赖

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```gradle
	dependencies {
        compile 'com.github.xesam:MoveMarker:0.1'
	}
```

第二步，添加相应的高德 SDk .jar 以及 .so 。

第三步，代码调用

在具体的工程中可以使用单个 marker 或者管理单个 marker。具体参见 [demo https://github.com/xesam/MoveMarker](https://github.com/xesam/MoveMarker)

```java

    MarkerOptions options = new MarkerOptions()
        .title("移动1")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
        .snippet("详细信息")
        .anchor(0.5f, 0.5f)
        .position(latLng);
    MoveMarker<Bus> moveMarker = new MoveMarker<>(mAMap, mAMap.addMarker(options));
    moveMarker.setData(data);
    moveMarker.setTotalDuration(5_000);
    moveMarker.setMovePoints(points);
    moveMarker.startMove();

```

或者

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

```

## 补充

高德 SDk 提供了基于 glAnimation 的 4 种动画： TranslateAnimation， AlphaAnimation， ScaleAnimation， RotateAnimation，
不过这 4 种动画都有以下问题（当前是 4.1.3 版本）：

1. Marker Animation 如果带有 InfoWindow，InfoWindow 的移动会有延迟。
2. Marker Animation 的缺陷，不在视界内，并且没有设置 showInfoWindow() 的时候（此时 Marker 已经被移除了），Animation 就不会执行。
3. 无法停止 Marker 的 Animation。

不过高德方面回复会在新版本解决这些问题，暂用解决办法（在 Marker 不可见的时候，已然有bug）：

1. sdk 4.1.3 已修正。
2. 不要相信 AnimationListener#onAnimationEnd 回调，使用一个与动画时长相等的 delay message 来修正最终的位置。
3. 想停止动画的时候，发起一个时间非常短（比如 10 ms）的动画来冲掉正在执行的动画。

或者完全不要使用 SDK 提供的各种 Animation， 使用 Android 自身的动画机制，不过需要对轨迹作平滑处理。

## 再次补充
高德地图的论坛太不给力了，有问题还是 Github 去提 issues 吧。

