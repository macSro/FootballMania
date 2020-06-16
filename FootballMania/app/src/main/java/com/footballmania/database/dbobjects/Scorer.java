package com.footballmania.database.dbobjects;

public class Scorer {

    private int id;
    private int numberOfGoals;
    private int idSeasonCompetition;
    private int idSeasonPlayer;
    private String seasonPlayerName;

    public Scorer(int id, int numberOfGoals, int idSeasonCompetition, int idSeasonPlayer, String seasonPlayerName){
        setId(id);
        setNumberOfGoals(numberOfGoals);
        setIdSeasonCompetition(idSeasonCompetition);
        setIdSeasonPlayer(idSeasonPlayer);
        setSeasonPlayerName(seasonPlayerName);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfGoals() {
        return numberOfGoals;
    }

    public void setNumberOfGoals(int numberOfGoals) {
        this.numberOfGoals = numberOfGoals;
    }

    public int getIdSeasonCompetition() {
        return idSeasonCompetition;
    }

    public void setIdSeasonCompetition(int idSeasonCompetition) {
        this.idSeasonCompetition = idSeasonCompetition;
    }

    public int getIdSeasonPlayer() {
        return idSeasonPlayer;
    }

    public void setIdSeasonPlayer(int idSeasonPlayer) {
        this.idSeasonPlayer = idSeasonPlayer;
    }

    public String getSeasonPlayerName() {
        return seasonPlayerName;
    }

    public void setSeasonPlayerName(String seasonPlayerName) {
        this.seasonPlayerName = seasonPlayerName;
    }
}
