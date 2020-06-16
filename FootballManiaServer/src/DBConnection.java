import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBConnection {

    private static final String QUANTITY = "quantity";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private String password;
    private String user;
    private String url;
    private Connection connection;

    public DBConnection(String password, String user, String url) {
        this.password = password;
        this.user = user;
        this.url = url;
    }

    public void connectDB() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getUser(String login) {
        JSONObject userJson = null;

        String query = "SELECT * " +
                        " FROM public.\"" + DatabaseContract.User.TABLE_NAME.toLowerCase() +
                        "\" WHERE " + DatabaseContract.User.COLUMN_NAME_LOGIN + " = '" + login + "'";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userJson = new JSONObject();
                userJson.put(DatabaseContract.User.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.User.COLUMN_NAME_ID));
                userJson.put(DatabaseContract.User.COLUMN_NAME_LOGIN, resultSet.getString(DatabaseContract.User.COLUMN_NAME_LOGIN));
                userJson.put(DatabaseContract.User.COLUMN_NAME_PASSWORD, resultSet.getString(DatabaseContract.User.COLUMN_NAME_PASSWORD));
                userJson.put(DatabaseContract.User.COLUMN_NAME_FIRST_NAME, resultSet.getString(DatabaseContract.User.COLUMN_NAME_FIRST_NAME));
                userJson.put(DatabaseContract.User.COLUMN_NAME_EMAIL, resultSet.getString(DatabaseContract.User.COLUMN_NAME_EMAIL));
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return userJson;
    }

    public JSONArray getAreas(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Area.TABLE_NAME;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                json = new JSONObject();
                json.put(DatabaseContract.Area.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Area.COLUMN_NAME_ID));
                json.put(DatabaseContract.Area.COLUMN_NAME_NAME, resultSet.getString(DatabaseContract.Area.COLUMN_NAME_NAME));
                json.put(DatabaseContract.Area.COLUMN_NAME_PARENT_AREA_ID, resultSet.getInt(DatabaseContract.Area.COLUMN_NAME_PARENT_AREA_ID));
                jsonArray.put(json);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return jsonArray;
    }


    public JSONArray getCompetitions(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                        "FROM " + DatabaseContract.Competition.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Competition.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Competition.COLUMN_NAME_ID));
                json.put(DatabaseContract.Competition.COLUMN_NAME_NAME, resultSet.getString(DatabaseContract.Competition.COLUMN_NAME_NAME));
                json.put(DatabaseContract.Competition.COLUMN_NAME_AREA_ID, resultSet.getInt(DatabaseContract.Competition.COLUMN_NAME_AREA_ID));
                json.put(DatabaseContract.Competition.COLUMN_NAME_LAST_UPDATE_DATE, resultSet.getDate(DatabaseContract.Competition.COLUMN_NAME_LAST_UPDATE_DATE).getTime());
                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getSeasons(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Season.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Season.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Season.COLUMN_NAME_ID));
                json.put(DatabaseContract.Season.COLUMN_NAME_START_DATE, resultSet.getDate(DatabaseContract.Season.COLUMN_NAME_START_DATE).getTime());
                json.put(DatabaseContract.Season.COLUMN_NAME_END_DATE, resultSet.getDate(DatabaseContract.Season.COLUMN_NAME_END_DATE).getTime());
                json.put(DatabaseContract.Season.COLUMN_NAME_CURRENT_MATCH_DAY, resultSet.getLong(DatabaseContract.Season.COLUMN_NAME_CURRENT_MATCH_DAY));
                jsonArray.put(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getSeasonCompetitions(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.SeasonCompetition.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.SeasonCompetition.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.SeasonCompetition.COLUMN_NAME_ID));
                json.put(DatabaseContract.SeasonCompetition.COLUMN_NAME_SEASON_ID, resultSet.getInt(DatabaseContract.SeasonCompetition.COLUMN_NAME_SEASON_ID));
                json.put(DatabaseContract.SeasonCompetition.COLUMN_NAME_COMPETITION_ID, resultSet.getInt(DatabaseContract.SeasonCompetition.COLUMN_NAME_COMPETITION_ID));
                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getTeams(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Team.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                    json = new JSONObject();
                    json.put(DatabaseContract.Team.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Team.COLUMN_NAME_ID));
                    json.put(DatabaseContract.Team.COLUMN_NAME_NAME, resultSet.getString(DatabaseContract.Team.COLUMN_NAME_NAME));
                    json.put(DatabaseContract.Team.COLUMN_NAME_TLA, resultSet.getString(DatabaseContract.Team.COLUMN_NAME_TLA));
                    json.put(DatabaseContract.Team.COLUMN_NAME_AREA_ID, resultSet.getInt(DatabaseContract.Team.COLUMN_NAME_AREA_ID));
                    json.put(DatabaseContract.Team.COLUMN_NAME_WEBSITE, resultSet.getString(DatabaseContract.Team.COLUMN_NAME_WEBSITE));
                    json.put(DatabaseContract.Team.COLUMN_NAME_LAST_UPDATE_DATE, resultSet.getDate(DatabaseContract.Team.COLUMN_NAME_LAST_UPDATE_DATE).getTime());
                    jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getPlayers() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Player.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Player.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Player.COLUMN_NAME_ID));
                json.put(DatabaseContract.Player.COLUMN_NAME_NAME, resultSet.getString(DatabaseContract.Player.COLUMN_NAME_NAME));
                json.put(DatabaseContract.Player.COLUMN_NAME_POSITION, resultSet.getInt(DatabaseContract.Player.COLUMN_NAME_POSITION));
                json.put(DatabaseContract.Player.COLUMN_NAME_AREA_ID, resultSet.getInt(DatabaseContract.Player.COLUMN_NAME_AREA_ID));
                json.put(DatabaseContract.Player.COLUMN_NAME_DATE_OF_BIRTH, resultSet.getDate(DatabaseContract.Player.COLUMN_NAME_DATE_OF_BIRTH.toLowerCase()) == null ? 0L : resultSet.getDate(DatabaseContract.Player.COLUMN_NAME_DATE_OF_BIRTH.toLowerCase()).getTime());
                json.put(DatabaseContract.Player.COLUMN_NAME_LAST_UPDATE_DATE, resultSet.getDate(DatabaseContract.Player.COLUMN_NAME_LAST_UPDATE_DATE.toLowerCase()).getTime());
                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getSeasonPlayers(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.SeasonPlayer.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.SeasonPlayer.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.SeasonPlayer.COLUMN_NAME_ID));
                json.put(DatabaseContract.SeasonPlayer.COLUMN_NAME_PLAYER_ID, resultSet.getInt(DatabaseContract.SeasonPlayer.COLUMN_NAME_PLAYER_ID));
                json.put(DatabaseContract.SeasonPlayer.COLUMN_NAME_TEAM_ID, resultSet.getInt(DatabaseContract.SeasonPlayer.COLUMN_NAME_TEAM_ID));
                jsonArray.put(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getMatches(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Match.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Match.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_ID));
                json.put(DatabaseContract.Match.COLUMN_NAME_SEASON_COMPETITION_ID, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_SEASON_COMPETITION_ID));
                json.put(DatabaseContract.Match.COLUMN_NAME_AWAY_TEAM_ID, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_AWAY_TEAM_ID));
                json.put(DatabaseContract.Match.COLUMN_NAME_HOME_TEAM_ID, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_HOME_TEAM_ID));
                json.put(DatabaseContract.Match.COLUMN_NAME_AWAY_TEAM_GOALS, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_AWAY_TEAM_GOALS));
                json.put(DatabaseContract.Match.COLUMN_NAME_HOME_TEAM_GOALS, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_HOME_TEAM_GOALS));
                json.put(DatabaseContract.Match.COLUMN_NAME_STATUS, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_STATUS));
                json.put(DatabaseContract.Match.COLUMN_NAME_MATCH_DAY, resultSet.getInt(DatabaseContract.Match.COLUMN_NAME_MATCH_DAY));
                json.put(DatabaseContract.Match.COLUMN_NAME_LAST_UPDATE_DATE, resultSet.getDate(DatabaseContract.Match.COLUMN_NAME_LAST_UPDATE_DATE).getTime());
                json.put(DatabaseContract.Match.COLUMN_NAME_UTC_DATE, resultSet.getDate(DatabaseContract.Match.COLUMN_NAME_UTC_DATE).getTime());
                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getStandings(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Standing.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Standing.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Standing.COLUMN_NAME_ID));
                json.put(DatabaseContract.Standing.COLUMN_NAME_SEASON_COMPETITION_ID, resultSet.getInt(DatabaseContract.Standing.COLUMN_NAME_SEASON_COMPETITION_ID));
                json.put(DatabaseContract.Standing.COLUMN_NAME_STAGE, resultSet.getInt(DatabaseContract.Standing.COLUMN_NAME_STAGE));

                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getTeamStandings(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.TeamStanding.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_STANDING_ID, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_STANDING_ID));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_TEAM_ID, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_TEAM_ID));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_PLAYED_GAMES, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_PLAYED_GAMES));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_GOALS_FOR, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_GOALS_FOR));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_GOALS_AGAINST, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_GOALS_AGAINST));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_WON, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_WON));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_DRAW, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_DRAW));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_LOST, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_LOST));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_POINTS, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_POINTS));
                json.put(DatabaseContract.TeamStanding.COLUMN_NAME_POSITION, resultSet.getInt(DatabaseContract.TeamStanding.COLUMN_NAME_POSITION));

                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getScorers(){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Scorer.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Scorer.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Scorer.COLUMN_NAME_ID));
                json.put(DatabaseContract.Scorer.COLUMN_NAME_SEASON_COMPETITION_ID, resultSet.getInt(DatabaseContract.Scorer.COLUMN_NAME_SEASON_COMPETITION_ID));
                json.put(DatabaseContract.Scorer.COLUMN_NAME_SEASON_PLAYER_ID, resultSet.getInt(DatabaseContract.Scorer.COLUMN_NAME_SEASON_PLAYER_ID));
                json.put(DatabaseContract.Scorer.COLUMN_NAME_NUMBER_OF_GOALS, resultSet.getInt(DatabaseContract.Scorer.COLUMN_NAME_NUMBER_OF_GOALS));

                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getJournalists() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Journalist.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Journalist.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Journalist.COLUMN_NAME_ID));
                json.put(DatabaseContract.Journalist.COLUMN_NAME_FIRST_NAME, resultSet.getString(DatabaseContract.Journalist.COLUMN_NAME_FIRST_NAME));
                json.put(DatabaseContract.Journalist.COLUMN_NAME_LAST_NAME, resultSet.getString(DatabaseContract.Journalist.COLUMN_NAME_LAST_NAME));
                json.put(DatabaseContract.Journalist.COLUMN_NAME_EMAIL, resultSet.getString(DatabaseContract.Journalist.COLUMN_NAME_EMAIL));

                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getArticles() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Article.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Article.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Article.COLUMN_NAME_ID));
                json.put(DatabaseContract.Article.COLUMN_NAME_JOURNALIST_ID, resultSet.getInt(DatabaseContract.Article.COLUMN_NAME_JOURNALIST_ID));
                json.put(DatabaseContract.Article.COLUMN_NAME_TITLE, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_TITLE));
                json.put(DatabaseContract.Article.COLUMN_NAME_CONTENT, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_CONTENT));
                json.put(DatabaseContract.Article.COLUMN_NAME_DATE_OF_PUBLICATION, resultSet.getDate(DatabaseContract.Article.COLUMN_NAME_DATE_OF_PUBLICATION).getTime());
                json.put(DatabaseContract.Article.COLUMN_NAME_CATEGORY, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_CATEGORY));
                json.put(DatabaseContract.Article.COLUMN_NAME_TAGS, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_TAGS));

                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getFavourites() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Favourites.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Favourites.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Favourites.COLUMN_NAME_ID));
                json.put(DatabaseContract.Favourites.COLUMN_NAME_USER_ID, resultSet.getInt(DatabaseContract.Favourites.COLUMN_NAME_USER_ID));


                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray getFavouriteTeams() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.FavouriteTeam.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.FavouriteTeam.COLUMN_NAME_FAVOURITES_ID, resultSet.getInt(DatabaseContract.FavouriteTeam.COLUMN_NAME_FAVOURITES_ID));
                json.put(DatabaseContract.FavouriteTeam.COLUMN_NAME_TEAM_ID, resultSet.getInt(DatabaseContract.FavouriteTeam.COLUMN_NAME_TEAM_ID));


                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray getFavouriteCompetitions() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        String query = "SELECT * " +
                "FROM " + DatabaseContract.FavouriteCompetition.TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.FavouriteCompetition.COLUMN_NAME_FAVOURITES_ID, resultSet.getInt(DatabaseContract.FavouriteCompetition.COLUMN_NAME_FAVOURITES_ID));
                json.put(DatabaseContract.FavouriteCompetition.COLUMN_NAME_COMPETITION_ID, resultSet.getInt(DatabaseContract.FavouriteCompetition.COLUMN_NAME_COMPETITION_ID));


                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void insertUser(String userLogin, String userPassword, String userEmail, String userFirstName) {
        String encryptedPassword = Encryption.createMD5(userPassword);
        int id = getNextUserId();
        String query = "INSERT INTO public.\"" + DatabaseContract.User.TABLE_NAME.toLowerCase() + "\" " +
                " VALUES(" + id + ", '" + userLogin + "', '" + encryptedPassword + "', '" + userEmail + "', '" + userFirstName + "')";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            query = "INSERT INTO public.\"" + DatabaseContract.Favourites.TABLE_NAME.toLowerCase() + "\" " + "(" + DatabaseContract.Favourites.COLUMN_NAME_USER_ID + ")" +
                    " VALUES(" + id + ")";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int getNextUserId(){
        String query = "SELECT  MAX(" + DatabaseContract.User.COLUMN_NAME_ID + ") " + " \"" + DatabaseContract.User.COLUMN_NAME_ID + "\"" +
                " FROM public.\"" + DatabaseContract.User.TABLE_NAME.toLowerCase() + "\"";

        int nextId = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nextId = resultSet.getInt(DatabaseContract.User.COLUMN_NAME_ID) + 1;
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return nextId;
    }

    public boolean checkForNewArticles(Date mobileLastUpdatedArticlesDate) {
        boolean hasNewArticles = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String query = "SELECT COUNT(" + DatabaseContract.Article.COLUMN_NAME_ID + ") " + "\"" + QUANTITY + "\"" +
                        " FROM " + DatabaseContract.Article.TABLE_NAME +
                        " WHERE " + DatabaseContract.Article.COLUMN_NAME_DATE_OF_PUBLICATION + " > \'" + dateFormat.format(mobileLastUpdatedArticlesDate) + "\'";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(QUANTITY);
                if (count > 0)
                    hasNewArticles = true;
            }
            return hasNewArticles;
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return hasNewArticles;

    }

    public JSONArray getNewArticles(Date mobileLastUpdatedArticlesDate) {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        String query = "SELECT * " +
                "FROM " + DatabaseContract.Article.TABLE_NAME +
                " WHERE " + DatabaseContract.Article.COLUMN_NAME_DATE_OF_PUBLICATION + " > \'" + dateFormat.format(mobileLastUpdatedArticlesDate) + "\'";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                json = new JSONObject();
                json.put(DatabaseContract.Article.COLUMN_NAME_ID, resultSet.getInt(DatabaseContract.Article.COLUMN_NAME_ID));
                json.put(DatabaseContract.Article.COLUMN_NAME_JOURNALIST_ID, resultSet.getInt(DatabaseContract.Article.COLUMN_NAME_JOURNALIST_ID));
                json.put(DatabaseContract.Article.COLUMN_NAME_TITLE, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_TITLE));
                json.put(DatabaseContract.Article.COLUMN_NAME_CONTENT, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_CONTENT));
                json.put(DatabaseContract.Article.COLUMN_NAME_DATE_OF_PUBLICATION, resultSet.getDate(DatabaseContract.Article.COLUMN_NAME_DATE_OF_PUBLICATION).getTime());
                json.put(DatabaseContract.Article.COLUMN_NAME_CATEGORY, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_CATEGORY));
                json.put(DatabaseContract.Article.COLUMN_NAME_TAGS, resultSet.getString(DatabaseContract.Article.COLUMN_NAME_TAGS));

                jsonArray.put(json);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public ServerUtils.User updateUser(String userLogin, String newName, String newPassword, String newEmail) {
        String encryptedPassword = Encryption.createMD5(newPassword);
        int id = getNextUserId();
        String query = "UPDATE public.\"" + DatabaseContract.User.TABLE_NAME.toLowerCase() + "\" " +
                " SET(" + DatabaseContract.User.COLUMN_NAME_FIRST_NAME + ", " + DatabaseContract.User.COLUMN_NAME_EMAIL + ", " + DatabaseContract.User.COLUMN_NAME_PASSWORD + ")" + " = " +
                " (\'" + newName + "\',\'" + newEmail +"\',\'" + encryptedPassword + "\')" +
                " WHERE public.\"" + DatabaseContract.User.TABLE_NAME.toLowerCase() + "\"." + DatabaseContract.User.COLUMN_NAME_LOGIN + " = \'" + userLogin + "\';";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ServerUtils.User(getUser(userLogin));

    }

    public boolean checkForNewData(String dateString) {
        try {
            String query = "SELECT COUNT(" + DatabaseContract.Team.COLUMN_NAME_ID + ") " + "\"" + QUANTITY + "\"" +
                    " FROM " + DatabaseContract.Team.TABLE_NAME +
                    " WHERE " + DatabaseContract.Team.COLUMN_NAME_LAST_UPDATE_DATE + " > \'" + dateString + "\'";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                if(resultSet.getInt(QUANTITY) > 0)
                    return true;

            query = "SELECT COUNT(" + DatabaseContract.Match.COLUMN_NAME_ID + ") " + "\"" + QUANTITY + "\"" +
                    " FROM " + DatabaseContract.Match.TABLE_NAME +
                    " WHERE " + DatabaseContract.Match.COLUMN_NAME_LAST_UPDATE_DATE + " > \'" + dateString + "\'";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                if(resultSet.getInt(QUANTITY) > 0)
                    return true;

            query = "SELECT COUNT(" + DatabaseContract.Player.COLUMN_NAME_ID + ") " + "\"" + QUANTITY + "\"" +
                    " FROM " + DatabaseContract.Player.TABLE_NAME +
                    " WHERE " + DatabaseContract.Player.COLUMN_NAME_LAST_UPDATE_DATE + " > \'" + dateString + "\'";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                if(resultSet.getInt(QUANTITY) > 0)
                    return true;

            query = "SELECT COUNT(" + DatabaseContract.Competition.COLUMN_NAME_ID + ") " + "\"" + QUANTITY + "\"" +
                    " FROM " + DatabaseContract.Competition.TABLE_NAME +
                    " WHERE " + DatabaseContract.Competition.COLUMN_NAME_LAST_UPDATE_DATE + " > \'" + dateString + "\'";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                if(resultSet.getInt(QUANTITY) > 0)
                    return true;


        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;

    }
}