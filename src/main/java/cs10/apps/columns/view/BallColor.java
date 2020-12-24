package cs10.apps.columns.view;

import java.awt.*;

public enum BallColor {
    RED(new Color(255, 0, 0)),
    ORANGE(new Color(200,100,0)),
    YELLOW(Color.yellow),
    GREEN(new Color(14, 165, 14)),
    BLUE(Color.cyan),
    PURPLE(new Color(252, 3, 252));

    private final Color color;

    BallColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
