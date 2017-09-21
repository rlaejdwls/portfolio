package com.example.coresample.activities.restful.converter;

import com.example.coresample.activities.restful.model.TestModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigris on 2017-09-18.
 */
public class TestDeserializer implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray array = (JsonArray) json;
        List<TestModel> results = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = (JsonObject) array.get(i);
            results.add(new TestModel(25, object.get("name").getAsString(),
                    object.get("age").getAsString(), object.get("nationality").getAsString()));
        }
        return results;
    }
}
