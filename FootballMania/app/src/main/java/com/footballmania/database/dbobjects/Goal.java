package com.footballmania.database.dbobjects;

public class Goal {

    private int id;
    private int minute;
    private int extraTime;
    private String type;
    private int idMatch;
    private int idScored;
    private int idAssist;
    private String scoredName;
    private String assistName;

    public static final String GOAL_TYPE_REGULAR = "REGULAR";
    public static final String GOAL_TYPE_OWN = "OWN GOAL";
    public static final String GOAL_TYPE_PENALTY = "PENALTY";

    public Goal(int id, int minute, int extraTime, int type, int idMatch,
                int idScored, int idAssist, String scoredName, String assistName){
        setId(id);
        setMinute(minute);
        setExtraTime(extraTime);
        setIdMatch(idMatch);
        setIdScored(idScored);
        setIdAssist(idAssist);
        setScoredName(scoredName);
        setAssistName(assistName);

        switch(type){
            case 1:
                setType(GOAL_TYPE_REGULAR);
                break;
            case 2:
                setType(GOAL_TYPE_OWN);
                break;
            case 3:
                setType(GOAL_TYPE_PENALTY);
                break;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(int extraTime) {
        this.extraTime = extraTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public int getIdScored() {
        return idScored;
    }

    public void setIdScored(int idScored) {
        this.idScored = idScored;
    }

    public int getIdAssist() {
        return idAssist;
    }

    public void setIdAssist(int idAssist) {
        this.idAssist = idAssist;
    }

    public String getScoredName() {
        return scoredName;
    }

    public void setScoredName(String scoredName) {
        this.scoredName = scoredName;
    }

    public String getAssistName() {
        return assistName;
    }

    public void setAssistName(String assistName) {
        this.assistName = assistName;
    }
}
