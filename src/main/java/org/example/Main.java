package org.example;
import controller.CarController;
import controller.OrderController;
import repository.CarMongoRepository;
import repository.OrderMongoRepository;
import service.CarService;
import service.OrderService;

public class Main {
    public static void main(String[] args) {
        final CarService carService = new CarService(new CarMongoRepository());
        final CarController carController = new CarController(carService);
        final OrderService orderService = new OrderService(new OrderMongoRepository(), carService);
        final OrderController orderController = new OrderController(orderService);

        carOperations(carController);
        orderOperations(orderController);
    }

    private static void carOperations(final CarController controller) {
        final String carID = controller.create();

        System.out.println("Created cars: ");
        controller.get(carID).ifPresentOrElse(System.out::println, () -> System.out.println("Can't find car with id " + carID));
        System.out.println();
        System.out.println("Getting all cars from repository: ");
        controller.getAllCars().forEach(System.out::println);
        System.out.println();
        System.out.println("Deleting car with id..: " + carID);
        System.out.println();
        System.out.println("Get deleted car from repository: ");
        controller.get(carID).ifPresentOrElse(System.out::println, () -> System.out.println("Can't find car with id " + carID));
        System.out.println();
    }

    public static void orderOperations(final OrderController orderController) {
        final String orderId = orderController.create();
        System.out.println("Created orders: ");
        orderController.get(orderId).ifPresentOrElse(System.out::println, () -> System.out.println("Can't find order with id " + orderId));
        System.out.println();
        System.out.println("Getting all orders from repository: ");
        orderController.getAll().forEach(System.out::println);
        System.out.println();
        System.out.println("Deleting order with id..: " + orderId);
        System.out.println();
        System.out.println("Get deleted order from repository: ");
        orderController.get(orderId).ifPresentOrElse(group -> {
            System.out.println(group);
            group.getCarOrder().forEach(System.out::println);
        }, () -> System.out.println("Can't find order with id " + orderId));


    }
    }