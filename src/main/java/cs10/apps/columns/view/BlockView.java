package cs10.apps.columns.view;

import cs10.apps.columns.model.ThreeBalls;

import javax.swing.*;
import java.awt.*;

public class BlockView extends JPanel {
    private BallView ballView1, ballView2, ballView3;

    public BlockView() {
        setBackground(Color.black);
        setLayout(new GridLayout(3, 1));
        ballView1 = new BallView();
        ballView2 = new BallView();
        ballView3 = new BallView();

        add(ballView1);
        add(ballView2);
        add(ballView3);

        setPreferredSize(new Dimension(50, 150));
    }

    public void setBlock(ThreeBalls threeBalls) {
        ballView1.setBall(threeBalls.getBall1());
        ballView2.setBall(threeBalls.getBall2());
        ballView3.setBall(threeBalls.getBall3());
    }
}
