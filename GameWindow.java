import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener , KeyListener {
    private JPanel mainPanel;
    private JPanel infoPanel;
    private GamePanel gamePanel;
    private JLabel statusBar;
    private JLabel keyPressedBar;
    private JLabel scoreBar;
    private Container c;

    public static JTextField status;
    private JTextField keyPressed;

    public static JTextField score;

    private JButton startB;
    private JButton stopB;
    private JButton musicB;
    public static Integer count = 0;
    public static Integer count2 = 0;
    private SoundManager soundManager;
    public static boolean muted = false;
    

    public GameWindow(){
        setTitle("A Game");
        setSize(700, 600);

        statusBar = new JLabel("Status:");
        statusBar.setForeground(Color.WHITE);
        status = new JTextField(25);
        status.setEditable(false);
        keyPressedBar = new JLabel("Key Pressed:");
        keyPressedBar.setForeground(Color.WHITE);
        keyPressed = new JTextField(25);
        keyPressed.setEditable(false);

        scoreBar = new JLabel("Score:");
        scoreBar.setForeground(Color.WHITE);
        score = new JTextField(25);
        score.setEditable(false);

        infoPanel = new JPanel();
        GridLayout grid = new GridLayout(3, 2);
        infoPanel.setBackground(new Color(27, 34, 89));
        infoPanel.setLayout(grid);
        infoPanel.add(statusBar);
        infoPanel.add(status);
        infoPanel.add(keyPressedBar);
        infoPanel.add(keyPressed);
        infoPanel.add(scoreBar);
        infoPanel.add(score);

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(600, 400));

        
        JPanel buttonPanel = new JPanel();
		grid = new GridLayout(1, 3);
		buttonPanel.setLayout(grid);
        startB = new JButton ("Start");
        stopB = new JButton ("Stop");
        musicB = new JButton("music");
        musicB.setBackground(new Color(125, 209, 148));
        startB.addActionListener(this);
        stopB.addActionListener(this);
        musicB.addActionListener(this);
		buttonPanel.add (startB);
        buttonPanel.add(stopB);
        buttonPanel.add(musicB);

        mainPanel = new JPanel();
        FlowLayout flow = new FlowLayout();
        mainPanel.setLayout(flow);
        mainPanel.add(infoPanel);
        mainPanel.add(gamePanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(new Color(108, 121, 163));
        mainPanel.addKeyListener(this);

        c = getContentPane();
        c.add(mainPanel);

        setVisible(true);
        status.setText("Waiting for start");

        soundManager = SoundManager.getInstance();
    }



    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP){
            keyPressed.setText("Up arrow pressed");
            gamePanel.moveHero("up");
        }
        if(key == KeyEvent.VK_DOWN){
            keyPressed.setText("Down arrow pressed");
            gamePanel.moveHero("down");

        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals(startB.getText())){
            soundManager.playClip("btnPress", false);
            gamePanel.startGame();
            mainPanel.requestFocusInWindow();

        }    
        if(command.equals(stopB.getText())){
            soundManager.playClip("btnPress", false);
            System.exit(0);
        }
        if(command.equals("music")){
            mainPanel.requestFocusInWindow();
            soundManager.playClip("btnPress", false);
            if(muted){
                musicB.setBackground(new Color(125, 209, 148));
                soundManager.setVolume("game", 1.0f);
                muted = false;
            }
            else{
                musicB.setBackground(new Color(238, 238, 238));
                soundManager.setVolume("game", 0.0f);
                muted = true;
            }
        }
    }
}
