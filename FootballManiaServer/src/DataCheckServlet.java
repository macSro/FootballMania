import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class DataCheckServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try{


            response.setCharacterEncoding("UTF-8");
            System.out.println("DataCheckServlet\n");
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
                errorCode = ServerUtils.ERROR_CODE_NEGATIVE;
                errorMessage = ServerUtils.ERROR_MESSAGE_WRONG_LOGIN;
            } else {
                user = new ServerUtils.User(userJson);
                if(Encryption.createMD5(userPassword).equals(user.getPassword())){
                    user.setPassword(userPassword);
                    errorCode = ServerUtils.ERROR_CODE_POSITIVE;
                } else{
                    errorCode = ServerUtils.ERROR_CODE_NEGATIVE;
                    errorMessage = ServerUtils.ERROR_MESSAGE_WRONG_PASSWORD;
                }

            }

            responseJSON.put(ServerUtils.ERROR_CODE, errorCode);
            if(errorCode != 0)
                responseJSON.put(ServerUtils.SERVER_ERROR_MESSAGE, errorMessage);
            else{
                int newDataCode = ServerUtils.SERVER_NO_NEW_DATA_CODE;
                String dateString = request.getParameter(ServerUtils.PARAM_KEY_MOBILE_DATA_LAST_UPDATE_DATE);
                if(dbConnection.checkForNewData(dateString))
                    newDataCode = ServerUtils.SERVER_NEW_DATA_CODE;

                responseJSON.put(ServerUtils.NEW_DATA_CODE, newDataCode);
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
