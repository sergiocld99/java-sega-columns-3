package cs10.apps.columns.core;

import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.Player;
import cs10.apps.columns.model.Position;
import cs10.apps.columns.sound.SoundUtils;
import cs10.apps.columns.view.BallColor;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExplosionHelper {
    private final Ball[][] matrixReference;
    private final Player playerReference;
    private Thread thread;
    private boolean running;

    private final List<Position> nextChain = new ArrayList<>();

    public ExplosionHelper(Ball[][] matrixReference, Player playerReference) {
        this.matrixReference = matrixReference;
        this.playerReference = playerReference;
    }

    public void explode(int scoreBase, int chain, Position... positions){
        running = true;

        thread = new Thread(() -> {
            List<Position> result = check(positions);
            if (result.isEmpty()) {
                running = false;
                return;
            }

            Set<Position> resultsWithoutRepeats = new HashSet<>(result);
            playerReference.incrementMainScore(scoreBase * (resultsWithoutRepeats.size() - 2));
            if (scoreBase > 0) playerReference.incrementSmallScore(resultsWithoutRepeats.size() - 2);
            Thread[] threads = new Thread[resultsWithoutRepeats.size()];
            int index = 0;

            for (Position p : resultsWithoutRepeats){
                Runnable runnable;
                runnable = () -> animate(p.getX(), p.getY());
                threads[index] = new Thread(runnable);
                threads[index++].start();
            }

            try {
                SoundUtils.playSound("explode" + Math.min(chain, 6));

                for (Thread t : threads){
                    t.join();
                    fullGravity();
                }

                if (scoreBase == 0){
                    fullGravity();
                }

                if (!nextChain.isEmpty()){
                    int base = 0;
                    if (scoreBase == 3) base = 7;
                    else if (scoreBase >= 7) base = 11;
                    TimeUnit.MILLISECONDS.sleep(500);
                    explode(base, chain+1, nextChain.toArray(new Position[0]));
                    nextChain.clear();
                } else {
                    running = false;
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        thread.start();
    }

    private void animate(int x, int y){
        Ball value = matrixReference[x][y];

        CountDownLatch latch = new CountDownLatch(10);
        ScheduledExecutorService animationService = Executors.newSingleThreadScheduledExecutor();
        animationService.scheduleAtFixedRate(() -> {
            if (matrixReference[x][y] != null) matrixReference[x][y] = null;
            else matrixReference[x][y] = value;
            latch.countDown();
        }, 0,100,TimeUnit.MILLISECONDS);


        try {
            latch.await();
            animationService.shutdown();
            matrixReference[x][y] = null;
            TimeUnit.MILLISECONDS.sleep(200);
            //if (gravity) applyGravity(x);
            //playerReference.getBoard().recalculateColumnHeight(x);
        } catch (InterruptedException e){
            System.err.println("Animation was interrupted");
        }

        /*try {
            //playerReference.getBoard().decreaseHeight(x,1);
            for (int i=0; i<FOCUS_COUNT; i++){
                matrixReference[x][y] = null;
                TimeUnit.MILLISECONDS.sleep(200);
                matrixReference[x][y] = value;
                TimeUnit.MILLISECONDS.sleep(200);
            }

            matrixReference[x][y] = null;
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e){
            System.err.println("Animation interrupted");
        }

        if (gravity) applyGravity(x);
        playerReference.getBoard().recalculateColumnHeight(x);*/
    }

    public void fullGravity(){
        int x = 0;

        while (x < Board.MAX_X){
            if (!applyGravity(x)){
                playerReference.getBoard().recalculateColumnHeight(x);
                x++;
            }
        }
    }

    private boolean applyGravity(int x){
        int emptySpacesBetween = 0;
        int y = Board.MAX_Y - 1 - playerReference.getBoard().getLines();

        while (isValid(x,y)) y--;
        while (y >= 0 && matrixReference[x][y] == null){
            emptySpacesBetween++;
            y--;
        }

        if (y < 0) return false;
        //playerReference.getBoard().decreaseHeight(x, emptySpacesBetween);

        if (emptySpacesBetween > 0){
            while (isValid(x,y)){
                nextChain.add(new Position(x,y+emptySpacesBetween));
                matrixReference[x][y+emptySpacesBetween] = matrixReference[x][y];
                matrixReference[x][y] = null;
                y--;
            }
        }

        return true;
    }

    private List<Position> check(Position... positions){
        List<Position> result = new ArrayList<>();
        //Set<BallColor> colors = new HashSet<>();

        for (Position p : positions){
            BallColor actual = getColor(p.getX(), p.getY());
            //if (colors.contains(actual)) continue;

            //int originalSize = result.size();
            checkHorizontal(p.getX(), p.getY(), result);
            checkDiagonals(p.getX(), p.getY(), result);
            checkVertical(p.getX(), p.getY(), result);
            //if (result.size() > originalSize) colors.add(actual);
        }

        /*BallColor lastColor = null, aux;
        for (Position p : positions){
            aux = getColor(p.getX(), p.getY());
            if (lastColor == null || aux != lastColor){
                lastColor = aux;
                result.add(p);
                checkDown(p.getX(), p.getY(), result);
            }
        }*/

        return result;
    }

    private void checkDiagonals(int x, int y, List<Position> result){
        List<Position> aux = new ArrayList<>();
        aux.add(new Position(x,y));
        checkDiagonal1(x,y,aux);
        checkDiagonal4(x,y,aux);
        if (aux.size() > 2) result.addAll(aux);

        aux.clear();
        aux.add(new Position(x,y));
        checkDiagonal2(x,y,aux);
        checkDiagonal3(x,y,aux);
        if (aux.size() > 2) result.addAll(aux);
    }

    private void checkHorizontal(int x, int y, List<Position> result){
        List<Position> aux = new ArrayList<>();
        aux.add(new Position(x,y));
        checkLeft(x, y, aux);
        checkRight(x, y, aux);

        if (aux.size() > 2){
            result.addAll(aux);
        }
    }

    private void checkVertical(int x, int y, List<Position> result){
        List<Position> aux = new ArrayList<>();
        aux.add(new Position(x,y));
        checkUp(x,y, aux);
        checkDown(x,y,aux);

        if (aux.size() > 2){
            result.addAll(aux);
        }
    }

    private void checkLeft(int xr, int y, List<Position> result){
        BallColor color = getColor(xr, y);

        int xl = xr-1;
        while (isValid(xl, y) && getColor(xl, y) == color){
            result.add(new Position(xl,y));
            xl--;
        }
    }

    private void checkRight(int xl, int y, List<Position> result){
        BallColor color = getColor(xl, y);

        int xr = xl+1;
        while (isValid(xr, y) && getColor(xr, y) == color){
            result.add(new Position(xr,y));
            xr++;
        }
    }

    private void checkDiagonal1(int xr, int yd, List<Position> result){
        BallColor color = getColor(xr, yd);

        int xl = xr-1, yt = yd-1;
        while (isValid(xl, yt) && getColor(xl,yt) == color){
            result.add(new Position(xl,yt));
            xl--;
            yt--;
        }
    }

    private void checkDiagonal2(int xl, int yd, List<Position> result){
        BallColor color = getColor(xl,yd);

        int xr = xl+1, yt = yd-1;
        while (isValid(xr, yt) && getColor(xr, yt) == color){
            result.add(new Position(xr, yt));
            xr++;
            yt--;
        }
    }

    private void checkDiagonal3(int xr, int yt, List<Position> result){
        BallColor color = getColor(xr, yt);

        int xl = xr-1, yd = yt+1;
        while (isValid(xl, yd) && getColor(xl, yd) == color){
            result.add(new Position(xl, yd));
            xl--;
            yd++;
        }
    }

    private void checkDiagonal4(int xl, int yt, List<Position> result){
        BallColor color = getColor(xl, yt);

        int xr = xl+1, yd = yt+1;
        while (isValid(xr, yd) && getColor(xr,yd) == color){
            result.add(new Position(xr, yd));
            xr++;
            yd++;
        }
    }

    private void checkUp(int x, int yd, List<Position> result){
        BallColor color = getColor(x, yd);

        int yt = yd-1;
        while (isValid(x, yt) && getColor(x,yt) == color){
            result.add(new Position(x,yt));
            yt--;
        }
    }

    private void checkDown(int x, int yt, List<Position> result){
        BallColor color = getColor(x, yt);

        int yd = yt+1;
        while (isValid(x, yd) && getColor(x, yd) == color){
            result.add(new Position(x,yd));
            yd++;
        }
    }

    private BallColor getColor(int x, int y){
        Ball ball = matrixReference[x][y];
        if (ball == null) return null;
        return ball.getColorBall();
    }

    private boolean isValid(int x, int y){
        return x >= 0 && x < Board.MAX_X
                && y >= 0 && y < Board.MAX_Y
                && matrixReference[x][y] != null;
    }

    public boolean isRunning() {
        return running;
    }

    public Thread getThread() {
        return thread;
    }
}
