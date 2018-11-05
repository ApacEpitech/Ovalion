package com.example.acuevas.ovalion;

import android.os.StrictMode;

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

}