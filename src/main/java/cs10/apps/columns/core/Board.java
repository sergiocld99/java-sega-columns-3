package cs10.apps.columns.core;

import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.ExplosionType;
import cs10.apps.columns.view.BallColor;

public class Board {
    public static final int MAX_X = 6, MAX_Y = 13;
    private final Ball[][] matrix = new Ball[MAX_X][MAX_Y];

    public void checkDownwards(int x, int y1, BallColor color) {
        int count = 1;

        for (int y = y1 + 1; y < MAX_Y; y++) {
            if (getColor(x, y) != color) break;
            count++;
        }

        if (count > 2) explode(x, y1, x, y1 + count - 1, ExplosionType.VERTICAL);
    }

    public void checkLeftwards(int x2, int y, BallColor color) {
        int count = 1;

        for (int x = x2 - 1; x >= 0; x--) {
            if (isEmpty(x, y) || getColor(x, y) != color) break;
            count++;
        }

        if (count > 2) explode(x2 - count + 1, y, x2, y, ExplosionType.HORIZONTAL);
    }

    public void checkRightwards(int x1, int y, BallColor color) {
        int count = 1;

        for (int x = x1 + 1; x < MAX_X; x++) {
            if (isEmpty(x, y) || getColor(x, y) != color) break;
            count++;
        }

        if (count > 2) explode(x1, y, x1 + count - 1, y, ExplosionType.HORIZONTAL);
    }

    public void explode(int x1, int y1, int x2, int y2, ExplosionType type) {

    }

    public Ball get(int x, int y) {
        return matrix[x][y];
    }

    public BallColor getColor(int x, int y) {
        return get(x, y).getColorBall();
    }

    public void set(int x, int y, Ball ball) {
        matrix[x][y] = ball;
    }

    public boolean isEmpty(int x, int y) {
        if (y >= MAX_Y || x < 0 || x >= MAX_X) return false;
        return get(x, y) == null;
    }
}