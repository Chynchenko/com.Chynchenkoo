package util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomGSONDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = jsonElement.getAsJsonPrimitive().getAsString();
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}