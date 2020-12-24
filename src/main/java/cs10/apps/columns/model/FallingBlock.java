package cs10.apps.columns.model;

import cs10.apps.columns.core.Board;

public class FallingBlock extends Block {
    private final Position position;

    public FallingBlock(Block block, Board referenceBoard){
        super(block.getBall1(), block.getBall2(), block.getBall3());
        position = new Position(2,-1,referenceBoard);
    }

    public void rotate() {
        Ball[] result = new Ball[3];
        result[0] = balls[2];
        result[1] = balls[0];
        result[2] = balls[1];
        balls = result;
    }

    public Position getPosition() {
        return position;
    }
}
