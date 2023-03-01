package model;


public class CarFactory {

    public Car.Builder getCar(CarType carType) {
        if (carType == CarType.CAR) {
            return new PassengerCar.Builder();
        }
        if (carType == CarType.TRUCK) {
            return new Truck.Builder();
        }

        return null;

    }
}