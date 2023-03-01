package model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;

@Getter
@Setter
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class Truck extends Car implements CountRestore {
    private int loadCapacity;

    public Truck() {
    }

    public Truck(String manufacturer, Engine engine, Color color, int loadCapacity) {
        super(manufacturer, engine, color);
        this.loadCapacity = loadCapacity;
    }

    public Truck(String manufacturer, Engine engine, Color color, int count, int price) {
        super(manufacturer, engine, color,count,price);
        this.loadCapacity = getRandom().nextInt(10,50);
    }
    public Truck(String manufacturer, Engine engine, Color color,  int count, int price, int loadCapacity) {
        super(manufacturer, engine, color,count,price);
        this.loadCapacity = loadCapacity;
        this.setCarType(CarType.TRUCK);
    }

    @Override
    public int restore() {
        return this.loadCapacity = 50;
    }
    @Override
    public String toString() {
        return String.format("%s: {manufacturer =%s ; Engine =%s; Color = %s; LoadCapacity=%d; ID=%s; Count=%d)}", getCarType(), getManufacturer(), getEngine(), getColor(), getLoadCapacity(), getId(), getCount());
    }
    public static class Builder extends Car.Builder {
        private int loadCapacity;
        @Override
        public Car.Builder withLoadCapacity(int loadCapacity) {
            this.loadCapacity=loadCapacity;
            return this;
        }
        @Override
        public Car build() {
            if (count <= 0) {
                throw new IllegalArgumentException("count should be more than 0");
            }
            return new Truck(manufacturer,engine,color,count,price,loadCapacity);
        }
    }

}