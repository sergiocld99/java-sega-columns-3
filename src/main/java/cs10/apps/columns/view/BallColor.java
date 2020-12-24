package cs10.apps.columns.view;

import java.awt.*;

public enum BallColor {
    RED(new Color(250, 45, 45)),
    ORANGE(new Color(238, 114, 26)),
    YELLOW(new Color(239, 248, 2)),
    GREEN(new Color(14, 165, 14)),
    BLUE(new Color(8, 252, 201)),
    PURPLE(new Color(252, 3, 252));

    private final Color color;

    BallColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
