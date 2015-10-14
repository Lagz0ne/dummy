package com.from.nowhere;

import com.from.nowhere.mongoclient.MongoClientVerticle;
import com.from.nowhere.rest.RestVerticle;
import io.vertx.core.AbstractVerticle;

public class Mothership extends AbstractVerticle {

    public static void main(String args[]) {
        Runner.run(Mothership.class);
    }

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(MongoClientVerticle.class.getName());
        vertx.deployVerticle(RestVerticle.class.getName());
    }
}
