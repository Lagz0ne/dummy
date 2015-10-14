package com.from.nowhere.service;

import com.from.nowhere.mongoclient.MongoClientProvider;
import io.vertx.core.json.JsonObject;
import javaslang.control.Option;
import rx.Observable;

import java.util.List;

public class ProductStore implements MongoClientProvider {

    private static ProductStore instance = new ProductStore();
    private static final String PRODUCTS = "products";

    public interface Container {
        default ProductStore getProductStore() {
            return ProductStore.instance;
        }
    }

    protected ProductStore() {
        initializeStoreData();
    }

    /**
     * Proof of concept: We can use mongodb in startup even without
     * DI. Defer works
     *
     * **/
    private void initializeStoreData() {
        String random = Math.random() + "";
        JsonObject product = new JsonObject()
                .put("id", random)
                .put("name", "Random whiskey");

        getMongoClient().flatMap(m -> m.insertObservable(PRODUCTS, product)).subscribe();
    }

    public Observable<Option<JsonObject>> getProduct(String id) {
        JsonObject query = new JsonObject().put("id", id);

        return getMongoClient()
                .flatMap(m -> m.findOneObservable(PRODUCTS, query, new JsonObject()))
                .map(Option::of);
    }

    public Observable<List<JsonObject>> getProducts() {

        return getMongoClient()
                .flatMap(m -> m.findObservable(PRODUCTS, new JsonObject()));
    }

}