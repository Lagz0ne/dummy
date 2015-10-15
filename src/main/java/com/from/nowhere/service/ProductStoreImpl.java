package com.from.nowhere.service;

import com.from.nowhere.Mothership;
import com.from.nowhere.mongoclient.MongoClientProvider;
import io.vertx.core.json.JsonObject;
import javaslang.control.Option;
import rx.Observable;
import rx.Subscription;

import java.util.List;

public class ProductStoreImpl implements ProductStore {

    static ProductStoreImpl instance = new ProductStoreImpl();
    private static final String PRODUCTS = "products";

    protected ProductStoreImpl() {
    }

    /**
     * Proof of concept: We can use mongodb in startup even without
     * DI. Defer works
     *
     * Even though Observable can be used in pretty static way, it would be super hard to write tests
     *
     * **/
    static {
        String random = Math.random() + "";
        JsonObject product = new JsonObject()
                .put("id", random)
                .put("name", "Random whiskey");

        MongoClientProvider.getMongoClient()
                .flatMap(m -> m.insertObservable(PRODUCTS, product)).subscribe();
    }

    private static final Subscription onServerStarted = Mothership.onAppStarted
            .subscribe(vertx -> System.out.println("Random"));

    public Observable<Option<JsonObject>> getProduct(String id) {
        JsonObject query = new JsonObject().put("id", id);
        return MongoClientProvider.getMongoClient()
                .flatMap(m -> m.findOneObservable(PRODUCTS, query, new JsonObject()))
                .map(Option::of);
    }

    public Observable<List<JsonObject>> getProducts() {
        return MongoClientProvider.getMongoClient()
                .flatMap(m -> m.findObservable(PRODUCTS, new JsonObject()));
    }

}