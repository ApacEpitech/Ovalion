package com.example.acuevas.ovalion.domain;

import java.util.Date;

public class Battle {

    private int id;

    private Date dateBattle;

    private int teamHome;

    private int teamVisitors;

    private String result;

    private String location;

    public Battle(int id, Date dateBattle, int teamHome, int teamVisitors, String result, String location) {
        this.id = id;
        this.dateBattle = dateBattle;
        this.teamHome = teamHome;
        this.teamVisitors = teamVisitors;
        this.result = result;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public Date getDateBattle() {
        return dateBattle;
    }

    public int getTeamHome() {
        return teamHome;
    }

    public int getTeamVisitors() {
        return teamVisitors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateBattle(Date dateBattle) {
        this.dateBattle = dateBattle;
    }

    public void setTeamHome(int teamHome) {
        this.teamHome = teamHome;
    }

    public void setTeamVisitors(int teamVisitors) {
        this.teamVisitors = teamVisitors;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Battle{" +
                "id=" + id +
                ", dateBattle=" + dateBattle +
                ", teamHome=" + teamHome +
                ", teamVisitors=" + teamVisitors +
                '}';
    }
}
