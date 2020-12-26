package cs10.apps.columns.model;

public class FallingPosition {
    private int x;
    private double y;

    public FallingPosition(int x, double y) {
        this.x = x;
        this.y = y;
    }

    public void changeX(int delta){
        x += delta;
    }

    public void changeY(double delta){
        y += delta;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
