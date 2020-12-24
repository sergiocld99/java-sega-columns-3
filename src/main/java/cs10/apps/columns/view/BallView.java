package cs10.apps.columns.view;

import cs10.apps.columns.model.Ball;

import javax.swing.*;
import java.awt.*;

public class BallView extends JLabel {
    private Ball ball;

    public BallView() {
        setMinimumSize(new Dimension(80,80));
    }

    public void setBall(Ball ball) {
        this.ball = ball;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50,50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        paintAbsolutePosition(0,0, graphics2D);
    }

    public void paintAbsolutePosition(int x, double y, Graphics2D graphics2D){
        if (ball == null){
            graphics2D.setColor(Color.black);
            graphics2D.fillOval(x+5,(int) y+5,40,40);
            return;
        }

        graphics2D.setColor(ball.getColorBall().getColor());
        graphics2D.fillOval(x+5,(int) y+5,40,40);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.fillOval(x+28,(int) y+12,11,9);
        graphics2D.drawOval(x+5,(int) y+5,40,40);
    }
}
