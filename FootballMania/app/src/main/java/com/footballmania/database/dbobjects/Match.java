package com.footballmania.database.dbobjects;

public class Match {

    private int id;
    private long utcDate;
    private int matchDay;
    private long lastUpdated;
    private String status;
    private int idSeasonCompetition;
    private int idTeamHome;
    private int idTeamAway;
    private int goalsHome;
    private int goalsAway;

    private static final String STATUS_FINISHED = "FINISHED";
    private static final String STATUS_SCHEDULED = "SCHEDULED";
    private static final String STATUS_LIVE = "LIVE";
    private static final String STATUS_IN_PLAY = "IN PLAY";
    private static final String STATUS_PAUSED = "PAUSED";
    private static final String STATUS_POSTPONED = "POSTPONED";
    private static final String STATUS_SUSPENDED = "SUSPENDED";
    private static final String STATUS_CANCELLED = "CANCELLED";

    public Match(int id, long utcDate, int matchDay, long lastUpdated, int status, int idSeasonCompetition,
                 int idTeamHome, int idTeamAway, int goalsHome, int goalsAway){
        setId(id);
        setUtcDate(utcDate);
        setMatchDay(matchDay);
        setLastUpdated(lastUpdated);
        setIdSeasonCompetition(idSeasonCompetition);
        setIdTeamHome(idTeamHome);
        setIdTeamAway(idTeamAway);
        setGoalsHome(goalsHome);
        setGoalsAway(goalsAway);

        switch (status){
            case 1:
                setStatus(STATUS_FINISHED);
                break;
            case 2:
                setStatus(STATUS_SCHEDULED);
                break;
            case 3:
                setStatus(STATUS_LIVE);
                break;
            case 4:
                setStatus(STATUS_IN_PLAY);
                break;
            case 5:
                setStatus(STATUS_PAUSED);
                break;
            case 6:
                setStatus(STATUS_POSTPONED);
                break;
            case 7:
                setStatus(STATUS_SUSPENDED);
                break;
            case 8:
                setStatus(STATUS_CANCELLED);
                break;
        }

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUtcDate() {
        return utcDate;
    }

    public void setUtcDate(long utcDate) {
        this.utcDate = utcDate;
    }

    public int getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(int matchDay) {
        this.matchDay = matchDay;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdSeasonCompetition() {
        return idSeasonCompetition;
    }

    public void setIdSeasonCompetition(int idSeasonCompetition) {
        this.idSeasonCompetition = idSeasonCompetition;
    }

    public int getIdTeamHome() {
        return idTeamHome;
    }

    public void setIdTeamHome(int idTeamHome) {
        this.idTeamHome = idTeamHome;
    }

    public int getIdTeamAway() {
        return idTeamAway;
    }
    public void setIdTeamAway(int idTeamAway) {
        this.idTeamAway = idTeamAway;
    }

    public int getGoalsHome() {
        return goalsHome;
    }

    public void setGoalsHome(int goalsHome) {
        this.goalsHome = goalsHome;
    }

    public int getGoalsAway() {
        return goalsAway;
    }

    public void setGoalsAway(int goalsAway) {
        this.goalsAway = goalsAway;
    }
}
