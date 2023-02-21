package controller;

import model.Car;
import model.CarType;
import service.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarController {
    private final CarService carService;

    public CarController(final CarService carService) {
        this.carService = carService;
    }

    public String create() {
        final Car car = carService.createCar(CarType.CAR);
        return car.getId();
    }

    public Optional<Car> get(final String carId) {
        return carService.find(carId);
    }

    public List<String> getCarsId() {
        return Arrays.stream(carService.getAll())
                .map(Car::getId)
                .collect(Collectors.toList());
    }

    public List<Car> getAllCars() {
        return Arrays.stream(carService.getAll()).toList();
    }

    public void delete(final String id) {
        carService.delete(id);
    }
}