package model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Car implements CountRestore {
    private String manufacturer;
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Engine engine;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private CarType carType;
    private int count;
    private int price;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_Id")
    private Order order;
    @Id
    private  String id;
    @Transient
    private final transient Random random = new Random();

    public Car(String manufacturer, Engine engine, Color color) {
        this.manufacturer = manufacturer;
        this.engine = engine;
        this.color = color;
        this.id = UUID.randomUUID().toString();
        this.count = 1;
        this.price = random.nextInt(0, 10000);
    }

    public CarType getCarType() {
        return carType;
    }

    public Car(Color color) {
        this.color = color;
        this.id = UUID.randomUUID().toString();
    }

    public Car() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Car{" +
                "manufacturer='" + manufacturer +
                ", engine=" + engine +
                ", color=" + color +
                ", count=" + count +
                ", price=" + price +
                ", id='" + id +
                ", count='" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (!Objects.equals(manufacturer, car.manufacturer)) return false;
        if (!Objects.equals(engine, car.engine)) return false;
        if (color != car.color) return false;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        int result = manufacturer != null ? manufacturer.hashCode() : 0;
        result = 31 * result + (engine != null ? engine.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}