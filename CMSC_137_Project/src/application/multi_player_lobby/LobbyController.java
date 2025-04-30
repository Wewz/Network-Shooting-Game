package application.multi_player_lobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import application.game_logic.Player;
import application.server_client.Client;
import application.server_client.Server;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class LobbyController {
	
	private Lobby current;
	private ArrayList<Player> others;
	private Player player;
	
	private Server server;
	
	private Client client;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	private boolean gameStart = false;
	private boolean isServer = false;
	private boolean isPlayerSet = false;
    
    private String uniqueIdentifier = "";
	
	@FXML
	ScrollPane chatScrollable;
	@FXML
	VBox messagesDiv;
	@FXML
	TextArea chatInput;
	@FXML
	Button sendChatButton;
	@FXML
	ImageView startGameButton;
	@FXML
	ImageView menuButton;
	@FXML
	ImageView slot01;
	@FXML
	ImageView slot02;
	@FXML
	ImageView slot03;
	@FXML
	ImageView slot04;
	@FXML
	ImageView bgImage;
	@FXML
	AnchorPane rootPane;
	@FXML
	Text lobbyText01;
	@FXML
	Text lobbyText02;
	
	public LobbyController() {
		this.others = new ArrayList<>();
	}
	
	@FXML
    public void initialize() {
        // Add a key event handler to the root pane to observe all key presses
		rootPane.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
		chatInput.setFocusTraversable(false);
	    
	    // Add focus listener to text area
	    chatInput.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
	        if (isNowFocused) {
	            System.out.println("TextArea focused");
	        } else {
	            System.out.println("TextArea lost focus");
	        }
	    });

	    // Add mouse click event handler to set focus on TextArea when clicked
	    chatInput.setOnMouseClicked(event -> chatInput.requestFocus());
    }
	
	public void initialSetup(Player player, boolean isServer) {
        this.player = player;
        this.isServer = isServer;

        // Disable chat input and send button initially
        chatInput.setDisable(true);
        sendChatButton.setDisable(true);

        Task<Void> setupTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (isServer) {
                    server = new Server();
                    executorService.submit(() -> server.run()); // Run the server on a separate thread
                }

                client = new Client();
                client.initialize(LobbyController.this);
                return null;
            }

			@Override
            protected void succeeded() {
                super.succeeded();
                // Enable chat input and send button after successful initialization
                chatInput.setDisable(false);
                sendChatButton.setDisable(false);
            }

            @Override
            protected void failed() {
                super.failed();
                // Optionally handle failure, update UI, etc.
                getException().printStackTrace();
            }
        };

        // Run the task on a background thread
        Thread setupThread = new Thread(setupTask);
        setupThread.setDaemon(true);  // Ensure the thread exits when the application closes
        setupThread.start();
    }

    // Handle key press events
    private void handleKeyPress(KeyEvent event) {
        if (chatInput.isFocused()) {
            // Let the TextArea handle key events when it is focused
            return;
        }
        
        if(!this.gameStart) return;

        // Custom handling for other key events
        switch (event.getCode()) {
            case UP:
        	     this.client.sendData("PlayerMove", this.player.getName(),"UP");
        	     break;
            case DOWN:
            	this.client.sendData("PlayerMove", this.player.getName(),"DOWN");
                break;
            case LEFT:
            	this.client.sendData("PlayerMove", this.player.getName(),"LEFT");
                break;
            case RIGHT:
            	this.client.sendData("PlayerMove", this.player.getName(),"RIGHT");
                break;
            case SPACE:
            	this.client.sendData("PlayerMove", this.player.getName(),"SPACE");
                break;
            default:
                System.out.println("Key pressed: " + event.getCode());
                break;
        }
    }
	
	public void displayChat() {
		
		int wordBaseX = 20, baseY = 14;

	    String messageInput = chatInput.getText();

	    if (messageInput.isEmpty()) {
	        return;
	    }

	    if (client != null) {
	        this.client.sendData("Chat", this.player.getName(), messageInput);
	    } else {
	        System.err.println("Client is not initialized yet.");
	    }

	    chatInput.setText("");
	}
	
	// Method to update the UI with received message
	public void updateChatUI(String playerName, String messageInput) {
	    Platform.runLater(() -> {
	    	
	    	HBox hbox = new HBox();
	    	VBox content = new VBox();
	    	
	    	if(playerName.equals(this.player.getName())) {
	    		hbox.setAlignment(Pos.CENTER_RIGHT);
	    	}
	    	else {
	    		hbox.setAlignment(Pos.CENTER_LEFT);
	    	}
	    	
	    	hbox.setPadding(new Insets(5, 10, 5, 10));
	    	
	        Text msg = new Text(messageInput);
	        Text ply = new Text(playerName);
	        TextFlow message = new TextFlow(msg);
	        TextFlow player_name = new TextFlow(ply);
	        
	        if(playerName.equals(this.player.getName())) {
	        	message.setStyle("-fx-color: rgb(239, 242, 255);" +
		                "-fx-background-color: #FF43C2;" +
		                "-fx-background-radius: 10px;"
		        );
	        	player_name.setTextAlignment(TextAlignment.RIGHT);
	    	}
	    	else {
	    		message.setStyle("-fx-color: rgb(239, 242, 255);" +
		                "-fx-background-color: #7272B8;" +
		                "-fx-background-radius: 10px;"
		        );
	    		player_name.setTextAlignment(TextAlignment.LEFT);
	    	}
	        
	        message.setPadding(new Insets(5, 10, 5, 10));
	        player_name.setPadding(new Insets(2, 2, 2, 2));
	        msg.setFill(Color.WHITE);
	        ply.setFill(Color.WHITE);
	        msg.setFont(Font.font("Montserrat", FontWeight.NORMAL, 15));
	        ply.setFont(Font.font("Montserrat", FontWeight.NORMAL, 10));
	        
	        content.getChildren().addAll(player_name, message);
	        hbox.getChildren().addAll(content);
	        this.messagesDiv.getChildren().addAll(hbox);
	        this.chatScrollable.setVvalue(1.0);
	    });
	}
	
	public void setPlayer(String identifier, String playerName) {
		Platform.runLater(() -> {
			
			if(this.getUniqueIdentifier().equals(identifier)) {
				this.player.setName(playerName);
			}
			else {
				System.out.println("Additional player " + playerName);
				Player newPlayer = new Player(playerName, 0, 0);
				newPlayer.setPlayerImage("/application/assets/Player01.gif");
				newPlayer.setName(playerName);
				this.others.add(newPlayer);
			}
	    });
	}
	
	public void setAdditionalPlayers(String otherPlayername) {
		Platform.runLater(() -> {
			
			for(Player current:this.others) {
				if(otherPlayername.equals(current.getName())) return;
				
				if(this.player.getName().equals(otherPlayername)) return;
			}

			Player newPlayer = new Player(otherPlayername, 0, 0);
			newPlayer.setPlayerImage("/application/assets/Player01.gif");
			newPlayer.setName(otherPlayername);
			this.others.add(newPlayer);
	    });
	}
	
	public void updatePlayersLocation(String playerName, String move) {
	    Platform.runLater(() -> {
	        if(this.player.getName().equals(playerName)) {
	            movePlayer(this.player, move);
	        } else {
	            for(Player other : this.others) {
	                if(other.getName().equals(playerName)) {
	                    movePlayer(other, move);
	                }
	            }
	        }
	    });
	}

	private void movePlayer(Player player, String move) {
	    if(move.equals("UP")) {
	        player.setDY(-10);
	    } else if(move.equals("DOWN")) {
	        player.setDY(10);
	    } else if(move.equals("LEFT")) {
	        player.setDX(-10);
	    } else if(move.equals("RIGHT")) {
	        player.setDX(10);
	    }
	    player.move();
	    
	    this.client.sendData("LocationUpdated", player.getName(), (player.getX() + ":" + player.getY()));
	}
	
	public void updateOtherPLayerLocation(String playerName, String x, String y) {
//		double locationX = Double.parseDouble(x);
//		double locationY = Double.parseDouble(y);
//		
//		for(Player other : this.others) {
//            if(other.getName().equals(playerName)) {
//            	other.setX(locationX);
//            	other.setY(locationY);
//            } 
//        }
	}
	
	public void gameBegin() {
		System.out.println("\nGame Start");
		
		slot01.setVisible(false);
		slot02.setVisible(false);
		slot03.setVisible(false);
		slot04.setVisible(false);
		startGameButton.setVisible(false);
		menuButton.setVisible(false);
		lobbyText01.setVisible(false);
		lobbyText02.setVisible(false);
		
		Image newImage = new Image(getClass().getResource("/application/assets/InGameBG.png").toExternalForm());
		bgImage.setImage(newImage);
		
		this.rootPane.getChildren().add(this.player.getPlayerImage());
		
		for(int i=0; i<this.others.size(); i++) {
			this.rootPane.getChildren().add(this.others.get(i).getPlayerImage());
		}
		
		this.gameStart = true;
		
//		gameLogic = new GameTimer(rootPane, this.current.getStage(), this.current.getScene(), this.current.getPLayers());
	}
	
	public void setCurrent(Lobby current) {
		this.current = current;
		System.out.println("Menu");
	}
	
	
	public void handleBackToMenu(MouseEvent  event) {
		System.out.println("Back to Menu");
		
		try {
			this.current.handelBackToMenu(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleStartGame(MouseEvent  event) {

		this.client.sendData("GameStart", "", "");

	}
	
	public void handleMouseEnteredStartGame(MouseEvent event) {
		mouseEnter(startGameButton);
    }

    public void handleMouseExitedStartGame(MouseEvent event) {
    	mouseExit(startGameButton);
    }
    
    public void handleMouseEnteredBackToMenu(MouseEvent event) {
		mouseEnter(menuButton);
    }

    public void handleMouseExitedBackToMenu(MouseEvent event) {
    	mouseExit(menuButton);
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
    
    public void setUniqueIdentifier(String identifier) {
    	this.uniqueIdentifier = identifier;
    }
    
    public String getUniqueIdentifier() {
    	return this.uniqueIdentifier;
    }

}
