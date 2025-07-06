package de.hawhamburg.classfee.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    public static String trim(String value) {
        return value != null ? value.trim() : null;
    }
}