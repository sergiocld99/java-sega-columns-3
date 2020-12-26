package cs10.apps.columns.view;

import cs10.apps.columns.core.Board;
import cs10.apps.columns.model.Player;

import javax.swing.*;
import java.awt.*;

public class MainScoreView extends JPanel {
    private final JLabel label;
    private Player player;

    public MainScoreView(){
        setPreferredSize(new Dimension(60,50));
        setBackground(Color.black);
        label = new JLabel("0");
        label.setPreferredSize(new Dimension(50, 40));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Century Gothic", Font.BOLD, 40));
        label.setForeground(Color.cyan);
        add(label);
    }

    public void update(){
        if (player == null) return;

        this.setNumber(player.getMainScore());
        if (player.getHeight() >= Board.MAX_Y - 3){
            label.setForeground(Color.red);
        } else label.setForeground(Color.cyan);
    }

    private void setNumber(int number) {
        label.setText(String.valueOf(number));
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
