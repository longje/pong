
public class Paddle {
	
	
	
	private Integer x;
	private Integer y;
	private final Direction swingDirection;
	
	public Paddle(Direction d, int x, int y)
	{
		swingDirection = d;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setYBottom(int bottomBoundary) {
		y += (y < (bottomBoundary - 80) ) ? Config.MOVEMENT_SPEED : 0;
	}
	
	public void setYTop(int topBoundary) {
		y -= (y > (topBoundary + 5) ) ? Config.MOVEMENT_SPEED : 0;
	}

}
