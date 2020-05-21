import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Sorts.*;

public class CsvReader {
    public static void main(String[] args) {
        CsvReader reader = new CsvReader();
        try (MongoClient mongoClient = new MongoClient("127.0.0.1", 27017)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("Students");
            List<Document> documentList = reader.parseStudents();
            collection.insertMany(documentList, new InsertManyOptions().ordered(false));


            System.out.println(collection.countDocuments());
            System.out.println(collection.find(gt("age", 40)).into(new ArrayList<>()).size());
            System.out.println(collection.find().sort(orderBy(ascending("age"))).first().get("name"));
            System.out.println(collection.find().sort(orderBy(descending("age"))).first().get("courses"));

        }
    }

    private List<Document> parseStudents() {
        List<Document> students = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"");
        try (BufferedReader in = new BufferedReader(new FileReader("C:\\Dev\\SkillboxJava\\module13\\3\\CsvReader\\src\\main\\resources\\mongo.csv"))) {
            students = in
                    .lines()
                    .map(line -> {
                        String[] x = pattern.split(line);
                        return new Document()
                                .append("name", x[0].split(",")[0])
                                .append("age", Integer.parseInt(x[0].split(",")[1]))
                                .append("courses", Arrays.asList(x[1].replaceAll("\"", "").split(",")));
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }
}
