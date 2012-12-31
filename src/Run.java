import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class Run extends State {

	public Run(StateManager p) {
		board = p;
	}
	
	@Override
	public void draw(Graphics2D ga) {
		if (!board.gameIsRunning()) {
			String s = "Press Start";
			ga.setColor(Color.RED);
			ga.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			ga.drawChars(s.toCharArray(), 0, s.length(), board.getWidth()/2 - 55, board.getHeight()/2);
		}
	}

	@Override
	public void pause() {
		board.changeState(board.getPauseState());
	}

	@Override
	public void gameover() {
		board.changeState(board.getGameOverState());
		board.notifyGui();
	}

	@Override
	public void run() {
		
		
	}


}
