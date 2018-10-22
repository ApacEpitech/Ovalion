package com.example.acuevas.ovalion;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLConnection {
    private static final String LOG = "DEBUG";
    private static String ip = "192.168.3.85";
    private static String port = "1433";
    private static String classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String db = "THTData";
    private static String userName = "sa";
    private static String password = "admin";
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
                Class.forName(classs);
                ConnURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";"
                        + "databaseName=" + db + ";user=" + userName + ";password="
                        + password + ";";
                instance = DriverManager.getConnection(ConnURL);
            } catch (SQLException | ClassNotFoundException e) {
                Log.d(LOG, e.getMessage());
            }
        }
    }

    public void close() throws SQLException {
        instance.close();
    }

    public boolean isValidUser(String mail, String password)  throws SQLException  {
        String sql = "Select count(*) as count from USERS where mail=? and password=?";
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

}