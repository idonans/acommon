package com.idonans.acommon.lang;

/**
 * Created by idonans on 2016/8/6.
 * <p>
 * 可设置默认最小时间间隔的事件标记
 */
public class SimpleEventTag {

    private final long mMinDuration;
    private final EventTag.Marker mMarker;

    public SimpleEventTag() {
        this(800L);
    }

    public SimpleEventTag(long minDuration) {
        mMinDuration = minDuration;
        mMarker = new EventTag.Marker();
    }

    public boolean mark(EventTag tag) {
        return mMarker.mark(tag, mMinDuration);
    }

    public boolean mark(EventTag tag, long minDuration) {
        return mMarker.mark(tag, minDuration);
    }

}
