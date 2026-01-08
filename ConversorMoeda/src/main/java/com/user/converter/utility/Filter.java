package com.user.converter.utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class Filter {
    public static Map<String, Double> filtrarMoedas(String json) {
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        JsonObject rates = obj.getAsJsonObject("conversion_rates");

        Map<String, Double> mapa = new HashMap<>();
        for (String key : rates.keySet()) {
            mapa.put(key, rates.get(key).getAsDouble());
        }
        return mapa;
    }
}