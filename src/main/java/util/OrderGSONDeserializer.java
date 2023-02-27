package util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import model.Car;
import model.Order;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderGSONDeserializer implements JsonDeserializer<Order> {
    @SneakyThrows
    @Override
    public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Order order = new Order();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = object.get("created").getAsString();

        java.util.Date utilDate = sdf.parse(dateStr);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        order.setCreated(sqlDate);
        order.setOrderId(object.get("_id").getAsString());
        order.setCarOrder(context.deserialize(object.get("carOrder"), new TypeToken<List<Car>>() {
        }.getType()));
        System.out.println();
        return order;
    }
}