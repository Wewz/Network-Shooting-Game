package application.server_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientThread extends Thread {
    private byte[] incoming = new byte[256];
    private DatagramSocket socket;
    private Client client;

    public ClientThread(DatagramSocket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("Starting Client Thread");

        while (true) {
            DatagramPacket packet = new DatagramPacket(incoming, incoming.length);

            try {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received from server: " + message + "\n");
                this.client.gameUpdates(message); // Call gameUpdates method of the client
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
