import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameOver extends State {
	
	public GameOver(StateManager p) {
		board = p;
	}
	
	@Override
	public void draw(Graphics2D ga) {
		ga.setColor(Color.RED);
		ga.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		ga.drawChars("Game Over".toCharArray(), 0, 9, board.getWidth()/2 - 55, board.getHeight()/2);
		//board.gameNotify();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		board.changeState(board.getRunState());
		board.res();
	}
}
