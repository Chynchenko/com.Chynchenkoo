package repository;

import annotation.Singleton;
import model.Car;

import java.util.*;
import java.util.function.BiPredicate;
@Singleton
public class CarMapRepository implements Repository<Car> {
    private static final Map<String,Car> CARS = new HashMap<>();
    private static CarMapRepository instance;

    private static final BiPredicate<String, String> CHECK_ID = String::equals;

    private static CarMapRepository getInstance() {
        if (instance == null) {
            instance = new CarMapRepository();
        }
        return instance;
    }

    @Override
    public void save(Car car) {
        CARS.entrySet().stream()
                .filter(entry -> CHECK_ID.test(entry.getKey(), car.getId()))
                .findAny()
                .ifPresentOrElse(entry -> entry.getValue().setCount(entry.getValue().getCount()), () -> CARS.put(car.getId(),car));
    }

    @Override
    public Car[] getAll() {
        return CARS.values().toArray(new Car[0]);
    }

    @Override
    public Optional<Car> getById(String id) {
        return   CARS.values().stream()
                .filter(entry -> CHECK_ID.test(String.valueOf(entry), id))
                .findAny();
    }
    @Override
    public void delete(String id) {
        CARS.remove(id);
    }
}