package cs10.apps.columns.view;

import cs10.apps.columns.core.Board;
import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.Block;
import cs10.apps.columns.model.FallingBlock;
import cs10.apps.columns.model.Position;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private final BallView falling1, falling2, falling3;
    private Position basePosition;

    public BoardView() {
        setLayout(null);
        setPreferredSize(new Dimension(300, 650));
        setBackground(Color.black);
        falling1 = new BallView();
        falling2 = new BallView();
        falling3 = new BallView();
    }

    public void paintFallingBlock(FallingBlock fallingBlock){
        falling1.setBall(fallingBlock.getBall1());
        falling2.setBall(fallingBlock.getBall2());
        falling3.setBall(fallingBlock.getBall3());
        basePosition = fallingBlock.getPosition();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        drawLines(graphics2D);
        drawFallingBlock(graphics2D);
    }

    private void drawFallingBlock(Graphics2D graphics2D){
        if (basePosition == null) return;
        falling3.paintAbsolutePosition(basePosition.getX()*50, basePosition.getY()*50, graphics2D);
        falling2.paintAbsolutePosition(basePosition.getX()*50, basePosition.getY()*50-50, graphics2D);
        falling1.paintAbsolutePosition(basePosition.getX()*50, basePosition.getY()*50-100, graphics2D);
    }

    private void drawLines(Graphics2D graphics2D){
        graphics2D.setColor(Color.gray);
        graphics2D.setStroke(new BasicStroke(1));

        for (int i=1; i<13; i++){
            graphics2D.drawLine(0,i*50, 300, i*50);
        }

        for (int i=1; i<6; i++){
            graphics2D.drawLine(i*50, 0, i*50, 650);
        }
    }
}
