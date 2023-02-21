package repository;
import lombok.SneakyThrows;
import model.Engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EngineJdbcRepository implements Repository<Engine> {
    private static EngineJdbcRepository engineJdbcRepository;

    private EngineJdbcRepository() {
    }

    public static EngineJdbcRepository getInstance() {
        if (engineJdbcRepository == null) {
            engineJdbcRepository = new EngineJdbcRepository();
        }
        return engineJdbcRepository;
    }

    @SneakyThrows
    @Override
    public void save(Engine car) {
        Connection connection = JdbcManager.getConnection();
        String prepareStatementSqlEngine = "insert into \"Engine\" (TYPE,POWER,ENGINE_ID) VALUES (?,?,?)";
        PreparedStatement preparedStatementInsertEngine = connection.prepareStatement(prepareStatementSqlEngine);
        preparedStatementInsertEngine.setString(1, car.getType());
        preparedStatementInsertEngine.setInt(2, car.getPower());
        preparedStatementInsertEngine.setString(3, car.getEngineID());
        preparedStatementInsertEngine.executeUpdate();
        connection.close();
    }
    @SneakyThrows
    @Override
    public Engine[] getAll() {
        List<Engine> engineList = new ArrayList<>();
        Connection connection = JdbcManager.getConnection();
        String getAllEngine = "Select POWER,TYPE,ENGINE_ID from \"Engine\"";
        PreparedStatement preparedStatement = connection.prepareStatement(getAllEngine);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            engineList.add(new Engine(resultSet.getInt("POWER"), resultSet.getString("TYPE"), resultSet.getString("ENGINE_ID")));
        }
        connection.close();
        return engineList.toArray(new Engine[0]);
    }

    @SneakyThrows
    @Override
    public Optional<Engine> getById(String engineId) {

        return Arrays.stream(getAll()).filter(engine -> engine.getEngineID().equals(engineId)).findAny();
    }

    @SneakyThrows
    @Override
    public void delete(String id) {
        Connection connection = JdbcManager.getConnection();
        String deleteEngine = "delete POWER,TYPE,ENGINE_ID from \"Engine\" where ENGINE_ID=?;" + "delete ENGINE_ID from \"Car\" where ENGINE_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteEngine);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, id);
        preparedStatement.executeUpdate();
        connection.close();
    }
}