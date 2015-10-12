package com.from.nowhere.service;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ProductStore {

    private static ProductStore instance = new ProductStore();

    public interface Container {

        default ProductStore getProductStore() {
            return ProductStore.instance;
        }
    }

    private Map<String, JsonObject> products = new HashMap<>();

    private ProductStore() {
        setUpInitialData();
    }

    private void setUpInitialData() {
        addProduct(new JsonObject().put("id", "prod3568").put("name", "Egg Whisk").put("price", 3.99).put("weight", 150));
        addProduct(new JsonObject().put("id", "prod7340").put("name", "Tea Cosy").put("price", 5.99).put("weight", 100));
        addProduct(new JsonObject().put("id", "prod8643").put("name", "Spatula").put("price", 1.00).put("weight", 80));
    }

    private void addProduct(JsonObject product) {
        products.put(product.getString("id"), product);
    }

    public void addProduct(String id, JsonObject product) {
        products.put(id, product);
    }

    public JsonObject getProduct(String id) {
        return products.get(id);
    }

    public JsonArray getProducts() {
        JsonArray arr = new JsonArray();
        products.forEach((k, v) -> arr.add(v));
        return arr;
    }

}