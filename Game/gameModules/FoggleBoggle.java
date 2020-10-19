package Homeexam.Game.gameModules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Homeexam.Game.Player.Player;
import Homeexam.Game.networking.Server;

public class FoggleBoggle extends StandardBoggle {
    private Server server;
    public ArrayList<String> totalWrittenWords = new ArrayList<String>();
    private Boolean generousBoggle;
    private static boolean foundInBoggleBoard;
    private static boolean showSolution;
    
    public FoggleBoggle(String boardsize, Server server, Boolean generousBoggle, Boolean showSolution) {
        super(boardsize, server, generousBoggle, showSolution);
        this.server = server;
        this.generousBoggle = generousBoggle;
        this.showSolution = showSolution;
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
    protected boolean checkWordExistInWritten(String word, Player player) {
        //Check if current expression is written

        return player.writtenWords.contains(word);
    }
    @Override
    public String checkWord(String[][] boggle, String word, Player player, Boolean generousBoggle) {
        foundInBoggleBoard = false;
        Boolean validWord;
        String[] expressions = word.split("=");
        try {
            validWord = (engine.eval(expressions[0])) == (engine.eval(expressions[1]));
            if (validWord && !checkWordExistInWritten(word, player)) {
                // The word exists in the dictionary, check if it exists on the board
                word = word.replaceAll("[^0-9]", ""); // For Foggle - just keep numbers
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
        } catch (Exception e) {
            return null;
        }
        
    }
    @Override
    public void showSolution(ArrayList<String> dictionary){
        //Number variants should not display solutions. 
        System.out.println("Show solution not supported");
    }
}
