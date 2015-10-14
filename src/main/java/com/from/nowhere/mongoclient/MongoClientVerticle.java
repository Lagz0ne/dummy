package com.from.nowhere.mongoclient;

import com.from.nowhere.NotReadyError;
import com.from.nowhere.Runner;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.mongo.MongoClient;
import rx.Observable;

import java.util.concurrent.atomic.AtomicBoolean;

public class MongoClientVerticle extends AbstractVerticle {

    public static void main(String args[]) {
        Runner.run(MongoClientVerticle.class);
    }

    private static AtomicBoolean isReady = new AtomicBoolean(false);
    private static MongoClient mongoClient = null;

    static Observable<MongoClient> mongoClientObservable = Observable.create(observer -> {
        if (!isReady.get()) {
            observer.onError(new NotReadyError());
        } else {
            observer.onNext(mongoClient);
        }
    });

    @Override
    public void start() throws Exception {
        mongoClient = defaultMongoClient();
        isReady.set(true);
    }

    private MongoClient defaultMongoClient() {
        JsonObject options = new JsonObject()
                .put("host", "localhost")
                .put("port", 27017)
                .put("db_name", "test");

        return MongoClient.createShared(vertx, options);
    }
}