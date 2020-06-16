package com.footballmania.ui.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.footballmania.serverConnection.NetworkConnection;
import com.footballmania.serverConnection.TomcatConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String login = sp.getString("login", "");
        boolean logged = sp.getBoolean("logged", false);

        if(logged){
            TomcatConnection.loggedUser = loginDatabase(login);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        final EditText editTextLogin = findViewById(R.id.loginEditText);
        final EditText editTextPassword = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        final TextView registerText = findViewById(R.id.registerLabel);

       loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                User user = loginDatabase(login);

                System.out.println("\n USER: " + user);

                if(user == null) {
                    if (NetworkConnection.networkAvailable(LoginActivity.this))
                        loginServer(login, password);
                    else
                        Toast.makeText(LoginActivity.this, R.string.internet_error, Toast.LENGTH_LONG).show();
                } else {
                    if(user.getPassword().equals(password)){
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("login",login);
                        editor.putBoolean("logged",true);
                        editor.commit();
                        TomcatConnection.loggedUser = user;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.password_error, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public static boolean hasPermissions(Context context, String... permissions){
        if(context != null && permissions != null) {
            for (String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return  true;
    }

    private User loginDatabase(String login) {
        FootballManiaContract.FootballManiaDbHelper dbHelper = FootballManiaContract.getDbHelperInstance(LoginActivity.this);
        return  dbHelper.getUser(login);
    }


    private void loginServer(String login, String password) {
        System.out.println("### loginServer");

        final String loginLocal = login;
        final String passwordLocal = password;

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = TomcatConnection.WEB_SERVICE_URL + TomcatConnection.SERVICE_LOGIN;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("");

                        try {
                            JSONObject responseJson = new JSONObject(response);
                            int error = responseJson.getInt(TomcatConnection.ERROR_CODE);

                            if (error != TomcatConnection.SERVER_ERROR_CODE) {
                                JSONObject loggedUserJSON = responseJson.getJSONObject(TomcatConnection.JSON_LOGGED_USER);
                                JSONArray favourites = responseJson.getJSONArray(TomcatConnection.ARRAY_KEY_FAVOURITES);

                                FootballManiaContract.FootballManiaDbHelper dbHelper =
                                        FootballManiaContract.getDbHelperInstance(LoginActivity.this);

                                TomcatConnection.loggedUser = dbHelper.insertUser(loggedUserJSON);
                                dbHelper.insertFavourites(favourites);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, responseJson.getString(TomcatConnection.SERVER_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch(JSONException e){
                                e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("");
                        System.out.println("## ERROR: that didn't work " + error.getMessage());
                        Toast.makeText(LoginActivity.this, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                    }
                }

        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(TomcatConnection.USER_LOGIN, loginLocal);
                params.put(TomcatConnection.USER_PASSWORD, passwordLocal);
                System.out.println(params);
                return params;
            }
        };
        System.out.println(stringRequest.toString());
        queue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {}
}
