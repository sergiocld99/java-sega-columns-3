package cs10.apps.columns.view;

import cs10.apps.columns.core.Board;
import cs10.apps.columns.model.Ball;
import cs10.apps.columns.model.FallingBlock;
import cs10.apps.columns.model.FallingPosition;
import cs10.apps.columns.model.MagicStone;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private final BallView falling1, falling2, falling3;
    private Board board;
    private FallingPosition basePosition;

    public BoardView() {
        setLayout(null);
        setPreferredSize(new Dimension(300, 650));
        setBackground(Color.black);
        falling1 = new BallView();
        falling2 = new BallView();
        falling3 = new BallView();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void paintFallingBlock(FallingBlock fallingBlock){
        MagicStone magicStone = fallingBlock.getMagicStone();

        if (magicStone != null){
            falling1.setItemMagicStone(magicStone.getItem(1));
            falling2.setItemMagicStone(magicStone.getItem(2));
            falling3.setItemMagicStone(magicStone.getItem(3));
        } else {
            falling1.setBall(fallingBlock.getBall1());
            falling2.setBall(fallingBlock.getBall2());
            falling3.setBall(fallingBlock.getBall3());
        }

        basePosition = fallingBlock.getPosition();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        drawBackground(graphics2D);
        drawFallingBlock(graphics2D);
        drawActualBlocks(graphics2D);
        drawBars(graphics2D);
    }

    private void drawBars(Graphics2D graphics2D){
        if (board == null) return;
        int lines = Math.max(board.getLines(), 0);

        graphics2D.setColor(Color.darkGray);
        graphics2D.fillRect(0,650 - lines * 50, 300,lines * 50);
    }

    private void drawActualBlocks(Graphics2D graphics2D){
        if (board == null) return;
        for (int x=0; x<Board.MAX_X; x++){
            for (int y=0; y<Board.MAX_Y; y++){
                Ball ball = board.get(x,y);
                if (ball == null) continue;
                BallView ballView = new BallView();
                ballView.setBall(ball);
                ballView.paintAbsolutePosition(x*50, y*50, graphics2D);
            }
        }
    }

    private void drawFallingBlock(Graphics2D graphics2D){
        if (basePosition == null) return;
        falling3.paintAbsolutePosition(basePosition.getX()*50, basePosition.getY()*50, graphics2D);
        falling2.paintAbsolutePosition(basePosition.getX()*50, basePosition.getY()*50-50, graphics2D);
        falling1.paintAbsolutePosition(basePosition.getX()*50, basePosition.getY()*50-100, graphics2D);
    }

    private void drawBackground(Graphics2D graphics2D){
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
