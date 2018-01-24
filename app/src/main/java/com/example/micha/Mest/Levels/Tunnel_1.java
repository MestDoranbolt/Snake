package com.example.micha.Mest.Levels;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.micha.Mest.OwnClass.Line;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michał on 21.09.2017.
 */

public class Tunnel_1 extends Level {
    public Tunnel_1() {
    }

    @Override
    public boolean checkCollision(int x, int y) {
        Log.d("POSITION", String.valueOf(x) + " " + String.valueOf(y));

        boolean verticalBorders = (x == 0 || x == width - 1);
        boolean horizontalBorders = (x <= 15 || x >= 20) && (y == 0 || y == height - 1);
        boolean leftLine = (x == 12 && y >= 2 && y <= 23);
        boolean rightLine = (x == 23 && y >= 2 && y <= 23);

        if (verticalBorders || horizontalBorders || leftLine || rightLine) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Line> generateLines(int spaceH, int spaceV, int pix, int screenWidth, int screenHeight) {
//        x1 = 12;
//        x2 = 23;
//        y1 = 1;
//        y2 = 23;

        lines.clear();

        // up
        lines.add(new Line(spaceH+2, spaceV + (pix / 2), spaceH + 16 * pix, spaceV + (pix / 2)));
        lines.add(new Line(spaceH + (16 + 4) * pix, spaceV + (pix / 2), screenWidth - spaceH - 3, spaceV + (pix / 2)));

        //bottom
        lines.add(new Line(spaceH+2, screenHeight - spaceV - (pix / 2), spaceH + 16 * pix, screenHeight - spaceV - (pix / 2)));
        lines.add(new Line(spaceH + (16 + 4) * pix, screenHeight - spaceV - (pix / 2), screenWidth - spaceH - 3, screenHeight - spaceV - (pix / 2)));

        //left
        lines.add(new Line(spaceH + (pix / 2), spaceV + 2, spaceH + (pix / 2), screenHeight - spaceV - 2));

        // right
        lines.add(new Line(screenWidth - spaceH - (pix / 2) - 1, spaceV +2, screenWidth - spaceH - (pix / 2) - 1, screenHeight - spaceV -2));


        //horizontal
        lines.add(new Line(spaceH + (pix / 2) + 12 * pix, spaceV + pix + pix, spaceH + (pix / 2) + 12 * pix, spaceV + pix + 23 * pix - 2));
        lines.add(new Line(spaceH + (pix / 2) + 23 * pix, spaceV + pix + pix, spaceH + (pix / 2) + 23 * pix, spaceV + pix + 23 * pix - 2));


        return lines;
    }

    @Override
    public Point randApple() {
        Random random = new Random();
        int x = random.nextInt(33) + 1;
        int y = random.nextInt(23) + 1;

        boolean leftLine = (x == 12 && y >= 2 && y <= 23);
        boolean rightLine = (x == 23 && y >= 2 && y <= 23);

        if (leftLine || rightLine) {
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
    protected Tunnel_1(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Tunnel_1> CREATOR = new Creator<Tunnel_1>() {
        @Override
        public Tunnel_1 createFromParcel(Parcel source) {
            return new Tunnel_1(source);
        }

        @Override
        public Tunnel_1[] newArray(int size) {
            return new Tunnel_1[size];
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
