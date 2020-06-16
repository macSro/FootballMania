public class DatabaseContract {
    public static class User   {

        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FIRST_NAME = "firstName";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_LOGIN+","+COLUMN_NAME_PASSWORD+","+COLUMN_NAME_EMAIL+","+COLUMN_NAME_FIRST_NAME;
        }
    }

    public static class Journalist  {

        public static final String TABLE_NAME = "Journalist";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_FIRST_NAME = "firstName";
        public static final String COLUMN_NAME_LAST_NAME = "lastName";
        public static final String COLUMN_NAME_EMAIL = "email";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_FIRST_NAME+","+COLUMN_NAME_LAST_NAME+","+COLUMN_NAME_EMAIL;
        }
    }

    public static class Article   {

        public static final String TABLE_NAME = "Article";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE_OF_PUBLICATION = "dateOfPublication";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_JOURNALIST_ID = "idJournalist";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_TAGS = "tags";


        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_TITLE+","+COLUMN_NAME_DATE_OF_PUBLICATION+","+COLUMN_NAME_CONTENT+","+COLUMN_NAME_JOURNALIST_ID;
        }
    }

    public static class Comment    {

        public static final String TABLE_NAME = "Comment";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE_OF_PUBLICATION = "dateOfPublication";
        public static final String COLUMN_NAME_USER_ID = "idUser";
        public static final String COLUMN_NAME_ARTICLE_ID = "idArticle";
        private static final String CONSTRAINT_FOREIGN_KEY_USER = "fk_id_user";
        private static final String CONSTRAINT_FOREIGN_KEY_ARTICLE = "fk_id_article";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_CONTENT+","+COLUMN_NAME_DATE_OF_PUBLICATION+","+COLUMN_NAME_USER_ID+","+COLUMN_NAME_ARTICLE_ID+","+CONSTRAINT_FOREIGN_KEY_USER+","+CONSTRAINT_FOREIGN_KEY_ARTICLE;
        }

    }

    public static class Favourites   {

        public static final String TABLE_NAME = "Favourites";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USER_ID = "idUser";

        @Override
        public String toString() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_USER_ID;
        }
    }

    public static class Area   {

        public static final String TABLE_NAME = "Area";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PARENT_AREA_ID = "idParentArea";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_NAME+","+COLUMN_NAME_PARENT_AREA_ID;
        }
    }

    public static class Team  {

        public static final String TABLE_NAME = "Team";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TLA = "tla";
        public static final String COLUMN_NAME_WEBSITE = "website";
        public static final String COLUMN_NAME_AREA_ID = "idArea";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdated";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_NAME+","+COLUMN_NAME_TLA+","+COLUMN_NAME_WEBSITE+","+COLUMN_NAME_AREA_ID+","+COLUMN_NAME_LAST_UPDATE_DATE;
        }
    }

    public static class Player {

        public static final String TABLE_NAME = "Player";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATE_OF_BIRTH = "dateOfBirth";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdated";
        public static final String COLUMN_NAME_AREA_ID = "idArea";
        public static final String COLUMN_NAME_POSITION = "idPosition";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_NAME+","+ COLUMN_NAME_DATE_OF_BIRTH +","+COLUMN_NAME_LAST_UPDATE_DATE+","+COLUMN_NAME_AREA_ID+","+COLUMN_NAME_POSITION;
        }

    }

    public static class Competition   {

        public static final String TABLE_NAME = "Competition";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LAST_UPDATE_DATE = "lastUpdated";
        public static final String COLUMN_NAME_AREA_ID = "idArea";


        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_NAME+","+COLUMN_NAME_LAST_UPDATE_DATE+","+COLUMN_NAME_AREA_ID;
        }
    }


    public static class FavouriteCompetition   {

        public static final String TABLE_NAME = "FavouriteCompetition";
        public static final String COLUMN_NAME_FAVOURITES_ID = "idFavourites";
        public static final String COLUMN_NAME_COMPETITION_ID = "idCompetition";

        public static String tableColumns() {
            return COLUMN_NAME_FAVOURITES_ID+","+COLUMN_NAME_COMPETITION_ID;
        }

    }

    public static class FavouriteTeam   {

        public static final String TABLE_NAME = "FavouriteTeam";
        public static final String COLUMN_NAME_FAVOURITES_ID = "idFavourites";
        public static final String COLUMN_NAME_TEAM_ID = "idTeam";

        public static String tableColumns() {
            return COLUMN_NAME_FAVOURITES_ID+","+COLUMN_NAME_TEAM_ID;
        }

    }

    public static class FavouritePlayer   {

        public static final String TABLE_NAME = "FavouritePlayer";
        public static final String COLUMN_NAME_FAVOURITES_ID = "idFavourites";
        public static final String COLUMN_NAME_PLAYER_ID = "idPlayer";

        public static String tableColumns() {
            return COLUMN_NAME_FAVOURITES_ID+","+COLUMN_NAME_PLAYER_ID;
        }

    }

    public static class Season   {

        public static final String TABLE_NAME = "Season";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_START_DATE = "startDate";
        public static final String COLUMN_NAME_END_DATE = "endDate";
        public static final String COLUMN_NAME_CURRENT_MATCH_DAY = "currentMatchDay";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_START_DATE+","+COLUMN_NAME_END_DATE+","+COLUMN_NAME_CURRENT_MATCH_DAY;
        }

    }

    public static class SeasonCompetition  {

        public static final String TABLE_NAME = "SeasonCompetition";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SEASON_ID = "idSeason";
        public static final String COLUMN_NAME_COMPETITION_ID = "idCompetition";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_SEASON_ID+","+COLUMN_NAME_COMPETITION_ID;
        }

    }

    public static class Standing {

        public static final String TABLE_NAME = "Standing";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_STAGE = "idStage";
        public static final String COLUMN_NAME_SEASON_COMPETITION_ID = "idSeasonCompetition";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_STAGE+","+COLUMN_NAME_SEASON_COMPETITION_ID;
        }

    }


    public static class TeamStanding  {

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

        public static String tableColumns() {
            return COLUMN_NAME_STANDING_ID+","+COLUMN_NAME_TEAM_ID+","+COLUMN_NAME_POSITION+","+COLUMN_NAME_PLAYED_GAMES+","+COLUMN_NAME_WON+","+COLUMN_NAME_DRAW+","+COLUMN_NAME_LOST+","+COLUMN_NAME_POINTS+","+COLUMN_NAME_GOALS_FOR+","+COLUMN_NAME_GOALS_AGAINST;
        }


    }

    public static class Match {

        public static final String TABLE_NAME = "Match";
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

        public static String tableColumns() {
            return COLUMN_NAME_AWAY_TEAM_GOALS+","+COLUMN_NAME_HOME_TEAM_GOALS+","+COLUMN_NAME_ID+","+COLUMN_NAME_UTC_DATE+","+COLUMN_NAME_MATCH_DAY+","+COLUMN_NAME_LAST_UPDATE_DATE+","+COLUMN_NAME_STATUS+","+COLUMN_NAME_SEASON_COMPETITION_ID+","+COLUMN_NAME_HOME_TEAM_ID+","+COLUMN_NAME_AWAY_TEAM_ID;
        }

    }



    public static class SeasonPlayer  {

        public static final String TABLE_NAME = "SeasonPlayer";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_PLAYER_ID = "idPlayer";
        public static final String COLUMN_NAME_TEAM_ID = "idTeam";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_PLAYER_ID+","+COLUMN_NAME_TEAM_ID;
        }
    }

    public static class Scorer  {

        public static final String TABLE_NAME = "Scorer";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NUMBER_OF_GOALS = "numberOfGoals";
        public static final String COLUMN_NAME_SEASON_COMPETITION_ID = "idSeasonCompetition";
        public static final String COLUMN_NAME_SEASON_PLAYER_ID = "idSeasonPlayer";


        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_NUMBER_OF_GOALS+","+COLUMN_NAME_SEASON_COMPETITION_ID+","+COLUMN_NAME_SEASON_PLAYER_ID;
        }
    }


    public static class Goal  {

        public static final String TABLE_NAME = "Goal";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_MINUTE = "minute";
        public static final String COLUMN_NAME_EXTRA_TIME = "extraTime";
        public static final String COLUMN_NAME_TYPE_ID = "idType";
        public static final String COLUMN_NAME_MATCH_ID = "idMatch";
        public static final String COLUMN_NAME_SCORED_ID = "idScored";
        public static final String COLUMN_NAME_ASSIST_ID = "idAssist";

        public static String tableColumns() {
            return COLUMN_NAME_ID+","+COLUMN_NAME_MINUTE+","+COLUMN_NAME_EXTRA_TIME+","+COLUMN_NAME_TYPE_ID+","+COLUMN_NAME_MATCH_ID+","+COLUMN_NAME_SCORED_ID+","+COLUMN_NAME_ASSIST_ID;
        }
    }

    public static class APINames {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String AREA = "area";
        public static final String PARENTAREAID = "parentAreaId";
        public static final String TLA = "tla";
        public static final String WEBSITE = "website";
        public static final String STRATDATE = "startDate";
        public static final String ENDDATE = "endDate";
        public static final String LASTUPDATED = "lastUpdated";
        public static final String CURRENTMATCHDAY = "currentMatchday";
        public static final String MINUTE = "minute";
        public static final String EXTRATIME = "extraTime";
        public static final String SCORER = "scorer";
        public static final String ASSIST = "assist";
        public static final String DATEOFBIRTH = "dateOfBirth";
        public static final String NATIONALITY = "nationality";
        public static final String POSITION = "position";
        public static final String UTCDATE = "utcDate";
        public static final String MATCHDAY = "matchday";
        public static final String STATUS = "status";
        public static final String HOMETEAM = "homeTeam";
        public static final String AWAYTEAM = "awayTeam";
        public static final String NUMBEROFGOALS = "numberOfGoals";
        public static final String STAGE = "stage";
        public static final String SEASON = "season";
        public static final String CURRENTSEASON = "currentSeason";
        public static final String PLAYEDGAMES = "playedGames";
        public static final String WON = "won";
        public static final String DRAW = "draw";
        public static final String LOST = "lost";
        public static final String POINTS = "points";
        public static final String GOALSAGAINST = "goalsAgainst";
        public static final String GOALSFOR = "goalsFor";
        public static final String TEAM = "team";

    }

}
