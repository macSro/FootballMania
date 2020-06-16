import org.json.JSONArray;

import java.io.IOException;

public class DBController {
    public static final String DB_PASSWORD = "password";
    public static final String DB_USER = "postgres";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/zpitest";

    public static void main(String[] args) {
        DBConnection connection = new DBConnection(DB_PASSWORD, DB_USER, DB_URL);
        connection.connectDB();
        try {
//            Script.getAreasData(connection);
//            System.out.println("AREAS EXECUTED");

//            Script.getTeamsData(connection);
//            System.out.println("TEAMS EXECUTED");

//            Script.getSeasonsData(connection);
//            System.out.println("SEASONS EXECUTED");

//            Script.getPlayersData(connection);
//            System.out.println("PLAYERS EXECUTED");

//            Script.getCompetitionsData(connection);
//            System.out.println("COMPETITIONS EXECUTED");

//            Script.getMatchesData(connection);
//            System.out.println("MATCHES EXECUTED");

//            Script.getScorersData(connection);
//            System.out.println("SCORERS EXECUTED");

            Script.getStandingsData(connection);
            System.out.println("STANDINGS EXECUTED");

                   } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
