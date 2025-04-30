package application.game_menu;

import java.io.IOException;

import application.multi_player_lobby.Lobby;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameMenu {
	
	private Scene scene;
	private Parent root;
	private GameMenuController controller;
	private Stage stage;
	public static final int WINDOW_WIDTH = 1267;
	public static final int WINDOW_HEIGHT = 743;
	
	
	public GameMenu() throws IOException {
		FXMLLoader loader = null;
		loader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
		this.root = loader.load();
		this.controller = loader.getController();
		this.controller.setCurrent(this);
	}
	
	public void setStage(Stage stage) {
		
		this.stage = stage;
		
		stage.setTitle("");	
		stage.show();
		
		stage.setMinWidth(1267);
		stage.setMinHeight(743);
		
		stage.setMaxHeight(743);
		stage.setMaxWidth(1267);
		this.scene = new Scene(this.root);
		String css = this.getClass().getResource("GameMenu.css").toExternalForm();
		this.scene.getStylesheets().add(css);
		stage.setScene(this.scene); 
		
	}
	
	public void receiveScence(Scene scene, Stage stage) {
		this.scene = scene;
		this.stage = stage;
		
		System.out.println("Nareceive ba?");
		
		this.scene = new Scene(this.root);
		String css = this.getClass().getResource("GameMenu.css").toExternalForm();
		this.scene.getStylesheets().add(css);
		this.stage.setScene(this.scene); 
	}
	
	public void handleStart(MouseEvent  event, boolean isServer) throws IOException {
		
		this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		Lobby theLobby = new Lobby(this.scene, isServer);
		theLobby.setStage(this.stage);
	}

}
