package com.example.acuevas.ovalion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIntent().setAction("created");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Context context = this.getApplication();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        System.out.println(preferences.getString("email", null));

        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView imageLanguage = findViewById(R.id.languageChanger);
        imageLanguage.setClickable(true);

        SharedPreferences prefs = getBaseContext().getSharedPreferences("general_settings", Context.MODE_PRIVATE);
        String lanSettings = prefs.getString("language", null);
        Resources res = getBaseContext().getResources();
        android.content.res.Configuration conf = res.getConfiguration();

        if (lanSettings == null || lanSettings.equals("en")) {
            conf.setLocale(new Locale("en"));
            imageLanguage.setBackgroundResource(R.drawable.french);
        } else {
            conf.setLocale(new Locale("efr"));
            imageLanguage.setBackgroundResource(R.drawable.uk);
        }

        imageLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getBaseContext().getSharedPreferences("general_settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String lanSettings = prefs.getString("language", null);

                Resources res = getBaseContext().getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                if (lanSettings == null || lanSettings.equals("fr")) {
                    conf.setLocale(new Locale("en"));
                    v.setBackgroundResource(R.drawable.french);
                    editor.putString("language", "en");
                } else {
                    conf.setLocale(new Locale("fr"));
                    v.setBackgroundResource(R.drawable.uk);
                    editor.putString("language", "fr");
                }
                editor.apply();
                res.updateConfiguration(conf, dm);
                finish();
                startActivity(getIntent());
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_settings);

        try {
            SQLConnection connection = new SQLConnection();
            final Spinner teamSpinner = findViewById(R.id.spinner);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, connection.getAllTeams());
            teamSpinner.setAdapter(spinnerArrayAdapter);
            teamSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (teamSpinner.getSelectedItem().toString().equals("All teams") || teamSpinner.getSelectedItem().toString().equals("Toutes les équipes")) {
                        editor.putInt("favoriteTeamID", 0);
                    } else {
                        try {
                            editor.putInt("favoriteTeamID", connection.getIDTeamByName(teamSpinner.getSelectedItem().toString()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    editor.apply();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
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
        navigationView.setCheckedItem(R.id.nav_settings);
    }
}
