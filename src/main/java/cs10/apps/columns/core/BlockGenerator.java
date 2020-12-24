package cs10.apps.columns.core;

import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.Block;
import cs10.apps.columns.view.BallColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockGenerator {
    private final Random random = new Random();
    private final List<Block> list = new ArrayList<>(64);

    public BlockGenerator() {
        for (int i=0; i<64; i++){
            list.add(generate());
        }
    }

    public Block getNext(int position){
        return list.get(position);
    }

    private Block generate(){
        Ball ball1 = new Ball(generateColor());
        Ball ball2 = new Ball(generateColor());
        Ball ball3 = new Ball(generateColor());
        return new Block(ball1, ball2, ball3);
    }

    private BallColor generateColor(){
        BallColor[] ballColors = BallColor.values();
        return ballColors[random.nextInt(ballColors.length)];
    }
}
