package Homeexam.networking;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    Scanner textInput = new Scanner(System.in);
    private boolean run = true;
    public Client(String ipAddress) {
        try {
            // Connect to server
            socket = new Socket(ipAddress, 2048);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            Runnable receive = new Runnable() {
                @Override
                public void run() {//Receive messages constantly.
                    try {
                        while(run){
                            readStringMessages();
                        }
                    }
                    catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("error in receive runnable: " + e);
                    }
                    }
                };
                new Thread(receive).start();
                clientLoop();

        } catch (Exception e) {
            System.out.println(e);
            // threadpool.shutdownNow();
        }
    }

    // Returns server response except for current grid which is printed direcly.
    public String readMessage() throws IOException {
        Object str = null;
        try {
            str = (Object) in.readObject();
            if (str instanceof String[][]) {
                // Grid received
                String[][] grid = (String[][]) str;
                printGrid(grid);
                return null;
            } else {
                return (String) str;
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Client readMessage error" + e);
            run = false;
            socket.close();
            return null;
        }
    }

    public void sendMessage(String input) throws IOException {

        try {
            input = input.toUpperCase();
            out.writeObject(input);
            out.flush();
        } catch (Exception e) {
            System.out.println("Client sendMessage error" + e);
            socket.close();
        }
    }    
    
    public void readStringMessages() throws IOException {
        String serverOutput;
        while((serverOutput = (String) readMessage()) != null){
            if (serverOutput == "Game over") {// Close socket
                System.out.println("Clear socket");
                socket.close();
                run = false;
            } else {
                printInfo(serverOutput);
            }
        }
    }
    public void clientLoop() throws Exception {
        while(!socket.isClosed()){
            String wordInput = textInput.nextLine();
            sendMessage(wordInput);
        }
    }

    public void printInfo(String str) {
        System.out.println(str);
    }

    public void printGrid(String[][] currentBoggle) {
        String returnMsg = "";
        for (String[] row : currentBoggle) {
            for (String column : row) {
                returnMsg += column + (column.equals("Qu") ? " " : "  ");
            }
            returnMsg += "\n";
        }
        System.out.println(returnMsg);
    }
        public static void main(String argv[]) {
        new Client("127.0.0.1");
    }

}