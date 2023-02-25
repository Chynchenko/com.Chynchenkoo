package action;

import controller.CarController;
import controller.OrderController;
import repository.CarHibernateRepository;
import repository.OrderHibernateRepository;
import service.CarService;
import service.OrderService;

public class HibernateOperations implements Action {
    @Override
    public void execute() {

        final CarService carService = new CarService(new CarHibernateRepository());
        final CarController carController = new CarController(carService);
        final OrderService orderService = new OrderService(new OrderHibernateRepository(), carService);
        final OrderController orderController = new OrderController(orderService);

        carOperations(carController);
        orderOperations(orderController);
    }
    private static void carOperations(final CarController controller) {
        final String carID = controller.create();

        controller.get(carID).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Can't find car with id " + carID)
        );

        controller.getCarsId().forEach(System.out::println);
        controller.getAllCars().forEach(System.out::println);

        controller.delete(carID);
        controller.get(carID).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Can't find car with id " + carID)
        );
    }

    public static void orderOperations (final OrderController orderController) {
        final String orderId = orderController.create();
        orderController.get(orderId).ifPresentOrElse(
                group -> {
                    System.out.println(group);
                    group.getCarOrder().forEach(System.out::println
                    );
                },
                () -> System.out.println("Can't find order with id " + orderId)
        );

        orderController.get(orderId).ifPresentOrElse(
                group -> {
                    System.out.println(group);
                    group.getCarOrder().forEach(System.out::println
                    );
                },
                () -> System.out.println("Can't find order with id " + orderId)
        );
    }
}