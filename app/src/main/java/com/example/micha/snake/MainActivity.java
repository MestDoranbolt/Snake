package com.example.micha.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.micha.snake.Levels.Entry;
import com.example.micha.snake.Levels.Square;
import com.example.micha.snake.Levels.Tunnel;
import com.example.micha.snake.Levels.Tunnel_1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton square1;
    private ImageButton square2;
    private ImageButton tunnel_1;
    private ImageButton tunnel;
    private Button lvl2;
    private Button lvl3;
    private Button lvl4;
    private TextView coins;
    private Context context = this;
    private int totalPremiumPoints = 0;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        preferences = getSharedPreferences("com.example,michal.snake.PREFERENCES", MODE_PRIVATE);

        totalPremiumPoints = preferences.getInt("total_premium", 0);


        // initialize variables
        coins = (TextView) findViewById(R.id.coins);
        square1 = (ImageButton) findViewById(R.id.square1);
        square2 = (ImageButton) findViewById(R.id.square2);
        tunnel_1 = (ImageButton) findViewById(R.id.tunnel_1);
        tunnel = (ImageButton) findViewById(R.id.tunel);

        lvl2 = (Button) findViewById(R.id.lvl2);
        lvl3 = (Button) findViewById(R.id.lvl3);
        lvl4 = (Button) findViewById(R.id.lvl4);


        checkingLevels(); // check which levels are enabled and which are disabled


        // set listeners
        lvl2.setOnClickListener(this);
        lvl3.setOnClickListener(this);
        lvl4.setOnClickListener(this);

        square1.setOnClickListener(this);
        square2.setOnClickListener(this);
        tunnel_1.setOnClickListener(this);
        tunnel.setOnClickListener(this);

        // set total coins text
        coins.setText("Your coins " + String.valueOf(totalPremiumPoints));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            totalPremiumPoints += data.getIntExtra("TOTAL_COINS", 0);
            coins.setText("Your coins " + String.valueOf(totalPremiumPoints));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, BoardActivity.class);
        switch (v.getId()) {
            case R.id.square1:
                intent.putExtra("MAP", new Square());
                break;

            case R.id.square2:
                intent.putExtra("MAP", new Entry());
                break;

            case R.id.tunel:
                intent.putExtra("MAP", new Tunnel());
                break;

            case R.id.tunnel_1:
                intent.putExtra("MAP", new Tunnel_1());
                break;
            default:
                unlockLevel((Button) v);
                break;
        }
        if(intent.hasExtra("MAP")){
            startActivityForResult(intent, 0);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();


        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("total_premium", totalPremiumPoints);
        editor.apply();

    }

    private void unlockLevel(Button level) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (level.getId()) {
            case R.id.lvl2:
                if (totalPremiumPoints >= 5) {
                    square2.setEnabled(true);
                    level.setVisibility(View.GONE);
                    totalPremiumPoints -= 5;
                    editor.putBoolean("LVL_2", true);
                }
                break;
            case R.id.lvl3:
                if (totalPremiumPoints >= 10) {
                    tunnel_1.setEnabled(true);
                    level.setVisibility(View.GONE);
                    totalPremiumPoints -= 10;
                    editor.putBoolean("LVL_3", true);
                }
                break;
            case R.id.lvl4:
                if (totalPremiumPoints >= 15) {
                    tunnel.setEnabled(true);
                    level.setVisibility(View.GONE);
                    totalPremiumPoints -= 15;
                    editor.putBoolean("LVL_4", true);
                }
                break;
        }
        editor.apply();
        coins.setText("Your coins " + String.valueOf(totalPremiumPoints));
    }

    private void checkingLevels(){
        if(preferences.getBoolean("LVL_2",false)){
            square2.setEnabled(true);
            lvl2.setVisibility(View.GONE);
        }else {
            square2.setEnabled(false);
        }

        if(preferences.getBoolean("LVL_3",false)){
            tunnel_1.setEnabled(true);
            lvl3.setVisibility(View.GONE);
        }else {
            tunnel_1.setEnabled(false);
        }

        if(preferences.getBoolean("LVL_4",false)){
            tunnel.setEnabled(true);
            lvl4.setVisibility(View.GONE);
        }else {
            tunnel.setEnabled(false);
        }
    }
}

//// TODO: 03.10.2017
// change premium delay
// delete logs

//// TODO: 05.10.2017
// change premium delay
// change button to unlock graphic
