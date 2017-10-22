package com.example.micha.snake.GameEngine;
import android.graphics.Point;
import com.example.micha.snake.Enums.Direction;
import com.example.micha.snake.Enums.States;
import com.example.micha.snake.Levels.Level;
import java.util.ArrayList;

/**
 * Created by Michał on 15.08.2017.
 */

public class GameEngine {
    private int height; // game height
    private int width; // game width
    private int length = 5; // beginning snake length
    private int premiumPoints = 0;
    private ArrayList<Point> body; // snake points collection

    private Point apple = new Point();

    private Point premium = new Point();

    private States currentState = States.Running;

    private Level map;

    public GameEngine(Level map) {
        this.map = map;
        initGame();
    }

    private void initGame() {
        this.width = map.getWidth();
        this.height = map.getHeight();
        body = new ArrayList<>();
        randApple();
        addSnake();
    }

    private void addSnake() {
        body.add(new Point(width / 2 -1, height / 2));
        for (int i = 0; i < 4; i++) {
            body.add(new Point(body.get(i).x + 1, body.get(i).y));
        }
    }

    public Point getPremium() {
        premium = map.randPremium();
        return premium;
    }

    public boolean move(Direction currentDirection) {
        Point firstPoint = body.get(0);
        Point newPoint = null;
        if (currentDirection.equals(Direction.Left)) {
            if (firstPoint.x <= 0) {
                newPoint = new Point(width - 1, firstPoint.y);
            } else {
                newPoint = new Point(firstPoint.x - 1, firstPoint.y);
            }

        } else if (currentDirection.equals(Direction.Right)) {
            if (firstPoint.x + 1 >= width) {
                newPoint = new Point(0, firstPoint.y);
            } else {
                newPoint = new Point(firstPoint.x + 1, firstPoint.y);
            }

        } else if (currentDirection.equals(Direction.Down)) {
            if (firstPoint.y + 1 >= height) {
                newPoint = new Point(firstPoint.x, 0);
            } else {
                newPoint = new Point(firstPoint.x, firstPoint.y + 1);
            }

        } else if (currentDirection.equals(Direction.Up)) {
            if (firstPoint.y <= 0) {
                newPoint = new Point(firstPoint.x, height - 1);
            } else {
                newPoint = new Point(firstPoint.x, firstPoint.y - 1);
            }
        }

        if (checkCollision(newPoint)) {
            currentState = States.Stop;
            return false;
        } else {
            body.remove(length - 1);
            body.add(0, newPoint);
            if (checkApple(firstPoint.x, firstPoint.y)) {
                length++;
                body.add(1, apple);
                randApple();
                currentState = States.Apple;
            } else if (premium != null && checkPremium(firstPoint.x, firstPoint.y)) {
                premiumPoints++;
                currentState = States.Premium;
            }
        }
        return true;
    }

    public void setPremium(Point premium) {
        this.premium = premium;
    }

    public int getPremiumPoints() {
        return premiumPoints;
    }

    public boolean checkCollision(Point newPoint) {
        boolean bodyCheck = body.contains(newPoint);
        boolean collision = map.checkCollision(newPoint.x, newPoint.y);
        if (collision || bodyCheck) {
            return true;
        }
        return false;

    }

    public boolean checkApple(int x, int y) {
        if (apple.x == x && apple.y == y) {
            return true;
        }
        return false;
    }

    public boolean checkPremium(int x, int y) {
        if (premium.x == x && premium.y == y) {
            return true;
        }
        return false;
    }

    public void randApple() {
        apple = map.randApple();
    }

    public Point getApple() {
        return apple;
    }

    public States getCurrentState() {
        return currentState;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<Point> getBody() {
        return body;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
