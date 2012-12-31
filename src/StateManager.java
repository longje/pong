
public interface StateManager {
	public void res();
	public void notifyGui();
	public State getPauseState();
	public State getGameOverState();
	public State getRunState();
	public void changeState(State s);
	public int getWidth();
	public int getHeight();
	public boolean gameIsRunning();
}
