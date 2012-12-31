import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GameGui extends JApplet implements Observer{
	
	GameManager game;
	JButton startButton;
	JLabel p1ScoreL;
	JLabel p2ScoreL;
	
	boolean keyPressed = false;
	
	public static void main(String[] args) {
		new GameGui();
	}
	
	public void init()
	{
		JPanel finalPanel = new JPanel();
		finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
		
		JPanel genInfo = new JPanel();
	        
        startButton = new JButton("START");
        JButton endButton = new JButton("END");
        
        startButton.setFocusable(false);
        endButton.setFocusable(false);
        
        genInfo.add(startButton);
        genInfo.add(endButton);
        endButton.addActionListener(new EndPopUp());
        startButton.addActionListener(new resetGame());

        
        JPanel p1Score = new JPanel();
        p1Score.add(new JLabel("Player One"));
        p1ScoreL = new JLabel("0");
        p1Score.add(p1ScoreL);
        
        JPanel p2Score = new JPanel();
        p2Score.add(new JLabel("Player Two"));
        p2ScoreL = new JLabel("0");
        p2Score.add(p2ScoreL);
        
        
        JPanel configP = new JPanel();
        JSlider ballS = new JSlider(JSlider.HORIZONTAL, Config.MIN_BALL_SPEED, Config.MAX_BALL_SPEED, Config.BALL_SPEED);
        ballS.addChangeListener(new ballConfig());
        ballS.setMajorTickSpacing(5);
        ballS.setMinorTickSpacing(1);
        ballS.setPaintTicks(true);
        ballS.setPaintLabels(true);
        ballS.setFocusable(false);
        ballS.setBorder(new TitledBorder("Ball Speed"));
        
        JSlider paddleS = new JSlider(JSlider.HORIZONTAL, Config.MIN_PADDLE_SPEED, Config.MAX_PADDLE_SPEED, Config.PADDLE_SPEED);
        paddleS.addChangeListener(new paddleConfig());
        paddleS.setMajorTickSpacing(5);
        paddleS.setMinorTickSpacing(1);
        paddleS.setPaintTicks(true);
        paddleS.setPaintLabels(true);
        paddleS.setFocusable(false);
        paddleS.setBorder(new TitledBorder("Paddle Speed"));
        
        //configP.setFocusable(false);
        configP.add(ballS);
        configP.add(paddleS);
        
        
        
        JPanel scoreP = new JPanel();
        scoreP.add(p1Score);
        scoreP.add(p2Score);
        
        // JSlider 
        
		game = new GameManager(this);
		
		game.board.setFocusable(true);
        finalPanel.add(game.board);
        finalPanel.add(genInfo);
        
        
        addKeyListener(new DotRes());
        Container cp = getContentPane();
        
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        cp.add(finalPanel);
        cp.add(scoreP);
        cp.add(configP);

        setSize(600,500);
        //frame.setLocationRelativeTo(null); // center window
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        setFocusable(true);
        requestFocus();
	}

	class resetGame implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			//requestFocus();
			game.resetGame();
			startButton.setEnabled(false);
		}
	}
	
	class ballConfig implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if (!source.getValueIsAdjusting()) {
		        int ballSpeed = (int)source.getValue();
		        Config.BALL_SPEED = ballSpeed;
			}
			
			
		}
		
	}
	
	class paddleConfig implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if (!source.getValueIsAdjusting()) {
		        int paddleSpeed = (int)source.getValue();
		        Config.PADDLE_SPEED = paddleSpeed;
			}
			
			
		}
		
	}
	
	class DotRes implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e) {
			final int keyCode = e.getKeyCode();
			keyPressed = true;
			Runnable a = new Runnable() {

				@Override
				public void run() {
					if (keyCode == KeyEvent.VK_SPACE) {
						game.pause();
						keyPressed = false;
					}
					
					while(keyPressed) {
						switch (keyCode) {
						case KeyEvent.VK_UP:
							game.board.movePaddleUp();
							break;
						case KeyEvent.VK_DOWN:
							game.board.movePaddleDown();
							break;
						}
						Helper.sleep(Config.PADDLE_SPEED);

					}
				}
			};
			
			Thread b = new Thread(a);
			b.start();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keyPressed = false;
			
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}
	}

	@Override
	public void gameNotify() {
		startButton.setEnabled(true);
		
		if (Config.winner == Player.ONE) {
			Integer newVal = Integer.valueOf(p1ScoreL.getText()) + 1;
			p1ScoreL.setText( newVal.toString() );
		} else {
			Integer newVal = Integer.valueOf(p2ScoreL.getText()) + 1;
			p2ScoreL.setText( newVal.toString() );
		}
			
		
	}

}
