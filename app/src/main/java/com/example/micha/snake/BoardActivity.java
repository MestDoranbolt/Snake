package com.example.micha.snake;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.micha.snake.Enums.States;
import com.example.micha.snake.GameEngine.GameEngine;
import com.example.micha.snake.Levels.Level;
import com.example.micha.snake.Views.Ground;

/**
 * Created by Michał on 11.09.2017.
 */

public class BoardActivity extends AppCompatActivity {

    private DisplayMetrics dimension = new DisplayMetrics();
    private android.os.Handler handler = new android.os.Handler();
    private Ground snake;
    private GameEngine game;
    private TextView score;
    private TextView coins;
    private ImageButton pause;
    private final int DELAY = 300;
    private final int INTERVAL = 50;
    private final int BREAKE = 15;
    private int currentInterval = 0;
    private Level map;
    private RunTheGame gameRunner;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_view);

        gameRunner = new RunTheGame();

        getWindowManager().getDefaultDisplay().getMetrics(dimension);

        map = getIntent().getExtras().getParcelable("MAP");

        snake = (Ground) findViewById(R.id.SnakeGround);
        game = new GameEngine(map);

        score = (TextView) findViewById(R.id.score);
        coins = (TextView) findViewById(R.id.coins);
        pause = (ImageButton) findViewById(R.id.pause);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new PauseDialog(context);
                handler.removeCallbacks(gameRunner);
                dialog.show();
            }
        });


        snake.setGameDimension(game.getWidth(), game.getHeight());

        snake.setMap(map);

        snake.setSnakeData(game.getBody());

        snake.setApple(game.getApple());

        snake.invalidate();
        handler.postDelayed(gameRunner, DELAY);

    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new PauseDialog(context);
        dialog.show();
    }

    private class PauseDialog extends Dialog implements View.OnClickListener{
        private ImageButton restart;
        private ImageButton home;
        private ImageButton play;

        public PauseDialog(@NonNull Context context) {
            super(context, R.style.DialogTheme);
            setContentView(R.layout.pause_menu);
            setCancelable(false);
            restart = (ImageButton) findViewById(R.id.restart);
            play = (ImageButton) findViewById(R.id.play);
            home = (ImageButton) findViewById(R.id.home);

            restart.setOnClickListener(this);
            play.setOnClickListener(this);
            home.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.restart:
                    game.restartSnakePosition();
                    handler.postDelayed(gameRunner,DELAY);
                    break;
                case R.id.home:
                    BoardActivity.this.finish();
                    break;
                case R.id.play:
                    handler.postDelayed(gameRunner,DELAY);
                    break;
            }
        }
    }

    private class RunTheGame implements Runnable {

        @Override
        public void run() {
            if (currentInterval == INTERVAL) {
                snake.setPremium(game.getPremium());
                currentInterval = 0;
            }
            if (currentInterval == BREAKE) {
                snake.setPremium(null);
                game.setPremium(null);
            }
            game.move(snake.getCurrentDirection());
            snake.setSnakeData(game.getBody());
            if (game.getCurrentState() == States.Apple) {
                snake.setApple(game.getApple());
                score.setText(getString(R.string.score) + " " + (game.getLength() - 5));
            }
            snake.invalidate();

            if (game.getCurrentState() == States.Running || game.getCurrentState() == States.Apple) {
                currentInterval++;
                handler.postDelayed(this, DELAY);
            } else if (game.getCurrentState() == States.Stop) {
                handler.removeCallbacks(this);
            }
        }
    }
}
