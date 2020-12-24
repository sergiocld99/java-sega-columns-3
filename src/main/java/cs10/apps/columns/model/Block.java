package cs10.apps.columns.model;

public class Block {
    protected Ball[] balls = new Ball[3];

    public Block(Ball ball1, Ball ball2, Ball ball3) {
        balls[0] = ball1;
        balls[1] = ball2;
        balls[2] = ball3;
    }

    public Ball getBall1() {
        return balls[0];
    }

    public Ball getBall2() {
        return balls[1];
    }

    public Ball getBall3() {
        return balls[2];
    }
}
