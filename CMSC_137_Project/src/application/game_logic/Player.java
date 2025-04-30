package application.game_logic;

import java.util.ArrayList;
import java.util.Random;

import application.game_menu.GameMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Sprite {
	private String name;
	private int strength;
	private boolean alive;
	
	private ArrayList<Bullet> bullets; 
	private PowerUp powerUp = null;		// collected power up 
	
	private ImageView playerImage;
	public static final int PLAYER_HEIGHT = 90;
	public static final int PLAYER_WIDTH = 120;
	
	public Player(String name, int x, int y){
		super(x,y);
		this.name = name;
		
		// set the image of the Mage 
//		this.normalMageMode();
		
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
	}
		
		public void setPlayerImage(String iamgePath) {
			this.playerImage = new ImageView();
			Image newImage = new Image(getClass().getResource(iamgePath).toExternalForm());
			this.playerImage.setImage(newImage);
			this.playerImage.setFitWidth(Player.PLAYER_WIDTH); // Set width to 200 pixels
			this.playerImage.setFitHeight(Player.PLAYER_HEIGHT); 
		}
		
		public ImageView getPlayerImage() {
			return this.playerImage;
		} 
		
		// method if mage got damage
		public void gotDamage(int damage) {
			this.strength -= damage;
		}
		
		// get the current strength of the mage
		public int getStrength() {
			return this.strength;
		}
		
		// get if the mage is still alive
		public boolean isAlive(){
			if(this.alive) return true;
			return false;
		} 
		
		public void setName(String name) {
			this.name = name;
		}
		
		// get the mage name
		public String getName(){
			return this.name;
		}
		
		// set mage alive to false
		public void die(){
	    	this.alive = false;
	    }

		//method that will get the bullets 'shot' by the ship
		public ArrayList<Bullet> getBullets(){
			return this.bullets;
		}
		
		//method called if spacebar is pressed
		public void shoot(){
			//compute for the x and y initial position of the bullet
			int x = (int) (this.x + this.width);
			int y = this.y;

			// Instantiate a new bullet and add it to the bullets arraylist of mage
			Bullet bullet = new Bullet(x, y);
			this.bullets.add(bullet);
	    }
		
		public double getX() {
	    	return this.playerImage.getLayoutX();
		}

		public double getY() {
	    	return this.playerImage.getLayoutY();
		}
		
		public void setX(double newX) {
			this.playerImage.setLayoutX(newX);
		}
		
		public void setY(double newY) {
			this.playerImage.setLayoutX(newY);
		}
		
		//method called if up/down/left/right arrow key is pressed.
		public void move() {
			
			// Changing Mage X position
			
			double newX = this.playerImage.getLayoutX() + dx;
		    double newY = this.playerImage.getLayoutY() + dy;

		    // Check if the new position is within the bounds of the game window
		    if (newX >= 0 && newX <= ((GameMenu.WINDOW_WIDTH - 360) - Player.PLAYER_WIDTH)) {
		        this.playerImage.setLayoutX(newX);
		        this.setDX(0);
		    }
		    
		    if (newY >= 0 && newY <= ((GameMenu.WINDOW_HEIGHT - 100) - Player.PLAYER_HEIGHT)) {
		        this.playerImage.setLayoutY(newY);
		        this.setDY(0);
		    }
			
		}
	
}
