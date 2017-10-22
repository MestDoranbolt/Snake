package com.example.micha.snake;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.micha.snake.Enums.Direction;
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
    private final int BREAK = 40;
    private int currentInterval = 0;
    private int totalPremium = 0;
    private Level map;
    private RunTheGame gameRunner;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_view);

        SharedPreferences pref = getSharedPreferences("com.example,michal.snake.PREFERENCES", MODE_PRIVATE);

        getWindowManager().getDefaultDisplay().getMetrics(dimension);

        score = (TextView) findViewById(R.id.score);
        coins = (TextView) findViewById(R.id.coins);
        pause = (ImageButton) findViewById(R.id.pause);

        map = getIntent().getExtras().getParcelable("MAP");

        gameRunner = new RunTheGame();
        snake = (Ground) findViewById(R.id.SnakeGround);
        game = new GameEngine(map);

        snake.setGameDimension(game.getWidth(), game.getHeight());


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new PauseDialog(context);
                handler.removeCallbacks(gameRunner);
                dialog.show();
            }
        });


        if (true) {
            Dialog dialog = new TipDialog(context);
            dialog.getWindow().setBackgroundDrawableResource(R.color.semi_green);
            dialog.show();
            startTheGame(false);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.postDelayed(gameRunner, DELAY);
                }
            });

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("first_use", false);
            editor.apply();

        } else {
            startTheGame(true);
        }

    }

    private void startTheGame(boolean run) {
        game = new GameEngine(map);

        snake.setCurrentDirection(Direction.Left);

        snake.setMap(map);

        snake.setSnakeData(game.getBody());

        snake.setPremium(null);

        snake.setApple(game.getApple());

        if (run) {
            handler.postDelayed(gameRunner, DELAY);
        }

        snake.invalidate();
    }


    @Override
    public void onBackPressed() {
        Dialog dialog = new PauseDialog(context);
        handler.removeCallbacks(gameRunner);
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onBackPressed();
    }

    // private classes

    public class PauseDialog extends Dialog implements View.OnClickListener {
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
            switch (v.getId()) {
                case R.id.restart:
                    startTheGame(true);
                    score.setText("score 0");
                    coins.setText("coins 0");
                    totalPremium += game.getPremiumPoints();
                    break;
                case R.id.home:
                    Intent returnIntent = new Intent();
                    totalPremium += game.getPremiumPoints();
                    returnIntent.putExtra("TOTAL_COINS", totalPremium);
                    setResult(RESULT_OK, returnIntent);
                    BoardActivity.this.finish();
                    break;
                case R.id.play:
                    snake.invalidate();
                    handler.postDelayed(gameRunner, DELAY);
                    break;
            }
            this.dismiss();
        }
    }

    private class TipDialog extends Dialog {

        public TipDialog(@NonNull Context context) {
            super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            setContentView(R.layout.hint);

            ImageButton close = (ImageButton) findViewById(R.id.closeTip);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

    }

    private class RunTheGame implements Runnable {

        @Override
        public void run() {
            if (currentInterval == INTERVAL) {
                snake.setPremium(game.getPremium());
                currentInterval = 0;
            }

            if (currentInterval == BREAK) {
                snake.setPremium(null);
                game.setPremium(null);
            }

            snake.setMoved(game.move(snake.getCurrentDirection()));
            snake.setSnakeData(game.getBody());

            if (game.getCurrentState() == States.Apple) {
                snake.setApple(game.getApple());
                score.setText(getString(R.string.score) + " " + (game.getLength() - 5));
            } else if (game.getCurrentState() == States.Premium) {
                snake.setPremium(null);
                coins.setText("coins " + " " + game.getPremiumPoints());
            }
            snake.invalidate();

            if (game.getCurrentState() == States.Running || game.getCurrentState() == States.Apple || game.getCurrentState() == States.Premium) {
                currentInterval++;
                handler.postDelayed(this, DELAY);
            } else if (game.getCurrentState() == States.Stop) {
                handler.removeCallbacks(this);
            }
        }
    }
}
