package com.example.acuevas.ovalion;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.acuevas.ovalion.domain.Battle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIntent().setAction("created");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_calendar);
        List<Battle> battles = new ArrayList<>();
        try {
            SQLConnection sqlConnection = new SQLConnection();
            ResultSet resultSet = sqlConnection.getBattlesByFavoriteTeamName("allTeams");
            while (resultSet.next()) {
                Battle battle = new Battle(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4)
                );
                battles.add(battle);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        battles = battles.stream().sorted(Comparator.comparing(Battle::getDateBattle))
                .collect(Collectors.toList());
        for (Battle battle : battles) {
            System.out.println(battle.toString());
            //TODO add to listView
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.nav_calendar) {
            intent = new Intent(this, CalendarActivity.class);
        } else if (id == R.id.nav_teams) {
            intent = new Intent(this, TeamsActivity.class);
        } else if (id == R.id.nav_booking) {
            intent = new Intent(this, BookingActivity.class);
        } else if (id == R.id.nav_trips) {
            intent = new Intent(this, TripsActivity.class);
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_calendar);
    }

    @Override
    protected void onResume() {
        String action = getIntent().getAction();
        if (action == null || !action.equals("created")) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
            finish();
        } else {
            getIntent().setAction(null);
        }
        super.onResume();
    }

}
