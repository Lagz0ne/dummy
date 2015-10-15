package com.from.nowhere.mongoclient;

import com.from.nowhere.Mothership;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.mongo.MongoClient;

public interface MongoClientProvider {
    JsonObject options = new JsonObject()
            .put("host", "localhost")
            .put("port", 27017)
            .put("db_name", "test");

    static rx.Observable<MongoClient> getMongoClient() {
        return Mothership.onAppStarted.map(vertx -> MongoClient.createShared(vertx, options));
    }
}