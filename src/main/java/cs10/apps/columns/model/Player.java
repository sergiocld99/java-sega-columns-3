package cs10.apps.columns.model;

public class Player {
    private int mainScore, secondaryScore;
    private ThreeBalls nextThreeBalls;

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

    public ThreeBalls getNextThreeBalls() {
        return nextThreeBalls;
    }

    public void setNextThreeBalls(ThreeBalls nextThreeBalls) {
        this.nextThreeBalls = nextThreeBalls;
    }
}
