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
        player1 = new Player(1);
        player2 = new Player(2);
        gameView = new GameView();

        // AUTO-PLAYER 1
        ScheduledExecutorService ses1 = Executors.newSingleThreadScheduledExecutor();
        ses1.scheduleAtFixedRate(() -> changeBlocks(player1), 1,5, TimeUnit.SECONDS);

        // AUTO-PLAYER 2
        ScheduledExecutorService ses2 = Executors.newSingleThreadScheduledExecutor();
        ses2.scheduleAtFixedRate(() -> changeBlocks(player2), 1,8, TimeUnit.SECONDS);
    }

    private void changeBlocks(Player player){
        ThreeBalls threeBalls = ballGenerator.getNext(player.getBlockIndex());
        gameView.getBlockView(player.getNumber()).setBlock(threeBalls);
        player.incrementBlockIndex();
    }
}
