import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PongBoard extends JPanel implements BallObserver {
	
	private Integer boardTop;
	private Integer boardWidth;
	private Integer boardBottom;
	
	private final Ball ball;
	private final Paddle rPaddle;
	private final Paddle lPaddle;

	private IStateM stateM;
	private final Observer obs;
	
	public void moveBall() {
		ball.moveBall();
	}

	public PongBoard(Observer obs, IStateM stateM)
	{
		this.stateM = stateM;
		ball = new Ball(this);
		rPaddle = new Paddle(Direction.RIGHT, 10, 10);
		lPaddle = new Paddle(Direction.LEFT, 10, 10);
		
		this.setPreferredSize(new Dimension(500, 350));
		this.obs = obs;
	}

	public void initGame()
	{
		rPaddle.setX(10);
		rPaddle.setY(10);
		lPaddle.setX(10);
		lPaddle.setY(10);

		boardTop = 0;
		boardBottom = 350;
		boardWidth = 500;

		ball.resetBallPosition();
	}
	
	public void repaintIt()
	{
		this.repaint();
	}
	
	public void notifyGui() {
		obs.gameNotify();
	}

	public void paint(Graphics g) {
		Graphics2D ga = (Graphics2D)g;

		ga.setColor(Color.WHITE);
		ga.clearRect(0, 0, this.getWidth(), this.getHeight());
		boardWidth = this.getWidth() - 20;
		boardBottom = this.getHeight() - 20;
		
		ga.setColor(Color.BLACK);
		ga.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
		ga.setBackground(Color.BLACK);
		ga.setColor(Color.WHITE);
		ga.fillRect(rPaddle.getX(), rPaddle.getY(), Config.PADDLE_SIZEX, Config.PADDLE_SIZEY);
		
		lPaddle.setX(this.getWidth()-40);
		
		ga.fillRect(lPaddle.getX(), lPaddle.getY(), Config.PADDLE_SIZEX, Config.PADDLE_SIZEY);
		ga.fillArc(ball.getBallX(), ball.getBallY(), 20, 20, 90, 360);
		
	/* Draw Dashes */
		int halfMark = this.getWidth()/2;
		int spaceBetweenDash = 20;
		int lengthOfDash = 5;
		ga.setStroke(new BasicStroke(5));
		for (int i = 0; i < this.getHeight(); i += spaceBetweenDash)
			ga.drawLine(halfMark, i, halfMark, i+lengthOfDash);

		stateM.drawState(ga);
	}

	public void lPaddlePlayer()
	{
		if (ball.getBallY() > (lPaddle.getY() + 50))
			lPaddle.setY( (lPaddle.getY() < (boardBottom - 80) ) ? Config.MOVEMENT_SPEED + lPaddle.getY() : lPaddle.getY() );
		else if (ball.getBallY() <= (lPaddle.getY() + 50))
			lPaddle.setY( (lPaddle.getY() > (boardTop + 5) ) ? lPaddle.getY() - Config.MOVEMENT_SPEED : lPaddle.getY() );
	}
	
	public boolean isBallTouchingPaddle(Integer x, Integer y)
	{
		Integer distX = Math.abs(ball.getBallX() - x);
		int yPos = ball.getBallY();
		if ( yPos >= y && yPos <= (Config.PADDLE_SIZEY + y) && distX < 10 ) {
			return true;
		}
		
		return false;
	}

	public void movePaddleUp()
	{
		rPaddle.setYTop(boardTop);
	}
	
	public void movePaddleDown()
	{
		rPaddle.setYBottom(boardBottom);
	}

	@Override
	public void ballMoved() {

		if (isBallTouchingPaddle(rPaddle.getX(), rPaddle.getY()))
			ball.changeBallXDirection(Direction.RIGHT);
		
		if (isBallTouchingPaddle(lPaddle.getX(), lPaddle.getY()))
			ball.changeBallXDirection(Direction.LEFT);
		
		if (ball.getBallY() > boardBottom)
			ball.changeBallYDirection();
		else if (ball.getBallY() < 1)
			ball.changeBallYDirection();
		
		if (ball.getBallX() > boardWidth) {
			Config.winner = Player.ONE;
			stateM.gameOver();
		} else if (ball.getBallX() < 1) {
			Config.winner = Player.TWO;
			stateM.gameOver();
		} 
	}
}
