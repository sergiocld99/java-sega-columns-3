package cs10.apps.columns.model;

import cs10.apps.columns.core.Board;

public class Player {
    private int number, mainScore, smallScore, blockIndex;
    private Block nextBlock;
    private FallingBlock fallingBlock;
    private Board board;

    public Player(int number) {
        this.number = number;
    }

    public void incrementBlockIndex(){
        if (++blockIndex == 64) blockIndex = 0;
    }

    public void nextFallingBlock(){
        System.out.println("Changing falling block");
        setFallingBlock(new FallingBlock(nextBlock, board));
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMainScore() {
        return mainScore;
    }

    public void setMainScore(int mainScore) {
        this.mainScore = mainScore;
    }

    public int getSmallScore() {
        return smallScore;
    }

    public void setSmallScore(int smallScore) {
        this.smallScore = smallScore;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }

    public FallingBlock getFallingBlock() {
        while (fallingBlock == null) System.out.println("I'm waiting for falling block");
        return fallingBlock;
    }

    public void setFallingBlock(FallingBlock fallingBlock) {
        this.fallingBlock = fallingBlock;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
