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
    Scanner in = new Scanner(System.in);

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

    /*public void sendMessage(Object message) {
        
        if (online) {
            try {
                server.sendMessage(message);
            } catch (Exception e) {
                System.out.println("player sendMessage error: " + e);
            }
        } else {
            System.out.println("Player not online");
        }

    }*/
    /*
    public String readMessage(){
        String word = "";
        if (online) {
            try {
                word = (String) inFromClient.readObject();
            } catch (Exception e) {
                System.out.println("player if: readMessage error: " + e);

            }
        } else
            try {
                word = in.nextLine();
            } catch (Exception e) {
                System.out.println("player else: readMessage error: " + e);

            }
        return word;
    }*/
    // public void sendMessage(Object message) {
    // // TODO Auto-generated method stub
    // String msg;
    // if(message instanceof String[][]){
    // msg = mainGame.standardBoggle.printGrid();
    // }else{
    // msg = (String)message;
    // }
    // if(online)
    // try {outToClient.writeObject(msg);} catch (Exception e) {}
    // else
    // System.out.println(msg);

    // }

    // public String readMessage() {
    // String word = "";
    // if(online){
    // try{word = (String) inFromClient.readObject();} catch (Exception e){}
    // }
    // else
    // try {word=in.nextLine();} catch(Exception e){}
    // return word;
    // }

    public void close() {
        // TODO Auto-generated method stub

    }

    public int calculateScore() {
        // TODO Auto-generated method stub
        return 0;
    }

}
