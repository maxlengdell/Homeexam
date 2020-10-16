package Homeexam.gameModules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Homeexam.Main.mainGame;
import Homeexam.Player.Player;
import Homeexam.networking.Server;

public class FoggleBoggle extends StandardBoggle {
    private Server server;
    public ArrayList<String> totalWrittenWords = new ArrayList<String>();
    private Boolean generousBoggle;
    private static boolean foundInBoggleBoard;
    
    public FoggleBoggle(String boardsize, Server server, Boolean generousBoggle) {
        super(boardsize, server, generousBoggle);
        this.server = server;
        this.generousBoggle = generousBoggle;
        // Creates a grid in the constructor depending on the size.
        if (boardsize.equalsIgnoreCase("4x4"))
            currentBoggle = randomBoggle(foggle16);
        else{
            System.out.println("boardSize not supported. Please change to 4x4");
        }
    }
    @Override
    public void boggle(Player player) {
        System.out.println("**********Starting foggle game**********");

        String clientInput;
        String check;
        try {
            while (!player.connection.isClosed()) {

                    server.sendMessage(currentBoggle, player);
                    clientInput = server.readMessage(player);
                    check = checkWord(currentBoggle, clientInput, player, generousBoggle);
                    System.out.println("Check output: " + check);
                    if (check == "OK") {
                        // Calculate score
                        server.sendMessage("Word ok", player);
                        player.writtenWords.add(clientInput);
                        System.out.println(clientInput + " APPROVED");
                    } else {
                        server.sendMessage("Word does not exist", player);
                    }
                
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public String checkWord(String[][] boggle, String word, Player player, Boolean generousBoggle) {
        foundInBoggleBoard = false;

        if (checkWordinDict(word) & !checkWordExistInWritten(word, player)) {
            // The word exists in the dictionary, check if it exists on the board
            word = word.replaceAll("QU", "Q"); // Treat as one character in word due to Boggle dice
            word = word.replaceAll("[^a-zA-Z0-9]", ""); // For Foggle - just keep numbers
            boolean[][] visited = new boolean[boggle.length][boggle.length];
            boolean found = false;
            for (int i = 0; i < boggle.length; i++) {
                for (boolean[] vrow : visited) {
                    Arrays.fill(vrow, false);
                }
                for (int j = 0; j < boggle.length; j++) {
                    if (boggle[i][j].startsWith(word.substring(0, 1))) {
                        // Check if the word exists on the Boggle board
                        // Start with matching positions on the boggle board
                        found = search(generousBoggle, boggle, word, i, j, 1, visited);
                    }
                }
            }
            return (found ? "OK" : "NOT OK");
        } else {
            return "Not in the dictionary";
        }
    }
}
