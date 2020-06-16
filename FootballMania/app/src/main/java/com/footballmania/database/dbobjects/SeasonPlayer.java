package com.footballmania.database.dbobjects;

public class SeasonPlayer {

    private int id;
    private int idPlayer;
    private int idTeam;
    private String playerName;
    private String teamName;

    public SeasonPlayer(int id, int idPlayer, int idTeam, String playerName, String teamName){
        setId(id);
        setIdPlayer(idPlayer);
        setIdTeam(idTeam);
        setPlayerName(playerName);
        setTeamName(teamName);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
