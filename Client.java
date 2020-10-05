package Homeexam;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    Scanner textInput = new Scanner(System.in);

    public Client(String ipAddress) {
        try {
            // Connect to server
            socket = new Socket(ipAddress, 2048);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            sendMessage("connected");
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
                System.out.println("Client received: " + (String) str);
                return (String) str;
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Client readMessage error" + e);
            return null;
        }
    }

    public void sendMessage(String input) {
        try {
            out.writeObject(input);
            out.flush();
        } catch (Exception e) {
            System.out.println("Client sendMessage error" + e);
        }
    }

    public void clientLoop() throws IOException {
        String serverOutput;
        while((serverOutput = (String) readMessage()) != null){
            if (serverOutput == "CLOSE SOCKET") {// Close socket
                System.out.println("Socket closing");
                socket.close();
            } else {
                printInfo(serverOutput);
            }
        }
        String wordInput = textInput.nextLine();
        System.out.println("User input: " + wordInput);
        sendMessage(wordInput);
        clientLoop();
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