package cs10.apps.columns.model;

import cs10.apps.columns.core.Board;
import cs10.apps.columns.sound.SoundUtils;
import cs10.apps.columns.view.BallColor;

public class Player {
    private final int number;
    private int mainScore;
    private int smallScore;
    private int blockIndex;
    private Block nextBlock;
    private FallingBlock fallingBlock;
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

        if (cpu && !waitingForPushing){
            if (mainScore > 19 || height > 10) push();
        }
    }

    public void incrementSmallScore(int delta){
        smallScore += delta;
        auxiliarySmallScore += delta;
    }

    public void checkForMagicStone(){
        if (auxiliarySmallScore >= 20){
            auxiliarySmallScore = 0;
            SoundUtils.playSound("magicStone");
            board.explodeRandomColor();
        }
    }

    public void nextFallingBlock(){
        setFallingBlock(new FallingBlock(nextBlock, board, 2, cpu));

        if (cpu) {

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

            fallingBlock.setTargetColumn(column);

            if (needsToRotate){
                fallingBlock.setAutoRotation(true);
                fallingBlock.setRotateTwoColorsDown(rotateTwoColorsToDown);

                if (targetBall > 0){
                    if (targetBall == 3) targetBall = 0;
                    fallingBlock.setTargetRotations(3-targetBall);
                    //int count = 3 - targetBall;
                    //for (int i=0; i<count; i++) getFallingBlock().rotate();
                } /*else {
                    if (rotateTwoColorsToDown) while (!getFallingBlock().sameColor(1,2))
                        getFallingBlock().rotate();
                    else while (!getFallingBlock().sameColor(0,1))
                        getFallingBlock().rotate();
                }*/
            }
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

                    if (otherPlayerReference.getFallingBlock().getPosition().getY() > 0){
                        SoundUtils.playSound("destroyFallingBlock");
                        otherPlayerReference.nextFallingBlock();
                    }

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setOtherPlayerReference(Player otherPlayerReference) {
        this.otherPlayerReference = otherPlayerReference;
    }
}
