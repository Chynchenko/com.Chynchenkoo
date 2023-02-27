package model;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name="Car_Order")
public class Order {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Car> carOrder;
    private Date created;
    @Id
    @SerializedName(value = "_id",alternate = "orderId")
    private String orderId;

    @SneakyThrows
    public Order() {
        if (created == null) {
            created = Date.valueOf(LocalDate.now());
        }
        if (orderId == null) {
            orderId = UUID.randomUUID().toString();
        }
        carOrder = new ArrayList<>();
    }

    public void addCarToOrder(final Order order, final Car car) {
        order.getCarOrder().add(car);
    }

    public void addAllCars(List<Car> cars) {
        carOrder.addAll(cars);
    }

    @PrePersist
    public void prePersist() {
        if (created == null) {
            created = Date.valueOf(LocalDate.now());
        }
        if (orderId == null) {
            orderId = UUID.randomUUID().toString();

        }
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}