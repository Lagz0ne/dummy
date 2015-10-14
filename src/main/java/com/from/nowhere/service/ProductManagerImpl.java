package com.from.nowhere.service;

import com.from.nowhere.rest.RestError;
import io.vertx.core.json.JsonObject;
import rx.Observable;

import java.util.List;

public class ProductManagerImpl implements ProductStore.Container, ProductManager {

    static ProductManager instance = new ProductManagerImpl();

    @Override
    public Observable<JsonObject> getProduct(String productId) {
        return getProductStore()
                .getProduct(productId)
                .flatMap(productOption ->
                                productOption.map(Observable::just)
                                        .orElse(Observable.error(new RestError()))
                );
    }

    @Override
    public Observable<List<JsonObject>> getProducts() {
        return getProductStore().getProducts();
    }
}