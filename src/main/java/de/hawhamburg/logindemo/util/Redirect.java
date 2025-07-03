package de.hawhamburg.logindemo.util;

import org.springframework.util.Assert;

public final class Redirect {

    private Redirect() {
    }

    public static String to(String endpoint) {
        Assert.notNull(endpoint, "endpoint must not be null");
        return "redirect:%s".formatted(endpoint);
    }
}