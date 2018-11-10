package com.example.acuevas.ovalion;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acuevas.ovalion.domain.Battle;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BattleView extends View {

    Battle battle;

    public BattleView(Context context, Battle battle) {
        super(context);
        this.battle = battle;
        init(context);
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    private void init(Context context) {
        try {
            SQLConnection sqlConnection = new SQLConnection();

            ImageView homeTeam = findViewById(R.id.homeTeam);
            ImageView visitorTeam = findViewById(R.id.visitorTeam);
            TextView dateOrScore = findViewById(R.id.dateOrScore);
            TextView dateOrHour = findViewById(R.id.dateOrHour);
            TextView location = findViewById(R.id.location);

            homeTeam.setImageResource(getStringIdentifier(context, sqlConnection.getNameTeamByID(battle.getTeamHome()) + ".png"));
            visitorTeam.setImageResource(getStringIdentifier(context, sqlConnection.getNameTeamByID(battle.getTeamVisitors()) + ".png"));

            if (battle.getDateBattle().after(new Date())) {
                dateOrScore.setText(battle.getResult());
                DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
                dateOrHour.setText(dateFormat.format(battle.getDateBattle()));
                location.setText(battle.getLocation());
            } else {
                DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
                dateOrScore.setText(dateFormat.format(battle.getDateBattle()));
                dateFormat = new SimpleDateFormat("HH:mm:ss");
                dateOrHour.setText(dateFormat.format(battle.getDateBattle()));
                location.setText(battle.getLocation());
            }
        } catch (SQLException ignored) {}
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
