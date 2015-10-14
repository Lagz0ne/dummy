package com.from.nowhere.service;

import io.vertx.core.json.JsonObject;
import rx.Observable;

import java.util.List;

public interface ProductManager {

    interface Container {
        default ProductManager getProductManager() {
            return ProductManagerImpl.instance;
        }
    }

    Observable<JsonObject> getProduct(String productId);
    Observable<List<JsonObject>> getProducts();
}
