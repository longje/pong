import java.util.Random;

enum Direction{UP, DOWN, LEFT, RIGHT};

public class Ball {

	private BallObserver obs;
	private Integer ballX;
	private Integer ballY;
	private Direction ballXD;
	private Direction ballYD;
	
	private Random randomForY;
	
	
	public Ball(BallObserver b) {
		randomForY = new Random();
		resetBallPosition();
		obs = b;
	}
	
	public void resetBallPosition() {
		ballX = Config.DEFAULT_BALL_X;
		ballY = randomForY.nextInt(300);
		ballXD = Direction.RIGHT;
		ballYD = Direction.DOWN;
	}
	
	public int getBallX()
	{
		return ballX;
	}
	
	public int getBallY()
	{
		return ballY;
	}
	
	public synchronized void moveX() {
		if (ballXD == Direction.LEFT)
			ballX--;
		else
			ballX++;
	}
	
	public synchronized void moveY() {
		if (ballYD == Direction.UP)
			ballY--;
		else
			ballY++;
	}
	
	public void changeBallXDirection(Direction d) {
		ballXD = d;
	}
	
	public void changeBallYDirection() {
		ballYD = (ballYD == Direction.UP) ? Direction.DOWN : Direction.UP;
	}
	
	public void moveBall()
	{
		moveX();
		moveY();
		
		obs.ballMoved();

	}
}
