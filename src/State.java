import java.awt.Graphics2D;


public abstract class State {
	
	StateManager board;
	
	public abstract void draw(Graphics2D ga);
	public abstract void run();
	public abstract void pause();
	public abstract void gameover();
	

}
