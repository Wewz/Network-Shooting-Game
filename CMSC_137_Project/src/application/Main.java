package application;
	
import java.io.IOException;
import application.game_menu.GameMenu;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		GameMenu theGameMenu = new GameMenu();
		theGameMenu.setStage(primaryStage);
	}
}

