package io.github.steeldev.customanvil.utils;

import java.util.logging.Logger;

public class CALogger extends Logger {
    protected CALogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static CALogger getLogger() {
        return new CALogger("", null);
    }

    @Override
    public void info(String msg) {
        Util.log(msg);
    }
}