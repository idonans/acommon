package com.idonans.acommon.util;

import com.idonans.acommon.lang.Available;
import com.idonans.acommon.lang.NotAvailableException;

/**
 * Created by idonans on 16-4-13.
 * <p/>
 * 如果需要使用一个可用的Available，可以使用AvailableUtil.always();
 */
public class AvailableUtil {

    public static boolean isAvailable(Object object) {
        if (object instanceof Available) {
            return ((Available) object).isAvailable();
        }
        return object != null;
    }

    /**
     * 如果不可用，则抛出异常
     */
    public static void mustAvailable(Object object) {
        if (!isAvailable(object)) {
            throw new NotAvailableException();
        }
    }

    private static final Available ALWAYS_AVAILABLE = new Available() {
        @Override
        public boolean isAvailable() {
            return true;
        }
    };

    public static Available always() {
        return ALWAYS_AVAILABLE;
    }

}
