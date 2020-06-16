package com.footballmania.database.dbobjects;

public class Standing {

    private int id;
    private String stage;
    private int idSeasonCompetition;

    public static final String FIRST_QUALIFYING_ROUND = "FIRST QUALIFYING ROUND";
    public static final String SECOND_QUALIFYING_ROUND = "SECOND QUALIFYING ROUND";
    public static final String THIRD_QUALIFYING_ROUND = "THIRD QUALIFYING ROUND";
    public static final String PLAY_OFF_ROUND = "PLAY OFF";
    public static final String GROUP_STAGE = "GROUP STAGE";
    public static final String ROUND_OF_16 = "ROUND OF 16";
    public static final String QUARTER_FINALS = "QUARTER FINALS";
    public static final String SEMI_FINALS = "SEMI FINALS";
    public static final String FINAL = "FINAL";
    public static final String REGULAR_SEASON = "REGULAR SEASON";

    public Standing(int id, int stage, int idSeasonCompetition){
        setId(id);
        setIdSeasonCompetition(idSeasonCompetition);

        switch(stage){
            case 1:
                setStage(FIRST_QUALIFYING_ROUND);
                break;
            case 2:
                setStage(SECOND_QUALIFYING_ROUND);
                break;
            case 3:
                setStage(THIRD_QUALIFYING_ROUND);
                break;
            case 4:
                setStage(PLAY_OFF_ROUND);
                break;
            case 5:
                setStage(GROUP_STAGE);
                break;
            case 6:
                setStage(ROUND_OF_16);
                break;
            case 7:
                setStage(QUARTER_FINALS);
                break;
            case 8:
                setStage(SEMI_FINALS);
                break;
            case 9:
                setStage(FINAL);
                break;
            case 10:
                setStage(REGULAR_SEASON);
                break;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public int getIdSeasonCompetition() {
        return idSeasonCompetition;
    }

    public void setIdSeasonCompetition(int idSeasonCompetition) {
        this.idSeasonCompetition = idSeasonCompetition;
    }
}
