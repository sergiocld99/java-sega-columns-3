package cs10.apps.columns.model;

import cs10.apps.columns.view.BallColor;

public class Ball {
    private InsideBallLocation insideBallLocation;
    private BallColor ballColor;
    private boolean blackAndWhite = false;
    private boolean isPower = false;

    public Ball(InsideBallLocation insideBallLocation, BallColor ballColor) {
        this.insideBallLocation = insideBallLocation;
        this.ballColor = ballColor;
    }

    public InsideBallLocation getBallInsideLocation() {
        return insideBallLocation;
    }

    public void setBallInsideLocation(InsideBallLocation insideBallLocation) {
        this.insideBallLocation = insideBallLocation;
    }

    public BallColor getColorBall() {
        return ballColor;
    }

    public void setColorBall(BallColor ballColor) {
        this.ballColor = ballColor;
    }

    public boolean isBlackAndWhite() {
        return blackAndWhite;
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public boolean isPower() {
        return isPower;
    }

    public void setPower(boolean power) {
        isPower = power;
    }
}
