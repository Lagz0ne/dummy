package com.from.nowhere.mongoclient;

import com.from.nowhere.Runner;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.mongo.MongoClient;
import rx.Observable;

public class MongoClientVerticle extends AbstractVerticle {

    public static void main(String args[]) {
        Runner.run(MongoClientVerticle.class);
    }

    private static MongoClient mongoClient;
    public static Observable<MongoClient> clientObservable = Observable
            .defer(() -> Observable.just(mongoClient))
            .filter(o -> mongoClient != null);

    @Override
    public void start() throws Exception {
        JsonObject options = new JsonObject()
                .put("host", "localhost")
                .put("port", 27017)
                .put("db_name", "test");

        mongoClient = MongoClient.createShared(vertx, options);
    }

}