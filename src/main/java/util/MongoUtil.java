package util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static MongoClient mongoClient;

    public static MongoDatabase connect(String databaseName) {
        return getMongoClient().getDatabase(databaseName);
    }


    private static MongoClient getMongoClient( ) {
        if (mongoClient != null) {
            return mongoClient;
        }
        return new MongoClient("localhost", 27017);

    }

}