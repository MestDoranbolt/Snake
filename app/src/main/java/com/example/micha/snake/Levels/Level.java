package com.example.micha.snake.Levels;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.micha.snake.OwnClass.Line;

import java.util.ArrayList;

/**
 * Created by Michał on 21.09.2017.
 */

public class Level implements Parcelable {
    int width = 36;
    int height = 26;

    ArrayList<Line> lines = new ArrayList<>();

    public Level() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Line> generateLines(int spaceH, int spaceV, int pix, int screenWidth, int screenHeigh) {
        return lines;
    }

    public boolean checkCollision(int x, int y) {
        return false;
    }

    public Point randApple() {
        return new Point();
    }

    public Point randPremium(){
        return new Point();
    }

    // parcelling

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeTypedList(lines);
    }

    public static final Parcelable.Creator<Level> CREATOR = new Creator<Level>() {
        @Override
        public Level createFromParcel(Parcel source) {
            return new Level(source);
        }

        @Override
        public Level[] newArray(int size) {
            return new Level[0];
        }
    };

    protected Level(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        in.readTypedList(new ArrayList<Level>(0),Level.CREATOR);
    }
}
