package com.from.nowhere.mongoclient;

import io.vertx.rxjava.ext.mongo.MongoClient;

public interface MongoClientProvider {
    default rx.Observable<MongoClient> getMongoClient() {
        return MongoClientVerticle.mongoClientObservable;
    }
}