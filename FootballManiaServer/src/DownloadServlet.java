import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DownloadServlet extends HttpServlet {

    public static final String ARRAY_KEY_AREAS = "areas";
    public static final String ARRAY_KEY_COMPETITIONS = "competitions";
    public static final String ARRAY_KEY_SEASONS = "seasons";
    public static final String ARRAY_KEY_SEASON_COMPETITIONS = "seasonCompetitions";
    public static final String ARRAY_KEY_TEAMS = "teams";
    public static final String ARRAY_KEY_PLAYERS = "players";
    public static final String ARRAY_KEY_SEASON_PLAYERS = "seasonPlayers";
    public static final String ARRAY_KEY_MATCHES = "matches";
    public static final String ARRAY_KEY_STANDINGS = "standings";
    public static final String ARRAY_KEY_TEAM_STANDINGS = "teamStandings";
    public static final String ARRAY_KEY_SCORERS = "scorers";
    public static final String ARRAY_KEY_JOURNALISTS = "journalists";
    public static final String ARRAY_KEY_ARTICLES = "articles";
    public static final String ARRAY_KEY_FAVOURITES = "favourites";
    public static final String ARRAY_KEY_FAVOURITE_TEAMS = "favouriteTeams";
    public static final String ARRAY_KEY_FAVOURITE_COMPETITIONS = "favouriteCompetitions";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try{

            response.setCharacterEncoding("UTF-8");
            System.out.println("DownloadServlet\n");
            out = response.getWriter();

            DBConnection dbConnection = new DBConnection(ServerUtils.DB_USER_PASSWORD, ServerUtils.DB_USER_LOGIN, ServerUtils.DB_URL);
            dbConnection.connectDB();

            JSONObject responseJSON = new JSONObject();
            JSONObject userJson;

            ServerUtils.User user = null;

            int errorCode;
            String errorMessage = null;

            String userLogin = request.getParameter(ServerUtils.USER_LOGIN);
            String userPassword = request.getParameter(ServerUtils.USER_PASSWORD);

            userJson = dbConnection.getUser(userLogin);
            if(userJson == null){
                errorCode = 1;
                errorMessage = ServerUtils.ERROR_MESSAGE_WRONG_LOGIN;
            } else {
                user = new ServerUtils.User(userJson);
                if(Encryption.createMD5(userPassword).equals(user.getPassword())){
                    user.setPassword(userPassword);
                    errorCode = 0;
                } else{
                    errorCode = 1;
                    errorMessage = ServerUtils.ERROR_MESSAGE_WRONG_PASSWORD;
                }

            }

            responseJSON.put(ServerUtils.ERROR_CODE, errorCode);
            if(errorCode != 0)
                responseJSON.put(ServerUtils.SERVER_ERROR_MESSAGE, errorMessage);
            else{
                JSONArray areasArray, competitionsArray, seasonsArray, seasonCompetitionsArray, matchesArray,
                        teamsArray, playersArray, seasonPlayersArray, standingsArray, teamStandingsArray, scorersArray,
                        journalistsArray, articlesArray, favouritesArray, favouriteTeamsArray, favouriteCompetitionsArray;

                System.out.println("\nGetting areas. . .");
                areasArray = dbConnection.getAreas();
                System.out.println("Areas size: " + areasArray.length());

                System.out.println("\nGetting competitions. . .");
                competitionsArray = dbConnection.getCompetitions();
                System.out.println("Competitions size: " + competitionsArray.length());

                System.out.println("\nGetting seasons. . .");
                seasonsArray = dbConnection.getSeasons();
                System.out.println("Seasons size: " + seasonsArray.length());

                System.out.println("\nGetting seasonCompetitions. . .");
                seasonCompetitionsArray = dbConnection.getSeasonCompetitions();
                System.out.println("SeasonCompetitions size: " + seasonCompetitionsArray.length());

                System.out.println("\nGetting matches. . .");
                matchesArray = dbConnection.getMatches();
                System.out.println("Matches size: " + matchesArray.length());

                System.out.println("\nGetting teams. . .");
                teamsArray = dbConnection.getTeams();
                System.out.println("Teams size: " + teamsArray.length());

                System.out.println("\nGetting players. . .");
                playersArray = dbConnection.getPlayers();
                System.out.println("Players size: " + playersArray.length());

                System.out.println("\nGetting seasonPlayers. . .");
                seasonPlayersArray = dbConnection.getSeasonPlayers();
                System.out.println("SeasonPlayers size: " + seasonPlayersArray.length());

                System.out.println("\nGetting standings. . .");
                standingsArray = dbConnection.getStandings();
                System.out.println("Standings size: " + standingsArray.length());

                System.out.println("\nGetting teamStandings. . .");
                teamStandingsArray = dbConnection.getTeamStandings();
                System.out.println("TeamStandings size size: " + teamStandingsArray.length());

                System.out.println("\nGetting scorers. . .");
                scorersArray = dbConnection.getScorers();
                System.out.println("Scorers size: " + scorersArray.length());

                System.out.println("\nGetting journalists. . .");
                journalistsArray = dbConnection.getJournalists();
                System.out.println("Journalists size: " + journalistsArray.length());

                System.out.println("\nGetting articles. . .");
                articlesArray = dbConnection.getArticles();
                System.out.println("Articles size: " + articlesArray.length());

                System.out.println("\nGetting favourites. . .");
                favouritesArray = dbConnection.getFavourites();
                System.out.println("Favouirites size: " + favouritesArray.length());

                System.out.println("\nGetting favouriteTeams. . .");
                favouriteTeamsArray = dbConnection.getFavouriteTeams();
                System.out.println("FavouriteTeams size: " + favouriteTeamsArray.length());

                System.out.println("\nGetting favouriteCompetitions. . .");
                favouriteCompetitionsArray = dbConnection.getFavouriteCompetitions();
                System.out.println("Articles size: " + favouriteCompetitionsArray.length());

                responseJSON.put(ARRAY_KEY_AREAS, areasArray);
                responseJSON.put(ARRAY_KEY_COMPETITIONS, competitionsArray);
                responseJSON.put(ARRAY_KEY_SEASONS, seasonsArray);
                responseJSON.put(ARRAY_KEY_SEASON_COMPETITIONS, seasonCompetitionsArray);
                responseJSON.put(ARRAY_KEY_TEAMS, teamsArray);
                responseJSON.put(ARRAY_KEY_PLAYERS, playersArray);
                responseJSON.put(ARRAY_KEY_SEASON_PLAYERS, seasonPlayersArray);
                responseJSON.put(ARRAY_KEY_MATCHES, matchesArray);
                responseJSON.put(ARRAY_KEY_STANDINGS, standingsArray);
                responseJSON.put(ARRAY_KEY_TEAM_STANDINGS, teamStandingsArray);
                responseJSON.put(ARRAY_KEY_SCORERS, scorersArray);
                responseJSON.put(ARRAY_KEY_JOURNALISTS, journalistsArray);
                responseJSON.put(ARRAY_KEY_ARTICLES, articlesArray);
                responseJSON.put(ARRAY_KEY_FAVOURITES, favouritesArray);
                responseJSON.put(ARRAY_KEY_FAVOURITE_TEAMS, favouriteTeamsArray);
                responseJSON.put(ARRAY_KEY_FAVOURITE_COMPETITIONS, favouriteCompetitionsArray);
            }
            

            out.print(responseJSON);
            out.flush();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
