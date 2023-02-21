package repository;

import lombok.SneakyThrows;
import model.Car;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OrderJdbcRepository implements Repository<Order> {
    private static OrderJdbcRepository orderJDBCRepository;
    private static final CarJdbcRepository CAR_JDBC_REPOSITORY = CarJdbcRepository.getInstance();

    public static OrderJdbcRepository getInstance() {
        if (orderJDBCRepository == null) {
            orderJDBCRepository = new OrderJdbcRepository();
        }
        return orderJDBCRepository;
    }

    @SneakyThrows
    public void dropAllTables() {
        Connection connection = JdbcManager.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("""
                DROP TABLE \"Car\";
                DROP TABLE \"Engine\";
                DROP TABLE \"PassengerCar\";
                DROP TABLE \"Truck\";
                DROP TABLE \"Orders\";
                """);

        preparedStatement.execute();
        connection.commit();

        connection.close();
    }

    @SneakyThrows
    @Override
    public void save(Order order) {
        Connection connection = JdbcManager.getConnection();
        connection.setAutoCommit(false);
        String insertOrder = "insert into \"Orders\" (REP_DATE,ORDER_ID) VALUES (?,?)";
        PreparedStatement preparedStatementInsertOrder = connection.prepareStatement(insertOrder);
        preparedStatementInsertOrder.setDate(1, order.getCreated());
        preparedStatementInsertOrder.setString(2, order.getOrderId());
        preparedStatementInsertOrder.executeUpdate();

        String addOrderIdToCar = "update \"Car\" set ORDER_ID =? where CAR_ID =?";
        PreparedStatement preparedStatementaddOrderIdToCar = connection.prepareStatement(addOrderIdToCar);
        for (Car car : order.getCarOrder()) {
            preparedStatementaddOrderIdToCar.setString(1, order.getOrderId());
            preparedStatementaddOrderIdToCar.setString(2, car.getId());
            preparedStatementaddOrderIdToCar.execute();
        }
        connection.commit();
        connection.close();

    }

    @SneakyThrows
    @Override
    public Order[] getAll() {
        List<Order> orderList = new ArrayList<>();
        Connection connection = JdbcManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select order_id,REP_DATE from \"Orders\"");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Order order = new Order();
            order.addAllCars(Arrays.stream(CAR_JDBC_REPOSITORY.getAllCarsByOrderId(resultSet.getString("order_id"))).toList());
            order.setOrderId(resultSet.getString("order_id"));
            order.setCreated(resultSet.getDate("REP_DATE"));
            orderList.add(order);
        }
        connection.close();

        return orderList.toArray(new Order[0]);
    }

    @Override
    public Optional<Order> getById(String id) {
        return Arrays.stream(getAll()).filter(order -> order.getOrderId().equals(id)).findAny();
    }

    @SneakyThrows
    @Override
    public void delete(String id) {
        Connection connection = JdbcManager.getConnection();
        String deleteOrder = "delete REP_DATE,ORDER_ID from  \"Orders\" where ORDER_ID=?;" + "delete ORDER_ID from \"Car\" where ORDER_ID =?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteOrder);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, id);
        preparedStatement.executeUpdate();
        connection.close();


    }
}