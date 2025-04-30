package application.game_logic;

public class PowerUp extends Sprite{
	public final static int POWERUP_WIDTH=60;		// power up width
	public final static int POWERUP_HEIGHT=70;		// power up height
	public final static int DURATION = 5;			// power up duration
	
	// Constructor
	public PowerUp(int xPos, int yPos) {
		super(xPos, yPos);
	}
}
