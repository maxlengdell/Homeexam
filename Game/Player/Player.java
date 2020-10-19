package Homeexam.Game.Player;

import Homeexam.Game.Main.mainGame;
import Homeexam.Game.networking.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Player implements IPlayerInterface{
    public int playerID;
    public boolean online;
    public Socket connection;
    public ArrayList<String> writtenWords = new ArrayList<String>();
    public Server server = mainGame.server;
    public ObjectInputStream inFromClient;
    public ObjectOutputStream outToClient;
    public int score;

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

    // public void close() {
    //     // TODO Auto-generated method stub

    // }

    public int calculateScore() {
        score = 0;
        for(String word : writtenWords) {
            if(word.length() == 3 || word.length() == 4)
                score += 1;
            if(word.length() == 5)
                score +=2;
            if(word.length() == 6)
                score += 3;
            if(word.length() == 7)
                score += 5;
            if(word.length()> 7)
                score += 11;
        }
        return score;
    }

}
