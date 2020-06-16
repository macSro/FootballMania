package com.footballmania.serverConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Entity;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.footballmania.MainActivity;
import com.footballmania.R;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.User;
import com.footballmania.ui.profile.ProfileFragment;
import com.footballmania.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TomcatConnection {

    public static final String WEB_SERVICE_URL  = "http://156.17.130.97/FootballMania/";

    public static final String SERVICE_LOGIN = "login";
    public static final String SERVICE_DOWNLOAD_DATA = "download";
    public static final String SERVICE_REGISTER_USER = "register";
    public static final String SERVICE_CHECK_DATA_UPDATE = "dataUpdateCheck";
    public static final String SERVICE_CHECK_FOR_ARTICLES_UPDATE = "articlesCheck";
    public static final String SERVICE_DOWNLOAD_ARTICLES = "articlesDownload";
    public static final String SERVICE_DOWNLOAD_ACCOUNT_UPDATE = "accountUpdate";


    public static final String METHOD = "method";

    public static final String EQUALS_SIGN = "=";
    public static final String QUESTION_MARK = "?";
    public static final String AMPERSAND_SIGN = "&";

    public static final String USER_ID = "user_id";
    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_FIRST_NAME = "user_first_name";
    public static final String USER_EMAIL = "user_email";
    public static final String LAST_MOBILE_UPDATE = "last_update_date";

    public static final String USER_NEW_NAME = "user_new_name";
    public static final String USER_NEW_EMAIL = "user_new_email";
    public static final String USER_NEW_PASSWORD = "user_new_password";


    public static final String ERROR_CODE = "error";
    public static final String SERVER_ERROR_MESSAGE = "error_message";
    public static final String ARTICLES_CODE = "articlesCode";
    public static final String NEW_DATA_CODE = "newDataCode";

    public static final String JSON_CHARSET = "ISO-8859-1";
    public static final String UTF8_CHARSET = "UTF-8";

    public static final String JSON_LOGGED_USER = "loggedUser";
    private static final String PARAM_KEY_ARTICLES_LAST_UPDATE_DATE = "articlesLastUpdateDate";
    public static final int SERVER_ERROR_CODE = 1;
    private static final int SERVER_NEW_ARTICLES_CODE = 101;
    private static final int SERVER_NO_NEW_ARTICLES_CODE = 102;
    private static final int SERVER_NEW_DATA_CODE = 101;



    public static final String DATA_DOWNLOAD_SUCCESSFUL = "Data download and insertion has successfully ended";
    private static final String ARTICLES_DATA_DOWNLOAD_SUCCESSFUL = "Articles data download and insertion ended with success";


    public static User loggedUser;


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


    public static void getAllDataFromServer(final Context context, final ProgressDialog progressDialog){

        String url = WEB_SERVICE_URL + SERVICE_DOWNLOAD_DATA;

        RequestQueue queue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        onDownloadDataResponse(context, response, progressDialog);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TomcatConnection.USER_LOGIN, loggedUser.getLogin());
                params.put(TomcatConnection.USER_PASSWORD, loggedUser.getPassword());
                params.put(TomcatConnection.LAST_MOBILE_UPDATE, Utility.getDateString(new Date(loggedUser.getLastUpdateDate())));
                System.out.println(params);
                return params;
            }
        };
        System.out.println(stringRequest.toString());
        queue.add(stringRequest);;

    }


    private static void onDownloadDataResponse(final Context context, String response, final ProgressDialog progressDialog) {

        try {
            final JSONObject responseJson = new JSONObject(response);

            final FootballManiaContract.FootballManiaDbHelper dbHelper = FootballManiaContract.getDbHelperInstance(context);


            new AsyncTask<Void,Integer, String>(){

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                    progressDialog.setIndeterminate(false);
                }

                @Override
                protected String doInBackground(Void... voids) {
                    try {
                    JSONArray areas, competitions, seasons, seasonCompetitions,
                                matches, teams, players, seasonPlayers, scorers,
                                standings, teamStandings, journalists, articles,
                                favourites, favouriteTeams, favouriteCompetitions;


                        areas = responseJson.getJSONArray(ARRAY_KEY_AREAS);
                        competitions = responseJson.getJSONArray(ARRAY_KEY_COMPETITIONS);
                        seasons = responseJson.getJSONArray(ARRAY_KEY_SEASONS);
                        seasonCompetitions = responseJson.getJSONArray(ARRAY_KEY_SEASON_COMPETITIONS);
                        matches = responseJson.getJSONArray(ARRAY_KEY_MATCHES);
                        teams = responseJson.getJSONArray(ARRAY_KEY_TEAMS);
                        players = responseJson.getJSONArray(ARRAY_KEY_PLAYERS);
                        seasonPlayers = responseJson.getJSONArray(ARRAY_KEY_SEASON_PLAYERS);
                        scorers = responseJson.getJSONArray(ARRAY_KEY_SCORERS);
                        standings = responseJson.getJSONArray(ARRAY_KEY_STANDINGS);
                        teamStandings = responseJson.getJSONArray(ARRAY_KEY_TEAM_STANDINGS);
                        journalists = responseJson.getJSONArray(ARRAY_KEY_JOURNALISTS);
                        articles = responseJson.getJSONArray(ARRAY_KEY_ARTICLES);
                        favourites = responseJson.getJSONArray(ARRAY_KEY_FAVOURITES);
                        favouriteTeams = responseJson.getJSONArray(ARRAY_KEY_FAVOURITE_TEAMS);
                        favouriteCompetitions = responseJson.getJSONArray(ARRAY_KEY_FAVOURITE_COMPETITIONS);


                        System.out.println("areas: " + areas.length());
                        System.out.println("competitions: " + competitions.length());
                        System.out.println("seasons: " + seasons.length());
                        System.out.println("seasonCompetitions: " + seasonCompetitions.length());
                        System.out.println("matches: " + matches.length());
                        System.out.println("teams: " + teams.length());
                        System.out.println("players: " + players.length());
                        System.out.println("seasonPlayers: " + seasonPlayers.length());
                        System.out.println("scorers: " + scorers.length());
                        System.out.println("standings: " + standings.length());
                        System.out.println("teamStandings: " + teamStandings.length());
                        System.out.println("journalists: " + journalists.length());
                        System.out.println("articles: " + articles.length());
                        System.out.println("favourites: " + favourites.length());
                        System.out.println("favouriteTeams: " + favouriteTeams.length());
                        System.out.println("favouriteCompetitions: " + favouriteCompetitions.length());

                        for (int i = 0; i < areas.length(); i++)
                            dbHelper.insertArea(areas.getJSONObject(i));

                        for (int i = 0; i < competitions.length(); i++)
                            dbHelper.insertCompetition(competitions.getJSONObject(i));

                        for (int i = 0; i < seasons.length(); i++)
                            dbHelper.insertSeason(seasons.getJSONObject(i));

                        for (int i = 0; i < seasonCompetitions.length(); i++)
                            dbHelper.insertSeasonCompetition(seasonCompetitions.getJSONObject(i));

                        for (int i = 0; i < teams.length(); i++)
                            dbHelper.insertTeam(teams.getJSONObject(i));

                        for (int i = 0; i < matches.length(); i++)
                            dbHelper.insertMatch(matches.getJSONObject(i));

                        for (int i = 0; i < players.length(); i++)
                            dbHelper.insertPlayer(players.getJSONObject(i));

                        for (int i = 0; i < seasonPlayers.length(); i++)
                            dbHelper.insertSeasonPlayer(seasonPlayers.getJSONObject(i));

                        for (int i = 0; i < scorers.length(); i++)
                            dbHelper.insertScorer(scorers.getJSONObject(i));

                        for (int i = 0; i < standings.length(); i++)
                            dbHelper.insertStanding(standings.getJSONObject(i));

                        for (int i = 0; i < teamStandings.length(); i++)
                            dbHelper.insertTeamStanding(teamStandings.getJSONObject(i));

                        for (int i = 0; i < journalists.length(); i++)
                            dbHelper.insertJournalist(journalists.getJSONObject(i));

                        for (int i = 0; i < articles.length(); i++)
                            dbHelper.insertArticle(articles.getJSONObject(i));

                        for (int i = 0; i < favourites.length(); i++)
                            dbHelper.insertFavourite(favourites.getJSONObject(i));

                        for (int i = 0; i < favouriteTeams.length(); i++)
                            dbHelper.insertFavouriteTeam(favouriteTeams.getJSONObject(i));

                        for (int i = 0; i < favouriteCompetitions.length(); i++)
                            dbHelper.insertFavouriteCompetition(favouriteCompetitions.getJSONObject(i));

                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    progressDialog.dismiss();
                    System.out.println(DATA_DOWNLOAD_SUCCESSFUL);
                    TomcatConnection.loggedUser = dbHelper.updateUserDownloadDate(TomcatConnection.loggedUser.getLogin());
                    TomcatConnection.loggedUser = dbHelper.updateUserDownloadArticleDate(TomcatConnection.loggedUser.getLogin());
                    ((MainActivity) context).onDownloadDataFinished();
                }
            }.execute(null,null,null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void registerUser(final Context context, final User user) {
        String url = WEB_SERVICE_URL + SERVICE_REGISTER_USER;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int errorCode = new JSONObject(response).getInt(ERROR_CODE);
                            if (errorCode == SERVER_ERROR_CODE)
                                Toast.makeText(context, R.string.server_register_error, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context, R.string.server_register_succesful, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TomcatConnection.USER_LOGIN, user.getLogin());
                params.put(TomcatConnection.USER_PASSWORD, user.getPassword());
                params.put(TomcatConnection.USER_EMAIL, user.getEmail());
                params.put(TomcatConnection.USER_FIRST_NAME, user.getFirstName());
                System.out.println(params);
                return params;
            }
        };
        System.out.println(stringRequest.toString());
        queue.add(stringRequest);;
    }
    public static void checkForNewArticles(final Context context, final ProgressDialog progressDialog){
        String url = WEB_SERVICE_URL + SERVICE_CHECK_FOR_ARTICLES_UPDATE;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            int errorCode = responseJson.getInt(ERROR_CODE);
                            if (errorCode == SERVER_ERROR_CODE)
                                Toast.makeText(context, responseJson.getString(SERVER_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            else {
                                int articlesCode = responseJson.getInt(ARTICLES_CODE);
                                System.out.println("KODDDDDDDDDDDDDDDDDDDDDDDDDDDDD:   " + articlesCode);
                                if(articlesCode == SERVER_NEW_ARTICLES_CODE){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage(R.string.new_articles_message);

                                    builder.setPositiveButton(R.string.download_now, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            progressDialog.show();
                                            TomcatConnection.getArticlesFromServer(context,progressDialog);
                                            TomcatConnection.loggedUser = FootballManiaContract.getDbHelperInstance(context).updateUserDownloadArticleDate(TomcatConnection.loggedUser.getLogin());
                                        }
                                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //do nothing
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } else
                                    Toast.makeText(context, R.string.no_new_articles, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TomcatConnection.USER_LOGIN, loggedUser.getLogin());
                params.put(TomcatConnection.USER_PASSWORD, loggedUser.getPassword());
                params.put(TomcatConnection.PARAM_KEY_ARTICLES_LAST_UPDATE_DATE, String.valueOf(loggedUser.getLastArticleUpdateDate()));
                System.out.println(params);
                return params;
            }
        };
        System.out.println(stringRequest.toString());
        queue.add(stringRequest);;
    }

    public static void getArticlesFromServer(final Context context, final ProgressDialog progressDialog) {
        String url = WEB_SERVICE_URL + SERVICE_DOWNLOAD_ARTICLES;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            int errorCode = responseJson.getInt(ERROR_CODE);
                            if (errorCode == SERVER_ERROR_CODE)
                                Toast.makeText(context, responseJson.getString(SERVER_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            else
                                onGetArticlesFromServerResponse(context, responseJson, progressDialog);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TomcatConnection.USER_LOGIN, loggedUser.getLogin());
                params.put(TomcatConnection.USER_PASSWORD, loggedUser.getPassword());
                params.put(TomcatConnection.PARAM_KEY_ARTICLES_LAST_UPDATE_DATE, String.valueOf(loggedUser.getLastArticleUpdateDate()));
                System.out.println(params);
                return params;
            }
        };
        System.out.println(stringRequest.toString());
        queue.add(stringRequest);;
    }

    private static void onGetArticlesFromServerResponse(final Context context, final JSONObject responseJson, final ProgressDialog progressDialog) {
        final FootballManiaContract.FootballManiaDbHelper dbHelper = FootballManiaContract.getDbHelperInstance(context);



        new AsyncTask<Void,Integer,String>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressDialog.setIndeterminate(false);
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    JSONArray articles = responseJson.getJSONArray(ARRAY_KEY_ARTICLES);
                    for (int i = 0; i < articles.length(); i++)
                        dbHelper.insertArticle(articles.getJSONObject(i));
                }catch (JSONException | UnsupportedEncodingException ex){
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(context, ARTICLES_DATA_DOWNLOAD_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                ((MainActivity) context).onDownloadDataFinished();
            }
        }.execute(null,null,null);
    }


    public static void updateAccountChanges(final Context context, final String newName, final String newEmail, final String newPassword) {
        String url = WEB_SERVICE_URL + SERVICE_DOWNLOAD_ACCOUNT_UPDATE;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            int errorCode = responseJson.getInt(ERROR_CODE);
                            if (errorCode == SERVER_ERROR_CODE)
                                Toast.makeText(context, responseJson.getString(SERVER_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            else
                                onUpdateAccountResponse(context, responseJson);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TomcatConnection.USER_LOGIN, loggedUser.getLogin());
                params.put(TomcatConnection.USER_PASSWORD, loggedUser.getPassword());
                params.put(TomcatConnection.USER_NEW_NAME, newName);
                params.put(TomcatConnection.USER_NEW_PASSWORD, newPassword);
                params.put(TomcatConnection.USER_NEW_EMAIL, newEmail);
                System.out.println(params);
                return params;
            }
        };
        System.out.println(stringRequest.toString());
        queue.add(stringRequest);;
    }

    private static void onUpdateAccountResponse(Context context, JSONObject responseJson) throws JSONException {
        JSONObject userJson  = responseJson.getJSONObject(JSON_LOGGED_USER);
        TomcatConnection.loggedUser = FootballManiaContract.getDbHelperInstance(context).updateUser(userJson);
        ((MainActivity) context).onAccountUpdateSuccess();
    }

    public static void checkForDataUpdate(final Context context, final ProgressDialog progressDialog){
        String url = WEB_SERVICE_URL + SERVICE_CHECK_DATA_UPDATE;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            int errorCode = responseJson.getInt(ERROR_CODE);
                            if (errorCode == SERVER_ERROR_CODE)
                                Toast.makeText(context, responseJson.getString(SERVER_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            else {
                                int newDataCode = responseJson.getInt(NEW_DATA_CODE);
                                if(newDataCode == SERVER_NEW_DATA_CODE){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage(R.string.new_data_message);

                                    builder.setPositiveButton(R.string.download_now, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            progressDialog.show();
                                            TomcatConnection.getAllDataFromServer(context,progressDialog);
                                        }
                                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //do nothing
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } else
                                    Toast.makeText(context, R.string.data_up_to_date, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TomcatConnection.USER_LOGIN, loggedUser.getLogin());
                params.put(TomcatConnection.USER_PASSWORD, loggedUser.getPassword());
                params.put(TomcatConnection.LAST_MOBILE_UPDATE, Utility.getDateString(new Date(TomcatConnection.loggedUser.getLastUpdateDate())));
                System.out.println(params);
                return params;
            }
        };
        System.out.println(stringRequest.toString());
        queue.add(stringRequest);;
    }
}
