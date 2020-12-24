package cs10.apps.columns.core;

import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.BallInsideLocation;
import cs10.apps.columns.model.ThreeBalls;
import cs10.apps.columns.view.BallColor;

import java.util.Random;

public class BallGenerator {
    private final Random random = new Random();

    public ThreeBalls generate(){
        Ball ball1 = new Ball(BallInsideLocation.UP, generateColor());
        Ball ball2 = new Ball(BallInsideLocation.MEDIUM, generateColor());
        Ball ball3 = new Ball(BallInsideLocation.DOWN, generateColor());
        return new ThreeBalls(ball1, ball2, ball3);
    }

    private BallColor generateColor(){
        BallColor[] ballColors = BallColor.values();
        return ballColors[random.nextInt(ballColors.length)];
    }
}
