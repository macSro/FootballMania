package com.footballmania.ui.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.footballmania.MainActivity;
import com.footballmania.R;
import com.footballmania.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2000;

    private Animation topAnim, bottomAnim;
    private ImageView image;
    private TextView appName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Context context;
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        image = findViewById(R.id.splashArtImageView);
        appName = findViewById(R.id.splashArtText);

        image.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                Pair pair = new Pair<View,String>(image, "logo");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);

    }


}
