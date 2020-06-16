import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ArticlesDownloadServlet extends HttpServlet {

    private static final String ARRAY_KEY_ARTICLES = "articles";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            System.out.println("ArticlesDownloadServlet\n");
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
            if (userJson == null) {
                errorCode = 1;
                errorMessage = ServerUtils.ERROR_MESSAGE_WRONG_LOGIN;
            } else {
                user = new ServerUtils.User(userJson);
                if (Encryption.createMD5(userPassword).equals(user.getPassword())) {
                    user.setPassword(userPassword);
                    errorCode = 0;
                } else {
                    errorCode = 1;
                    errorMessage = ServerUtils.ERROR_MESSAGE_WRONG_PASSWORD;
                }

            }

            responseJSON.put(ServerUtils.ERROR_CODE, errorCode);
            if (errorCode != 0)
                responseJSON.put(ServerUtils.SERVER_ERROR_MESSAGE, errorMessage);
            else {
                JSONArray articlesArray;
                System.out.println("Getting articles. . .");
                long dateTimeStamp = Long.parseLong(request.getParameter(ServerUtils.PARAM_KEY_ARTICLES_LAST_UPDATE_DATE));
                Date mobileLastUpdatedArticlesDate = new Date(dateTimeStamp);
                articlesArray = dbConnection.getNewArticles(mobileLastUpdatedArticlesDate);
                responseJSON.put(ARRAY_KEY_ARTICLES, articlesArray);
            }

            System.out.println(responseJSON);

            out.print(responseJSON);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
