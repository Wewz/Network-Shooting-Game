package application.game_menu;

import java.io.IOException;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class GameMenuController {
	
	private GameMenu current;
	
	@FXML
    private ImageView singlePlay;
	@FXML
    private ImageView createLobby;
	@FXML
    private ImageView exit;
	
	public GameMenuController() {
		
	}

	public void handleMouseEnteredSinglePlay(MouseEvent event) {
		mouseEnter(singlePlay);
    }

    public void handleMouseExitedSingleplay(MouseEvent event) {
    	mouseExit(singlePlay);
    }
    
    public void handleMouseEnteredCreateLobby(MouseEvent event) {
		mouseEnter(createLobby);
    }

    public void handleMouseExitedCreateLobby(MouseEvent event) {
    	mouseExit(createLobby);
    }
    
    public void handleMouseEnteredExit(MouseEvent event) {
		mouseEnter(exit);
    }

    public void handleMouseExitedExit(MouseEvent event) {
    	mouseExit(exit);
    }
    
    public void mouseEnter(ImageView button) {
    	ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setToX(1.05); // Increase X scale by 5%
        scaleTransition.setToY(1.05); // Increase Y scale by 5%
        scaleTransition.play(); // Start the animation
    }
    
    public void mouseExit(ImageView button) {
    	ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setToX(1.0); // Set X scale back to original size
        scaleTransition.setToY(1.0); // Set Y scale back to original size
        scaleTransition.play(); // Start the animation
    }
	
	public void setCurrent(GameMenu current) {
		this.current = current;
		System.out.println("Menu");
	}
	
	public void handleCreateLobby(MouseEvent  event) {
		System.out.println("Game Start");
		
		try {
			this.current.handleStart(event, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleSinglePlay(MouseEvent  event) {
		System.out.println("Game Start");
		
		try {
			this.current.handleStart(event, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleExitButton() {
		System.exit(0);
	}

}
