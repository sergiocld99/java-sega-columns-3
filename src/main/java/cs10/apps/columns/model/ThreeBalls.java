package cs10.apps.columns.model;

public class ThreeBalls {
    private Ball ball1, ball2, ball3;

    public ThreeBalls(Ball ball1, Ball ball2, Ball ball3) {
        this.ball1 = ball1;
        this.ball2 = ball2;
        this.ball3 = ball3;
    }

    public Ball getBall1() {
        return ball1;
    }

    public void setBall1(Ball ball1) {
        this.ball1 = ball1;
    }

    public Ball getBall2() {
        return ball2;
    }

    public void setBall2(Ball ball2) {
        this.ball2 = ball2;
    }

    public Ball getBall3() {
        return ball3;
    }

    public void setBall3(Ball ball3) {
        this.ball3 = ball3;
    }
}
