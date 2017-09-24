package com.example.micha.snake.Levels;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.micha.snake.Enums.States;
import com.example.micha.snake.OwnClass.Line;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michał on 21.09.2017.
 */

public class Square extends Level {
    public Square() {

    }

    @Override
    public boolean checkCollision(int x, int y) {
        if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Line> generateLines(int spaceH, int spaceV, int pix, int screenWidth, int screenHeight) {
        lines.clear();
        lines.add(new Line(spaceH, spaceV + (pix / 2), screenWidth - spaceH - 1, spaceV + (pix / 2)));
        lines.add(new Line(spaceH, screenHeight - spaceV - (pix / 2), screenWidth - spaceH - 1, screenHeight - spaceV - (pix / 2)));
        lines.add(new Line(spaceH + (pix / 2), spaceV, spaceH + (pix / 2), screenHeight - spaceV));
        lines.add(new Line(screenWidth - spaceH - (pix / 2) - 1, spaceV, screenWidth - spaceH - (pix / 2) - 1, screenHeight - spaceV));
        return lines;
    }

    @Override
    public Point randApple() {
        Random random = new Random();
        int x = random.nextInt(33) + 1;
        int y = random.nextInt(23) + 1;
        return new Point(x, y);
    }

    @Override
    public Point randPremium() {
        return randApple();
    }

    // parcelling

    protected Square(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Square> CREATOR = new Creator<Square>() {
        @Override
        public Square createFromParcel(Parcel source) {
            return new Square(source);
        }

        @Override
        public Square[] newArray(int size) {
            return new Square[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
