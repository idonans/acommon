package com.idonans.acommon.lang;

import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by idonans on 2016/8/6.
 * <p>
 * 事件标记
 */
public final class EventTag {

    private EventTag() {
    }

    public static EventTag newTag() {
        return new EventTag();
    }

    public final static class Marker {

        private final HashMap<EventTag, Long> mMarkingMap = new HashMap<>();

        /**
         * 标记一个事件，如果标记成功，返回 ture, 否则返回 false. 可以指定参数限制对同一个事件的最小标记时间间隔，
         * 如果对同一个事件的两次标记时间间隔太小，则会标记失败。
         */
        public boolean mark(@Nullable EventTag tag, long minDuration) {
            Long lastMarking = mMarkingMap.get(tag);
            if (lastMarking == null) {
                lastMarking = 0L;
            }

            long timeNow = System.currentTimeMillis();
            if (timeNow - lastMarking >= minDuration) {
                mMarkingMap.put(tag, timeNow);
                return true;
            } else {
                return false;
            }
        }

    }

}