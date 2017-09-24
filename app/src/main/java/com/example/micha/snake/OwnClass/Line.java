package com.example.micha.snake.OwnClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michał on 18.09.2017.
 */

public class Line implements Parcelable{
    private int startX;
    private int startY;
    private int stopX;
    private int stopY;


    public Line(int startX, int startY, int stopX, int stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getStopX() {
        return stopX;
    }

    public int getStopY() {
        return stopY;
    }

    // parcelling

    public static final Parcelable.Creator<Line> CREATOR = new Creator<Line>() {
        @Override
        public Line createFromParcel(Parcel source) {
            return new Line(source);
        }

        @Override
        public Line[] newArray(int size) {
            return new Line[size];
        }
    };

    private Line (Parcel in){
        startX = in.readInt();
        startY = in.readInt();
        stopX = in.readInt();
        stopY = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(startX);
        dest.writeInt(startY);
        dest.writeInt(stopX);
        dest.writeInt(stopY);
    }
}
