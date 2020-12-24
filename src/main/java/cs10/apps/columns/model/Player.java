package cs10.apps.columns.model;

public class Player {
    private int number, mainScore, secondaryScore, blockIndex;
    private ThreeBalls nextThreeBalls;

    public Player(int number) {
        this.number = number;
    }

    public void incrementBlockIndex(){
        if (blockIndex == 64) blockIndex = 0;
        else blockIndex++;
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

    public int getSecondaryScore() {
        return secondaryScore;
    }

    public void setSecondaryScore(int secondaryScore) {
        this.secondaryScore = secondaryScore;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }

    public ThreeBalls getNextThreeBalls() {
        return nextThreeBalls;
    }

    public void setNextThreeBalls(ThreeBalls nextThreeBalls) {
        this.nextThreeBalls = nextThreeBalls;
    }
}
