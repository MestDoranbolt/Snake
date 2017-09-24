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

public class Plus extends Level {
    public Plus() {
    }

    @Override
    public boolean checkCollision(int x, int y) {
        if ((x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) || (x >= 17 && x <= 19 && y >= 2 && y <= 22)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Line> generateLines(int spaceH, int spaceV, int pix, int screenWidth, int screenHeight) {
        int x1 = 17;
        int y1 = 2;
        int x2 = 7;

        lines.clear();

        lines.add(new Line(spaceH, spaceV + (pix / 2), screenWidth - spaceH - 1, spaceV + (pix / 2)));
        lines.add(new Line(spaceH, screenHeight - spaceV - (pix / 2), screenWidth - spaceH - 1, screenHeight - spaceV - (pix / 2)));
        lines.add(new Line(spaceH + (pix / 2), spaceV, spaceH + (pix / 2), screenHeight - spaceV));
        lines.add(new Line(screenWidth - spaceH - (pix / 2) - 1, spaceV, screenWidth - spaceH - (pix / 2) - 1, screenHeight - spaceV));

        lines.add(new Line(spaceH + pix + 17 * pix, spaceV + (pix / 2) + pix + 2 * pix, spaceH + pix + (17) * pix, spaceV + (pix / 2) + pix + (2 + 19) * pix));
        lines.add(new Line(spaceH + pix + 7 * pix, spaceV + (pix / 2) + pix + (11) * pix, spaceH + pix + (7 + 20) * pix, spaceV + (pix / 2) + pix + 11 * pix));
        return lines;
    }

    @Override
    public Point randApple() {
        Random random = new Random();
        int x = random.nextInt(33) + 1;
        int y = random.nextInt(23) + 1;
        if ((x == 7 && (y >= 2 || y <= 21)) || (y == 11 && (x >= 7 || x <= 27))) {
            x = random.nextInt(33) + 1;
            y = random.nextInt(23) + 1;
        }
        return new Point(x, y);
    }

    @Override
    public Point randPremium() {
        return randApple();
    }

    //parcelling
    protected Plus(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Plus> CREATOR = new Creator<Plus>() {
        @Override
        public Plus createFromParcel(Parcel source) {
            return new Plus(source);
        }

        @Override
        public Plus[] newArray(int size) {
            return new Plus[size];
        }
    };

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
