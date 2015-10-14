package com.from.nowhere.service;

import com.from.nowhere.rest.RestError;
import io.vertx.core.json.JsonObject;
import rx.Observable;

public class ProductManager implements ProductStore.Container {

    private static ProductManager instance = new ProductManager();
    public interface Container {
        default ProductManager getProductManager() {
            return instance;
        }
    }

    public Observable<JsonObject> getProduct(String productId) {
        return getProductStore().getProduct(productId).flatMap(productOption ->
                productOption.map(Observable::just).orElse(Observable.error(new RestError())));
    }
}