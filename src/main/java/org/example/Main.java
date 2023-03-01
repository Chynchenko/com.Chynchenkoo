package org.example;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import controller.CarController;
import controller.OrderController;
import lombok.SneakyThrows;
import model.*;
import org.slf4j.LoggerFactory;
import repository.CarHibernateRepository;
import repository.OrderHibernateRepository;
import service.CarService;
import service.OrderService;
import java.io.FileInputStream;

public class Main {

    @SneakyThrows

    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            configurator.doConfigure(new FileInputStream("src/main/resources/logback.xml"));
        final CarService carService = new CarService(new CarHibernateRepository());
        final CarController carController = new CarController(carService);
        final OrderService orderService = new OrderService(new OrderHibernateRepository(), carService);
        final OrderController orderController = new OrderController(orderService);
        carOperations(carController);
        orderOperations(orderController);
    }

    private static void carOperations(final CarController controller) {
        final String carID = controller.create();

        controller.get(carID).ifPresentOrElse(System.out::println, () -> System.out.println("Can't find car with id " + carID));
        controller.getCarsId().forEach(System.out::println);
        controller.getAllCars().forEach(System.out::println);
        controller.delete(carID);
        controller.get(carID).ifPresentOrElse(System.out::println, () -> System.out.println("Can't find car with id " + carID));
    }

    public static void orderOperations(final OrderController orderController) {
        final String orderId = orderController.create();
        orderController.get(orderId).ifPresentOrElse(group -> {
            System.out.println(group);
            group.getCarOrder().forEach(System.out::println);
        }, () -> System.out.println("Can't find order with id " + orderId));

        orderController.delete(orderId);

        CarFactory carFactory = new CarFactory();
        Car build1 = carFactory.getCar(CarType.CAR)
                .withColor(Color.GREEN)
                .withEngine(new Engine(100, "MEGA"))
                .withManufacturer("VOLVO")
                .withPrice(1500)
                .withPassengerCount(1)
                .withCount(1)
                .build();
        System.out.println(build1);


        Car build2 = carFactory.getCar(CarType.TRUCK)
                .withColor(Color.BLUE)
                .withEngine(new Engine(400, "ALPHA"))
                .withManufacturer("AUDI")
                .withPrice(5000)
                .withLoadCapacity(4)
                .withCount(1)
                .build();
        System.out.println(build2);

    }
}