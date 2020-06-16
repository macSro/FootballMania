package DatabaseUtils;

import Actors.Journalist;

import java.sql.*;

public class JournalistManagement {
    public static final String InsertQuery = "INSERT INTO journalist(firstname, lastname, email) VALUES (?,?,?)";
    public static final String DeleteQuery = "DELETE FROM journalist WHERE id = ?";
    public static final String SelectQuery = "SELECT * FROM journalist WHERE email = ?";
    public static final String SelectByIDQuery = "SELECT * FROM journalist WHERE id = ?";

    public static void createJournalistAccount(Journalist journalist, Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,journalist.getFirstName());
            preparedStatement.setString(2,journalist.getLastName());
            preparedStatement.setString(3,journalist.getEmail());
            preparedStatement.executeUpdate();
            insertQueryExecutionLog(preparedStatement.getGeneratedKeys());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteJournalistAccount(int journalistID, Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DeleteQuery);
            preparedStatement.setInt(1,journalistID);
            int affectedRows = preparedStatement.executeUpdate();
            deleteQueryExecutionLog(affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Journalist selectJournalist(String email, Connection connection){
        Journalist journalist = new Journalist();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SelectQuery);
            preparedStatement.setString(1,email);
            mapSQLtoObject(preparedStatement.executeQuery(), journalist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return journalist;
    }

    public static Journalist selectJournalistById(int id, Connection connection){
        Journalist journalist = new Journalist();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SelectByIDQuery);
            preparedStatement.setInt(1,id);
            mapSQLtoObject(preparedStatement.executeQuery(), journalist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return journalist;
    }

    private static void mapSQLtoObject(ResultSet resultSet, Journalist journalist) throws SQLException {
        if(resultSet.next()){
            journalist.setJournalistID(resultSet.getInt(1));
            journalist.setFirstName(resultSet.getString(2));
            journalist.setLastName(resultSet.getString(3));
            journalist.setEmail(resultSet.getString(4));
        }
    }

    private static void insertQueryExecutionLog(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            System.out.println("Insert successful! Journalist ID: " + resultSet.getInt(1));
        }
    }

    private static void deleteQueryExecutionLog(int affectedRows){
            System.out.println("Deleted " + affectedRows + " row(s)!");
    }
}
