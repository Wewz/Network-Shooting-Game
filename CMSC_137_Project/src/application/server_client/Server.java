package application.server_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

public class Server extends Thread {
	
    private static final int PORT = 8003;
    private DatagramSocket socket;
    private ArrayList<ClientInfo> users;

    public Server() throws SocketException {
        this.socket = new DatagramSocket(PORT);
        this.users = new ArrayList<>();
    }

    public void run() {
        System.out.println("Server started at port " + PORT);

        while (true) {
            byte[] incoming = new byte[256];
            DatagramPacket packet = new DatagramPacket(incoming, incoming.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String clientMessage = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Server received: " + clientMessage);

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            
            String newJoin = clientMessage.split(":")[0];
            
            
            if (newJoin.equals("init")) {
//            	System.out.println("users count" + users.size());
                users.add(new ClientInfo(clientAddress, clientPort, "Player " + (users.size() + 1)));
                
                byte[] byteMessage = (clientMessage + ":Player " + (users.size())).getBytes();
                DatagramPacket forward = new DatagramPacket(byteMessage, byteMessage.length, clientAddress, clientPort);

                try {
                    socket.send(forward);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                	
            	for(int i=0; i<users.size(); i++) {
            		
            		for(int j=0; j<users.size(); j++) {
            			
            			if(j==i) continue;
            			
            			byte[] otherInfo = ("OtherUsers:" + users.get(j).getName()).getBytes();
                    	DatagramPacket forward2 = new DatagramPacket(otherInfo, otherInfo.length, users.get(i).getAddress(), users.get(i).getPort());
                    	
                    	try {
                            socket.send(forward2);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
            		}
            	}
                	
                
            } else {
                for (ClientInfo user : users) {
                	
                    DatagramPacket forward = new DatagramPacket(clientMessage.getBytes(), clientMessage.length(), user.getAddress(), user.getPort());

                    try {
                        socket.send(forward);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                
                }
            }
        }
    }
    

    private static class ClientInfo {
        private InetAddress address;
        private int port;
        private String name;

        public ClientInfo(InetAddress address, int port, String name) {
            this.address = address;
            this.port = port;
            this.name = name;
        }

        public InetAddress getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }
        
        public String getName() {
        	return name;
        }
    }
}
