package cs10.apps.columns.model;

import cs10.apps.columns.core.Board;

public class Position {
    private int x;
    private double y;
    private final Board referenceBoard;

    public Position(int x, int y, Board referenceBoard) {
        this.x = x;
        this.y = y;
        this.referenceBoard = referenceBoard;
    }

    public void moveLeft(){
        if (x > 0 && referenceBoard.isEmpty(x-1,(int) y)) x--;
    }

    public void moveRight(){
        if (x < Board.MAX_X && referenceBoard.isEmpty(x+1,(int) y)) x++;
    }

    public boolean moveDown(){
        if (y < Board.MAX_Y && referenceBoard.isEmpty(x,(int) y+1)) {
            y += 0.5;
            return true;
        } else {
            y = (int) y;
            System.out.println("Crushed with " + x + ", " + (y+1));
            return false;
        }
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

    public void setY(int y) {
        this.y = y;
    }
}
