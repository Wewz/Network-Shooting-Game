package application.game_logic;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameTimer extends AnimationTimer {
	private AnchorPane rootPane;
	private Stage stage;
	private Scene theScene;
	
	private ArrayList<Player> players;
	
	
	public GameTimer(AnchorPane rootPane, Stage stage, Scene theScene, ArrayList<Player> players){
		this.theScene = theScene;
		this.rootPane = rootPane;
		this.stage = stage;
		
		this.players = players;
		
		//instantiate the ArrayList of Enemies
//		this.enemies = new ArrayList<Enemies>();
//		this.startSpawn = System.nanoTime();	//get current nanotime
		
		// Spawn the Enemies at the start of the game
//		this.spawnEnemies(GameTimer.START_NUM_ENEMIES);
		
		// check key pressed by the user
		this.rootPane.getChildren().add(players.get(0).getPlayerImage());
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		// TODO Auto-generated method stub
		
		
		
		
	}

	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
//                moveMyShip(code);
			}
		});
		
		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
//		                stopMyShip(code);
            }
        });
    }
		
//	private void moveMyShip(KeyCode ke) {
//		
//		if(ke==KeyCode.UP) this.players.get(0).setDY(-10);    
//		
//		if(ke==KeyCode.LEFT) this.players.get(0).setDX(-10);
//
//		if(ke==KeyCode.DOWN) this.players.get(0).setDY(10);
//		
//		if(ke==KeyCode.RIGHT) this.players.get(0).setDX(10);
//		
//		if(ke==KeyCode.SPACE) this.players.get(0).shoot();			
//		
//		System.out.println(ke+" key pressed.");
//   	}
}
