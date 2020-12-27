package cs10.apps.columns.core;

import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.Player;
import cs10.apps.columns.model.Position;
import cs10.apps.columns.sound.SoundUtils;
import cs10.apps.columns.view.BallColor;
import cs10.apps.columns.view.ItemMagicStone;

import java.util.ArrayList;
import java.util.List;

import static cs10.apps.columns.view.ItemMagicStone.SQUARE;

public class Board {
    public static final int MAX_X = 6, MAX_Y = 13;
    private final Ball[][] matrix = new Ball[MAX_X][MAX_Y];
    private final ExplosionHelper explosionHelper;
    private final int[] columnsHeight = new int[MAX_X];
    private final Player playerReference;
    private static final boolean AUTO_MAGIC_STONE = true;
    private int lines, futureLines, pendingIncrements, pendingDecrements;

    public Board(Player playerReference){
        this.explosionHelper = new ExplosionHelper(matrix, playerReference);
        this.playerReference = playerReference;
    }

    public void applyMagicStone(ItemMagicStone action, int xa, int ya){

        switch (action) {
            case DOWN_TRIANGLE -> decreaseLines(3);
            case UP_TRIANGLE -> playerReference.getOtherPlayerReference().getBoard().increaseLines(2);
            case SQUARE -> {
                BallColor color1 = getColor(xa, ya);
                List<Position> positions = new ArrayList<>();
                for (int x = 0; x < MAX_X; x++) {
                    for (int y = 0; y < MAX_Y; y++) {
                        if (!isEmpty(x, y)) {
                            if (getColor(x, y) == color1) {
                                positions.add(new Position(x, y));
                            }
                        }
                    }
                }
                explosionHelper.explode(0, 1, positions.toArray(new Position[0]));
            }
        }
    }

    public ItemMagicStone getRecommendedMagicStoneAction(){
        ItemMagicStone result;

        if (lines > 3) result = ItemMagicStone.DOWN_TRIANGLE;
        else if (isEmpty(2,MAX_Y-lines-1)) result = ItemMagicStone.UP_TRIANGLE;
        else result = ItemMagicStone.SQUARE;

        System.out.println("Lines: " + lines + " || " + result.name());
        return result;
    }

    public synchronized Ball get(int x, int y) {
        try {
            return matrix[x][y];
        } catch (IndexOutOfBoundsException e){
            // System.err.println("Invalid coordinates: " + x+ ", " + y);
        }

        return null;
    }

    public BallColor getColor(int x, int y) {
        Ball ball = get(x,y);
        if (ball == null){
            //recalculateColumnsHeights();
            return null;
        } else return ball.getColorBall();
    }

    public void set(int x, int y, Ball ball) {
        matrix[x][y] = ball;
    }

    public boolean isEmpty(int x, int y) {
        if (y < 0 || y >= MAX_Y || x < 0 || x >= MAX_X) return false;
        return get(x, y) == null;
    }

    public void incrementHeight(int x, int delta){
        columnsHeight[x] += delta;
        playerReference.setHeight(columnsHeight[x]);
    }

    public int getColumnHeight(int x){
        return columnsHeight[x];
    }

    public void recalculateColumnHeight(int x){
        int y = MAX_Y-1-lines;
        while (!isEmpty(x,y) && y >= 0) y--;
        columnsHeight[x] = MAX_Y-y-1;
    }

    public int getShortestColumn(){
        int index = (int) (Math.random() * MAX_X);
        int min = getColumnHeight(index);

        if (min > MAX_Y - 3){
            for (int x=0; x<MAX_X; x++){
                recalculateColumnHeight(x);
            }
        }

        for (int x=0; x<MAX_X; x++){
            if (getColumnHeight(x) < min){
                min = getColumnHeight(x);
                index = x;
            }
        }

        return index;
    }

    public int findColumnByColor(BallColor color){
        if (color == null) return -1;

        for (int x=0; x<MAX_X; x++){
            if (getColumnTopColor(x) == color) {
                return x;
            }
        }

        return -1;
    }

    public BallColor getColumnTopColor(int x){
        if (getColumnHeight(x) <= 0 || getColumnHeight(x) > MAX_Y-3) return null;
        return getColor(x,MAX_Y-getColumnHeight(x));
    }

    public ExplosionHelper getExplosionHelper() {
        return explosionHelper;
    }

    public void increaseLines(int delta){
        futureLines = lines + delta;
        pendingIncrements += delta;

        Runnable runnable = () -> {
            int realDelta = pendingIncrements - pendingDecrements;

            if (realDelta <= 0)
                return;

            for (int x=0; x<MAX_X; x++){
                for (int y=0; y<MAX_Y-realDelta; y++){
                    set(x,y,get(x,y+realDelta));
                }

                for (int y=MAX_Y-realDelta; y<MAX_Y; y++){
                    set(x,y,null);
                }
            }

            lines += pendingIncrements - pendingDecrements;
            SoundUtils.playSound(realDelta > 2 ? "bigBar" : "bar");
            pendingIncrements = 0;

            for (int x=0; x<MAX_X; x++){
                incrementHeight(x,realDelta);
            }
        };

        if (explosionHelper.isRunning()){
            new Thread(() -> {
               try {
                   //System.out.println("Waiting for increase");
                   while (explosionHelper.isRunning()) Thread.sleep(50);
                   runnable.run();
               } catch (InterruptedException e){
                   System.err.println("Increase Lines Thread interrupted");
               }
            }).start();
        } else runnable.run();
    }

    public void decreaseLines(int delta){
        int delta2 = Math.min(futureLines, delta);
        pendingDecrements = delta;

        Runnable runnable = () -> {
            if (pendingDecrements - pendingIncrements <= 0)
                return;

            lines -= pendingDecrements - pendingIncrements;
            if (lines < 0) lines = 0;
            explosionHelper.fullGravity();
            SoundUtils.playSound("bar");
            pendingDecrements = 0;
        };

        if (explosionHelper.isRunning()){
            try {
                //System.out.println("Waiting for decrease");
                while (explosionHelper.isRunning()) Thread.sleep(50);
                runnable.run();
            } catch (InterruptedException e){
                System.err.println("Decrease Lines Thread interrupted");
            }
        } else runnable.run();
    }

    public int getLines() {
        if (lines < 0) System.err.println("Lines is a negative number!");
        return lines;
    }
}