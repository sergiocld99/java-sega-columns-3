package cs10.apps.columns.core;

import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.InsideBallLocation;
import cs10.apps.columns.model.ThreeBalls;
import cs10.apps.columns.view.BallColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallGenerator {
    private final Random random = new Random();
    private final List<ThreeBalls> list = new ArrayList<>(64);

    public BallGenerator() {
        for (int i=0; i<64; i++){
            list.add(generate());
        }
    }

    public ThreeBalls getNext(int position){
        return list.get(position);
    }

    private ThreeBalls generate(){
        Ball ball1 = new Ball(InsideBallLocation.UP, generateColor());
        Ball ball2 = new Ball(InsideBallLocation.MEDIUM, generateColor());
        Ball ball3 = new Ball(InsideBallLocation.DOWN, generateColor());
        return new ThreeBalls(ball1, ball2, ball3);
    }

    private BallColor generateColor(){
        BallColor[] ballColors = BallColor.values();
        return ballColors[random.nextInt(ballColors.length)];
    }
}
