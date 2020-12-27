package cs10.apps.columns.model;

public class AutoMoveParameters {
    private boolean autoRotation, rotateTwoColorsDown;
    private int targetColumn, targetRotations;

    public boolean isAutoRotation() {
        return autoRotation;
    }

    public void setAutoRotation(boolean autoRotation) {
        this.autoRotation = autoRotation;
    }

    public boolean isRotateTwoColorsDown() {
        return rotateTwoColorsDown;
    }

    public void setRotateTwoColorsDown(boolean rotateTwoColorsDown) {
        this.rotateTwoColorsDown = rotateTwoColorsDown;
    }

    public int getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(int targetColumn) {
        this.targetColumn = targetColumn;
    }

    public int getTargetRotations() {
        return targetRotations;
    }

    public void setTargetRotations(int targetRotations) {
        this.targetRotations = targetRotations;
    }

    public void decreaseTargetRotations(){
        this.targetRotations--;
    }

    @Override
    public String toString() {
        return "autoRotation=" + autoRotation +
                "\n rotateTwoColorsDown=" + rotateTwoColorsDown +
                "\n targetColumn=" + targetColumn +
                "\n targetRotations=" + targetRotations;
    }
}
