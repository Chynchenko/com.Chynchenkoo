package model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Engine {
    private int power;
    private String type;
    @Id
    @OnDelete(action = OnDeleteAction.CASCADE)
    private String engineID;

    public Engine(int power, String type) {
        this.power = power;
        this.type = type;
        this.engineID= UUID.randomUUID().toString();
    }

    public Engine(int power, String type,String engineID) {
        this.power = power;
        this.type = type;
        this.engineID= engineID;
    }

    @Override
    public String toString() {
        return "[" +
                "power=" + power +
                ", type=" + type + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Engine engine = (Engine) o;

        if (power != engine.power) return false;
        return type.equals(engine.type);
    }

    @Override
    public int hashCode() {
        int result = power;
        result = 31 * result + type.hashCode();
        return result;
    }
}