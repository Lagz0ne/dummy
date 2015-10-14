package com.from.nowhere.service;

import io.vertx.core.json.JsonObject;
import javaslang.control.Option;
import rx.Observable;

import java.util.List;

public interface ProductStore {

    interface Container {
        default ProductStore getProductStore() {
            return ProductStoreImpl.instance;
        }
    }

    Observable<Option<JsonObject>> getProduct(String id);
    Observable<List<JsonObject>> getProducts();

}
