import org.json.JSONObject;

public class ServerUtils {


    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_FIRST_NAME = "user_first_name";

    public static final String USER_NEW_NAME = "user_new_name";
    public static final String USER_NEW_EMAIL = "user_new_email";
    public static final String USER_NEW_PASSWORD = "user_new_password";

    public static final String ERROR_CODE = "error";
    public static final String SERVER_ERROR_MESSAGE = "error_message";
    public static final String ARTICLES_CODE = "articlesCode";
    public static final String NEW_DATA_CODE = "newDataCode";

    public static final String DB_USER_LOGIN = "postgres";
    public static final String DB_USER_PASSWORD = "Passw0rd";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/footballdb";

    public static final String ERROR_MESSAGE_WRONG_LOGIN = "Error, incorrect login";
    public static final String ERROR_MESSAGE_WRONG_PASSWORD = "Error, incorrect password";
    public static final String ERROR_MESSAGE_USER_EXISTS = "User with that login already exists in database";

    public static final String PARAM_KEY_ARTICLES_LAST_UPDATE_DATE = "articlesLastUpdateDate";

    public static final int SERVER_NEW_ARTICLES_CODE = 101;
    public static final int SERVER_NO_NEW_ARTICLES_CODE = 102;
    public static final int SERVER_NEW_DATA_CODE = 101;
    public static final int SERVER_NO_NEW_DATA_CODE = 102;

    public static final int ERROR_CODE_POSITIVE = 0;
    public static final int ERROR_CODE_NEGATIVE = 1;
    public static final String ARRAY_KEY_FAVOURITES = "favourites";
    public static final String PARAM_KEY_MOBILE_DATA_LAST_UPDATE_DATE = "last_update_date";


    public static class User {
        private int id;
        private String login;
        private String password;
        private String name;
        private String email;


        public User(String login, String password){
            this.login = login;
            this.password = password;
        }

        public User(JSONObject jsonUser){
            id = jsonUser.getInt(DatabaseContract.User.COLUMN_NAME_ID);
            login = jsonUser.getString(DatabaseContract.User.COLUMN_NAME_LOGIN);
            password = jsonUser.getString(DatabaseContract.User.COLUMN_NAME_PASSWORD);
            name = jsonUser.getString(DatabaseContract.User.COLUMN_NAME_FIRST_NAME);
            email = jsonUser.getString(DatabaseContract.User.COLUMN_NAME_EMAIL);
        }

        public int getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


}
