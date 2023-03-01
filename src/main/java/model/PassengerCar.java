package model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class PassengerCar extends Car {
    private int passengerCount;
    public PassengerCar() {
    }

    public PassengerCar(String manufacturer, Engine engine, Color color,  int passengerCount) {
        super(manufacturer, engine, color);
        this.passengerCount = passengerCount;
    }
    public PassengerCar(String manufacturer, Engine engine, Color color,  int count, int price) {
        super(manufacturer, engine, color,count,price);
        this.passengerCount = getRandom().nextInt(2,6);
    }
    public PassengerCar(String manufacturer, Engine engine, Color color,  int count, int price, int passengerCount) {
        super(manufacturer, engine, color,count,price);
        this.passengerCount = passengerCount;
        this.setCarType(CarType.CAR);
    }

    @Override
    public int restore() {
        return this.passengerCount = 100;
    }
    @Override
    public String toString() {
        return String.format("%s: {manufacturer =%s ; Engine =%s; Color = %s; PassengerCount=%d; ID=%s; Count=%d}", getCarType(), getManufacturer(), getEngine(), getColor(), getPassengerCount(), getId(), getCount());

    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }
    public static class Builder extends Car.Builder {
        private int passengerCount;

        @Override
        public Builder withPassengerCount(int passengerCount) {
            this.passengerCount = passengerCount;
            return this;
        }

        @Override
        public Car build() {
            if (count <= 0) {
                throw new IllegalArgumentException("count should be more than 0");
            }
            return new PassengerCar(manufacturer,engine,color,count,price,passengerCount);
        }
    }
}