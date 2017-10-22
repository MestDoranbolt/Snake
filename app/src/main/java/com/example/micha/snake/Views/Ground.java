package com.example.micha.snake.Views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.micha.snake.Enums.Direction;
import com.example.micha.snake.Levels.Level;
import com.example.micha.snake.OwnClass.Line;

import java.util.ArrayList;


/**
 * Created by Michał on 15.08.2017.
 */

public class Ground extends View {
    private ArrayList<Point> body; // snake points collection
    private Context context;

    private boolean moved = true;

    private Direction currentDirection; // current direction in which snake moves

    private ArrayList<Line> lines; // map lines

    private int width; // this view width
    private int height; // this view height

    private Paint paint; // paint for line

    private int gameWidth; // game width from another class
    private int gameHeight; // game height from another class

    private int spaceH; // horizontal space
    private int spaceV; // vertical space

    private int pix; // single pix size

    private Point apple; // apple point

    private Level map; // Map

    private Point premium; // Premium point


    public Ground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.currentDirection = Direction.Left;
    }

    public void setGameDimension(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        pixSize();
    }

    public void setSnakeData(ArrayList<Point> body) {
        this.body = body;
    }

    public void setMap(Level map) {
        this.map = map;
    }

    private void pixSize() {
        pix = Math.min(height, width) / 26;
        spaceH = (width - (pix * gameWidth)) / 2;

        spaceV = (height - (pix * gameHeight)) / 2;

        paint = new Paint();
    }

    public void setPremium(Point premium) {
        this.premium = premium;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMap(canvas);
        for (Point p : body) {
            canvas.drawRect(p.x * pix + spaceH, p.y * pix + spaceV, p.x * pix + spaceH + pix - 2, p.y * pix + spaceV + pix - 2, paint);
        }

        canvas.drawRect(apple.x * pix + spaceH, apple.y * pix + spaceV, apple.x * pix + spaceH + pix - 2, apple.y * pix + spaceV + pix - 2, paint);
        if (premium != null) {
            canvas.drawCircle(premium.x * pix + spaceH + pix / 2, premium.y * pix + spaceV + pix / 2, 13, paint);
        }


    }

    private void drawMap(Canvas canvas) {
        paint.setStrokeWidth(pix -2);
        paint.setColor(Color.BLACK);

        lines = map.generateLines(spaceH, spaceV, pix, width, height);

        for (Line line : lines) {
            canvas.drawLine(line.getStartX(), line.getStartY(), line.getStopX(), line.getStopY(), paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (moved) {
            if (x >= 0 && x <= (width * 0.25) && currentDirection != Direction.Right) {
                currentDirection = Direction.Left;
            } else if (x <= width && x >= (width * 0.75) && currentDirection != Direction.Left) {
                currentDirection = Direction.Right;
            } else if (x < (width * 0.75) && x > (width * 0.25) && y >= (height / 2) && y <= height && currentDirection != Direction.Up) {
                currentDirection = Direction.Down;
            } else if (x < (width * 0.75) && x > (width * 0.25) && y < (height / 2) && y >= 0 && currentDirection != Direction.Down) {
                currentDirection = Direction.Up;
            }
            moved = false;
        }


        return super.onTouchEvent(event);
    }

    public void setApple(Point apple) {
        this.apple = apple;

    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

}
