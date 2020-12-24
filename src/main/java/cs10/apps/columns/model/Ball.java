package cs10.apps.columns.model;

import cs10.apps.columns.view.BallColor;

public class Ball {
    private BallColor ballColor;
    private boolean isPower = false;

    public Ball(BallColor ballColor) {
        this.ballColor = ballColor;
    }

    public BallColor getColorBall() {
        return ballColor;
    }

    public void setColorBall(BallColor ballColor) {
        this.ballColor = ballColor;
    }

    public boolean isPower() {
        return isPower;
    }

    public void setPower(boolean power) {
        isPower = power;
    }
}
