import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;


public class Script {

    public static final String URL = "http://api.football-data.org/v2/";

    public static final String X_API_TOKEN = "93e07270775d4b70acc7ba7273f70704";

    public static final String COMPETITIONS_ENDPOINT = "competitions";
    public static final String TEAMS_ENDPOINT = "teams";
    public static final String MATCHES_ENDPOINT = "matches";
    public static final String STANDINGS_ENDPOINT = "standings?standingType=HOME";
    public static final String AREAS_ENDPOINT = "areas";

    public static final String ARRAY_KEY_AREAS = "areas";
    public static final String ARRAY_KEY_COMPETITIONS = "competitions";
    public static final String ARRAY_KEY_SEASONS = "seasons";
    public static final String ARRAY_KEY_TEAMS = "teams";
    public static final String ARRAY_KEY_SQUAD = "squad";
    public static final String ARRAY_KEY_MATCHES = "matches";
    public static final String ARRAY_KEY_SCORERS = "scorers";
    public static final String ARRAY_KEY_STANDINGS = "standings";

    public static final String JSON_KEY_COMPETITION = "competition";
    public static final String JSON_KEY_SEASON = "season";
    public static final String JSON_KEY_ID = "id";

    public static final String SEPARATOR_SIGN = "/";
    public static final String QUESTION_MARK = "?";
    public static final String EQUALS_SIGN = "=";

    public static final String PARAMETER_PLAN = "plan";

    public static final String PLAN_TIER_ONE = "TIER_ONE";



    public static final int RESPONSE_CODE_TIMEOUT = 429;
    public static final long NEXT_REQUEST_DELAY_TIME = 60 * 1000; // MINUTE IN MILISECONDS


    public static void getAreasData(DBConnection dbConnection) throws IOException {

        JSONObject responseJson = getEndpointResponseJSON(AREAS_ENDPOINT);
        JSONArray areasArray = responseJson.getJSONArray(ARRAY_KEY_AREAS);
        //TODO: Funkcja powinna wpisywac do tabeli Areas
        dbConnection.insertAreas(areasArray);
    }

    public static void getCompetitionsData(DBConnection dbConnection) throws IOException {
        JSONObject responseJson = getEndpointResponseJSON(COMPETITIONS_ENDPOINT + QUESTION_MARK + PARAMETER_PLAN + EQUALS_SIGN + PLAN_TIER_ONE);
        JSONArray competitionsArray = responseJson.getJSONArray(ARRAY_KEY_COMPETITIONS);
        //TODO: Funkcja powinna wpisywać do tabeli Competitions i SeasonCompetitions. Do SeasonCompetition wpisujemy tylko i wyłącznie 'currentSeason'.
        dbConnection.insertCompetitions(competitionsArray);
        dbConnection.insertSeasonCompetitions(competitionsArray);
    }

    public static void getSeasonsData(DBConnection dbConnection) throws IOException {
        JSONObject responseJson;
        JSONArray seasonsArray;

        //TODO:napisać funkcję któa zwraca listę wsyzstkich 'id' competitions
        ArrayList<Integer> competitionsIds = dbConnection.getCompetitionsIds();

        for(Integer competitionId : competitionsIds){
            responseJson = getEndpointResponseJSON(COMPETITIONS_ENDPOINT + SEPARATOR_SIGN + competitionId);
            seasonsArray = responseJson.getJSONArray(ARRAY_KEY_SEASONS);
            //TODO: Funkcja powinna wpisywać do tabeli Season
            dbConnection.insertSeasons(seasonsArray);
        }
    }

    public static void getTeamsData(DBConnection dbConnection) throws IOException {
        JSONObject responseJson;
        JSONArray teamsArray;

        //TODO:napisać funkcję któa zwraca listę wsyzstkich 'id' competitions
        ArrayList<Integer> competitionsIds = dbConnection.getCompetitionsIds();

        for(Integer id : competitionsIds){
            responseJson = getEndpointResponseJSON(COMPETITIONS_ENDPOINT + SEPARATOR_SIGN + id + SEPARATOR_SIGN + TEAMS_ENDPOINT);
            teamsArray = responseJson.getJSONArray(ARRAY_KEY_TEAMS);
            //TODO: połączyć projekt z klasami Filipa
            dbConnection.insertTeams(teamsArray);
        }
    }

    public static void getPlayersData(DBConnection dbConnection) throws IOException {
        JSONObject responseJson;
        JSONArray squadArray;

        //TODO: napisać funkcję któa zwraca listę 'id' wszystkich teamów
        ArrayList<Integer> teamsIds = dbConnection.getTeamsIds();

        for(Integer teamId : teamsIds){
            responseJson = getEndpointResponseJSON(TEAMS_ENDPOINT + SEPARATOR_SIGN + teamId);
            squadArray = responseJson.getJSONArray(ARRAY_KEY_SQUAD);
            //TODO: Funkcja powinna wpisywac do tabeli Player i SeasonPlayer. Należy zmapować 'position' na odpowiedniego inta przed spiwaniem do bazy. -> na razie wpisujemy tylko Player
            dbConnection.insertPlayers(squadArray);
            dbConnection.insertSeasonPlayers(squadArray, teamId);
        }
    }


    public static void getMatchesData(DBConnection dbConnection) throws IOException {
        JSONObject responseJson;
        JSONArray matchesArray;

        ArrayList<Integer> competitionsIds = dbConnection.getCompetitionsIds();

        for(Integer competitionId : competitionsIds){
            responseJson = getEndpointResponseJSON(COMPETITIONS_ENDPOINT + SEPARATOR_SIGN + competitionId + SEPARATOR_SIGN + MATCHES_ENDPOINT);
            matchesArray = responseJson.getJSONArray(ARRAY_KEY_MATCHES);
            //TODO: insertMatches przyjmuje JSONArray z matches dla podanego competition i competitionId w celu pobrania później seasonCompetitionId dla każego meczu
            //TODO: każdy match z JSONArray 'matches' ma w sobie 'season'. Trzeba pobrać z 'season' 'id' i odpytać bazę o seasonCompetitionId używając getSeasonCompetitionId(seasonId, competitionId)
            dbConnection.insertMatches(matchesArray, competitionId);
        }
    }

    public static void getStandingsData(DBConnection dbConnection) throws IOException {
        JSONObject responseJson;
        JSONObject standingJson = null;
        JSONArray standingsArray;

        int seasonCompetitionId;
        int seasonId;
        int competitionId;
        int standingId = 0;
        ArrayList<Integer> competitionsIds = dbConnection.getCompetitionsIds();

        for(Integer id : competitionsIds){

            responseJson = getEndpointResponseJSON(COMPETITIONS_ENDPOINT + SEPARATOR_SIGN + id + SEPARATOR_SIGN + STANDINGS_ENDPOINT);
            standingsArray = responseJson.getJSONArray(ARRAY_KEY_STANDINGS);
            seasonId = responseJson.getJSONObject(JSON_KEY_SEASON).getInt(JSON_KEY_ID);
            competitionId = responseJson.getJSONObject(JSON_KEY_COMPETITION).getInt(JSON_KEY_ID);
            for(int i=0; i<standingsArray.length(); i++) {
                standingJson = standingsArray.getJSONObject(i);

                //TODO: getSeasonCompetition przyjumuje seasonId i competitionId. Powinna zwaracać id z tabeli SeasonCompetition gdzie 'seasonId' i 'competitionId' sa równe podanym parametrom
                seasonCompetitionId = dbConnection.getSeasonCompetitionId(seasonId, competitionId);

                //TODO: insertStanding przyjmujme idSeasonCompetition ktore musi byc wpisane do tabeli Standing pod pola 'id' oraz 'idSeasonCompetition'.
                //TODO: standingJson zawiera w sobie 'stage' które trzeba zmapować na int i  wpisac do tabeli Standing oraz JSONArray 'table' w którym każdy JSON to rekord do wpisania do tabeli TeamStandings
                dbConnection.insertStanding(standingJson, standingId, seasonCompetitionId);
                dbConnection.insertTeamStandings(standingJson, standingId);
                standingId++;
            }
            standingId++;
            System.out.println(standingId);
        }
    }

    public static void getScorersData(DBConnection dbConnection) throws IOException {
        JSONObject responseJson;
        JSONArray scorersArray;

        int seasonId;
        int seasonCompetitionId;

        ArrayList<Integer> competitionsIds = dbConnection.getCompetitionsIds();

        for(Integer competitionId : competitionsIds){
            responseJson = getEndpointResponseJSON(COMPETITIONS_ENDPOINT + SEPARATOR_SIGN + competitionId + SEPARATOR_SIGN + ARRAY_KEY_SCORERS);
            seasonId = responseJson.getJSONObject(JSON_KEY_SEASON).getInt(JSON_KEY_ID);
            seasonCompetitionId = dbConnection.getSeasonCompetitionId(seasonId, competitionId);
            scorersArray = responseJson.getJSONArray(ARRAY_KEY_SCORERS);
            //TODO: insertScorers przujmuje JSONArray scorersow, z ktorego nalezy pobrac 'id' i 'team'.id' w celu uzyskania SeasonPlayerId do wpisania do tabeli Scorer.
            dbConnection.insertScorers(scorersArray, seasonCompetitionId);
        }
    }

    private static JSONObject getEndpointResponseJSON(String endpoint) throws IOException {
        StringBuilder inline = new StringBuilder();
        JSONObject responseJson = null;

        URL url = new URL(URL + endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Auth-Token", X_API_TOKEN);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Language", "en-US");
        conn.setUseCaches(true);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        try {
            conn.connect();

            int responsecode = conn.getResponseCode();
            System.out.println("Response code is: " + responsecode);


            if (responsecode != 200) {
                if(responsecode == RESPONSE_CODE_TIMEOUT){
                    System.out.println("Going to sleep - waiting for API to be available" );
                    Thread.currentThread().sleep(NEXT_REQUEST_DELAY_TIME);
                    System.out.println("Waking up - executing query" );
                    return getEndpointResponseJSON(endpoint);
                }else
                    throw new RuntimeException("HttpResponseCode: " + responsecode);
            }
            else {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    inline.append(inputLine);
                }

                responseJson = new JSONObject(inline.toString());
                System.out.println("\nJSON Response in String format");
                System.out.println(responseJson.toString());

                in.close();

            }

        }catch (IOException | InterruptedException ex){
            ex.printStackTrace();
        }

        return responseJson;

    }

}
