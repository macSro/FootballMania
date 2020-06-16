package com.footballmania.database.dbobjects;

import androidx.annotation.NonNull;

public class TeamStanding {

    private int idStanding;
    private String team;
    private int position;
    private int playedGames;
    private int won;
    private int draw;
    private int lost;
    private int points;
    private int goalsFor;
    private int goalsAgainst;

    public TeamStanding( int idStanding, String team, int position, int playedGames, int won,
                        int draw, int lost, int points, int goalsFor, int goalsAgainst){

        setIdStanding(idStanding);
        setTeam(team);
        setPosition(position);
        setPlayedGames(playedGames);
        setWon(won);
        setDraw(draw);
        setLost(lost);
        setPoints(points);
        setGoalsFor(goalsFor);
        setGoalsAgainst(goalsAgainst);
    }


    public int getIdStanding() {
        return idStanding;
    }

    public void setIdStanding(int idStanding) {
        this.idStanding = idStanding;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    @NonNull
    @Override
    public String toString() {
        return "position: " + getPosition() + "\tteam: " + getTeam() + "\tpoints: " + getPoints();
    }
}
