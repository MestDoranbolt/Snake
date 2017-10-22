package com.example.micha.snake.Levels;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

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
        boolean horizontalBorders = (x == 0 || x == width - 1);
        boolean verticalBorders = (y == 0 || y == height - 1);
        if (horizontalBorders || verticalBorders) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Line> generateLines(int spaceH, int spaceV, int pix, int screenWidth, int screenHeight) {
        lines.clear();
        //up
        lines.add(new Line(spaceH + 2, spaceV + (pix / 2), screenWidth - spaceH - 3, spaceV + (pix / 2)));
        //bottom
        lines.add(new Line(spaceH + 2, screenHeight - spaceV - (pix / 2), screenWidth - spaceH - 3, screenHeight - spaceV - (pix / 2)));
        //left
        lines.add(new Line(spaceH + (pix / 2), spaceV + 2, spaceH + (pix / 2), screenHeight - spaceV - 2));
        // right
        lines.add(new Line(screenWidth - spaceH - (pix / 2) - 1, spaceV +2, screenWidth - spaceH - (pix / 2) - 1, screenHeight - spaceV -2));
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
