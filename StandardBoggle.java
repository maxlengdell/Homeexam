package Homeexam;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.script.*;

public class StandardBoggle implements IGameInterface {
    // Clear board;
    String[][] currentBoggle = randomBoggle(boggle16);
    public ScriptEngineManager mgr = new ScriptEngineManager();
    public ScriptEngine engine = mgr.getEngineByName("JavaScript");
    private Server server;
    private static int M;
    private static int N;
    private static boolean foundInBoggleBoard;

    public StandardBoggle(String boardsize, Server server) {
        this.server = server;
        // Creates a grid in the constructor depending on the size.
        if (boardsize.equalsIgnoreCase("4x4"))
            currentBoggle = randomBoggle(boggle16);
        if (boardsize.equalsIgnoreCase("5x5"))
            currentBoggle = randomBoggle(boggle25);
        /*
         * if(mode.equals("Foggle")) currentBoggle = randomBoggle(foggle16);
         */
        M = currentBoggle.length;
        N = currentBoggle[0].length;
        System.out.println("M: " + M + "N: " + N);
    }

    public void boggle(Player player) {
        // server.sendMessage("test to playerID: "+ player.playerID);
        boolean run = true;
        String clientInput;
        String check;
        // Send grid to client
        try {
            while(run){
                server.sendMessage(currentBoggle, player);
                clientInput = server.readMessage(player);
                check = checkWord(currentBoggle, clientInput);
                System.out.println(check + "::::" + clientInput);
            }

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


            /*String textInput = player.readMessage();
            System.out.println("player word:" + textInput);
            String check = checkWord(currentBoggle, textInput);
            System.out.println(check);*/
        
    }
    public String checkWord(String[][] boggle, String word) {
        word = word.toUpperCase();
        foundInBoggleBoard = false;
        boolean validWord = false;
        boolean foggle = false;
        try {
            Integer.parseInt(boggle[0][0]);
            foggle = true;
            String[] expressions = word.split("=");
            validWord = (engine.eval(expressions[0])) == (engine.eval(expressions[1]));
        } catch (Exception e) {            
            if(mainGame.dictionary.contains(word))
                validWord = true;
        }            
        if(validWord) {
            //The word exists in the dictionary, check if it exists on the board
            word = word.replaceAll("QU", "Q"); //Treat as one character in word due to Boggle dice
            word = word.replaceAll("[^a-zA-Z0-9]", ""); //For Foggle - just keep numbers
            boolean[][] visited = new boolean[boggle.length][boggle.length];
            boolean found = false;
            for(int i=0; i< boggle.length; i++) {
                for(boolean[] vrow : visited) {Arrays.fill(vrow, false);}
                for(int j=0; j<boggle.length; j++) {
                    if(boggle[i][j].startsWith(word.substring(0,1))) {
                        //Check if the word exists on the Boggle board
                        //Start with matching positions on the boggle board 
                        found = search(boggle, word, i, j, 1, visited);
                    }
                }
            }
            return (found?"OK":"NOT OK");
        } else {
            if(foggle)
                return "Not a valid expression";
            return "Not in the dictionary";
        }
    }
    
    public boolean search(String[][] boggle, String word, int i, int j, int matches, boolean[][] visited) {
        int[] dirx = { -1, 0, 0, 1 , -1,  1, 1, -1}; //8 directions including diagonals
        int[] diry = { 0, -1, 1, 0 , -1, -1, 1,  1};
        // if(!generousBoggle)
        //     visited[i][j] = true;
        int size=boggle.length;
        if(matches>=word.length()) {foundInBoggleBoard=true;} //The word was found on the boggle board
        for(int z=0; z<8; z++) {
            if(((i+dirx[z])>=0 && (i+dirx[z])<size) && ((j+diry[z])>=0 && (j+diry[z])<size) && (!visited[i+dirx[z]][j+diry[z]]) && matches<word.length()) {
                if(word.substring(matches,matches+1).equals(boggle[i+dirx[z]][j+diry[z]])) {
                    search(boggle, word, i+dirx[z], j+diry[z], matches+1, visited);
                }
            }
            if(foundInBoggleBoard) return true; //some branch found the word in the boggleboard
        }
        return false; //The word was not found on the boggle board
    }
/*
    public static boolean checkWord(String[][] currentGrid, String word) {
        System.out.println("Words in boggle");
        findWords(currentGrid, word);
        return false;
    }

    public static void findWords(String[][] currentGrid, String word) {
        // Mark all characters as not visited
        boolean visited[][] = new boolean[M][N];

        // Initialize current string
        String str = "";

        // Consider every character and look for all words
        // starting with this character
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                findWordOnGrid(currentGrid, visited, i, j, str);
    }

    public static void findWordOnGrid(String[][] boggle, boolean visited[][], int i, int j, String str) {
        // Mark cell as visited
        visited[i][j] = true;
        str = str + boggle[i][j];

        // If str is present in dictionary, print it
        if (isWord(str)) {
            System.out.println("isWord: " + str);
        }
        // Traverse 8 adjacent cells of boggle[i][j]
        if (boggle[i][j] == String.valueOf(str.charAt(0))) {

            for (int row = i - 1; row <= i + 1 && row < M; row++)
                for (int col = j - 1; col <= j + 1 && col < N; col++) {
                    // Check for words with adjacent letters.
                }
        }
        // Erase current character from string and mark as visited
        str = "" + str.charAt(str.length() - 1);
        visited[i][j] = false;
    }

    

    public static boolean isWord(String word) {
        // Linearly search all words
        for (int i = 0; i < mainGame.dictionary.size(); i++)
            if (word.equals(mainGame.dictionary.get(i)))
                return true;
        return false;
    }
*/
    public void printGrid() {
        String returnMsg = "";
        for (String[] row : currentBoggle) {
            for (String column : row) {
                returnMsg += column + (column.equals("Qu") ? " " : "  ");
            }
            returnMsg += "\n";
        }
        System.out.println(returnMsg);
    }

    public String[][] randomBoggle(String[][] boggleDie) {
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
