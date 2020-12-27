package cs10.apps.columns.model;

import cs10.apps.columns.view.BallColor;

public class Block {
    protected Ball[] balls = new Ball[3];

    public Block(Ball ball1, Ball ball2, Ball ball3) {
        balls[0] = ball1;
        balls[1] = ball2;
        balls[2] = ball3;
    }

    public int containsColor(BallColor color){
        if (getBall1().getColorBall() == color) return 1;
        if (getBall2().getColorBall() == color) return 2;
        if (getBall3().getColorBall() == color) return 3;
        return 0;
    }

    public BallColor containsRepeatedBall(){
        BallColor result = getBall1().getColorBall();

        if (result == getBall2().getColorBall() || result == getBall3().getColorBall())
            return result;
        else {
            result = getBall2().getColorBall();
            if (result == getBall3().getColorBall()) return result;
            else return null;
        }
    }

    public boolean differentColor(int n1, int n2){
        return balls[n1].getColorBall() != balls[n2].getColorBall();
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
