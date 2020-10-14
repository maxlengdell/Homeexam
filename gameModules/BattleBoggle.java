package Homeexam.gameModules;

import Homeexam.networking.*;
import Homeexam.Main.mainGame;
import Homeexam.Player.*;
import Homeexam.gameModules.*;
import java.io.*;
import java.util.*;
import javax.script.*;

public class BattleBoggle extends StandardBoggle{
    private Server server;
    private Boolean generousBoggle;
    public ArrayList<String> totalWrittenWords = new ArrayList<String>();
    
    public BattleBoggle(String boardsize, Server server, Boolean generousBoggle) {
        super(boardsize, server, generousBoggle);
        this.generousBoggle = generousBoggle;
        this.server = server;
        if (boardsize.equalsIgnoreCase("4x4"))
            currentBoggle = randomBoggle(boggle16);
        if (boardsize.equalsIgnoreCase("5x5"))
            currentBoggle = randomBoggle(boggle25);
        super.printGrid(currentBoggle);
    }
    @Override
    public void boggle(Player player){
        totalWrittenWords.clear();
        System.out.println("**********Starting battle game**********");

        String clientInput;
        String check;
        try {
            while (mainGame.run) {
                if(mainGame.run){
                    server.sendMessage(currentBoggle, player);
                    clientInput = server.readMessage(player);
                    check = checkWord(currentBoggle, clientInput, player, generousBoggle);
                    System.out.println("Check output: " + check);
                    if (checkContainBattleWord(clientInput) & check == "OK") {
                        // Calculate score
                        server.sendMessage("Word ok", player);
                        player.writtenWords.add(clientInput);
                        totalWrittenWords.add(clientInput);
                        sendApprovedWordToAllPlayers(player, clientInput);
                        System.out.println(clientInput + " APPROVED");

                    } else {
                        server.sendMessage("Word does not exist", player);
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void sendApprovedWordToAllPlayers(Player luckyPlayer, String word){
        try{
            for (Player player : server.players) {
                server.sendMessage("Player " + luckyPlayer.playerID + " wrote the word: " + word, player);
            }
        }
        catch(Exception e){
            System.out.println("Unable to send approved word");
        }

    }
    private boolean checkContainBattleWord(String word){

        if(totalWrittenWords.contains(word)) return true;
        else return false;
    }

}
