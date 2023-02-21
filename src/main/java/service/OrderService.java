package service;
import model.Car;
import model.CarType;
import model.Order;
import repository.Repository;

import java.util.*;

public class OrderService {


    private final Repository<Order> repository;

    private final CarService carService;


    public OrderService(final Repository<Order> repository, final CarService carService) {
        this.repository = repository;
        this.carService = carService;
    }

    public Order create() {
        final Order order = new Order();
        createAndLinkCars(order);
        repository.save(order);
        return order;
    }


    private List<Car> createAndLinkCars(final Order order) {
        final List<Car> cars = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final Car car = carService.createCar(CarType.CAR);
            car.setOrder(order);
            cars.add(car);
        }

        order.setCarOrder(cars);
        return cars;
    }

    public Optional<Order> get(final String id) {
        return repository.getById(id);
    }

    public Order[] getAll() {
        return repository.getAll();
    }

    public void delete ( final String id){
        if (!(id == null || id.isEmpty())) {
            repository.delete(id);
        }
    }

}
