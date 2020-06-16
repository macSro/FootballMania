package com.footballmania.ui.rules;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.footballmania.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class RulesActivity extends AppCompatActivity {

    public static final String ARG_RULES_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rules);
        Bundle extras = getIntent().getExtras();
        String type = extras.getString(ARG_RULES_TYPE);

        TextView title = (TextView) findViewById(R.id.rulesTitle);
        TextView text1 = (TextView) findViewById(R.id.rulesText1);
        ImageView image1 = (ImageView) findViewById(R.id.rulesImage);
        TextView text2 = (TextView) findViewById(R.id.rulesText2);
        ImageView image2= (ImageView) findViewById(R.id.rulesImage2);
        TextView text3 = (TextView) findViewById(R.id.rulesText3);

        switch (type){
            case "general":
                title.setText(R.string.title_general);
                text1.setText(R.string.general1);
                image1.setImageResource(R.drawable.general_image_1);
                text2.setText(R.string.general2);
                image2.setImageResource(R.drawable.general_image_2);
                text3.setText(R.string.general3);
                break;
            case "offside":
                title.setText(R.string.title_offside);
                text1.setText(R.string.offside1);
                image1.setImageResource(R.drawable.offside_image_1);
                text2.setText(R.string.offside2);
                image2.setImageResource(R.drawable.offside_image_2);
                text3.setText(R.string.offside3);
                break;
            case "fouls":
                title.setText(R.string.title_fouls);
                text1.setText(R.string.fouls1);
                image1.setImageResource(R.drawable.fouls_image_1);
                text2.setText(R.string.fouls2);
                image2.setImageResource(R.drawable.fouls_image_2);
                text3.setText(R.string.fouls3);
                break;
            case "financial":
                title.setText(R.string.title_financial);
                text1.setText(R.string.financial1);
                image1.setImageResource(R.drawable.financial_image_1);
                text2.setText(R.string.financial2);
                image2.setImageResource(R.drawable.financial_image_2);
                text3.setText(R.string.financial3);
                break;
            case "history":
                title.setText(R.string.title_history);
                text1.setText(R.string.history1);
                image1.setImageResource(R.drawable.history_image_1);
                text2.setText(R.string.history2);
                image2.setImageResource(R.drawable.history_image_2);
                text3.setText(R.string.history3);
                break;

        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            text1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            text2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            text3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

    }

}
