package com.footballmania.ui.rules;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.footballmania.R;

public class RulesFragment extends Fragment implements View.OnClickListener {


    CardView general, offside, fouls, financial, history;

    public static final String RULES_TYPE_GENERAL = "general";
    public static final String RULES_TYPE_OFFSIDE = "offside";
    public static final String RULES_TYPE_FOULS = "fouls";
    public static final String RULES_TYPE_FINANCIAL = "financial";
    public static final String RULES_TYPE_HISTORY = "history";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_rules, container, false);

        general = root.findViewById(R.id.rulesGeneral);
        offside = root.findViewById(R.id.rulesOffside);
        fouls = root.findViewById(R.id.rulesFouls);
        financial = root.findViewById(R.id.rulesFinancial);
        history = root.findViewById(R.id.rulesHistory);

        general.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RulesActivity.class);
                intent.putExtra(RulesActivity.ARG_RULES_TYPE, "general");
                startActivity(intent);
            }
        });
        offside.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RulesActivity.class);
                intent.putExtra(RulesActivity.ARG_RULES_TYPE, "offside");
                startActivity(intent);
            }
        });
        fouls.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RulesActivity.class);
                intent.putExtra(RulesActivity.ARG_RULES_TYPE, "fouls");
                startActivity(intent);
            }
        });
        financial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RulesActivity.class);
                intent.putExtra(RulesActivity.ARG_RULES_TYPE, "financial");
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RulesActivity.class);
                intent.putExtra(RulesActivity.ARG_RULES_TYPE, "history");
                startActivity(intent);
            }
        });

        return root;
    }

    private void startRulesActivity(String rulesType){
        Intent intent = new Intent(getActivity(), RulesActivity.class);
        intent.putExtra(RulesActivity.ARG_RULES_TYPE, rulesType);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch(viewId){
            case R.id.rulesGeneral:
                startRulesActivity(RULES_TYPE_GENERAL);
                break;

            case R.id.rulesOffside:
                startRulesActivity(RULES_TYPE_OFFSIDE);
                break;

            case R.id.rulesFouls:
                startRulesActivity(RULES_TYPE_FOULS);
                break;

            case R.id.rulesFinancial:
                startRulesActivity(RULES_TYPE_FINANCIAL);
                break;

            case R.id.rulesHistory:
                startRulesActivity(RULES_TYPE_HISTORY);
                break;

        }
    }
}