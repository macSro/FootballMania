package com.footballmania;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.footballmania.database.FootballManiaContract;
import com.footballmania.serverConnection.TomcatConnection;
import com.footballmania.ui.articles.ArticlesFragment;
import com.footballmania.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_articles, R.id.nav_competitions, R.id.nav_matches,
                R.id.nav_favourites, R.id.nav_rules, R.id.nav_profile, R.id.nav_info)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        final ProgressDialog progressDialog;

        progressDialog = getDownloadingProgressDialog();


        final FootballManiaContract.FootballManiaDbHelper dbHelper =  FootballManiaContract.getDbHelperInstance(this);

        TomcatConnection.checkForDataUpdate(this, progressDialog);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        TextView username = findViewById(R.id.navMenuUserName);
        username.setText(TomcatConnection.loggedUser.getLogin());

        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){ }

    public void checkForUpdates(MenuItem item) {
        TomcatConnection.checkForDataUpdate(this, getDownloadingProgressDialog());
    }

    public void checkForNewArticles(MenuItem item) {
        TomcatConnection.checkForNewArticles(this, getDownloadingProgressDialog());
    }

    private ProgressDialog getDownloadingProgressDialog(){
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.data_downloading_text));
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public void onAccountUpdateSuccess(){
        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_profile);
        Toast.makeText(this, R.string.account_data_update_success, Toast.LENGTH_SHORT).show();
    }

    public void onDownloadDataFinished() {
        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_articles);
        Toast.makeText(this, TomcatConnection.DATA_DOWNLOAD_SUCCESSFUL, Toast.LENGTH_SHORT).show();
    }
}
