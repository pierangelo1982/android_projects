package com.sviluppo.pierangelo.androidretrofitgson;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by pierangelo on 26/06/16.
 */
public class CluniacensiDeserializer implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement element = json.getAsJsonObject().get("fields");
        return new Gson().fromJson(element, Cluniacensi.class);
    }
}
