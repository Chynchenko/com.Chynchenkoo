package repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import model.Car;
import org.bson.Document;
import util.CarDeserialization;
import util.MongoUtil;

import java.util.ArrayList;
import java.util.Optional;

public class CarMongoRepository implements Repository<Car> {
    private final MongoCollection<Document> collection;
    private final Gson gson;

    MongoDatabase mongoDatabase = MongoUtil.connect("Mongo");

    public CarMongoRepository() {

        this.collection = mongoDatabase.getCollection("Car");
        gson = new GsonBuilder().registerTypeAdapter(Car.class, new CarDeserialization()).setPrettyPrinting().create();
    }

    protected Document mapFrom(Car car) {
        return Document.parse(gson.toJson(car));
    }

    @SneakyThrows
    @Override
    public void save(Car car) {
        collection.insertOne(mapFrom(car));
    }

    @SneakyThrows
    @Override
    public Car[] getAll() {
        return collection.find().map(x -> gson.fromJson(x.toJson(), Car.class)).into(new ArrayList<>()).toArray(new Car[0]);
    }

    @SneakyThrows
    @Override
    public Optional<Car> getById(String id) {
        Document document = collection.find(Filters.eq("id", id)).first();
        if (document != null) {
            return Optional.ofNullable(gson.fromJson(document.toJson(), Car.class));
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(Filters.eq("id", id));

    }
}