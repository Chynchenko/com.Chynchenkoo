package util;

import com.google.gson.*;
import model.Car;
import model.PassengerCar;
import model.Truck;

import java.lang.reflect.Type;

public class CarDeserialization implements JsonDeserializer<Car> {

    @Override
    public Car deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String carType = jsonObject.get("carType").getAsString();
        Car car;
        if (carType.equals("CAR")) {
            car = new PassengerCar();
        } else if (carType.equals("TRUCK")) {
            car = new Truck();
        } else {
            throw new IllegalArgumentException();
        }
        return jsonDeserializationContext.deserialize(jsonObject, car.getClass());
    }

}