import java.awt.Graphics2D;


public class GameManager implements StateManager, IStateM {
	
	private final Object pausedObj = new Object();
	
	Thread b;
	Thread c;
	Thread e;
	
	PongBoard board;
	
	private State runS;
	private State pauseS;
	private State gameoverS;
	private State state;
	
	public GameManager(GameGui gameGui) {
		runS = new Run(this);
		pauseS = new Pause(this);
		gameoverS = new GameOver(this);
		state = runS;
		board = new PongBoard(gameGui, this);
		
		Runnable a = new Runnable()
		{

			@Override
			public void run() {
				while (true) {
					board.moveBall();
					Helper.sleep(Config.BALL_SPEED);
					test();
				}
			}
			
		};
		b = new Thread(a);

		Runnable lPlayer = new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					board.lPaddlePlayer();
					Helper.sleep(Config.PADDLE_SPEED);
					test();
				}
			}
		};
		
		c = new Thread(lPlayer);
		
		Runnable d = new Runnable()
		{
			@Override
			public void run() {
				while (true) {
					Helper.sleep(Config.REDRAW_SPEED);
					board.repaintIt();
					test();
				}
			}
		};
		
		e = new Thread(d);
	}
	
	public void pause()
	{
		state.pause();
	}
	
	public void resetGame()
	{
		initGame();
		board.repaintIt();
	}
	
	
	public void initGame()
	{
		board.initGame();
		state.run();
		
		if (!b.isAlive()) {
			b.start();
			c.start();
			e.start();
		}
	}
	
	
	
	private void test() {
		if (state instanceof GameOver || state instanceof Pause) {
			synchronized (pausedObj) {
				try {
					pausedObj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void res()
	{
		synchronized(pausedObj) {
			pausedObj.notifyAll();
		}
		
	}

	public State getRunState() {
		return runS;
	}
	
	public State getPauseState() {
		return pauseS;
	}
	
	public State getGameOverState() {
		return gameoverS;
	}
	
	public void changeState(State s) {
		state = s;
	}
	
	public void drawState(Graphics2D ga) {
		state.draw(ga);
	}
	
	public void gameOver() {
		state.gameover();
	}

	@Override
	public int getWidth() {
		return board.getWidth();
	}

	@Override
	public int getHeight() {
		return board.getHeight();
	}

	@Override
	public void notifyGui() {
		board.notifyGui();
		
	}
	
	public boolean gameIsRunning() {
		return e.isAlive();
	}

}
