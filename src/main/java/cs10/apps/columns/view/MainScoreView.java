package cs10.apps.columns.view;

import javax.swing.*;
import java.awt.*;

public class MainScoreView extends JPanel {
    private final JLabel label;

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

    public void setNumber(int number) {
        label.setText(String.valueOf(number));
    }
}
