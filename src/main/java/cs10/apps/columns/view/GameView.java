package cs10.apps.columns.view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private final BoardView[] boardViews = new BoardView[2];
    private final BlockView[] nextBlockViews = new BlockView[2];
    private final MainScoreView[] mainScoreViews = new MainScoreView[2];
    private final SmallScoreView[] smallScoreViews = new SmallScoreView[2];
    private TimeView timeView;

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

        boardViews[0] = addPlayerBoardPanel(mainPanel, 0);
        nextBlockViews[0] = addNextBlockPanel(mainPanel, 1);
        nextBlockViews[1] = addNextBlockPanel(mainPanel, 2);
        boardViews[1] = addPlayerBoardPanel(mainPanel, 3);
        mainScoreViews[0] = addPlayerMainScorePanel(mainPanel, 1);
        mainScoreViews[1] = addPlayerMainScorePanel(mainPanel, 2);
        smallScoreViews[0] = addPlayerSmallScorePanel(mainPanel, 1);
        smallScoreViews[1] = addPlayerSmallScorePanel(mainPanel, 2);
        addTimePanel(mainPanel);
        addCharacterPanel(mainPanel);
        setVisible(true);
    }

    private BoardView addPlayerBoardPanel(JPanel mainPanel, int xPos){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xPos;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridheight = 5;
        gbc.insets = new Insets(0, 30, 0, 30);
        BoardView boardView = new BoardView();
        mainPanel.add(boardView, gbc);
        return boardView;
    }

    private BlockView addNextBlockPanel(JPanel mainPanel, int xPos){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xPos;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 30, 0, 30);

        BlockView blockView = new BlockView();
        mainPanel.add(blockView, gbc);
        return blockView;
    }

    private MainScoreView addPlayerMainScorePanel(JPanel mainPanel, int xPos){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xPos;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 30, 10, 30);

        MainScoreView mainScoreView = new MainScoreView();
        mainPanel.add(mainScoreView, gbc);
        return mainScoreView;
    }

    private SmallScoreView addPlayerSmallScorePanel(JPanel mainPanel, int xPos){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = xPos;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 30, 10, 30);

        SmallScoreView smallScoreView = new SmallScoreView();
        mainPanel.add(smallScoreView, gbc);
        return smallScoreView;
    }

    private void addTimePanel(JPanel mainPanel){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 30, 10, 30);

        timeView = new TimeView();
        mainPanel.add(timeView, gbc);
    }

    private void addCharacterPanel(JPanel mainPanel){
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(50, 30, 20, 30);
        mainPanel.add(panel, gbc);
    }

    public BoardView getBoardView(int playerNumber){
        return boardViews[playerNumber-1];
    }

    public BlockView getNextBlockView(int playerNumber){
        return nextBlockViews[playerNumber-1];
    }

    public MainScoreView getMainScoreView(int playerNumber){
        return mainScoreViews[playerNumber-1];
    }

    public SmallScoreView getSmallScoreView(int playerNumber){
        return smallScoreViews[playerNumber-1];
    }

    public TimeView getTimeView() {
        return timeView;
    }
}
