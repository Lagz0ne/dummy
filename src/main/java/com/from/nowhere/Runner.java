package com.from.nowhere;

import io.vertx.core.Launcher;

public interface Runner {

    static void run(Class clazz) {
        new Launcher().dispatch(new String[]{"run", clazz.getName()});
    }
}
