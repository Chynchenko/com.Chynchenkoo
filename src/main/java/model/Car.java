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
    @ManyToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "orderId")
    private Order order;
    @Id
    private String id;
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
    public Car(String manufacturer, Engine engine, Color color,int count, int price) {
        this.manufacturer = manufacturer;
        this.engine = engine;
        this.color = color;
        this.id = UUID.randomUUID().toString();
        this.count = count;
        this.price = price;

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
        return "Car{" + "manufacturer='" + manufacturer + ", engine=" + engine + ", color=" + color + ", count=" + count + ", price=" + price + ", id='" + id + ", count='" + count + '}';
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
    @Getter
    public abstract static class Builder {
        protected String manufacturer;

        protected Engine engine;
        protected Color color;
        protected CarType carType;
        protected int count;
        protected int price;
        protected Order order;
        protected String id;

        public Builder withManufacturer(String manufacturer) {
            this.manufacturer=manufacturer;
            return this;
        }

        public Builder withEngine(Engine engine) {
            this.engine=engine;
            return this;
        }

        public Builder withColor(Color color) {
            this.color=color;
            return this;
        }

        public Builder withCount(int count) {
            this.count=count;
            return this;
        }

        public Builder withPrice(int price) {
            if (price < 1000) {
                throw new IllegalArgumentException("Price should be more than 1000$");
            }
            this.price = price;
            return this;
        }

        public Builder withOrder(Order order) {
            this.order=order;
            return this;
        }

        public Builder withId(String id) {
            this.id =id;
            return this;
        }
        public Builder withPassengerCount(int passengerCount) {
            return this;
        }
        public Builder withLoadCapacity(int loadCapacity) {
            return this;
        }
        public abstract Car build ();

    }
}