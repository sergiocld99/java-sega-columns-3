package cs10.apps.columns.model;

import cs10.apps.columns.view.BallColor;

public class Ball {
    private BallInsideLocation ballInsideLocation;
    private BallColor ballColor;
    private boolean blackAndWhite = false;
    private boolean isPower = false;

    public Ball(BallInsideLocation ballInsideLocation, BallColor ballColor) {
        this.ballInsideLocation = ballInsideLocation;
        this.ballColor = ballColor;
    }

    public BallInsideLocation getBallInsideLocation() {
        return ballInsideLocation;
    }

    public void setBallInsideLocation(BallInsideLocation ballInsideLocation) {
        this.ballInsideLocation = ballInsideLocation;
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
