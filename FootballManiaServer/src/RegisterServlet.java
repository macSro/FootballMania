import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    public static final String JSON_LOGGED_USER = "loggedUser";



    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try{

            response.setCharacterEncoding("UTF-8");
            System.out.println("RegisterServlet\n");
            out = response.getWriter();

            DBConnection dbConnection = new DBConnection(ServerUtils.DB_USER_PASSWORD, ServerUtils.DB_USER_LOGIN, ServerUtils.DB_URL);
            dbConnection.connectDB();

            JSONObject responseJSON = new JSONObject();
            JSONObject userJson;

            JSONArray favourites;

            ServerUtils.User user = null;

            int errorCode;
            String errorMessage = null;

            String userLogin = request.getParameter(ServerUtils.USER_LOGIN);
            String userPassword = request.getParameter(ServerUtils.USER_PASSWORD);
            String userEmail = request.getParameter(ServerUtils.USER_EMAIL);
            String userFirstName = request.getParameter(ServerUtils.USER_FIRST_NAME);

            System.out.println("login: " + userLogin + "\tpassword: " + userPassword + "\temail: " + userEmail + "\tfirstName: " + userFirstName);

            userJson = dbConnection.getUser(userLogin);
            if(userJson == null){
                errorCode = ServerUtils.ERROR_CODE_POSITIVE;
                dbConnection.insertUser(userLogin, userPassword, userEmail, userFirstName);

            } else {
                errorCode = ServerUtils.ERROR_CODE_NEGATIVE;
                errorMessage = ServerUtils.ERROR_MESSAGE_USER_EXISTS;
                favourites = dbConnection.getFavourites();
                responseJSON.put(ServerUtils.ARRAY_KEY_FAVOURITES, favourites);
            }

            responseJSON.put(ServerUtils.ERROR_CODE, errorCode);
            if(errorCode != ServerUtils.ERROR_CODE_POSITIVE)
                responseJSON.put(ServerUtils.SERVER_ERROR_MESSAGE, errorMessage);

            System.out.println(responseJSON);

            out.print(responseJSON);
            out.flush();
            out.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
