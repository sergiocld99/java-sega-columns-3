package cs10.apps.columns.view;

import cs10.apps.columns.model.Block;
import cs10.apps.columns.model.FallingBlock;
import cs10.apps.columns.model.MagicStone;

import javax.swing.*;
import java.awt.*;

public class BlockView extends JPanel {
    private final BallView ballView1, ballView2, ballView3;

    public BlockView() {
        setBackground(Color.black);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(60, 150));

        ballView1 = new BallView();
        ballView2 = new BallView();
        ballView3 = new BallView();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor=GridBagConstraints.CENTER;
        add(ballView1, gbc);

        gbc.gridy=1;
        add(ballView2, gbc);

        gbc.gridy=2;
        add(ballView3, gbc);
    }

    public void setBlock(FallingBlock block) {
        MagicStone magicStone = block.getMagicStone();

        if (magicStone != null){
            ballView1.setItemMagicStone(magicStone.getItem(1));
            ballView2.setItemMagicStone(magicStone.getItem(2));
            ballView3.setItemMagicStone(magicStone.getItem(3));
        } else {
            ballView1.setBall(block.getBall1());
            ballView2.setBall(block.getBall2());
            ballView3.setBall(block.getBall3());
        }
    }
}
