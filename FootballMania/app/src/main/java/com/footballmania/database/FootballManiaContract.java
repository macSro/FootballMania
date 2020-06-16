package com.footballmania.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Pair;

import com.footballmania.serverConnection.TomcatConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FootballManiaContract {

    public static final String PRAGMA_FOREIGN_KEY_ON = "PRAGMA foreign_keys = ON;";
    public static final String PRAGMA_FOREIGN_KEY_OFF= "PRAGMA foreign_keys = OFF;";
    public static final String PRAGMA_UTF8_ENCODING = "PRAGMA encoding = \"UTF-8\";";

    public static FootballManiaDbHelper footballManiaDbHelper = null;

    public static class User implements BaseColumns {

        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FIRST_NAME = "firstName";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdateDate";
        public static final String COLUMN_NAME_LAST_ARTICLE_UPDATE_DATE = "lastArticleUpdateDate";

        public static final String SQL_CREATE_TABLE_USER =
                "CREATE TABLE " + User.TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_LOGIN + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_NAME_PASSWORD + " TEXT NOT NULL, " +
                        COLUMN_NAME_EMAIL + " TEXT UNIQUE, " +
                        COLUMN_NAME_FIRST_NAME + " TEXT, " +
                        COLUMN_NAME_LAST_UPDATE_DATE + " LONG DEFAULT 0, " +
                        COLUMN_NAME_LAST_ARTICLE_UPDATE_DATE + " LONG DEFAULT 0)";

        public static final String SQL_DELETE_TABLE_USER =
                "DROP TABLE IF EXISTS " + User.TABLE_NAME;
    }

    public static class Journalist implements BaseColumns {

        public static final String TABLE_NAME = "Journalist";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_FIRST_NAME = "firstName";
        public static final String COLUMN_NAME_LAST_NAME = "lastName";
        public static final String COLUMN_NAME_EMAIL = "email";

        public static final String SQL_CREATE_TABLE_JOURNALIST =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_FIRST_NAME + " TEXT NOT NULL, " +
                        COLUMN_NAME_LAST_NAME + " TEXT NOT NULL, " +
                        COLUMN_NAME_EMAIL + " TEXT UNIQUE)";

        public static final String SQL_DELETE_TALBE_JOURNALIST =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Article implements BaseColumns {

        public static final String TABLE_NAME = "Article";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE_OF_PUBLICATION = "dateOfPublication";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_JOURNALIST_ID = "idJournalist";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_TAGS = "tags";

        private static final String CONSTRAINT_FOREING_KEY = "fk_id_journalist";

        public static final String SQL_CREATE_TABLE_ARTICLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                        COLUMN_NAME_DATE_OF_PUBLICATION + " LONG NOT NULL, " +
                        COLUMN_NAME_CONTENT + " TEXT NOT NULL, " +
                        COLUMN_NAME_JOURNALIST_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_CATEGORY + " TEXT, " +
                        COLUMN_NAME_TAGS + " TEXT)";

        public static final String SQL_DELETE_TABLE_ARTICLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Comment implements  BaseColumns {

        public static final String TABLE_NAME = "Comment";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE_OF_PUBLICATION = "dateOfPublication";
        public static final String COLUMN_NAME_USER_ID = "idUser";
        public static final String COLUMN_NAME_ARTICLE_ID = "idArticle";

        private static final String CONSTRAINT_FOREIGN_KEY_USER = "fk_id_user";
        private static final String CONSTRAINT_FOREIGN_KEY_ARTICLE = "fk_id_article";

        public static final String SQL_CREATE_TABLE_COMMENT =
                "CREATE TABLE " + TABLE_NAME  + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_CONTENT + " TEXT NOT NULL, " +
                        COLUMN_NAME_DATE_OF_PUBLICATION + " LONG NOT NULL, " +
                        COLUMN_NAME_USER_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_ARTICLE_ID + " INTEGER NOT NULL) ";



        public static final String SQL_DELETE_TABLE_COMMENT =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class Favourites implements BaseColumns {

        public static final String TABLE_NAME = "Favourites";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USER_ID = "idUser";

        public static final String SQL_CREATE_TABLE_FAVOURITES =
                "CREATE TABLE " + Favourites.TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_USER_ID + " INTEGER UNIQUE NOT NULL " +
                                "REFERENCES " + User.TABLE_NAME + "(" + User.COLUMN_NAME_ID + "))";

        public static final String SQL_DELETE_TABLE_FAVOURITES =
                "DROP TABLE IF EXISTS " + Favourites.TABLE_NAME;
    }

    public static class Area implements BaseColumns {

        public static final String TABLE_NAME = "Area";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PARENT_AREA_ID = "idParentArea";

        public static final String CONSTRAINT_PARENT_ID = "fk_area_parent_id";

        public static final String SQL_CREATE_TABLE_AREA =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_NAME + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_NAME_PARENT_AREA_ID + " INTEGER)";

        public static final String SQL_DELETE_TALBE_AREA =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Team implements BaseColumns {

        public static final String TABLE_NAME = "Team";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TLA = "tla";
        public static final String COLUMN_NAME_WEBSITE = "website";
        public static final String COLUMN_NAME_AREA_ID = "idArea";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdated";

        public static final String SQL_CREATE_TABLE_TEAM =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_NAME + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_NAME_TLA + " TEXT NOT NULL, " +
                        COLUMN_NAME_WEBSITE + " TEXT, " +
                        COLUMN_NAME_AREA_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_LAST_UPDATE_DATE + " LONG)";

        public static final String SQL_DELETE_TABLE_TEAM =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Player implements BaseColumns {

        public static final String TABLE_NAME = "Player";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATE_OF_BIRTH = "dateOfBirth";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdated";
        public static final String COLUMN_NAME_AREA_ID = "idArea";
        public static final String COLUMN_NAME_POSITION = "idPosition";

        public static final String SQL_CREATE_TABLE_PLAYER =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_NAME + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_NAME_DATE_OF_BIRTH + " LONG, " +
                        COLUMN_NAME_LAST_UPDATE_DATE + " LONG, " +
                        COLUMN_NAME_AREA_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_POSITION + " INTEGER NOT NULL)";

        public static final String SQL_DELETE_TABLE_PLAYER =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class Competition implements BaseColumns {

        public static final String TABLE_NAME = "Competition";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdated";
        public static final String COLUMN_NAME_AREA_ID = "idArea";

        public static final String SQL_CREATE_TABLE_COMPETITION =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_NAME + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_NAME_LAST_UPDATE_DATE + " LONG, " +
                        COLUMN_NAME_AREA_ID + " INTEGER NOT NULL)";

        public static final String SQL_DELETE_TABLE_COMPETITION =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public static class FavouriteCompetition implements BaseColumns {

        public static final String TABLE_NAME = "FavouriteCompetition";
        public static final String COLUMN_NAME_FAVOURITES_ID = "idFavourites";
        public static final String COLUMN_NAME_COMPETITION_ID = "idCompetition";

        private static final String CONSTRAINT_PRIMARY_KEY = "pk_favourite_competition";

        public static final String SQL_CREATE_TABLE_FAVOURITES_COMPETITION =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_FAVOURITES_ID + " INTEGER NOT NULL REFERENCES " + Favourites.TABLE_NAME + "(" + Favourites.COLUMN_NAME_ID + "), " +
                        COLUMN_NAME_COMPETITION_ID + " INTEGER NOT NULL REFERENCES " + Competition.TABLE_NAME + "(" + Competition.COLUMN_NAME_ID + "), " +
                        " CONSTRAINT " + CONSTRAINT_PRIMARY_KEY + " PRIMARY KEY (" + COLUMN_NAME_FAVOURITES_ID + "," + COLUMN_NAME_COMPETITION_ID +"))";

        public static final String SQL_DELETE_TABLE_FAVOURITES_COMPETITION =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class FavouriteTeam implements BaseColumns {

        public static final String TABLE_NAME = "FavouriteTeam";
        public static final String COLUMN_NAME_FAVOURITES_ID = "idFavourites";
        public static final String COLUMN_NAME_TEAM_ID = "idTeam";

        private static final String CONSTRAINT_PRIMARY_KEY = "pk_favourite_team";

        public static final String SQL_CREATE_TABLE_FAVOURITES_TEAM =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_FAVOURITES_ID + " INTEGER NOT NULL REFERENCES " + Favourites.TABLE_NAME + "(" + Favourites.COLUMN_NAME_ID + "), " +
                        COLUMN_NAME_TEAM_ID + " INTEGER NOT NULL REFERENCES " + Team.TABLE_NAME + "(" + Team.COLUMN_NAME_ID + "), " +
                        " CONSTRAINT " + CONSTRAINT_PRIMARY_KEY + " PRIMARY KEY (" + COLUMN_NAME_FAVOURITES_ID + "," + COLUMN_NAME_TEAM_ID +"))";

        public static final String SQL_DELETE_TABLE_FAVOURITES_TEAM =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class FavouritePlayer implements BaseColumns {

        public static final String TABLE_NAME = "FavouritePlayer";
        public static final String COLUMN_NAME_FAVOURITES_ID = "idFavourites";
        public static final String COLUMN_NAME_PLAYER_ID = "idPlayer";

        private static final String CONSTRAINT_PRIMARY_KEY = "pk_favourite_player"
                ;
        public static final String SQL_CREATE_TABLE_FAVOURITES_PLAYER =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_FAVOURITES_ID + " INTEGER NOT NULL REFERENCES " + Favourites.TABLE_NAME + "(" + Favourites.COLUMN_NAME_ID + "), " +
                        COLUMN_NAME_PLAYER_ID + " INTEGER NOT NULL REFERENCES " + Player.TABLE_NAME + "(" + Player.COLUMN_NAME_ID + "), " +
                        " CONSTRAINT " + CONSTRAINT_PRIMARY_KEY +  " PRIMARY KEY (" + COLUMN_NAME_FAVOURITES_ID + "," + COLUMN_NAME_PLAYER_ID +"))";

        public static final String SQL_DELETE_TABLE_FAVOURITES_PLAYER =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Season implements BaseColumns {

        public static final String TABLE_NAME = "Season";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_START_DATE = "startDate";
        public static final String COLUMN_NAME_END_DATE = "endDate";
        public static final String COLUMN_NAME_CURRENT_MATCH_DAY = "currentMatchDay";

        private static final String CONSTRAINT_DATES = "ck_dates";

        public static final String SQL_CREATE_TABLE_SEASON =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_START_DATE + " LONG NOT NULL, " +
                        COLUMN_NAME_END_DATE + " LONG NOT NULL," +
                        COLUMN_NAME_CURRENT_MATCH_DAY + " INTEGER NOT NULL, " +
                " CONSTRAINT " + CONSTRAINT_DATES + " CHECK(" + COLUMN_NAME_END_DATE + ">" + COLUMN_NAME_START_DATE +"))";

        public static final String SQL_DELETE_TABLE_SEASON =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class SeasonCompetition implements BaseColumns {

        public static final String TABLE_NAME = "SeasonCompetition";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SEASON_ID = "idSeason";
        public static final String COLUMN_NAME_COMPETITION_ID = "idCompetition";

        public static final String SQL_CREATE_TABLE_SEASON_COMPETITION =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_SEASON_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_COMPETITION_ID + " INTEGER NOT NULL)";

        public static final String SQL_DELETE_TALBE_SEASON_COMPETITION =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Standing implements BaseColumns {

        public static final String TABLE_NAME = "Standing";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_STAGE = "idStage";
        public static final String COLUMN_NAME_SEASON_COMPETITION_ID = "idSeasonCompetition";

        public static final String SQL_CREATE_TABLE_STANDING =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_STAGE + " INTEGER NOT NULL, " +
                        COLUMN_NAME_SEASON_COMPETITION_ID + " INTEGER NOT NULL)";

        public static final String SQL_DELETE_TALBE_STANDING =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public static class TeamStanding implements BaseColumns {

        public static final String TABLE_NAME = "TeamStanding";
        public static final String COLUMN_NAME_STANDING_ID = "idStanding";
        public static final String COLUMN_NAME_TEAM_ID = "idTeam";
        public static final String COLUMN_NAME_POSITION = "position";
        public static final String COLUMN_NAME_PLAYED_GAMES = "playedGames";
        public static final String COLUMN_NAME_WON = "won";
        public static final String COLUMN_NAME_DRAW = "draw";
        public static final String COLUMN_NAME_LOST = "lost";
        public static final String COLUMN_NAME_POINTS = "points";
        public static final String COLUMN_NAME_GOALS_FOR = "goalsFor";
        public static final String COLUMN_NAME_GOALS_AGAINST = "goalsAgainst";

        private static final String CONSTRAINT_PRIMARY_KEY = "pk_team_standing";

        public static final String SQL_CREATE_TABLE_TEAM_STANDING =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_STANDING_ID + " INTEGER NOT NULL , " +
                        COLUMN_NAME_TEAM_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_POSITION + " INTEGER NOT NULL, " +
                        COLUMN_NAME_PLAYED_GAMES + " INTEGER NOT NULL, " +
                        COLUMN_NAME_WON + " INTEGER NOT NULL, " +
                        COLUMN_NAME_DRAW + " INTEGER NOT NULL, " +
                        COLUMN_NAME_LOST + " INTEGER NOT NULL, " +
                        COLUMN_NAME_POINTS + " INTEGER NOT NULL, " +
                        COLUMN_NAME_GOALS_FOR + " INTEGER NOT NULL, " +
                        COLUMN_NAME_GOALS_AGAINST + " INTEGER NOT NULL) ";

        public static final String SQL_DELETE_TABLE_TEAM_STANDING =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class Match implements BaseColumns {

        public static final String TABLE_NAME = "[Match]";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_UTC_DATE = "utcDate";
        public static final String COLUMN_NAME_MATCH_DAY = "matchDay";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdated";
        public static final String COLUMN_NAME_STATUS = "idStatus";
        public static final String COLUMN_NAME_SEASON_COMPETITION_ID = "idSeasonCompetition";
        public static final String COLUMN_NAME_HOME_TEAM_ID = "idTeamHome";
        public static final String COLUMN_NAME_AWAY_TEAM_ID = "idTeamAway";
        public static final String COLUMN_NAME_HOME_TEAM_GOALS = "goalsHome";
        public static final String COLUMN_NAME_AWAY_TEAM_GOALS = "goalsAway";

        private static final String CONSTRAINT_TEAMS = "ck_teams";

        public static final String SQL_CREATE_TABLE_MATCH =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_UTC_DATE + " LONG NOT NULL, " +
                        COLUMN_NAME_MATCH_DAY + " INTEGER NOT NULL, " +
                        COLUMN_NAME_LAST_UPDATE_DATE + " LONG NOT NULL, " +
                        COLUMN_NAME_STATUS + " INTEGER NOT NULL, " +
                        COLUMN_NAME_SEASON_COMPETITION_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_HOME_TEAM_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_AWAY_TEAM_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_HOME_TEAM_GOALS + " INTEGER NOT NULL, " +
                        COLUMN_NAME_AWAY_TEAM_GOALS + " INTEGER NOT NULL, " +
                        " CONSTRAINT " + CONSTRAINT_TEAMS + " CHECK(" + COLUMN_NAME_HOME_TEAM_ID +"<>" + COLUMN_NAME_AWAY_TEAM_ID + "))";

        public static final String SQL_DELETE_TABLE_MATCH =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class SeasonPlayer implements BaseColumns {

        public static final String TABLE_NAME = "SeasonPlayer";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_PLAYER_ID = "idPlayer";
        public static final String COLUMN_NAME_TEAM_ID = "idTeam";

        public static final String SQL_CREATE_TABLE_SEASON_PLAYER =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                        COLUMN_NAME_PLAYER_ID + " INTEGER NOT NULL , " +
                        COLUMN_NAME_TEAM_ID + " INTEGER NOT NULL)";

        public static final String SQL_DELETE_TABLE_SEASON_PLAYER =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Scorer implements BaseColumns {

        public static final String TABLE_NAME = "Scorer";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NUMBER_OF_GOALS = "numberOfGoals";
        public static final String COLUMN_NAME_SEASON_COMPETITION_ID = "idSeasonCompetition";
        public static final String COLUMN_NAME_SEASON_PLAYER_ID = "idSeasonPlayer";

        public static final String SQL_CREATE_TABLE_SCORER =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                        COLUMN_NAME_NUMBER_OF_GOALS+ " INTEGER NOT NULL, " +
                        COLUMN_NAME_SEASON_COMPETITION_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_SEASON_PLAYER_ID + " INTEGER NOT NULL)";


        public static final String SQL_DELETE_TABLE_SCORER =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public static class Goal implements BaseColumns {

        public static final String TABLE_NAME = "Goal";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_MINUTE = "minute";
        public static final String COLUMN_NAME_EXTRA_TIME = "extraTime";
        public static final String COLUMN_NAME_TYPE_ID = "idType";
        public static final String COLUMN_NAME_MATCH_ID = "idMatch";
        public static final String COLUMN_NAME_SCORED_ID = "idScored";
        public static final String COLUMN_NAME_ASSIST_ID = "idAssist";

        public static final String SQL_CREATE_TABLE_GOAL =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                        COLUMN_NAME_MINUTE + " INTEGER NOT NULL, " +
                        COLUMN_NAME_EXTRA_TIME + " INTEGER, " +
                        COLUMN_NAME_TYPE_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_MATCH_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_SCORED_ID + " INTEGER NOT NULL, " +
                        COLUMN_NAME_ASSIST_ID + " INTEGER NOT NULL)";


        public static final String SQL_DELETE_TABLE_GOAL =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static FootballManiaDbHelper getDbHelperInstance(Context context){
        if(footballManiaDbHelper == null){
            FootballManiaDbHelper dbHelper = new FootballManiaDbHelper(context);
            footballManiaDbHelper = dbHelper;
        }
        return footballManiaDbHelper;
    }

    public static class FootballManiaDbHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "football_mania.db";
        private static final int DATABASE_VERSION = 1;


        public FootballManiaDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(PRAGMA_FOREIGN_KEY_ON);
            db.execSQL(User.SQL_CREATE_TABLE_USER);
            db.execSQL(Journalist.SQL_CREATE_TABLE_JOURNALIST);
            db.execSQL(Article.SQL_CREATE_TABLE_ARTICLE);
            db.execSQL(Comment.SQL_CREATE_TABLE_COMMENT);
            db.execSQL(Favourites.SQL_CREATE_TABLE_FAVOURITES);
            db.execSQL(Area.SQL_CREATE_TABLE_AREA);
            db.execSQL(Season.SQL_CREATE_TABLE_SEASON);
            db.execSQL(Competition.SQL_CREATE_TABLE_COMPETITION);
            db.execSQL(SeasonCompetition.SQL_CREATE_TABLE_SEASON_COMPETITION);
            db.execSQL(Standing.SQL_CREATE_TABLE_STANDING);
            db.execSQL(Team.SQL_CREATE_TABLE_TEAM);
            db.execSQL(Player.SQL_CREATE_TABLE_PLAYER);
            db.execSQL(TeamStanding.SQL_CREATE_TABLE_TEAM_STANDING);
            db.execSQL(FavouriteCompetition.SQL_CREATE_TABLE_FAVOURITES_COMPETITION);
            db.execSQL(FavouriteTeam.SQL_CREATE_TABLE_FAVOURITES_TEAM);
            db.execSQL(FavouritePlayer.SQL_CREATE_TABLE_FAVOURITES_PLAYER);
            db.execSQL(Match.SQL_CREATE_TABLE_MATCH);
            db.execSQL(SeasonPlayer.SQL_CREATE_TABLE_SEASON_PLAYER);
            db.execSQL(Scorer.SQL_CREATE_TABLE_SCORER);
            db.execSQL(Goal.SQL_CREATE_TABLE_GOAL);

            initializeAdminUser(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void disableConstraints(){
            SQLiteDatabase database = getWritableDatabase();
            database.execSQL(PRAGMA_FOREIGN_KEY_OFF);
            database.close();
        }

        public void enableConstraints(){
            SQLiteDatabase database = getWritableDatabase();
            database.execSQL(PRAGMA_FOREIGN_KEY_ON);
            database.close();
        }

        public boolean hasUserDownloadedData(String userLogin){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * " +
                    " FROM " + User.TABLE_NAME +
                    " WHERE " +  User.COLUMN_NAME_LOGIN + " ='" + userLogin + "'";

            Cursor cursor = database.rawQuery(query, null);
            int flag = 0;
            if(cursor.moveToFirst()){
                flag = cursor.getInt(cursor.getColumnIndex(User.COLUMN_NAME_LAST_UPDATE_DATE));
            }

            return flag == 1;
        }

        public com.footballmania.database.dbobjects.User getUser(String login) {
            com.footballmania.database.dbobjects.User loggedUser = null;

            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * " +
                            " FROM " + User.TABLE_NAME +
                            " WHERE " +  User.COLUMN_NAME_LOGIN + " ='" + login + "'";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                loggedUser = new com.footballmania.database.dbobjects.User(
                        cursor.getInt(cursor.getColumnIndex(User.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME_LOGIN)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME_PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME_FIRST_NAME)),
                        cursor.getLong(cursor.getColumnIndex(User.COLUMN_NAME_LAST_UPDATE_DATE)),
                        cursor.getLong(cursor.getColumnIndex(User.COLUMN_NAME_LAST_ARTICLE_UPDATE_DATE))
                );
            }

            return loggedUser;
        }

        public com.footballmania.database.dbobjects.User insertUser(JSONObject json) throws JSONException {
            com.footballmania.database.dbobjects.User loggedUser;


            loggedUser = new com.footballmania.database.dbobjects.User(
                    json.getInt(User.COLUMN_NAME_ID),
                    json.getString(User.COLUMN_NAME_LOGIN),
                    json.getString(User.COLUMN_NAME_PASSWORD),
                    json.getString(User.COLUMN_NAME_EMAIL),
                    json.getString(User.COLUMN_NAME_FIRST_NAME),
                    0,
                    0
            );

            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(User.COLUMN_NAME_ID, loggedUser.getId());
            values.put(User.COLUMN_NAME_LOGIN, loggedUser.getLogin());
            values.put(User.COLUMN_NAME_PASSWORD, loggedUser.getPassword());
            values.put(User.COLUMN_NAME_EMAIL, loggedUser.getEmail());
            values.put(User.COLUMN_NAME_FIRST_NAME, loggedUser.getFirstName());


            database.insertWithOnConflict(User.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();

            return loggedUser;
        }

        public void initializeAdminUser(SQLiteDatabase database){
            ContentValues values = new ContentValues();

            values.put(User.COLUMN_NAME_ID, 1);
            values.put(User.COLUMN_NAME_LOGIN, "admin@footballmania");
            values.put(User.COLUMN_NAME_PASSWORD, "P@ssw0rd");
            values.put(User.COLUMN_NAME_EMAIL, "admin@footballmania.com");
            values.put(User.COLUMN_NAME_FIRST_NAME, "Administrator");

            database.insertWithOnConflict(User.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        }

        public ArrayList<com.footballmania.database.dbobjects.Article> getArticleList(){
            ArrayList<com.footballmania.database.dbobjects.Article> list = new ArrayList<>();

            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT A.id, A.title, A.dateOfPublication, J.firstName, J.lastName, A.category, A.tags FROM Article A JOIN Journalist J ON J.id = A.idJournalist";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do{
                    list.add(new com.footballmania.database.dbobjects.Article(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getLong(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6)
                            )
                    );
                }while (cursor.moveToNext());
            }

            return list;
        }

        public ArrayList<com.footballmania.database.dbobjects.Comment> getArticleComments(int articleId) {
            ArrayList<com.footballmania.database.dbobjects.Comment> list = new ArrayList<>();

            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * " +
                    "FROM " + Comment.TABLE_NAME +
                    " WHERE " + Comment.COLUMN_NAME_ARTICLE_ID + "=" + articleId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do{
                    list.add(new com.footballmania.database.dbobjects.Comment(
                            cursor.getInt(cursor.getColumnIndex(Comment.COLUMN_NAME_ID)),
                            cursor.getString(cursor.getColumnIndex(Comment.COLUMN_NAME_CONTENT)),
                            cursor.getLong(cursor.getColumnIndex(Comment.COLUMN_NAME_DATE_OF_PUBLICATION)),
                            cursor.getInt(cursor.getColumnIndex(Comment.COLUMN_NAME_USER_ID)),
                            cursor.getInt(cursor.getColumnIndex(Comment.COLUMN_NAME_ARTICLE_ID))
                            )
                    );
                }while (cursor.moveToNext());
            }

            return list;
        }

        public ArrayList<com.footballmania.database.dbobjects.Comment> getUserComments(int userId) {
            ArrayList<com.footballmania.database.dbobjects.Comment> list = new ArrayList<>();

            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * " +
                    "FROM " + Comment.TABLE_NAME +
                    " WHERE " + Comment.COLUMN_NAME_USER_ID + " = " + userId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do{
                    list.add(new com.footballmania.database.dbobjects.Comment(
                                    cursor.getInt(cursor.getColumnIndex(Comment.COLUMN_NAME_ID)),
                                    cursor.getString(cursor.getColumnIndex(Comment.COLUMN_NAME_CONTENT)),
                                    cursor.getLong(cursor.getColumnIndex(Comment.COLUMN_NAME_DATE_OF_PUBLICATION)),
                                    cursor.getInt(cursor.getColumnIndex(Comment.COLUMN_NAME_USER_ID)),
                                    cursor.getInt(cursor.getColumnIndex(Comment.COLUMN_NAME_ARTICLE_ID))
                            )
                    );
                }while (cursor.moveToNext());
            }

            return list;
        }

        public void insertArea(JSONObject json) throws JSONException, UnsupportedEncodingException {
            SQLiteDatabase database = this.getWritableDatabase();



            ContentValues values = new ContentValues();
            values.put(Area.COLUMN_NAME_ID, json.getInt(Area.COLUMN_NAME_ID));
            values.put(Area.COLUMN_NAME_NAME, new String(json.getString(Area.COLUMN_NAME_NAME).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));
            values.put(Area.COLUMN_NAME_PARENT_AREA_ID, json.getInt(Area.COLUMN_NAME_PARENT_AREA_ID));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Area.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertCompetition(JSONObject json) throws JSONException, UnsupportedEncodingException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Competition.COLUMN_NAME_ID, json.getInt(Competition.COLUMN_NAME_ID));
            values.put(Competition.COLUMN_NAME_NAME, new String(json.getString(Competition.COLUMN_NAME_NAME).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));
            values.put(Competition.COLUMN_NAME_AREA_ID, json.getInt(Competition.COLUMN_NAME_AREA_ID));
            values.put(Competition.COLUMN_NAME_LAST_UPDATE_DATE, json.getLong(Competition.COLUMN_NAME_LAST_UPDATE_DATE));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Competition.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertSeason(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Season.COLUMN_NAME_ID, json.getInt(Season.COLUMN_NAME_ID));
            values.put(Season.COLUMN_NAME_START_DATE, json.getLong(Season.COLUMN_NAME_START_DATE));
            values.put(Season.COLUMN_NAME_END_DATE, json.getLong(Season.COLUMN_NAME_END_DATE));
            values.put(Season.COLUMN_NAME_CURRENT_MATCH_DAY, json.getInt(Season.COLUMN_NAME_CURRENT_MATCH_DAY));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Season.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertSeasonCompetition(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SeasonCompetition.COLUMN_NAME_ID, json.getInt(SeasonCompetition.COLUMN_NAME_ID));
            values.put(SeasonCompetition.COLUMN_NAME_SEASON_ID, json.getInt(SeasonCompetition.COLUMN_NAME_SEASON_ID));
            values.put(SeasonCompetition.COLUMN_NAME_COMPETITION_ID, json.getInt(SeasonCompetition.COLUMN_NAME_COMPETITION_ID));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(SeasonCompetition.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertMatch(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Match.COLUMN_NAME_ID, json.getInt(Match.COLUMN_NAME_ID));
            values.put(Match.COLUMN_NAME_SEASON_COMPETITION_ID, json.getInt(Match.COLUMN_NAME_SEASON_COMPETITION_ID));
            values.put(Match.COLUMN_NAME_HOME_TEAM_ID, json.getInt(Match.COLUMN_NAME_HOME_TEAM_ID));
            values.put(Match.COLUMN_NAME_AWAY_TEAM_ID, json.getInt(Match.COLUMN_NAME_AWAY_TEAM_ID));
            values.put(Match.COLUMN_NAME_MATCH_DAY, json.getInt(Match.COLUMN_NAME_MATCH_DAY));
            values.put(Match.COLUMN_NAME_STATUS, json.getInt(Match.COLUMN_NAME_STATUS));
            values.put(Match.COLUMN_NAME_UTC_DATE, json.getLong(Match.COLUMN_NAME_UTC_DATE));
            values.put(Match.COLUMN_NAME_LAST_UPDATE_DATE, json.getLong(Match.COLUMN_NAME_LAST_UPDATE_DATE));
            values.put(Match.COLUMN_NAME_HOME_TEAM_GOALS, json.getInt(Match.COLUMN_NAME_HOME_TEAM_GOALS));
            values.put(Match.COLUMN_NAME_AWAY_TEAM_GOALS, json.getInt(Match.COLUMN_NAME_AWAY_TEAM_GOALS));


            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Match.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertTeam(JSONObject json) throws JSONException, UnsupportedEncodingException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Team.COLUMN_NAME_ID, json.getInt(Team.COLUMN_NAME_ID));
            values.put(Team.COLUMN_NAME_AREA_ID, json.getInt(Team.COLUMN_NAME_AREA_ID));
            values.put(Team.COLUMN_NAME_NAME, new String(json.getString(Team.COLUMN_NAME_NAME).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));
            values.put(Team.COLUMN_NAME_LAST_UPDATE_DATE, json.getLong(Team.COLUMN_NAME_LAST_UPDATE_DATE));
            values.put(Team.COLUMN_NAME_TLA, new String(json.getString(Team.COLUMN_NAME_TLA).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));
            values.put(Team.COLUMN_NAME_WEBSITE, new String(json.getString(Team.COLUMN_NAME_WEBSITE).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Team.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertPlayer(JSONObject json) throws JSONException, UnsupportedEncodingException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Player.COLUMN_NAME_ID, json.getInt(Player.COLUMN_NAME_ID));
            values.put(Player.COLUMN_NAME_AREA_ID, json.getInt(Player.COLUMN_NAME_AREA_ID));
            values.put(Player.COLUMN_NAME_NAME, new String(json.getString(Player.COLUMN_NAME_NAME).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));
            values.put(Player.COLUMN_NAME_POSITION, json.getInt(Player.COLUMN_NAME_POSITION));
            values.put(Player.COLUMN_NAME_DATE_OF_BIRTH, json.isNull(Player.COLUMN_NAME_DATE_OF_BIRTH) ? 0 : json.getLong(Player.COLUMN_NAME_DATE_OF_BIRTH));
            values.put(Player.COLUMN_NAME_LAST_UPDATE_DATE, json.getLong(Player.COLUMN_NAME_LAST_UPDATE_DATE));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Player.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertSeasonPlayer(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SeasonPlayer.COLUMN_NAME_ID, json.getInt(SeasonPlayer.COLUMN_NAME_ID));
            values.put(SeasonPlayer.COLUMN_NAME_PLAYER_ID, json.getInt(SeasonPlayer.COLUMN_NAME_PLAYER_ID));
            values.put(SeasonPlayer.COLUMN_NAME_TEAM_ID, json.getInt(SeasonPlayer.COLUMN_NAME_TEAM_ID));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(SeasonPlayer.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertScorer(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Scorer.COLUMN_NAME_ID, json.getInt(Scorer.COLUMN_NAME_ID));
            values.put(Scorer.COLUMN_NAME_SEASON_PLAYER_ID, json.getInt(Scorer.COLUMN_NAME_SEASON_PLAYER_ID));
            values.put(Scorer.COLUMN_NAME_SEASON_COMPETITION_ID, json.getInt(Scorer.COLUMN_NAME_SEASON_COMPETITION_ID));
            values.put(Scorer.COLUMN_NAME_NUMBER_OF_GOALS, json.getInt(Scorer.COLUMN_NAME_NUMBER_OF_GOALS));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Scorer.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertStanding(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Standing.COLUMN_NAME_ID, json.getInt(Standing.COLUMN_NAME_ID));
            values.put(Standing.COLUMN_NAME_SEASON_COMPETITION_ID, json.getInt(Standing.COLUMN_NAME_SEASON_COMPETITION_ID));
            values.put(Standing.COLUMN_NAME_STAGE, json.getInt(Standing.COLUMN_NAME_STAGE));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Standing.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertTeamStanding(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TeamStanding.COLUMN_NAME_STANDING_ID, json.getInt(TeamStanding.COLUMN_NAME_STANDING_ID));
            values.put(TeamStanding.COLUMN_NAME_TEAM_ID, json.getInt(TeamStanding.COLUMN_NAME_TEAM_ID));
            values.put(TeamStanding.COLUMN_NAME_PLAYED_GAMES, json.getInt(TeamStanding.COLUMN_NAME_PLAYED_GAMES));
            values.put(TeamStanding.COLUMN_NAME_POSITION, json.getInt(TeamStanding.COLUMN_NAME_POSITION));
            values.put(TeamStanding.COLUMN_NAME_WON, json.getInt(TeamStanding.COLUMN_NAME_WON));
            values.put(TeamStanding.COLUMN_NAME_DRAW, json.getInt(TeamStanding.COLUMN_NAME_DRAW));
            values.put(TeamStanding.COLUMN_NAME_LOST, json.getInt(TeamStanding.COLUMN_NAME_LOST));
            values.put(TeamStanding.COLUMN_NAME_GOALS_FOR, json.getInt(TeamStanding.COLUMN_NAME_GOALS_FOR));
            values.put(TeamStanding.COLUMN_NAME_GOALS_AGAINST, json.getInt(TeamStanding.COLUMN_NAME_GOALS_AGAINST));
            values.put(TeamStanding.COLUMN_NAME_POINTS, json.getInt(TeamStanding.COLUMN_NAME_POINTS));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(TeamStanding.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertJournalist(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Journalist.COLUMN_NAME_ID, json.getInt(Journalist.COLUMN_NAME_ID));
            values.put(Journalist.COLUMN_NAME_FIRST_NAME, json.getString(Journalist.COLUMN_NAME_FIRST_NAME));
            values.put(Journalist.COLUMN_NAME_LAST_NAME, json.getString(Journalist.COLUMN_NAME_LAST_NAME));
            values.put(Journalist.COLUMN_NAME_EMAIL, json.getString(Journalist.COLUMN_NAME_EMAIL));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Journalist.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertArticle(JSONObject json) throws JSONException, UnsupportedEncodingException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Article.COLUMN_NAME_ID, json.getInt(Article.COLUMN_NAME_ID));
            values.put(Article.COLUMN_NAME_JOURNALIST_ID, json.getInt(Article.COLUMN_NAME_JOURNALIST_ID));
            values.put(Article.COLUMN_NAME_TITLE, new String(json.getString(Article.COLUMN_NAME_TITLE).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));
            values.put(Article.COLUMN_NAME_CONTENT, new String(json.getString(Article.COLUMN_NAME_CONTENT).getBytes(TomcatConnection.JSON_CHARSET), StandardCharsets.UTF_8));
            values.put(Article.COLUMN_NAME_DATE_OF_PUBLICATION, json.getLong(Article.COLUMN_NAME_DATE_OF_PUBLICATION));
            values.put(Article.COLUMN_NAME_CATEGORY, json.getString(Article.COLUMN_NAME_CATEGORY));
            values.put(Article.COLUMN_NAME_TAGS, json.getString(Article.COLUMN_NAME_TAGS));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Article.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void testDatabase(){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * " +
                    "FROM " + Team.TABLE_NAME;

            Cursor cursor = database.rawQuery(query, null);

            System.out.println("Testing Database");
            if(cursor.moveToFirst()){
                System.out.println("Data fount in Teams table");
                do{
                    System.out.println(cursor.getString(cursor.getColumnIndex(Team.COLUMN_NAME_NAME)));
                }while (cursor.moveToNext());
            }
        }


        public ArrayList<com.footballmania.database.dbobjects.Competition> getCompetitionsList(){
            ArrayList<com.footballmania.database.dbobjects.Competition> competitions = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String area = "areaName";
            String compId = "competitionId";

            String query = "SELECT " + Competition.TABLE_NAME + "." + Competition.COLUMN_NAME_ID + " '" + compId + "' " +", " + Competition.TABLE_NAME + "." + Competition.COLUMN_NAME_NAME + ", " +
                            Competition.COLUMN_NAME_LAST_UPDATE_DATE + ", " + Area.TABLE_NAME + "." + Area.COLUMN_NAME_NAME + " '" + area + "' " +
                            " FROM " + Competition.TABLE_NAME +
                            " JOIN " + SeasonCompetition.TABLE_NAME + " ON " + Competition.TABLE_NAME + "." +  Competition.COLUMN_NAME_ID + " = " + SeasonCompetition.COLUMN_NAME_COMPETITION_ID +
                            " JOIN " + Area.TABLE_NAME + " ON " + Competition.COLUMN_NAME_AREA_ID + " = " + Area.TABLE_NAME + "." + Area.COLUMN_NAME_ID;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    competitions.add(new com.footballmania.database.dbobjects.Competition(
                            cursor.getInt(cursor.getColumnIndex(compId)),
                            cursor.getString(cursor.getColumnIndex(Competition.COLUMN_NAME_NAME)),
                            cursor.getLong(cursor.getColumnIndex(Competition.COLUMN_NAME_LAST_UPDATE_DATE)),
                            cursor.getString(cursor.getColumnIndex(area))
                    ));
                }while(cursor.moveToNext());
            }

            return competitions;
        }

        public Pair<ArrayList<com.footballmania.database.dbobjects.Team>, ArrayList<com.footballmania.database.dbobjects.TeamStanding>> getTeamAndTeamStandingLists(int competitionId){
            ArrayList<com.footballmania.database.dbobjects.Team> teams = new ArrayList<>();
            ArrayList<com.footballmania.database.dbobjects.TeamStanding> teamStandings = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String area = "areaName";

            String query = "SELECT " + Team.TABLE_NAME + "." + Team.COLUMN_NAME_ID + ", " + Team.TABLE_NAME + "." + Team.COLUMN_NAME_NAME + ", " + Team.COLUMN_NAME_TLA + ", " +
                    Team.COLUMN_NAME_WEBSITE + ", " + Area.TABLE_NAME + "." + Area.COLUMN_NAME_NAME + " '" + area + "' " + ", " + Team.TABLE_NAME + "." + Team.COLUMN_NAME_LAST_UPDATE_DATE + ", " +
                    TeamStanding.COLUMN_NAME_STANDING_ID + ", " +  TeamStanding.COLUMN_NAME_POSITION + ", " + TeamStanding.COLUMN_NAME_PLAYED_GAMES + ", " + TeamStanding.COLUMN_NAME_WON + ", " +
                    TeamStanding.COLUMN_NAME_DRAW + ", " + TeamStanding.COLUMN_NAME_LOST + ", " + TeamStanding.COLUMN_NAME_POINTS + ", " + TeamStanding.COLUMN_NAME_GOALS_FOR + ", " +
                    TeamStanding.COLUMN_NAME_GOALS_AGAINST +
                    " FROM " + Team.TABLE_NAME +
                    " JOIN " + TeamStanding.TABLE_NAME + " ON " + Team.TABLE_NAME + "." + Team.COLUMN_NAME_ID + " = " + TeamStanding.TABLE_NAME + "." + TeamStanding.COLUMN_NAME_TEAM_ID +
                    " JOIN " + Standing.TABLE_NAME + " ON " + Standing.TABLE_NAME + "." + Standing.COLUMN_NAME_ID + " = " + TeamStanding.COLUMN_NAME_STANDING_ID +
                    " JOIN " + SeasonCompetition.TABLE_NAME + " ON " + SeasonCompetition.TABLE_NAME + "." + SeasonCompetition.COLUMN_NAME_ID + " = " + Standing.TABLE_NAME + "." + Standing.COLUMN_NAME_SEASON_COMPETITION_ID +
                    " JOIN " + Area.TABLE_NAME + " ON " + Team.COLUMN_NAME_AREA_ID + " = " + Area.TABLE_NAME + "." + Area.COLUMN_NAME_ID +
                    " WHERE " + SeasonCompetition.TABLE_NAME + "." +SeasonCompetition.COLUMN_NAME_COMPETITION_ID + "  = " + competitionId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    teams.add(new com.footballmania.database.dbobjects.Team(
                            cursor.getInt(0),
                            cursor.getString(cursor.getColumnIndex(Team.COLUMN_NAME_NAME)),
                            cursor.getString(cursor.getColumnIndex(Team.COLUMN_NAME_TLA)),
                            cursor.getString(cursor.getColumnIndex(Team.COLUMN_NAME_WEBSITE)),
                            cursor.getString(cursor.getColumnIndex(area)),
                            cursor.getLong(cursor.getColumnIndex(Team.COLUMN_NAME_LAST_UPDATE_DATE))
                    ));

                    teamStandings.add(new com.footballmania.database.dbobjects.TeamStanding(
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_STANDING_ID)),
                        cursor.getString(cursor.getColumnIndex(Team.COLUMN_NAME_NAME)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_POSITION)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_PLAYED_GAMES)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_WON)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_DRAW)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_LOST)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_POINTS)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_GOALS_FOR)),
                        cursor.getInt(cursor.getColumnIndex(TeamStanding.COLUMN_NAME_GOALS_AGAINST))
                    ));
                }while(cursor.moveToNext());
            }

            return new Pair<>(teams, teamStandings);
        }

        public ArrayList<com.footballmania.database.dbobjects.Scorer> getScorersList(int competitionId){
            ArrayList<com.footballmania.database.dbobjects.Scorer> scorers = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String scorerId = "scorerId";
            String scorerSeasonCompetitionId = "scorerSeasonCompetitionId";
            String scorerSeasonPlayerId = "scorerSeasonPlayerId";
            String playerName = "playerName";

            String query = "SELECT " + Scorer.TABLE_NAME + "." + Scorer.COLUMN_NAME_ID + " '" + scorerId + "', " +  Scorer.COLUMN_NAME_NUMBER_OF_GOALS + ", " +
                    Scorer.TABLE_NAME + "." + Scorer.COLUMN_NAME_SEASON_COMPETITION_ID + " '" + scorerSeasonCompetitionId + "', " +
                    Scorer.TABLE_NAME + "." + Scorer.COLUMN_NAME_SEASON_COMPETITION_ID + " '" + scorerSeasonPlayerId + "', " +
                    Player.TABLE_NAME + "." + Player.COLUMN_NAME_NAME + " '" + playerName + "' " +
                    " FROM " + Scorer.TABLE_NAME +
                    " JOIN " + SeasonCompetition.TABLE_NAME + " ON " + SeasonCompetition.TABLE_NAME + "." + SeasonCompetition.COLUMN_NAME_ID + " = " + Scorer.COLUMN_NAME_SEASON_COMPETITION_ID +
                    " JOIN " + SeasonPlayer.TABLE_NAME + " ON " + Scorer.COLUMN_NAME_SEASON_PLAYER_ID + " = " + SeasonPlayer.TABLE_NAME + "." + SeasonPlayer.COLUMN_NAME_ID +
                    " JOIN " + Player.TABLE_NAME + " ON " + Player.TABLE_NAME + "." + Player.COLUMN_NAME_ID + " = " + SeasonPlayer.COLUMN_NAME_PLAYER_ID +
                    " WHERE " + SeasonCompetition.COLUMN_NAME_COMPETITION_ID + " = " + competitionId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do{
                    scorers.add(new com.footballmania.database.dbobjects.Scorer(
                       cursor.getInt(cursor.getColumnIndex(scorerId)),
                       cursor.getInt(cursor.getColumnIndex(Scorer.COLUMN_NAME_NUMBER_OF_GOALS)),
                       cursor.getInt(cursor.getColumnIndex(scorerSeasonCompetitionId)),
                       cursor.getInt(cursor.getColumnIndex(scorerSeasonPlayerId)),
                       cursor.getString(cursor.getColumnIndex(playerName))
                    ));
                }while(cursor.moveToNext());
            }

            return scorers;
        }

        public com.footballmania.database.dbobjects.Competition getCompetition(int competitionId) {
            com.footballmania.database.dbobjects.Competition competition = null;

            SQLiteDatabase database = this.getReadableDatabase();

            String area = "areaName";
            String compId = "competitionId";

            String query = "SELECT " + Competition.TABLE_NAME + "." + Competition.COLUMN_NAME_ID + " '" + compId + "' " +", " + Competition.TABLE_NAME + "." + Competition.COLUMN_NAME_NAME + ", " +
                    Competition.COLUMN_NAME_LAST_UPDATE_DATE + ", " + Area.TABLE_NAME + "." + Area.COLUMN_NAME_NAME + " '" + area + "' " +
                    " FROM " + Competition.TABLE_NAME +
                    " JOIN " + SeasonCompetition.TABLE_NAME + " ON " + Competition.TABLE_NAME + "." +  Competition.COLUMN_NAME_ID + " = " + SeasonCompetition.COLUMN_NAME_COMPETITION_ID +
                    " JOIN " + Area.TABLE_NAME + " ON " + Competition.COLUMN_NAME_AREA_ID + " = " + Area.TABLE_NAME + "." + Area.COLUMN_NAME_ID +
                    " WHERE " + Competition.TABLE_NAME + "." + Competition.COLUMN_NAME_ID + " = " + competitionId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                competition = new com.footballmania.database.dbobjects.Competition(
                        cursor.getInt(cursor.getColumnIndex(compId)),
                        cursor.getString(cursor.getColumnIndex(Competition.COLUMN_NAME_NAME)),
                        cursor.getLong(cursor.getColumnIndex(Competition.COLUMN_NAME_LAST_UPDATE_DATE)),
                        cursor.getString(cursor.getColumnIndex(area))
                );
            }

            return competition;
        }

        public void updateUserDownloadedData(String login) {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(User.COLUMN_NAME_LAST_UPDATE_DATE, true);

            database.update(User.TABLE_NAME, values,User.COLUMN_NAME_LOGIN + " = ?", new String[]{login});
            database.close();
        }

        public void printDatabaseState(){
            int competitions, seasons, seasonCompetitions, teams, players, seasonPlayers,
                    matches, standings, teamStandings, scorers;

            SQLiteDatabase database = this.getReadableDatabase();
            String query = "SELECT COUNT(*), (SELECT COUNT(*)  FROM " + SeasonCompetition.TABLE_NAME +
                    "), (SELECT COUNT(*)  FROM " + Season.TABLE_NAME + "), (SELECT COUNT(*)  FROM " + Team.TABLE_NAME +
                    "), (SELECT COUNT(*)  FROM " + Player.TABLE_NAME + "), (SELECT COUNT(*)  FROM " + SeasonPlayer.TABLE_NAME +
                    "), (SELECT COUNT(*)  FROM " + Standing.TABLE_NAME + "), (SELECT COUNT(*)  FROM " + TeamStanding.TABLE_NAME +
                    "), (SELECT COUNT(*)  FROM " + Scorer.TABLE_NAME + "), (SELECT COUNT(*)  FROM " + Match.TABLE_NAME +
                    ") FROM " + Competition.TABLE_NAME;


            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                competitions = cursor.getInt(0);
                seasons = cursor.getInt(2);
                seasonCompetitions = cursor.getInt(1);
                players = cursor.getInt(4);
                seasonPlayers = cursor.getInt(5);
                standings = cursor.getInt(6);
                teamStandings = cursor.getInt(7);
                scorers = cursor.getInt(8);
                matches = cursor.getInt(9);
                teams = cursor.getInt(3);

                System.out.println("competitions: " + competitions);
                System.out.println("seasons: " + seasons);
                System.out.println("seasonCompetitions: " + seasonCompetitions);
                System.out.println("teams: " + teams);
                System.out.println("players: " + players);
                System.out.println("seasonPlayers: " + seasonPlayers);
                System.out.println("standings: " + standings);
                System.out.println("teamStandings: " + teamStandings);
                System.out.println("scorers: " + scorers);
                System.out.println("matches: " + matches);
            }
        }

        public com.footballmania.database.dbobjects.Competition getSeasonCompetitionParent(int seasonCompetitionId) {
            com.footballmania.database.dbobjects.Competition competition = null;

            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT C.id, C.name, C.lastUpdated, A.name area " +
                            "FROM SeasonCompetition S " +
                            "JOIN Competition C ON S.idCompetition = C.id " +
                            "JOIN Area A ON C.idArea = A.id " +
                            "WHERE S.id = " + seasonCompetitionId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                competition = new com.footballmania.database.dbobjects.Competition(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                        cursor.getString(cursor.getColumnIndex("area"))
                );
            }

            return competition;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getMatchList(){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * " +
                            "FROM [Match]";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getTeamMatchList(int teamId){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * " +
                            "FROM [Match] " +
                            "WHERE idTeamHome = " + teamId + " OR " + "idTeamAway = " + teamId + " " +
                            "ORDER BY utcDate DESC";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getMatchListPast(){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM [Match] WHERE idStatus = 1 OR idStatus = 8 ORDER BY utcDate DESC";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getMatchListLive(){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM [Match] WHERE idStatus = 3 OR idStatus = 4 OR idStatus = 5 OR idStatus = 7 ORDER BY utcDate DESC";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getMatchListUpcoming(){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM [Match] WHERE idStatus = 2 OR idStatus = 6 ORDER BY utcDate DESC";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getTeamMatchListPast(int teamId){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM [Match] WHERE (idTeamHome = " + teamId + " OR idTeamAway = " + teamId + ") AND (idStatus = 1 OR idStatus = 8) ORDER BY utcDate DESC";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getTeamMatchListLive(int teamId){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM [Match] WHERE (idStatus = 3 OR idStatus = 4 OR idStatus = 5 OR idStatus = 7) AND (idTeamHome = " + teamId + " OR idTeamAway = " + teamId + ") ORDER BY utcDate DESC";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public ArrayList<com.footballmania.database.dbobjects.Match> getTeamMatchListUpcoming(int teamId){
            ArrayList<com.footballmania.database.dbobjects.Match> matches = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM [Match] WHERE (idStatus = 2 OR idStatus = 6) AND (idTeamHome = " + teamId + " OR idTeamAway = " + teamId + ") ORDER BY utcDate DESC";

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){

                do {
                    matches.add(new com.footballmania.database.dbobjects.Match(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getLong(cursor.getColumnIndex("utcDate")),
                            cursor.getInt(cursor.getColumnIndex("matchDay")),
                            cursor.getLong(cursor.getColumnIndex("lastUpdated")),
                            cursor.getInt(cursor.getColumnIndex("idStatus")),
                            cursor.getInt(cursor.getColumnIndex("idSeasonCompetition")),
                            cursor.getInt(cursor.getColumnIndex("idTeamHome")),
                            cursor.getInt(cursor.getColumnIndex("idTeamAway")),
                            cursor.getInt(cursor.getColumnIndex("goalsHome")),
                            cursor.getInt(cursor.getColumnIndex("goalsAway"))
                    ));
                }while(cursor.moveToNext());
            }
            return matches;
        }

        public com.footballmania.database.dbobjects.Team getTeam(int teamId){
            com.footballmania.database.dbobjects.Team team = null;
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT T.id, T.name, T.tla, T.website, A.name area, T.lastUpdated " +
                            "FROM Team T " +
                            "JOIN Area A ON T.idArea = A.id " +
                            "WHERE T.id = " + teamId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do {
                    team = new com.footballmania.database.dbobjects.Team(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5)
                    );
                }while(cursor.moveToNext());
            }
            return team;
        }

        public com.footballmania.database.dbobjects.Article getArticle(int articleId){
            com.footballmania.database.dbobjects.Article article = null;
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT A.title, A.dateOfPublication, A.content, J.firstName, J.lastName,A.category, A.tags FROM Article A JOIN Journalist J ON A.idJournalist = J.id WHERE A.id = " + articleId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do {
                    article = new com.footballmania.database.dbobjects.Article(
                            cursor.getString(0),
                            cursor.getLong(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6)
                    );
                }while(cursor.moveToNext());
            }
            return article;
        }

        public com.footballmania.database.dbobjects.User updateUser(JSONObject userJson) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(User.COLUMN_NAME_FIRST_NAME, userJson.getString(User.COLUMN_NAME_FIRST_NAME));
            values.put(User.COLUMN_NAME_PASSWORD, userJson.getString(User.COLUMN_NAME_PASSWORD));
            values.put(User.COLUMN_NAME_EMAIL, userJson.getString(User.COLUMN_NAME_EMAIL));

            database.update(User.TABLE_NAME, values,User.COLUMN_NAME_LOGIN + " = ?", new String[]{userJson.getString(User.COLUMN_NAME_LOGIN)});
            database.close();

            return getUser(userJson.getString(User.COLUMN_NAME_LOGIN));

        }
        
        public ArrayList<com.footballmania.database.dbobjects.Player> getTeamPlayers(int teamId){
            ArrayList<com.footballmania.database.dbobjects.Player> players = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "select P.name, A.name, P.idPosition from Player P join SeasonPlayer S on P.id = S.idPlayer join Team T on S.idTeam = T.id join Area A on P.idArea = A.id where T.id = " + teamId;

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do {
                    players.add(new com.footballmania.database.dbobjects.Player(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getInt(2)
                    ));
                }while(cursor.moveToNext());
            }
            return players;
        }

        public void insertFavourite(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Favourites.COLUMN_NAME_ID, json.getInt(Favourites.COLUMN_NAME_ID));
            values.put(Favourites.COLUMN_NAME_USER_ID, json.getInt(Favourites.COLUMN_NAME_USER_ID));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(Favourites.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertFavouriteTeam(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(FavouriteTeam.COLUMN_NAME_FAVOURITES_ID, json.getInt(FavouriteTeam.COLUMN_NAME_FAVOURITES_ID));
            values.put(FavouriteTeam.COLUMN_NAME_TEAM_ID, json.getInt(FavouriteTeam.COLUMN_NAME_TEAM_ID));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(FavouriteTeam.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertFavouriteCompetition(JSONObject json) throws JSONException {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(FavouriteCompetition.COLUMN_NAME_FAVOURITES_ID, json.getInt(FavouriteCompetition.COLUMN_NAME_FAVOURITES_ID));
            values.put(FavouriteCompetition.COLUMN_NAME_COMPETITION_ID, json.getInt(FavouriteCompetition.COLUMN_NAME_COMPETITION_ID));

            database.execSQL(PRAGMA_UTF8_ENCODING);
            database.insertWithOnConflict(FavouriteCompetition.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            database.close();
        }

        public void insertFavourites(JSONArray favourites) throws JSONException {
            for (int i = 0; i < favourites.length(); i++)
                insertFavourite(favourites.getJSONObject(i));

        }

        public ArrayList<com.footballmania.database.dbobjects.Competition> getFavouriteCompetitions(){
            ArrayList<com.footballmania.database.dbobjects.Competition> competitions = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT C.id, C.name FROM FavouriteCompetition FC JOIN Favourites F ON FC.idFavourites = F.id JOIN Competition C ON FC.idCompetition = C.id WHERE F.idUser = " + TomcatConnection.loggedUser.getId();

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do {
                    competitions.add(new com.footballmania.database.dbobjects.Competition(
                            cursor.getInt(0),
                            cursor.getString(1)
                    ));
                }while(cursor.moveToNext());
            }
            return competitions;
        }

        public ArrayList<com.footballmania.database.dbobjects.Team> getFavouriteTeams(){
            ArrayList<com.footballmania.database.dbobjects.Team> teams = new ArrayList<>();
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT T.id, T.name FROM FavouriteTeam FT JOIN Favourites F ON FT.idFavourites = F.id JOIN Team T ON FT.idTeam = T.id WHERE F.idUser = " + TomcatConnection.loggedUser.getId();

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do {
                    teams.add(new com.footballmania.database.dbobjects.Team(
                            cursor.getInt(0),
                            cursor.getString(1)
                    ));
                }while(cursor.moveToNext());
            }
            return teams;
        }

        public void deleteFromFavouriteTeams(int teamId){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "DELETE FROM FavouriteTeam WHERE idFavourites = (SELECT id FROM Favourites WHERE idUser = " + TomcatConnection.loggedUser.getId() + ") AND idTeam = " + teamId;

            database.execSQL(query);
        }

        public void deleteFromFavouriteCompetitions(int competitionId){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "DELETE FROM FavouriteCompetition WHERE idFavourites = (SELECT id FROM Favourites WHERE idUser = " + TomcatConnection.loggedUser.getId() + ") AND idCompetition = " + competitionId;

            database.execSQL(query);
        }

        public void insertFavouriteTeam(int teamId){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "INSERT INTO FavouriteTeam VALUES ((SELECT id FROM Favourites WHERE idUser = " + TomcatConnection.loggedUser.getId() + ")," + teamId + ")";

            database.execSQL(query);
        }

        public void insertFavouriteCompetition(int competitionId){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "INSERT INTO FavouriteCompetition VALUES ((SELECT id FROM Favourites WHERE idUser = " + TomcatConnection.loggedUser.getId() + ")," + competitionId + ")";

            database.execSQL(query);
        }

        public boolean isFavouriteTeam(int teamId){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT COUNT(*) FROM FavouriteTeam FT JOIN Favourites F ON FT.idFavourites = F.id WHERE F.idUser = " + TomcatConnection.loggedUser.getId() + " AND FT.idTeam = " + teamId;

            Cursor cursor = database.rawQuery(query, null);

            int count=0;
            if(cursor.moveToFirst()) count = cursor.getInt(0);

            if(count==0) return false;
            return true;
        }

        public boolean isFavouriteCompetition(int compId){
            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT COUNT(*) FROM FavouriteCompetition FC JOIN Favourites F ON FC.idFavourites = F.id WHERE F.idUser = " + TomcatConnection.loggedUser.getId() + " AND FC.idCompetition = " + compId;

            Cursor cursor = database.rawQuery(query, null);

            int count=0;
            if(cursor.moveToFirst()) count = cursor.getInt(0);

            if(count==0) return false;
            return true;
        }

        public com.footballmania.database.dbobjects.User updateUserDownloadDate(String login) {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(User.COLUMN_NAME_LAST_UPDATE_DATE, new Date().getTime());

            database.update(User.TABLE_NAME, values,User.COLUMN_NAME_LOGIN + " = ?", new String[]{login});
            database.close();

            return getUser(login);
        }

        public com.footballmania.database.dbobjects.User updateUserDownloadArticleDate(String login) {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(User.COLUMN_NAME_LAST_ARTICLE_UPDATE_DATE, new Date().getTime());

            database.update(User.TABLE_NAME, values,User.COLUMN_NAME_LOGIN + " = ?", new String[]{login});
            database.close();

            return getUser(login);
        }
    }

}
