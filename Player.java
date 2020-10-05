package Homeexam;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.script.*;

public class Player {
    public int playerID;
    public boolean online;
    public Socket connection;
    public ArrayList<String> writtenWords = new ArrayList<String>();
    public Server server = mainGame.server;
    public ObjectInputStream inFromClient;
    public ObjectOutputStream outToClient;

    public Player(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID;
        if (connection == null) {
            this.online = false;
        } else {
            this.online = true;
        }
        this.connection = connection;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;

    }

    public void close() {
        // TODO Auto-generated method stub

    }

    public int calculateScore() {
        // TODO Auto-generated method stub
        return 0;
    }

}
