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

    private ProductStore() {}

    public Observable<String> addProduct(final JsonObject product) {
        return getMongoClient().flatMap(m -> m.saveObservable(PRODUCTS, product));
    }

    public Observable<Option<JsonObject>> getProduct(String id) {
        JsonObject query = new JsonObject().put("id", id);

        return getMongoClient()
                .flatMap(m -> m.findOneObservable(PRODUCTS, query, new JsonObject()))
                .map(Option::of);
    }

    public Observable<List<JsonObject>> getProducts() {
        return getMongoClient().flatMap(mongoClient ->
                        mongoClient.findObservable(PRODUCTS, new JsonObject())
        );
    }

}