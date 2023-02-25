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

    public Truck(String manufacturer, Engine engine, Color color, String id, int loadCapacity) {
        super(manufacturer, engine, color);
        this.loadCapacity = loadCapacity;
    }
    @Override
    public int restore() {
        return this.loadCapacity = 50;
    }
    @Override
    public String toString() {
        return String.format("%s: {manufacturer =%s ; Engine =%s; Color = %s; LoadCapacity=%d; ID=%s; Count=%d)}",
                getCarType(), getManufacturer(), getEngine(), getColor(), getLoadCapacity(), getId(),getCount());
    }

}