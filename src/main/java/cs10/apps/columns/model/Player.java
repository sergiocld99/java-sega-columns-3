package cs10.apps.columns.model;

import cs10.apps.columns.core.BlockGenerator;
import cs10.apps.columns.core.Board;
import cs10.apps.columns.sound.SoundUtils;
import cs10.apps.columns.view.BallColor;

public class Player {
    private final int number;
    private int mainScore;
    private int smallScore;
    private int blockIndex;
    private FallingBlock nextBlock, fallingBlock;
    private final Board board;
    private Player otherPlayerReference;

    private final boolean cpu;
    private boolean waitingForPushing;
    private int auxiliarySmallScore, height;

    public Player(int number, boolean cpu) {
        this.number = number;
        this.cpu = cpu;
        this.board = new Board(this);
    }

    public void incrementBlockIndex(){
        if (++blockIndex == 64) blockIndex = 0;
    }

    public void incrementMainScore(int delta){
        mainScore = Math.min(mainScore + delta, 30);
    }

    public void incrementSmallScore(int delta){
        smallScore += delta;
        auxiliarySmallScore += delta;
    }

    public void checkForAutoPush(){

        if (!waitingForPushing){
            if ((mainScore >= 16 && mainScore <= 24) || height >= Board.MAX_Y - 3) push();
        }
    }

    public void nextFallingBlock(){
        AutoMoveParameters parameters = new AutoMoveParameters();

        if (cpu) {
            if (nextBlock.getMagicStone() != null) {
                parameters.setTargetColumn(2);
                parameters.setAutoRotation(true);

                switch (board.getRecommendedMagicStoneAction()) {
                    case DOWN_TRIANGLE -> parameters.setTargetRotations(3);
                    case SQUARE -> parameters.setTargetRotations(1);
                    case UP_TRIANGLE -> parameters.setTargetRotations(2);
                }

                fallingBlock = nextBlock;
                fallingBlock.setParameters(parameters);
                return;
            }

            BallColor repeated = nextBlock.containsRepeatedBall();
            boolean needsToRotate = repeated != null;
            boolean rotateTwoColorsToDown = false;
            int column, targetBall = 0;

            try {
                column = board.findColumnByColor(repeated);
            } catch (Exception e){
                System.err.println("Unable to get column color: " + e.getMessage());
                column = -1;
            }

            if (column == -1) {
                column = board.getShortestColumn();
                targetBall = nextBlock.containsColor(board.getColumnTopColor(column));
                if (targetBall > 0) needsToRotate = true;
            } else rotateTwoColorsToDown = true;

            //fallingBlock.setTargetColumn(column);
            parameters.setTargetColumn(column);

            if (needsToRotate){
                //fallingBlock.setAutoRotation(true);
                parameters.setAutoRotation(true);

                //fallingBlock.setRotateTwoColorsDown(rotateTwoColorsToDown);
                parameters.setRotateTwoColorsDown(rotateTwoColorsToDown);

                if (targetBall > 0){
                    if (targetBall == 3) targetBall = 0;
                    //fallingBlock.setTargetRotations(3-targetBall);
                    parameters.setTargetRotations(3-targetBall);
                }
            }

            fallingBlock = nextBlock;
            fallingBlock.setParameters(parameters);
        }
    }

    public int getNumber() {
        return number;
    }

    public int getMainScore() {
        return mainScore;
    }

    public void push(){
        if (mainScore < 10) return;
        waitingForPushing = true;

        if (otherPlayerReference != null){
            new Thread(() -> {
                try {
                    while (board.getExplosionHelper().isRunning()) Thread.sleep(50);
                    int lines = mainScore / 10;
                    board.decreaseLines(lines);
                    otherPlayerReference.getBoard().increaseLines(lines);
                    otherPlayerReference.destroyFallingBlock();
                    waitingForPushing = false;
                    mainScore -= lines * 10;
                } catch (InterruptedException e){
                    System.err.println("Push interrupted");
                }
            }).start();
        }
    }

    public int getSmallScore() {
        return smallScore;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public FallingBlock getNextBlock() {
        return nextBlock;
    }

    public void setNextBlock(Block nextBlock) {
        boolean magicStone = false;

        if (getAuxiliarySmallScore() >= MagicStone.OFFSET){
            setAuxiliarySmallScore(0);
            magicStone = true;
            SoundUtils.playSound("magicStone");
        }

        this.nextBlock = new FallingBlock(nextBlock, board, cpu, magicStone);
    }

    public FallingBlock getFallingBlock() {
        while (fallingBlock == null) System.out.println("I'm waiting for falling block");
        return fallingBlock;
    }

    public Board getBoard() {
        return board;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setOtherPlayerReference(Player otherPlayerReference) {
        this.otherPlayerReference = otherPlayerReference;
    }

    public void destroyFallingBlock(){
        if (fallingBlock.getPosition().getY() >= 0){
            SoundUtils.playSound("destroyFallingBlock");
            System.out.println(this + " falling block destroyed");
            nextFallingBlock();
            changeNextBlock();
        }
    }

    public void changeNextBlock(){
        Block block = BlockGenerator.getNext(getBlockIndex());
        incrementBlockIndex();
        setNextBlock(block);
    }

    public Player getOtherPlayerReference() {
        return otherPlayerReference;
    }

    public int getAuxiliarySmallScore() {
        return auxiliarySmallScore;
    }

    public void setAuxiliarySmallScore(int auxiliarySmallScore) {
        this.auxiliarySmallScore = auxiliarySmallScore;
    }

    @Override
    public String toString() {
        return "Player " + number;
    }
}
