package application.game_logic;
import application.game_menu.GameMenu;
import javafx.scene.image.Image;

public class Bullet extends Sprite {
	private final static int BULLET_SPEED = 10;
	public final static Image BULLET_IMAGE = new Image("Bullet.gif", Bullet.BULLET_WIDTH, Bullet.BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 70;
	
	public Bullet(int x, int y){
		super(x,y);
		this.loadImage(Bullet.BULLET_IMAGE);
	}
	
	public int getX() {
		return this.x;
	}


	//method that will move/change the x position of the bullet 
	public void move(){
		
		// update/change the bullet x position depending on the bullet speed
		this.x += Bullet.BULLET_SPEED;
		
		// check if he x position has reached the right boundary of the screen
        if (this.getX() > GameMenu.WINDOW_WIDTH) {
        	//set the bullet's visibility to false
            this.setVisible(false);
        }
	}
}
