package UserInterface;

import Actors.Article;
import Actors.Journalist;
import DatabaseUtils.ArticleManagement;
import DatabaseUtils.DBConnection;
import DatabaseUtils.JournalistManagement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Controller {

    public static boolean deleteArticle(String articleInfo, Connection connection){
        String[] words = articleInfo.split("\\s");
        int articleId = Integer.valueOf(words[0]);
        return (ArticleManagement.deleteArticle(articleId, connection) > 0);
    }

    public static boolean modifyArticle(int articleId, String authorEmail, String title, String content, Connection connection){
        Journalist journalist = JournalistManagement.selectJournalist(authorEmail, connection);
        return (ArticleManagement.modifyArticle(articleId, authorEmail, title, content,  journalist.getJournalistID(), connection) > 0);
    }

    public static boolean createArticle(String authorEmail, String title, String selectedFile, String category, String tags, Connection connection){
        String content = readFile(selectedFile);
        Journalist journalist = JournalistManagement.selectJournalist(authorEmail, connection);
        Article article = new Article(title, content, journalist, category);
        article.setContent(content);
        article.setTags(tags);
        return (ArticleManagement.insertArticle(article, connection) > 0);
    }

    public static String readFile(String filePath){
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static Article mapResultSetToArticle(ResultSet resultSet, Connection connection) throws SQLException {
        Article article = null;
        while(resultSet.next()){
            Journalist journalist = JournalistManagement.selectJournalistById(resultSet.getInt("idjournalist"), connection);
            article = new Article(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"), journalist, resultSet.getString("category"), resultSet.getString("tags"));
        }
        return article;
    }


}
