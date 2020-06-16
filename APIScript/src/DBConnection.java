import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class DBConnection {

    private String password;
    private String user;
    private String url;
    private Connection connection;
    private int lastId = 0;

    public DBConnection(String password, String user, String url) {
        this.password = password;
        this.user = user;
        this.url = url;
    }

    public void connectDB(){
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** Funkcje zapisujące do bazy danych */

    public void insertAreas(JSONArray areas){
        for(int i=0; i<areas.length(); i++){
            JSONObject area = (JSONObject)areas.get(i);
            insertArea(area);
        }
    }

    //https://api.football-data.org/v2/areas
    public void insertArea(JSONObject area){
        String SQL = prepareInsertString(DatabaseContract.Area.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, (int)area.get(DatabaseContract.APINames.ID));
            preparedStatement.setString(2, area.get(DatabaseContract.APINames.NAME).toString());
            int parentAreaId = area.get(DatabaseContract.APINames.PARENTAREAID).toString().equals("null") ? 2267 : (int)area.get(DatabaseContract.APINames.PARENTAREAID);
            preparedStatement.setInt(3, parentAreaId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertTeams(JSONArray teams){
        for(int i=0; i<teams.length(); i++){
            JSONObject team = (JSONObject)teams.get(i);
            insertTeam(team);
        }
    }
    //https://api.football-data.org/v2/teams
    public void insertTeam(JSONObject team){
        String SQL = prepareInsertString(DatabaseContract.Team.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, (int)team.get(DatabaseContract.APINames.ID));
            preparedStatement.setString(2, team.get(DatabaseContract.APINames.NAME).toString());
            preparedStatement.setString(3, team.get(DatabaseContract.APINames.TLA).toString());
            preparedStatement.setString(4, team.get(DatabaseContract.APINames.WEBSITE).toString());
            preparedStatement.setInt(5, (int)team.getJSONObject(DatabaseContract.APINames.AREA).get(DatabaseContract.APINames.ID));
            preparedStatement.setDate(6, currentDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertCompetitions(JSONArray competitions){
        for(int i=0; i<competitions.length(); i++){
            JSONObject competition = (JSONObject)competitions.get(i);
            insertCompetition(competition);
        }
    }

    //https://api.football-data.org/v2/competitions/2000
    public void insertCompetition(JSONObject competition){
        String SQL = prepareInsertString(DatabaseContract.Competition.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, (int)competition.get(DatabaseContract.APINames.ID));
            preparedStatement.setString(2, competition.get(DatabaseContract.APINames.NAME).toString());
            preparedStatement.setDate(3, currentDate());
            preparedStatement.setInt(4, (int)competition.getJSONObject(DatabaseContract.APINames.AREA).get(DatabaseContract.APINames.ID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSeasons(JSONArray seasons){
        for(int i=0; i<seasons.length(); i++){
            JSONObject season = (JSONObject)seasons.get(i);
            insertSeason(season);
        }
    }
    public void insertSeason(JSONObject season){
        String SQL = prepareInsertString(DatabaseContract.Season.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, (int)season.get(DatabaseContract.APINames.ID));
            preparedStatement.setDate(2, Date.valueOf(season.get(DatabaseContract.APINames.STRATDATE).toString()));
            preparedStatement.setDate(3, Date.valueOf(season.get(DatabaseContract.APINames.ENDDATE).toString()));
            if(season.get(DatabaseContract.APINames.CURRENTMATCHDAY).toString().equals("null")){
                preparedStatement.setInt(4, 0);
            }else{
                preparedStatement.setInt(4, (int)season.get(DatabaseContract.APINames.CURRENTMATCHDAY));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: czekamy na decyzję o subskrypcji
    public void insertGoal(JSONObject goal, int id, int matchId){
        String SQL = prepareInsertString(DatabaseContract.Season.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, (int)goal.get(DatabaseContract.APINames.MINUTE));
            preparedStatement.setInt(3, (int)goal.get(DatabaseContract.APINames.EXTRATIME));
            preparedStatement.setInt(4, (int)goal.get("minute"));
            preparedStatement.setInt(5, matchId);
            preparedStatement.setInt(6, (int)goal.getJSONObject(DatabaseContract.APINames.SCORER).get(DatabaseContract.APINames.ID));
            preparedStatement.setInt(7, (int)goal.getJSONObject(DatabaseContract.APINames.ASSIST).get(DatabaseContract.APINames.ID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertPlayers(JSONArray players){
        for(int i=0; i<players.length(); i++){
            JSONObject player = (JSONObject)players.get(i);
            insertPlayer(player);
        }
    }
    //https://api.football-data.org/v2/players/57502
    public void insertPlayer(JSONObject player){
        String SQL = prepareInsertString(DatabaseContract.Player.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, (int)player.get(DatabaseContract.APINames.ID));
            preparedStatement.setString(2, player.get(DatabaseContract.APINames.NAME).toString());
            preparedStatement.setDate(3, player.get(DatabaseContract.APINames.DATEOFBIRTH).toString().equals("null") ? null :Date.valueOf(formatDate(player.get(DatabaseContract.APINames.DATEOFBIRTH).toString())));
            preparedStatement.setDate(4, currentDate());
            preparedStatement.setInt(5, getAreaId(player.get(DatabaseContract.APINames.NATIONALITY).toString()));
            preparedStatement.setInt(6, getPositionId(player.get(DatabaseContract.APINames.POSITION).toString()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMatches(JSONArray matches, int competitionId){
        for(int i=0; i<matches.length(); i++){
            JSONObject match = (JSONObject)matches.get(i);
            int seasonId = (int)match.getJSONObject(DatabaseContract.APINames.SEASON).get(DatabaseContract.APINames.ID);
            insertMatch(match, seasonId, competitionId);
        }
    }

    public void insertMatch(JSONObject match, int seasonId, int competitionId){
        String SQL = prepareInsertString(DatabaseContract.Match.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            int homeGoals;
            if (match.getJSONObject("score").getJSONObject("fullTime").get("homeTeam").toString().equals("null")) homeGoals = 0;
            else homeGoals = (int) match.getJSONObject("score").getJSONObject("fullTime").get("homeTeam");
            int awayGoals;
            if (match.getJSONObject("score").getJSONObject("fullTime").get("awayTeam").toString().equals("null")) awayGoals = 0;
            else awayGoals = (int) match.getJSONObject("score").getJSONObject("fullTime").get("awayTeam");
            preparedStatement.setInt(1, awayGoals);
            preparedStatement.setInt(2, homeGoals);
            preparedStatement.setInt(3, (int)match.get(DatabaseContract.APINames.ID));
            preparedStatement.setDate(4, Date.valueOf(formatDate(match.get(DatabaseContract.APINames.UTCDATE).toString())));
            int matchday;
            if (match.get(DatabaseContract.APINames.MATCHDAY).toString().equals("null")) matchday = 0;
            else matchday = (int) match.get(DatabaseContract.APINames.MATCHDAY);
            preparedStatement.setInt(5, matchday);
            preparedStatement.setDate(6, currentDate());
            preparedStatement.setInt(7, getStatusId(match.get(DatabaseContract.APINames.STATUS).toString()));
            preparedStatement.setInt(8, getSeasonCompetitionId(seasonId, competitionId));
            preparedStatement.setInt(9, (int)match.getJSONObject(DatabaseContract.APINames.HOMETEAM).get(DatabaseContract.APINames.ID));
            preparedStatement.setInt(10, (int)match.getJSONObject(DatabaseContract.APINames.AWAYTEAM).get(DatabaseContract.APINames.ID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertScorers(JSONArray scorers, int seasonCompetitionId){
        for(int i=0; i<scorers.length(); i++){
            JSONObject scorer = (JSONObject)scorers.get(i);
            insertScorer(scorer, seasonCompetitionId);
        }
    }

    public void insertScorer(JSONObject scorer, int seasonCompetitionId){
        String SQL = prepareInsertString(DatabaseContract.Scorer.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, lastId++);
            preparedStatement.setInt(2, (int)scorer.get(DatabaseContract.APINames.NUMBEROFGOALS));
            preparedStatement.setInt(3, seasonCompetitionId);
            preparedStatement.setInt(4, getSeasonPlayerId((int)scorer.getJSONObject(DatabaseContract.APINames.PLAYER).get(DatabaseContract.APINames.ID),(int)scorer.getJSONObject(DatabaseContract.APINames.TEAM).get(DatabaseContract.APINames.ID)));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertStanding(JSONObject standing, int id, int seasonCompetitionId){
        String SQL = prepareInsertString(DatabaseContract.Standing.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, getStageId(standing.get(DatabaseContract.APINames.STAGE).toString()));
            preparedStatement.setInt(3, seasonCompetitionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTeamStandings(JSONObject standing, int standingId){
        JSONArray table = standing.getJSONArray("table");
        for(int i=0; i<table.length(); i++){
            JSONObject team = (JSONObject)table.get(i);
            insertTeamStanding(team, standingId);
        }
    }

    public void insertTeamStanding(JSONObject standing, int standingId){
        String SQL = prepareInsertString(DatabaseContract.TeamStanding.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, standingId);
            preparedStatement.setInt(2, (int)standing.getJSONObject(DatabaseContract.APINames.TEAM).get(DatabaseContract.APINames.ID));
            preparedStatement.setInt(3, (int)standing.get(DatabaseContract.APINames.POSITION));
            preparedStatement.setInt(4, (int)standing.get(DatabaseContract.APINames.PLAYEDGAMES));
            preparedStatement.setInt(5, (int)standing.get(DatabaseContract.APINames.WON));
            preparedStatement.setInt(6, (int)standing.get(DatabaseContract.APINames.DRAW));
            preparedStatement.setInt(7, (int)standing.get(DatabaseContract.APINames.LOST));
            preparedStatement.setInt(8, (int)standing.get(DatabaseContract.APINames.POINTS));
            preparedStatement.setInt(9, (int)standing.get(DatabaseContract.APINames.GOALSFOR));
            preparedStatement.setInt(10, (int)standing.get(DatabaseContract.APINames.GOALSAGAINST));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSeasonPlayers(JSONArray players, int teamId){
        for(int i=0; i<players.length(); i++){
            JSONObject player = (JSONObject)players.get(i);
            insertSeasonPlayer((int)player.get(DatabaseContract.APINames.ID), teamId);
        }
    }

    public void insertSeasonPlayer(int playerId, int teamId){
        String SQL = prepareInsertString(DatabaseContract.SeasonPlayer.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, lastId++);
            preparedStatement.setInt(2, playerId);
            preparedStatement.setInt(3, teamId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSeasonCompetitions(JSONArray competitions){
        for(int i=0; i<competitions.length(); i++){
            JSONObject competition = (JSONObject)competitions.get(i);
            insertSeasonCompetition(competition, i);
        }
    }

    public void insertSeasonCompetition(JSONObject competition, int id){
        String SQL = prepareInsertString(DatabaseContract.SeasonCompetition.TABLE_NAME);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1,  id);
            preparedStatement.setInt(2, (int)competition.getJSONObject(DatabaseContract.APINames.CURRENTSEASON).get(DatabaseContract.APINames.ID));
            preparedStatement.setInt(3, (int)competition.get(DatabaseContract.APINames.ID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** Funkcje odpytujące bazę danych */

    int getAreaId(String areaName){
        int id=0;
        // Było "SELECT id FROM public.area WHERE name = ?";
        // Powinno być tak:
        String SQL = "SELECT " + DatabaseContract.Area.COLUMN_NAME_ID +
                        " FROM public." + DatabaseContract.Area.TABLE_NAME.toLowerCase() +
                        " WHERE " + DatabaseContract.Area.COLUMN_NAME_NAME + " = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, areaName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    int getPositionId(String positionName){
        int id=0;
        String SQL = "SELECT idposition FROM public.position WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, positionName.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("idposition");
            System.out.println(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    int getStatusId(String statusName){
        int id=0;
        String SQL = "SELECT idstatus FROM  \"status\" WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, statusName.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("idstatus");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    int getStageId(String stageName){
        int id=0;
        String SQL = "SELECT idstage FROM stage WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, stageName.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("idstage");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    int getSeasonCompetitionId(int seasonId, int competitionId){
        int id=0;
        String SQL = "SELECT id FROM public.seasoncompetition WHERE idseason = ? AND idcompetition = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, seasonId);
            preparedStatement.setInt(2, competitionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    int getSeasonPlayerId(int playerId, int teamId){
        int id=0;
        String SQL = "SELECT id FROM seasonplayer WHERE idplayer = ? AND idteam = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, teamId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    ArrayList<Integer> getCompetitionsIds(){
        ArrayList<Integer> listOfIds = new ArrayList<>();
        String SQL = "SELECT id FROM public.Competition";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            insertIntoArrayList(resultSet, listOfIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfIds;
    }

    ArrayList<Integer> getTeamsIds(){
        ArrayList<Integer> listOfIds = new ArrayList<>();
        String SQL = "SELECT id FROM public.Team";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            insertIntoArrayList(resultSet, listOfIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfIds;
    }


    /** Funkcje - narzędzią do przygotowania zapytań SQL */

    private String prepareInsertString(String tableName){
        String tableColumns = prepareTableColumns(tableName);
        String insertQuery = "INSERT INTO public." + tableName + "(" + tableColumns + ")"
                + " VALUES " + prepareQuestionMarks(tableName)
                + prepareOnConflictStatement(tableName);
        System.out.println(insertQuery);
        return insertQuery;
    }

    private String prepareTableColumns(String tableName) {
        switch (tableName){
            case "Area": {
                return DatabaseContract.Area.tableColumns();
            }
            case "Competition": {
                return DatabaseContract.Competition.tableColumns();
            }
            case "Team": {
                return DatabaseContract.Team.tableColumns();
            }
            case "Season": {
                return DatabaseContract.Season.tableColumns();
            }
            case "Player": {
                return DatabaseContract.Player.tableColumns();
            }
            case "Match": {
                return DatabaseContract.Match.tableColumns();
            }
            case "SeasonCompetition": {
                return DatabaseContract.SeasonCompetition.tableColumns();
            }
            case "Standing": {
                return DatabaseContract.Standing.tableColumns();
            }
            case "TeamStanding": {
                return DatabaseContract.TeamStanding.tableColumns();
            }
            case "Scorer": {
                return DatabaseContract.Scorer.tableColumns();
            }
            case "Goal": {
                return DatabaseContract.Goal.tableColumns();
            }
            case "SeasonPlayer": {
                return DatabaseContract.SeasonPlayer.tableColumns();
            }
            default: {
                return "none";
            }
        }
    }

    private Field[] prepareFields(String tableName) {
        Field[] fields;
        switch (tableName){
            case "Area": {
                fields = DatabaseContract.Area.class.getFields();
                return fields;
            }
            case "Competition": {
                fields = DatabaseContract.Competition.class.getFields();
                return fields;
            }
            case "Team": {
                fields = DatabaseContract.Team.class.getFields();
                return fields;
            }
            case "Season": {
                fields = DatabaseContract.Season.class.getFields();
                return fields;
            }
            case "Player": {
                fields = DatabaseContract.Player.class.getFields();
                return fields;
            }
            case "Match": {
                fields = DatabaseContract.Match.class.getFields();
                return fields;
            }
            case "SeasonCompetition": {
                fields = DatabaseContract.SeasonCompetition.class.getFields();
                return fields;
            }
            case "Standing": {
                fields = DatabaseContract.Standing.class.getFields();
                return fields;
            }
            case "TeamStanding": {
                fields = DatabaseContract.TeamStanding.class.getFields();
                return fields;
            }
            case "Scorer": {
                return DatabaseContract.Scorer.class.getFields();
            }
            case "Goal": {
                return DatabaseContract.Goal.class.getFields();
            }
            case "SeasonPlayer": {
                return DatabaseContract.SeasonPlayer.class.getFields();
            }
            default: {
                return null;
            }
        }
    }

    private String prepareQuestionMarks(String tableName){
        String questionmarks = "(";
        Field[] fields = prepareFields(tableName);
        for (int i=0; i < fields.length-1; i++){
            if(i==0)
                questionmarks += "?";
            else
                questionmarks += ",?";
        }
        questionmarks += ")";
        return questionmarks;
    }

    private void insertIntoArrayList(ResultSet resultSet,  ArrayList<Integer> listOfIds)throws SQLException{
        while (resultSet.next()) {
            listOfIds.add(resultSet.getInt("id"));
        }
    }

    private LocalDate formatDate(String dateToFormat){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateToFormat, formatter);
        System.out.println(date);
        return date;
    }

    private Date currentDate(){
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        return date;
    }

    private void logQueryExecution(ResultSet resultSet, String tableName) throws SQLException {
        System.out.println("Query to " + tableName);
        int insertionCounter = 0;
        while(resultSet.next()){
            System.out.println("Inserted row - ID: " + resultSet.getInt("id"));
            insertionCounter++;
        }
        System.out.println("Total rows affected: " + insertionCounter);
    }

    private String prepareOnConflictStatement(String tableName){
        switch (tableName){
            case "Competition": {
                return "ON CONFLICT (id) DO UPDATE SET lastupdated = EXCLUDED.lastupdated";
            }
            case "Team": {
                return "ON CONFLICT (id) DO UPDATE SET lastupdated = EXCLUDED.lastupdated, website = EXCLUDED.website";
            }
            case "Player": {
                return "ON CONFLICT (id) DO UPDATE SET lastupdated = EXCLUDED.lastupdated";
            }
            case "Match": {
                return "ON CONFLICT (id) DO UPDATE SET lastupdated = EXCLUDED.lastupdated, idstatus = EXCLUDED.idstatus, goalshome = EXCLUDED.goalshome, goalsaway = EXCLUDED.goalsaway";
            }
            case "TeamStanding": {
                return "ON CONFLICT (idstanding, idteam) DO NOTHING";
            }
            case "Scorer": {
                return "ON CONFLICT (id) DO UPDATE SET numberofgoals = EXCLUDED.numberofgoals, idseasonplayer = EXCLUDED.idseasonplayer, idseasoncompetition = EXCLUDED.idseasoncompetition";
            }
            case "SeasonPlayer": {
                return "ON CONFLICT (id) DO UPDATE SET idplayer = EXCLUDED.idplayer, idteam = EXCLUDED.idteam";
            }
            default: {
                return "ON CONFLICT (id) DO NOTHING";
            }
        }
    }


}

