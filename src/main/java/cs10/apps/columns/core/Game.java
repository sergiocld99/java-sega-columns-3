package cs10.apps.columns.core;

import cs10.apps.columns.model.Player;
import cs10.apps.columns.model.Block;
import cs10.apps.columns.view.GameView;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    private final BlockGenerator blockGenerator = new BlockGenerator();
    private final Player player1, player2;
    private final GameView gameView;
    private int secondsActual = 0;

    public Game(){
        player1 = new Player(1);
        player2 = new Player(2);
        gameView = new GameView();

        player1.setBoard(new Board());
        player2.setBoard(new Board());
        updateNextBlock(player1, false);
        updateNextBlock(player2, false);
    }

    public void start(){
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        // AUTO-FALL BLOCK
        ScheduledExecutorService autoFallService = Executors.newSingleThreadScheduledExecutor();
        autoFallService.scheduleAtFixedRate(() -> {
            boolean could = player1.getFallingBlock().getPosition().moveDown();
            if (!could) nextFallingBlock(player1);
        }, 1200,500, TimeUnit.MILLISECONDS);


        // AUTO-UPDATE BOARD VIEW
        ScheduledExecutorService fallViewService = Executors.newSingleThreadScheduledExecutor();
        fallViewService.scheduleAtFixedRate(() -> {
            gameView.getBoardView(1).paintFallingBlock(player1.getFallingBlock());
        }, 50, 20, TimeUnit.MILLISECONDS);


        // TIME
        ScheduledExecutorService ses3 = Executors.newSingleThreadScheduledExecutor();
        ses3.scheduleAtFixedRate(() -> {
                gameView.getTimeView().setSeconds(++secondsActual);
                if (secondsActual == 3600) ses3.shutdown();
            }, 1,1, TimeUnit.SECONDS);


        // MUSIC
        playBgMusic();

        nextFallingBlock(player1);
    }

    private void nextFallingBlock(Player player){
        player.nextFallingBlock();
        updateNextBlock(player, true);
    }

    private void updateNextBlock(Player player, boolean delay){
        System.out.println("Changing next block");
        Block block = blockGenerator.getNext(player.getBlockIndex());
        player.setNextBlock(block);
        player.incrementBlockIndex();

        new Thread(() -> {
            if (delay) try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            gameView.getNextBlockView(player.getNumber()).setBlock(block);
        }).start();
    }

    private void playBgMusic(){
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                File file = new File("src/main/resources/explorer.wav");
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
