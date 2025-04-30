package application.multi_player_lobby;

import java.io.IOException;
import java.util.ArrayList;

import application.game_logic.Player;
import application.game_menu.GameMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Lobby {
	private Scene scene;
	private Parent root;
	private LobbyController controller;
	private Stage stage;
	private ArrayList<Player> players;
	
	public Lobby(Scene scene, boolean isServer) throws IOException {
		FXMLLoader loader = null;
		loader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
		this.root = loader.load();
		this.controller = loader.getController();
		this.controller.setCurrent(this);
		
		this.players = new ArrayList<>();
		Player currentuser = new Player("temp", 0, 0);
		currentuser.setPlayerImage("/application/assets/Player01.gif");
		this.players.add(currentuser);
		
		this.controller.initialSetup(currentuser, isServer);
	}
	
	public void setStage(Stage stage) {
		
		this.stage = stage;
		
		this.scene = new Scene(this.root);
		String css = this.getClass().getResource("Lobby.css").toExternalForm();
		this.scene.getStylesheets().add(css);
		stage.setScene(this.scene); 
	}
	
	public void handelBackToMenu(MouseEvent  event) throws IOException {
		
		this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		GameMenu theGameMenu = new GameMenu();
		theGameMenu.receiveScence(this.scene, this.stage);
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public ArrayList<Player> getPLayers() {
		return this.players;
	}

}
