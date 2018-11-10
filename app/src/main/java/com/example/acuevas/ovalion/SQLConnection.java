package com.example.acuevas.ovalion;

import android.os.StrictMode;

import com.example.acuevas.ovalion.domain.Battle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SQLConnection {
    private static String ip = "10.109.253.139";
    private static String port = "3306";
    private static String classs = "com.mysql.jdbc.Driver";
    private static String db = "ovalion";
    private static String userName = "ben";
    private static String password = "camion";
    private Connection instance;

    public SQLConnection() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        if (instance == null || instance.isClosed()) {
            instance = null;
            String ConnURL;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Class.forName(classs).newInstance();
                ConnURL = "jdbc:mysql://" + ip + ":" + port + "/"
                        + db;
                instance = DriverManager.getConnection(ConnURL, userName, password);
                System.out.println("test");
            } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws SQLException {
        instance.close();
    }

    public boolean isValidUser(String mail, String password) throws SQLException {
        String sql = "Select count(*) as count from USER where mail=? and password=?";
        PreparedStatement statement = instance.prepareStatement(sql);
        statement.setString(1, mail);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count != 0;
    }

    public ResultSet getBookingByUserIdAndMatchId(int userId, int battleId) throws SQLException {
        String sql = "Select * from booking where userId=? and battleId=?";
        PreparedStatement statement = instance.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, battleId);

        return statement.executeQuery();
    }

    public ArrayList<String> getAllTeams() throws SQLException {
        String sql = "Select name from team order by name";
        PreparedStatement statement = instance.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<String> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(resultSet.getString(1));
        }
        results.add("All teams");
        return results;
    }

    public ArrayList<String> getAllTypeTicket() throws SQLException {
        String sql = "Select name from type where shortType in 'btl' order by name";
        PreparedStatement statement = instance.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<String> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(resultSet.getString(1));
        }
        return results;
    }

    public ArrayList<String> getAllTypeBusTrip() throws SQLException {
        String sql = "Select name from type where shortType in 'bus' order by name";
        PreparedStatement statement = instance.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<String> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(resultSet.getString(1));
        }
        return results;
    }

    public ArrayList<Battle> getAllBookingForUser(String userMail) throws SQLException {
        String sql = "Select b.ID, b.dateBattle, b.teamHome, b.teamVisitors, b.results, t.location from battle b, user u, booking bo, team t where u.mail=? and U.ID=bo.personID and b.ID=bo.battleID and t.ID=b.teamHome";
        PreparedStatement statement = instance.prepareStatement(sql);
        statement.setString(1, userMail);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Battle> battles = new ArrayList<>();
        while (resultSet.next()) {
            battles.add(new Battle(resultSet.getInt(1), resultSet.getDate(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
        }
        return battles;
    }

    public ArrayList<Battle> getAllBattleByTeam(Integer ID) throws SQLException {
        String sql = "Select b.ID, b.dateBattle, b.teamHome, b.teamVisitors, b.results, t.location from battle b, team t where (b.teamHome=? or b.teamVisitors=?) and t.ID=b.teamHome";
        PreparedStatement statement = instance.prepareStatement(sql);
        statement.setInt(1, ID);
        statement.setInt(2, ID);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Battle> battles = new ArrayList<>();
        while (resultSet.next()) {
            battles.add(new Battle(resultSet.getInt(1), resultSet.getDate(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
        }
        return battles;
    }

    public String getNameTeamByID(int ID) throws SQLException {
        String sql = "Select name from team where ID=?";
        PreparedStatement statement = instance.prepareStatement(sql);
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.getString(1);
    }

    public int getIDTeamByName(String name) throws SQLException {
        String sql = "Select ID from team where name=?";
        PreparedStatement statement = instance.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.getInt(1);
    }
}