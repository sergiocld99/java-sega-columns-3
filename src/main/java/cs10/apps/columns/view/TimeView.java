package cs10.apps.columns.view;

import javax.swing.*;
import java.awt.*;

public class TimeView extends JPanel {
    private final JLabel label;

    public TimeView(){
        setPreferredSize(new Dimension(130,70));
        setBackground(Color.black);
        label = new JLabel("0:00");
        label.setPreferredSize(new Dimension(130, 55));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Century Gothic", Font.BOLD, 50));
        label.setForeground(Color.cyan);
        add(label);
    }

    public void setSeconds(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        label.setText(minutes + ":" + ((seconds < 10) ? "0" : "") + seconds);
    }
}
