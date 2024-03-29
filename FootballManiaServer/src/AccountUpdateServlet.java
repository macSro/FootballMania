import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountUpdateServlet extends HttpServlet {

    public static final String JSON_LOGGED_USER = "loggedUser";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try{


            response.setCharacterEncoding("UTF-8");
            System.out.println("AccountUpdateServlet\n");
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

            String newName = request.getParameter(ServerUtils.USER_NEW_NAME);
            String newEmail = request.getParameter(ServerUtils.USER_NEW_EMAIL);
            String newPassword = request.getParameter(ServerUtils.USER_NEW_PASSWORD);

            userJson = dbConnection.getUser(userLogin);
            if(userJson == null){
                errorCode = ServerUtils.ERROR_CODE_NEGATIVE;
                errorMessage = ServerUtils.ERROR_MESSAGE_WRONG_LOGIN;
            } else {
                user = dbConnection.updateUser(userLogin, newName, newPassword, newEmail);
                errorCode = ServerUtils.ERROR_CODE_POSITIVE;
            }

            responseJSON.put(ServerUtils.ERROR_CODE, errorCode);
            if(errorCode != ServerUtils.ERROR_CODE_POSITIVE)
                responseJSON.put(ServerUtils.SERVER_ERROR_MESSAGE, errorMessage);
            else{
                JSONObject loggedUserJSON = new JSONObject();
                loggedUserJSON.put(DatabaseContract.User.COLUMN_NAME_ID, user.getId());
                loggedUserJSON.put(DatabaseContract.User.COLUMN_NAME_LOGIN, user.getLogin());
                loggedUserJSON.put(DatabaseContract.User.COLUMN_NAME_PASSWORD, newPassword);
                loggedUserJSON.put(DatabaseContract.User.COLUMN_NAME_FIRST_NAME, user.getName());
                loggedUserJSON.put(DatabaseContract.User.COLUMN_NAME_EMAIL, user.getEmail());
                responseJSON.put(JSON_LOGGED_USER, loggedUserJSON);
            }

            System.out.println(responseJSON);

            out.print(responseJSON);
            out.flush();
            out.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
