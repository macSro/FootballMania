package com.footballmania.ui.competitions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.footballmania.R;
import com.footballmania.utils.Utility;
import com.footballmania.database.FootballManiaContract;
import com.footballmania.database.dbobjects.Competition;
import com.footballmania.ui.Adapters.CompetitionFragmentsViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class CompetitionActivity extends AppCompatActivity {

    public static final String ARG_COMPETITION_ID = "id";

    public static Competition currentCompetition;

    TabLayout fragmentsTabLayout;
    ViewPager fragmentsViewPager;
    ImageView competitionLogoImageView;
    ImageView favourite;

    private int lastTabSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_competition);

        final FootballManiaContract.FootballManiaDbHelper dbHelper = FootballManiaContract.getDbHelperInstance(this);

        Bundle extras = getIntent().getExtras();
        final int compId = extras.getInt(ARG_COMPETITION_ID);

        favourite = findViewById(R.id.imageView14);

        boolean isFavourite = dbHelper.isFavouriteCompetition(compId);

        if(isFavourite) favourite.setImageResource(R.drawable.ic_favorite_primary_24dp);
        else favourite.setImageResource(R.drawable.ic_favorite_border_primary_24dp);

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavourite = dbHelper.isFavouriteCompetition(compId);
                if(isFavourite){
                    favourite.setImageResource(R.drawable.ic_favorite_border_primary_24dp);
                    dbHelper.deleteFromFavouriteCompetitions(compId);
                }
                else{
                    favourite.setImageResource(R.drawable.ic_favorite_primary_24dp);
                    dbHelper.insertFavouriteCompetition(compId);
                }
            }
        });

        currentCompetition = dbHelper.getCompetition(compId);

        competitionLogoImageView = findViewById(R.id.competitionLogoImageView);

        String s = Utility.getCompetitionLogoFleName(currentCompetition.getName());
        int resID = getResources().getIdentifier(s, "drawable", getPackageName());
        competitionLogoImageView.setImageResource(resID);

        fragmentsTabLayout = findViewById(R.id.competitionTabLayout);
        fragmentsViewPager = findViewById(R.id.competitionFragmentsViewPager);

        fragmentsTabLayout.addTab(fragmentsTabLayout.newTab().setText(R.string.teams_tab));
        fragmentsTabLayout.addTab(fragmentsTabLayout.newTab().setText(R.string.standings_tab));
        fragmentsTabLayout.addTab(fragmentsTabLayout.newTab().setText(R.string.scorers_tab));
        fragmentsTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        CompetitionFragmentsViewPagerAdapter viewPagerAdapter = new CompetitionFragmentsViewPagerAdapter(getSupportFragmentManager(), fragmentsTabLayout.getTabCount());
        fragmentsViewPager.setAdapter(viewPagerAdapter);
        fragmentsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(fragmentsTabLayout));
        fragmentsTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                lastTabSelected = tab.getPosition();
                fragmentsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentsViewPager.setAdapter(new CompetitionFragmentsViewPagerAdapter(getSupportFragmentManager(), fragmentsTabLayout.getTabCount()));
        fragmentsViewPager.setCurrentItem(lastTabSelected);
    }
}
