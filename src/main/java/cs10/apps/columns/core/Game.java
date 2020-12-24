package cs10.apps.columns.core;

import cs10.apps.columns.model.Player;
import cs10.apps.columns.model.ThreeBalls;
import cs10.apps.columns.view.GameView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    private final BallGenerator ballGenerator = new BallGenerator();
    private Player player1, player2;
    private GameView gameView;

    public void start(){
        player1 = new Player();
        player2 = new Player();
        gameView = new GameView();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::changeBlocks, 1,5, TimeUnit.SECONDS);
    }

    private void changeBlocks(){
        ThreeBalls threeBalls = ballGenerator.generate();
        gameView.getBlockView1().setBlock(threeBalls);
        gameView.getBlockView2().setBlock(threeBalls);
    }
}
