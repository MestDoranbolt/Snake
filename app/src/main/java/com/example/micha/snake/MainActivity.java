package com.example.micha.snake;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.micha.snake.Levels.Entry;
import com.example.micha.snake.Levels.Plus;
import com.example.micha.snake.Levels.Square;
import com.example.micha.snake.Levels.Tunnel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton square1;
    private ImageButton square2;
    private ImageButton plus;
    private ImageButton tunnel;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        square1 = (ImageButton) findViewById(R.id.square1);
        square2 = (ImageButton) findViewById(R.id.square2);
        plus = (ImageButton) findViewById(R.id.plus);
        tunnel = (ImageButton) findViewById(R.id.tunel);

        square1.setOnClickListener(this);
        square2.setOnClickListener(this);
        plus.setOnClickListener(this);
        tunnel.setOnClickListener(this);


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

        startActivity(intent);
    }
}

 // to do list
// score and premium points