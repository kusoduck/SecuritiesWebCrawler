package com.kusoduck.utils;

import com.kusoduck.stock.constant.ChineseHeader;

public class HeaderUtils {
    public static <T extends Enum<T> & ChineseHeader> T getByZhTitle(Class<T> enumClass, String value) {
        if (value != null) {
            String realValue = value.trim();
            if (!realValue.isEmpty()) {
                for (T constant : enumClass.getEnumConstants()) {
                    if (constant.getZhTitle().equals(realValue)) {
                        return constant;
                    }
                }
            }
        }
        return null;
    }
}