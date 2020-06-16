package DatabaseUtils;

import Actors.Article;

import java.sql.*;
import java.util.ArrayList;

public class ArticleManagement {
    public static final String InsertArticleQuery = "INSERT INTO article(title, dateofpublication, category, content, idjournalist, tags) VALUES (?,?,?,?,?,?)";
    public static final String DeleteArticleQuery = "DELETE FROM article WHERE id = ?";
    public static final String UpdateArticleQuery = "UPDATE article SET title = ?, dateofpublication = ?, content = ?, idjournalist = ? WHERE id = ?";
    public static final String SelectArticleQuery = "SELECT * FROM article";
    public static final String SelectArticleByIdQuery = "SELECT * FROM article WHERE id = ?";
    public static final String SelectArticleCategories = "SELECT * FROM articlecategory";

    public static int insertArticle(Article article, Connection connection){
        int affectedRows = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(InsertArticleQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setDate(2,  new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setString(3, article.getCategory());
            preparedStatement.setString(4, article.getContent());
            preparedStatement.setInt(5, article.getAuthor().getJournalistID());
            preparedStatement.setString(6, article.getTags());
            affectedRows = preparedStatement.executeUpdate();
            insertQueryExecutionLog(preparedStatement.getGeneratedKeys());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int deleteArticle(int articleID, Connection connection){
        int affectedRows = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DeleteArticleQuery);
            preparedStatement.setInt(1, articleID);
            affectedRows = preparedStatement.executeUpdate();
            queryExecutionLog(affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int modifyArticle(int articleID, String author, String title, String content, int idjournalist, Connection connection){
        int affectedRows = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UpdateArticleQuery);
            preparedStatement.setInt(5, articleID);
            preparedStatement.setString(1, title);
            preparedStatement.setDate(2,  new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setString(3,  content);
            preparedStatement.setInt(4,  idjournalist);
            affectedRows = preparedStatement.executeUpdate();
            queryExecutionLog(affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static ArrayList<String> getAllArticles(Connection connection){
        ArrayList<String> articles = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SelectArticleQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
               String article = resultSet.getInt("id") + " - " + resultSet.getString("title");
               articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public static ResultSet getAllArticlesDetails(Connection connection){
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SelectArticleQuery);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet getArticleByID(Connection connection, int id){
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SelectArticleByIdQuery);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ArrayList<String> getArticleCategories(Connection connection){
        ArrayList<String> articlesCategories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SelectArticleCategories);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String article = resultSet.getString(1);
                articlesCategories.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articlesCategories;
    }



    private static void insertQueryExecutionLog(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            System.out.println("Insert successful! Article ID: " + resultSet.getInt(1));
        }
    }


    private static void queryExecutionLog(int affectedRows){
        System.out.println("Affected " + affectedRows + " row(s)!");
    }
}
