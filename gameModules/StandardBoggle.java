package Homeexam.gameModules;

import Homeexam.networking.*;
import Homeexam.Main.mainGame;
import Homeexam.Player.*;
import Homeexam.gameModules.*;
import java.io.*;
import java.util.*;
import javax.script.*;

public class StandardBoggle implements IGameInterface {
    // Clear board;
    String[][] currentBoggle = randomBoggle(boggle16);
    public ScriptEngineManager mgr = new ScriptEngineManager();
    public ScriptEngine engine = mgr.getEngineByName("JavaScript");
    private Server server;
    private static int M;
    private static int N;
    private Boolean generousBoggle;
    private static boolean foundInBoggleBoard;

    public StandardBoggle(String boardsize, Server server, Boolean generousBoggle) {
        this.server = server;
        this.generousBoggle = generousBoggle;
        // Creates a grid in the constructor depending on the size.
        if (boardsize.equalsIgnoreCase("4x4"))
            currentBoggle = randomBoggle(boggle16);
        if (boardsize.equalsIgnoreCase("5x5"))
            currentBoggle = randomBoggle(boggle25);
        M = currentBoggle.length;
        N = currentBoggle[0].length;
        System.out.println("M: " + M + "N: " + N);
    }

    public void boggle(Player player) {
        System.out.println("**********Starting game**********");

        String clientInput;
        String check;
        try {
            while (!player.connection.isClosed()) {
                //if(mainGame.run){
                    server.sendMessage(currentBoggle, player);
                    clientInput = server.readMessage(player);
                    if(clientInput!= ""){
                        check = checkWord(currentBoggle, clientInput, player, generousBoggle);
                        //System.out.println("Check output: " + check);
                        if (check == "OK") {
                            // Calculate score
                            server.sendMessage("Word ok", player);
                            player.writtenWords.add(clientInput);
                            System.out.println(clientInput + " APPROVED");
                        } else {
                            server.sendMessage("Word does not exist", player);
                        }
                    }
                //}
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    protected boolean checkWordinDict(String word){
        if (mainGame.dictionary.contains(word))
            return true;
        else return false;
    }
    protected boolean checkWordExistInWritten(String word, Player player){
        //System.out.println("Written words: " + player.writtenWords);
        if(player.writtenWords.contains(word)){
            return true;
        }else return false;
    }
    public String checkWord(String[][] boggle, String word, Player player, Boolean generousBoggle) {
        // TODO: Check if word has already been written:
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

    protected boolean search(Boolean generousBoggle, String[][] boggle, String word, int i, int j, int matches, boolean[][] visited) {
        int[] dirx = { -1, 0, 0, 1, -1, 1, 1, -1 }; // 8 directions including diagonals
        int[] diry = { 0, -1, 1, 0, -1, -1, 1, 1 };
        if(!generousBoggle){
            visited[i][j] = true;
        }
        int size = boggle.length;
        if (matches >= word.length()) {
            foundInBoggleBoard = true;
        } // The word was found on the boggle board
        for (int z = 0; z < 8; z++) {
            if (((i + dirx[z]) >= 0 && (i + dirx[z]) < size) && ((j + diry[z]) >= 0 && (j + diry[z]) < size)
                    && (!visited[i + dirx[z]][j + diry[z]]) && matches < word.length()) {
                if (word.substring(matches, matches + 1).equals(boggle[i + dirx[z]][j + diry[z]])) {
                    search(generousBoggle, boggle, word, i + dirx[z], j + diry[z], matches + 1, visited);
                }
            }
            if (foundInBoggleBoard)
                return true; // some branch found the word in the boggleboard
        }
        return false; // The word was not found on the boggle board
    }
//Helper function for testing. Printing grid on server.
    protected void printGrid(String[][] currentBoggle) {
        String returnMsg = "";
        for (String[] row : currentBoggle) {
            for (String column : row) {
                returnMsg += column + (column.equals("Qu") ? " " : "  ");
            }
            returnMsg += "\n";
        }
        System.out.println(returnMsg);
    }

    protected String[][] randomBoggle(String[][] boggleDie) {
        int size = (int) Math.sqrt(boggleDie.length);
        int returnRow = 0, returnColumn = 0;
        Random rnd = new Random();
        String[][] returnBoggle = new String[size][size];
        List<String[]> rows = Arrays.asList(boggleDie);
        Collections.shuffle(rows);
        for (String[] row : rows) {
            returnBoggle[returnRow][returnColumn] = row[rnd.nextInt(6)];
            returnColumn = (returnColumn < (size - 1) ? returnColumn + 1 : 0);
            returnRow = (returnColumn == 0 ? returnRow + 1 : returnRow);
        }
        return returnBoggle;
    }
}
