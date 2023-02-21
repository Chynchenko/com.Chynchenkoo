package container;

import model.Car;

import java.util.Optional;
import java.util.Random;

public class GenericContainer <T extends Car> {
    private final T car;
    private final Random RANDOM = new Random();

    public GenericContainer(T car) {
        this.car = car;
    }

    public void print() {
        System.out.println(car);
    }

    public void increaseCount () {
        car.setCount(RANDOM.nextInt(100,300));
    }

    public <N extends Number>  void increaseCount (N number) {
        if(Optional.ofNullable(number).isPresent()) {
            car.setCount(number.intValue());
        }
    }


}