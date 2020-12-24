package cs10.apps.columns.view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private BlockView blockView1, blockView2;

    public GameView(){
        getContentPane().setBackground(Color.yellow);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);

        getContentPane().setLayout(new GridLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.yellow.darker());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10,5 ,10,5 );
        getContentPane().add(mainPanel, gbc);

        addPlayerBoardPanel(mainPanel, 0);
        blockView1 = addNextBlockPanel(mainPanel, 1);
        blockView2 = addNextBlockPanel(mainPanel, 2);
        addPlayerBoardPanel(mainPanel, 3);
        setVisible(true);
    }

    private BlockView addNextBlockPanel(JPanel mainPanel, int xPos){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xPos;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 30, 10, 30);

        BlockView blockView = new BlockView();
        mainPanel.add(blockView, gbc);
        return blockView;
    }

    private void addPlayerBoardPanel(JPanel mainPanel, int xPos){
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(300, 650));
        boardPanel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xPos;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 30, 0, 30);
        mainPanel.add(boardPanel, gbc);
    }

    public BlockView getBlockView1() {
        return blockView1;
    }

    public void setBlockView1(BlockView blockView1) {
        this.blockView1 = blockView1;
    }

    public BlockView getBlockView2() {
        return blockView2;
    }

    public void setBlockView2(BlockView blockView2) {
        this.blockView2 = blockView2;
    }
}
