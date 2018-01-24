package com.example.micha.Mest.Levels;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.micha.Mest.OwnClass.Line;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michał on 21.09.2017.
 */

public class Entry extends Level {
    public Entry() {
    }

    @Override
    public boolean checkCollision(int x, int y) {
        boolean verticalBorders = ((x==0 || x == width-1) && (y <= 10 || y >= 15));
        boolean horizontalBorders = (x <= 15 || x >= 20) && (y == 0 || y == height - 1);

        if (verticalBorders || horizontalBorders) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Line> generateLines(int spaceH, int spaceV, int pix, int screenWidth, int screenHeight) {
        lines.clear();

        // up
        lines.add(new Line(spaceH+2, spaceV + (pix / 2), spaceH + 16 * pix, spaceV + (pix / 2)));
        lines.add(new Line(spaceH + (16 + 4) * pix, spaceV + (pix / 2), screenWidth - spaceH - 3, spaceV + (pix / 2)));

        //bottom
        lines.add(new Line(spaceH+2, screenHeight - spaceV - (pix / 2), spaceH + 16 * pix, screenHeight - spaceV - (pix / 2)));
        lines.add(new Line(spaceH + (16 + 4) * pix, screenHeight - spaceV - (pix / 2), screenWidth - spaceH - 3, screenHeight - spaceV - (pix / 2)));

        // left
        lines.add(new Line(spaceH + (pix / 2), spaceV+2, spaceH + (pix / 2), spaceV + (11 * pix)));
        lines.add(new Line(spaceH + (pix / 2), spaceV + (15 * pix), spaceH + (pix / 2), screenHeight - spaceV-2));


        // right
        lines.add(new Line(screenWidth - spaceH - (pix / 2) - 1, spaceV+2, screenWidth - spaceH - (pix / 2) - 1, spaceV + (11 * pix)));
        lines.add(new Line(screenWidth - spaceH - (pix / 2) - 1, spaceV + (15 * pix), screenWidth - spaceH - (pix / 2) - 1, screenHeight - spaceV-2));
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

    //parcelling

    protected Entry(Parcel in){
        super(in);
    }

    public static final Parcelable.Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel source) {
            return new Entry(source);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
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
