package cs10.apps.columns.view;

import cs10.apps.columns.model.Ball;

import javax.swing.*;
import java.awt.*;

public class BallView extends JLabel {
    private Ball ball;
    private ItemMagicStone itemMagicStone;

    public BallView() {
        setMinimumSize(new Dimension(80,80));
    }

    public void setBall(Ball ball) {
        this.itemMagicStone = null;
        this.ball = ball;
        repaint();
    }

    public void setItemMagicStone(ItemMagicStone itemMagicStone) {
        this.itemMagicStone = itemMagicStone;
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
        if (itemMagicStone != null){
            if (itemMagicStone == ItemMagicStone.DOWN_TRIANGLE) drawDownTriangle(x,y,graphics2D);
            else if (itemMagicStone == ItemMagicStone.UP_TRIANGLE) drawUpTriangle(x,y,graphics2D);
            else drawSquare(x,y,graphics2D);
            return;
        }

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

    private void drawUpTriangle(int x, double y, Graphics2D graphics2D){
        graphics2D.setColor(Color.white);
        Polygon polygon = new Polygon();
        polygon.addPoint(x+5,(int) y+45);
        polygon.addPoint(x+25,(int) y+5);
        polygon.addPoint(x+45, (int) y+45);
        graphics2D.fillPolygon(polygon);
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawPolygon(polygon);
    }

    private void drawDownTriangle(int x, double y, Graphics2D graphics2D){
        graphics2D.setColor(Color.white);
        Polygon polygon = new Polygon();
        polygon.addPoint(x+5,(int) y+5);
        polygon.addPoint(x+25,(int) y+45);
        polygon.addPoint(x+45, (int) y+5);
        graphics2D.fillPolygon(polygon);
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawPolygon(polygon);
    }

    private void drawSquare(int x, double y, Graphics2D graphics2D){
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(x+5,(int) y+5,40,40);
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawRect(x+5,(int) y+5,40,40);
    }
}
