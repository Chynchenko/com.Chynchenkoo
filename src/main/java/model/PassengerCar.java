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

    public PassengerCar(String manufacturer, Engine engine, Color color, String id, int passengerCount) {
        super(manufacturer, engine, color);
        this.passengerCount = passengerCount;
    }

    @Override
    public int restore() {
        return this.passengerCount = 100;
    }

    @Override
    public String toString() {

        return String.format("%s: {manufacturer =%s ; Engine =%s; Color = %s; PassengerCount=%d; ID=%s; Count=%d}",
                getCarType(), getManufacturer(), getEngine(), getColor(), getPassengerCount(), getId(), getCount());
    }
}