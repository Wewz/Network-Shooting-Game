package application.server_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.SecureRandom;

import application.multi_player_lobby.LobbyController;
import javafx.application.Platform;

public class Client {
	
	private LobbyController controller;
    private static final int SERVER_PORT = 8003;
    private DatagramSocket socket;
    private InetAddress address;
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public Client() throws SocketException, IOException {
        this.socket = new DatagramSocket();
        this.address = InetAddress.getByName("192.168.2.104");
    }

    public void initialize(LobbyController controller) throws IOException {
    	this.controller = controller;
    	this.controller.setUniqueIdentifier(this.generateRandomString(8));
    	
        ClientThread clientThread = new ClientThread(this.socket, this);
        clientThread.start(); // Use start() to run in a separate thread

        byte[] uuid = ("init:" + this.controller.getUniqueIdentifier()).getBytes();
        DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, address, SERVER_PORT);
        socket.send(initialize);
    }

    public void sendData(String header, String playerName, String data) {
    	
        String msg = header + ":" + playerName + ":" + data;
        byte[] message = msg.getBytes();
        DatagramPacket send = new DatagramPacket(message, message.length, address, SERVER_PORT);

        try {
            socket.send(send);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @SuppressWarnings("unused")
	public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
    
    public void gameUpdates(String serverUpdate) {
    	Platform.runLater(() -> {
        	
    		String[] gameUpdates = serverUpdate.split(":");
            
            if(gameUpdates[0].equals("init")) {
            	this.controller.setPlayer(gameUpdates[1], gameUpdates[2]);
            }
            else if(gameUpdates[0].equals("Chat")) {
            	this.controller.updateChatUI(gameUpdates[1], gameUpdates[2]);
            }
            else if(gameUpdates[0].equals("PlayerMove")) {
            	this.controller.updatePlayersLocation(gameUpdates[1], gameUpdates[2]);
            }
            else if(gameUpdates[0].equals("GameStart")) {
            	this.controller.gameBegin();
            }
            else if(gameUpdates[0].equals("OtherUsers")) {
            	this.controller.setAdditionalPlayers(gameUpdates[1]);
            }
            else if(gameUpdates[0].equals("LocationUpdated")) {
            	this.controller.updateOtherPLayerLocation(gameUpdates[1], gameUpdates[2], gameUpdates[3]);
            }
        });
	}


}
