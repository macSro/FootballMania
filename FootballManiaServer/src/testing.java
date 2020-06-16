import java.io.IOException;

public class testing {
    public static void main(String[] args) {
        DBConnection connection = new DBConnection("password", "postgres", "jdbc:postgresql://localhost:5432/zpitest");
        connection.connectDB();
        System.out.println(connection.getCompetitions());
    }
}
