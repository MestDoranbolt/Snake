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

public class Tunnel extends Level {
    public Tunnel() {
    }

    @Override
    public boolean checkCollision(int x, int y) {
        boolean borders = (x == 0 || y == 0 || x == width - 1 || y == height - 1);
        boolean tunnel = (x > 7 && x <= 27) && (y == 9 || y == 16);
        if (borders || tunnel) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Line> generateLines(int spaceH, int spaceV, int pix, int screenWidth, int screenHeight) {
//        x1 = 7;
//        y1 = 8;
//        x2 = 27;

        lines.clear();
        //up
        lines.add(new Line(spaceH + 2, spaceV + (pix / 2), screenWidth - spaceH - 3, spaceV + (pix / 2)));
        //bottom
        lines.add(new Line(spaceH + 2, screenHeight - spaceV - (pix / 2), screenWidth - spaceH - 3, screenHeight - spaceV - (pix / 2)));
        //left
        lines.add(new Line(spaceH + (pix / 2), spaceV + 2, spaceH + (pix / 2), screenHeight - spaceV - 2));
        // right
        lines.add(new Line(screenWidth - spaceH - (pix / 2) - 1, spaceV +2, screenWidth - spaceH - (pix / 2) - 1, screenHeight - spaceV -2));;

        //tunnel
        lines.add(new Line(spaceH + pix + 7 * pix, spaceV + (pix / 2) + pix + 8 * pix, spaceH + pix + 27 * pix, spaceV + (pix / 2) + pix + 8 * pix));
        lines.add(new Line(spaceH + pix + 7 * pix, spaceV + (pix / 2) + pix + (8 + 7) * pix, spaceH + pix + 27 * pix, spaceV + (pix / 2) + pix + (8 + 7) * pix));
        return lines;
    }

    @Override
    public Point randApple() {
        Random random = new Random();
        int x = random.nextInt(33) + 1;
        int y = random.nextInt(23) + 1;
        if ((x >= 7 || x <= 27) && (y == 8 || y == 15)) {
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
    protected Tunnel(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Tunnel> CREATOR = new Creator<Tunnel>() {
        @Override
        public Tunnel createFromParcel(Parcel source) {
            return new Tunnel(source);
        }

        @Override
        public Tunnel[] newArray(int size) {
            return new Tunnel[size];
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
