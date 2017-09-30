package com.example.micha.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.micha.snake.Levels.Entry;
import com.example.micha.snake.Levels.Plus;
import com.example.micha.snake.Levels.Square;
import com.example.micha.snake.Levels.Tunnel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton square1;
    private ImageButton square2;
    private ImageButton plus;
    private ImageButton tunnel;
    private TextView coins;
    private Context context = this;
    private int totalPremiumPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        SharedPreferences preferences = getSharedPreferences("com.example,michal.snake.PREFERENCES", MODE_PRIVATE);

        totalPremiumPoints = preferences.getInt("total_premium",0);


        coins = (TextView) findViewById(R.id.coins);
        square1 = (ImageButton) findViewById(R.id.square1);
        square2 = (ImageButton) findViewById(R.id.square2);
        plus = (ImageButton) findViewById(R.id.plus);
        tunnel = (ImageButton) findViewById(R.id.tunel);

        square1.setOnClickListener(this);
        square2.setOnClickListener(this);
        plus.setOnClickListener(this);
        tunnel.setOnClickListener(this);

        coins.setText("Your coins " + String.valueOf(totalPremiumPoints));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            totalPremiumPoints += data.getIntExtra("TOTAL_COINS",0);
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

            case R.id.plus:
                intent.putExtra("MAP", new Plus());
                break;
        }

        startActivityForResult(intent,0);
    }


    @Override
    protected void onStop() {
        super.onStop();



        SharedPreferences preferences = getSharedPreferences("com.example,michal.snake.PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("total_premium",totalPremiumPoints);
        editor.apply();

    }
}

 // to do list
// repair plus map