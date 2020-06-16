package com.footballmania.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {

//CONVERTERS

    public static final String LOGO_POSTFIX = "_logo";
    public static final String SERVER_DATABASE_DATE_FORMAT = "dd-MM-yyyy";

    public static String dateLongToString(long dateLong){
        if(dateLong==0) return "-";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateLong);
        return (calendar.get(Calendar.DAY_OF_MONTH)<10?"0":"") + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)<10?"0":"") + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR);
    }

    public static String getDateString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(SERVER_DATABASE_DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static String getTeamLogoFleName(String teamName){
        teamName = teamName.toLowerCase();
        teamName = teamName.replaceAll("[ ]", "_");
        teamName = teamName.replaceAll("[-]", "_");
        teamName = teamName.replaceAll("[']", "_");
        teamName = teamName.replaceAll("[â]", "a");
        teamName = teamName.replaceAll("[ã]", "a");
        teamName = teamName.replaceAll("[á]", "a");
        teamName = teamName.replaceAll("[ê]", "e");
        teamName = teamName.replaceAll("[é]", "e");
        teamName = teamName.replaceAll("[é]", "e");
        teamName = teamName.replaceAll("[ô]", "o");
        teamName = teamName.replaceAll("[ó]", "o");
        teamName = teamName.replaceAll("[ö]", "o");
        teamName = teamName.replaceAll("[ç]", "c");
        teamName = teamName.replaceAll("[í]", "i");
        teamName = teamName.replaceAll("[î]", "i");
        teamName = teamName.replaceAll("[ü]", "u");
        teamName = teamName.replaceAll("[ú]", "u");
        teamName = teamName.replaceAll("[&]", "and");
        teamName = teamName.replaceAll("[.]", "");
        if(teamName.charAt(0) == '1')
            teamName = teamName.replace('1', '_');
        return teamName + LOGO_POSTFIX;
    }

    public static String getCompetitionLogoFleName(String competitionName){
        competitionName = competitionName.replaceAll("[ ]", "_");
        competitionName = competitionName.replaceAll("[â]", "a");
        competitionName = competitionName.replaceAll("[ã]", "a");
        competitionName = competitionName.replaceAll("[á]", "a");
        competitionName = competitionName.replaceAll("[ê]", "e");
        competitionName = competitionName.replaceAll("[é]", "a");
        competitionName = competitionName.replaceAll("[ô]", "o");
        competitionName = competitionName.replaceAll("[ó]", "o");
        competitionName = competitionName.replaceAll("[ç]", "c");
        competitionName = competitionName.replaceAll("[í]", "i");
        return competitionName.toLowerCase() + LOGO_POSTFIX;
    }
}
