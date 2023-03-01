package repository;
import model.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarJdbcRepository implements Repository <Car>{
    private static final EngineJdbcRepository ENGINE_JDBC_REPOSITORY = EngineJdbcRepository.getInstance();
    private static CarJdbcRepository carJDBCRepository;

    public CarJdbcRepository() {
        createTablesIfNotExist();
    }

    public static CarJdbcRepository getInstance() {
        if (carJDBCRepository == null) {
            carJDBCRepository = new CarJdbcRepository();
        }
        return carJDBCRepository;
    }

    @SneakyThrows
    public void createTablesIfNotExist() {
        Connection connection = JdbcManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                CREATE TABLE IF NOT EXISTS \"PassengerCar\"
                (
                    PASSENGER_COUNT integer not null,
                    CAR_ID varchar (255) not null,
                    CONSTRAINT PassengerCar_pkey PRIMARY KEY (CAR_ID)
                );
                                
                CREATE TABLE IF NOT EXISTS \"Truck\"
                (
                    LOAD_CAPACITY integer null,
                    CAR_ID varchar (255) not null,
                    CONSTRAINT Truck_pkey PRIMARY KEY (CAR_ID)
                );
                CREATE TABLE IF NOT EXISTS \"Orders\"
                (
                    REP_DATE date  not null,
                    ORDER_ID varchar (255) not null,
                    CONSTRAINT Orders_pkey PRIMARY KEY (ORDER_ID)
                );
                                
                CREATE TABLE IF NOT EXISTS \"Engine\"
                (
                    POWER integer not null,
                    TYPE varchar (255) not null,
                    ENGINE_ID varchar (255) not null,
                    CONSTRAINT Engine_pkey PRIMARY KEY (ENGINE_ID)
                );
                                
                CREATE TABLE IF NOT EXISTS \"Car\"
                (
                    CAR_ID varchar (255) NOT NULL,
                    MANUFACTURER varchar (255) not null,
                    COLOR varchar (255) not null,
                    PRICE integer null,
                    COUNT integer null,
                    ENGINE_ID varchar (255) null,
                    ORDER_ID varchar (255) null,
                    CONSTRAINT Car_pkey PRIMARY KEY (CAR_ID),
                    CONSTRAINT fk_engine FOREIGN KEY (ENGINE_ID)
                        REFERENCES \"Engine\" (ENGINE_ID),
                    CONSTRAINT fk_order FOREIGN KEY (ORDER_ID)
                        REFERENCES \"Orders\" (ORDER_ID) 
     
                );""");
        preparedStatement.executeUpdate();
        connection.close();
    }

    @SneakyThrows
    @Override
    public void save(Car car) {
        Connection connection = JdbcManager.getConnection();
        ENGINE_JDBC_REPOSITORY.save(car.getEngine());
        connection.setAutoCommit(false);
        if (car.getCarType() == CarType.CAR) {
            String prepareStatementSqlPC = "insert into \"PassengerCar\" (passenger_count,car_id) VALUES (?,?)";
            PreparedStatement preparedStatementPC = connection.prepareStatement(prepareStatementSqlPC);
            preparedStatementPC.setInt(1, ((PassengerCar) car).getPassengerCount());
            preparedStatementPC.setString(2, car.getId());
            preparedStatementPC.executeUpdate();
        } else if (car.getCarType() == CarType.TRUCK) {
            String prepareStatementSqlPC = "insert into \"Truck\" (load_capacity,car_id) VALUES (?,?)";
            PreparedStatement preparedStatementTC = connection.prepareStatement(prepareStatementSqlPC);
            preparedStatementTC.setInt(1, ((Truck) car).getLoadCapacity());
            preparedStatementTC.setString(2, car.getId());
            preparedStatementTC.executeUpdate();
        }
        String prepareStatementSql = "insert into \"Car\" (car_id,manufacturer,color,price,count,engine_id) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatementInsertCar = connection.prepareStatement(prepareStatementSql);
        preparedStatementInsertCar.setString(1, car.getId());
        preparedStatementInsertCar.setString(2, car.getManufacturer());
        preparedStatementInsertCar.setString(3, car.getColor().toString());
        preparedStatementInsertCar.setInt(4, car.getPrice());
        preparedStatementInsertCar.setInt(5, car.getCount());
        preparedStatementInsertCar.setString(6, car.getEngine().getEngineID());

        System.out.println(preparedStatementInsertCar);
        preparedStatementInsertCar.executeUpdate();
        connection.commit();
        connection.close();
    }

    @SneakyThrows
    @Override
    public Car[] getAll() {
        List<Car> carList = new ArrayList<>();
        Connection connection = JdbcManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                Select t1.car_id,
                       t1.manufacturer, 
                       t1.color,
                       t1.price,
                       t1.count,
                       t2.TYPE,
                       t2.POWER,
                       t3.passenger_count,
                       t4.load_capacity
                  from \"Car\" as t1
                  left join \"Engine\" as t2 on t2.ENGINE_ID=t1.ENGINE_ID
                  left join \"PassengerCar\" as t3 on t3.car_id=t1.car_id 
                  left join \"Truck\" as t4 on t4.car_id=t1.car_id
                 
                  """);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("passenger_count") != null) {
                carList.add(createPC(resultSet));
            } else if (resultSet.getString("load_capacity") != null) {
                carList.add(createTruck(resultSet));
            }
        }
        connection.close();

        return carList.toArray(new Car[0]);
    }

    @SneakyThrows
    public Car[] getAllCarsByOrderId(String orderId) {
        List<Car> carList = new ArrayList<>();
        Connection connection = JdbcManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                Select t1.car_id,
                       t1.manufacturer, 
                       t1.color,
                       t1.price,
                       t1.count,
                       t2.TYPE,
                       t2.POWER,
                       t3.passenger_count,
                       t4.load_capacity,
                       t5.order_id
                  from \"Car\" as t1 
                  left join \"Engine\" as t2 on t2.ENGINE_ID=t1.ENGINE_ID
                  left join \"PassengerCar\" as t3 on t3.car_id=t1.car_id 
                  left join \"Truck\" as t4 on t4.car_id=t1.car_id
                  join \"Orders\" as t5  on t5.order_id=t1.order_id
                  where t5.order_id=?
                 
                  """);
        preparedStatement.setString(1,orderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("passenger_count") != null) {
                carList.add(createPC(resultSet));
            } else if (resultSet.getString("load_capacity") != null) {
                carList.add(createTruck(resultSet));
            }
        }
        connection.close();

        return carList.toArray(new Car[0]);
    }

    @SneakyThrows
    @Override
    public Optional<Car> getById(String id) {
        Connection connection = JdbcManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                Select t1.car_id,
                       t1.manufacturer, 
                       t1.color,
                       t1.price,
                       t1.count,
                       t2.TYPE,
                       t2.POWER,
                       t3.passenger_count,
                       t4.load_capacity
                  from \"Car\" as t1 
                  join \"Engine\" as t2 on t2.ENGINE_ID=t1.ENGINE_ID
                  left join \"PassengerCar\" as t3 on t3.car_id=t1.car_id 
                  left join \"Truck\" as t4 on t4.car_id=t1.car_id
                  where t1.car_id =?
                  """);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("passenger_count") != null) {
                return Optional.of(createPC(resultSet));
            } else if (resultSet.getString("load_capacity") != null) {
                return Optional.of(createTruck(resultSet));
            }
        }
        connection.close();
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public void delete(String id) {
        Connection connection = JdbcManager.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement( """
                select engine_id 
                into \"#e\"
                from \"Car\" 
                where car_id=?;
                               
                delete 
                from \"Car\"
                where car_id =?;
                
                delete
                from \"Engine\"
                where engine_id = (select engine_id from \"#e\" );
                                                
                delete 
                from \"PassengerCar\"
                where car_id =?;  
                
                delete 
                from \"Truck\"
                where car_id =?;
                
                drop table \"#e\";                  
                """);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, id);
        preparedStatement.setString(3, id);
        preparedStatement.setString(4, id);
        preparedStatement.execute();
        connection.commit();
        connection.close();
    }

    @SneakyThrows
    private PassengerCar createPC(ResultSet resultSet) {
        PassengerCar passengerCar = new PassengerCar();
        passengerCar.setId(resultSet.getString("car_id"));
        passengerCar.setManufacturer(resultSet.getString("manufacturer"));
        passengerCar.setColor(EnumUtils.getEnum(Color.class, resultSet.getString("color")));
        passengerCar.setPrice(resultSet.getInt("price"));
        passengerCar.setCount(resultSet.getInt("count"));
        passengerCar.setEngine(new Engine(resultSet.getInt("power"), resultSet.getString("type")));
        passengerCar.setPassengerCount(resultSet.getInt("passenger_count"));
        passengerCar.setCarType(CarType.CAR);
        return passengerCar;
    }

    @SneakyThrows
    private Truck createTruck(ResultSet resultSet) {
        Truck truck = new Truck();
        truck.setId(resultSet.getString("car_id"));
        truck.setManufacturer(resultSet.getString("manufacturer"));
        truck.setColor(EnumUtils.getEnum(Color.class, resultSet.getString("color")));
        truck.setPrice(resultSet.getInt("price"));
        truck.setCount(resultSet.getInt("count"));
        truck.setEngine(new Engine(resultSet.getInt("power"), resultSet.getString("type")));
        truck.setLoadCapacity(resultSet.getInt("load_capacity"));
        truck.setCarType(CarType.TRUCK);
        return truck;
    }
}