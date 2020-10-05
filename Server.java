package Homeexam;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.script.*;

//import jdk.internal.jline.internal.InputStreamReader;

public class Server {

    public ServerSocket server;
    public Socket clientSocket;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int port;
    public int numberOfPlayers;
    public ArrayList<Player> players = new ArrayList<Player>();

     public Server(int port, int numberOfPlayers) {
        this.port = port;
        this.numberOfPlayers = numberOfPlayers;
     }
/*
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(2048);
    }
    */
    public void start() throws IOException {
        try {
            server = new ServerSocket(port);

            for(int i = 0; i< numberOfPlayers; i++){
                System.out.println("waiting for client");
                clientSocket = server.accept();
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                players.add(new Player(i, clientSocket, in, out));

                System.out.println("Player: " + i + " is connected");
                sendMessage("Player "+ i + " You are connected", players.get(i));
            }

        } catch (Exception e) {
            System.out.println("Server exception: " + e);
        }
    }

    public void sendMessage(Object message, Player player) throws IOException {
        try {
            player.outToClient.writeObject(message);
            player.outToClient.flush();
        } catch (Exception e) {
            System.out.println("Send message error: " + e);
        }

    }
    public String readMessage(Player player) throws IOException {
        try {
            String str = (String) player.inFromClient.readObject();
            System.out.println("Message received = " + str);
            return str;
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("read message error: " + e);
            return null;
        }

    }
}
