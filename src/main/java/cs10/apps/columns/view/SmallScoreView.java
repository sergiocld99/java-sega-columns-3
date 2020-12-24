package cs10.apps.columns.view;

import javax.swing.*;
import java.awt.*;

public class SmallScoreView extends JPanel {
    private final JLabel label;

    public SmallScoreView(){
        setPreferredSize(new Dimension(60,30));
        setBackground(Color.black);
        label = new JLabel("0");
        label.setPreferredSize(new Dimension(40, 20));
        label.setFont(new Font("Century Gothic", Font.BOLD, 20));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setForeground(Color.white);
        add(label);
    }

    public void setNumber(int number) {
        label.setText(String.valueOf(number));
    }
}
