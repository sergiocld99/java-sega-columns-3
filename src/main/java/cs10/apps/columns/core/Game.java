package cs10.apps.columns.core;

import cs10.apps.columns.model.FallingBlock;
import cs10.apps.columns.model.Player;
import cs10.apps.columns.sound.SoundUtils;
import cs10.apps.columns.view.GameView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    private final Player player1, player2;
    private final GameView gameView;
    private ScheduledExecutorService autoFallService, autoFallService2;
    private int secondsActual = 0;

    private boolean started = false;

    public Game(){
        BlockGenerator.build();

        player1 = new Player(1, true);
        player2 = new Player(2, true);
        gameView = new GameView();

        player1.setOtherPlayerReference(player2);
        player2.setOtherPlayerReference(player1);
        updateNextBlock(player1, false);
        updateNextBlock(player2, false);

        gameView.getBoardView(1).setBoard(player1.getBoard());
        gameView.getBoardView(2).setBoard(player2.getBoard());
        gameView.getMainScoreView(1).setPlayer(player1);
        gameView.getMainScoreView(2).setPlayer(player2);
        gameView.getNextBlockView(1).setBlock(player1.getNextBlock());
        gameView.getNextBlockView(2).setBlock(player2.getNextBlock());
    }

    public void start(){
        started = true;

        SoundUtils.playSound("startGame");

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        // TIME
        ScheduledExecutorService timeService = Executors.newSingleThreadScheduledExecutor();
        timeService.scheduleAtFixedRate(() -> {
            gameView.getTimeView().setSeconds(++secondsActual);
            if (secondsActual == 3600) timeService.shutdown();
        }, 1,1, TimeUnit.SECONDS);


        // AUTO-FALL BLOCK FOR PLAYER 1
        autoFallService = Executors.newSingleThreadScheduledExecutor();
        autoFallService.scheduleAtFixedRate(() -> {
            if (player1.getBoard().getExplosionHelper().isRunning())
                return;

            gameView.getNextBlockView(1).setBlock(player1.getNextBlock());
            player1.checkForAutoPush();

            FallingBlock fallingBlock = player1.getFallingBlock();
            boolean could = fallingBlock.moveDown(false);
            if (!could) {
                SoundUtils.playSound("blockPositioned");

                if (fallingBlock.isLose()) {
                    SoundUtils.playSound("boardSetException");
                    player2.destroyFallingBlock();
                    autoFallService.shutdown();
                    //autoFallService2.shutdown();
                    timeService.shutdown();
                } else {
                    nextFallingBlock(player1);
                }
            }
        }, 1200,50, TimeUnit.MILLISECONDS);


        // AUTO-FALL BLOCK FOR PLAYER 2
        autoFallService2 = Executors.newSingleThreadScheduledExecutor();
        autoFallService2.scheduleAtFixedRate(() -> {
            if (player2.getBoard().getExplosionHelper().isRunning())
                return;

            gameView.getNextBlockView(2).setBlock(player2.getNextBlock());
            player2.checkForAutoPush();

            FallingBlock fallingBlock = player2.getFallingBlock();
            boolean could = fallingBlock.moveDown(false);
            if (!could) {
                SoundUtils.playSound("blockPositioned");

                if (fallingBlock.isLose()) {
                    SoundUtils.playSound("boardSetException");
                    player1.destroyFallingBlock();
                    autoFallService2.shutdown();
                    //autoFallService.shutdown();
                    timeService.shutdown();
                } else {
                    nextFallingBlock(player2);
                }
            }
        }, 1200,50, TimeUnit.MILLISECONDS);


        // AUTO-UPDATE BOARD VIEW
        ScheduledExecutorService fallViewService = Executors.newSingleThreadScheduledExecutor();
        fallViewService.scheduleAtFixedRate(() -> {
            gameView.getBoardView(1).paintFallingBlock(player1.getFallingBlock());
            gameView.getBoardView(2).paintFallingBlock(player2.getFallingBlock());
            updateScoreViews(player1);
            updateScoreViews(player2);
        }, 50, 50, TimeUnit.MILLISECONDS);


        // MUSIC
        SoundUtils.playMusic("music3");

        nextFallingBlock(player1);
        nextFallingBlock(player2);
    }

    private void nextFallingBlock(Player player){
        player.nextFallingBlock();
        updateNextBlock(player, true);
    }

    private void updateScoreViews(Player player){
        gameView.getMainScoreView(player.getNumber()).update();
        gameView.getSmallScoreView(player.getNumber()).setNumber(player.getSmallScore());
    }

    private void updateNextBlock(Player player, boolean delay){
        player.changeNextBlock();
    }

    public boolean isStarted() {
        return started;
    }

    public FallingBlock getFallingBlock() {
        if (!started) throw new RuntimeException("Game not started yet");
        return player1.getFallingBlock();
    }

    public Player getPlayer1() {
        return player1;
    }
}
