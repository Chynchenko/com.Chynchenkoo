package repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import model.Car;
import model.Order;
import org.bson.Document;
import util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class OrderMongoRepository implements Repository<Order> {
    private final MongoCollection<Document> collection;
    private final Gson gson;

    public OrderMongoRepository() {
        MongoDatabase mongoDatabase = MongoUtil.connect("Mongo");
        this.collection = mongoDatabase.getCollection("Order");
        gson = new GsonBuilder().setDateFormat("dd-MM-yyyy")
                .registerTypeAdapter(Car.class, new CarDeserialization())
                .registerTypeAdapter(Order.class, new OrderGSONDeserializer())
                .registerTypeAdapter(Date.class, new CustomGSONSerializer())
                .registerTypeAdapter(Date.class, new CustomGSONDeserializer())
                .create();
    }

    protected Document mapFrom(Order order) {
        return Document.parse(gson.toJson(order));
    }
    @SneakyThrows
    @Override
    public void save(Order order) {
        collection.insertOne(mapFrom(order));
    }

    @Override
    public Order[] getAll() {
        return collection.find().map(x -> gson.fromJson(x.toJson(), Order.class)).into(new ArrayList<>()).toArray(new Order[0]);

    }

    @SneakyThrows
    @Override
    public Optional<Order> getById(String id) {
        Document document = collection.find(Filters.eq("_id", id)).first();
        if (document != null) {
            return Optional.ofNullable(gson.fromJson(document.toJson(), Order.class));
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(Filters.eq("_id", id));
    }
}