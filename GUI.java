package Homeexam;

import java.util.Scanner;

public class GUI {
    public static String playmode;
    public static String selectedMode;

    String menuMsg = ("**************************************\n" +
    "*      Welcome to VarietyBoggle      *\n" +
    "**************************************\n" +
    "* Current settings:                  *\n" +
    "*    Board size: " + mainGame.boardsize + "                 *\n" +
    "*    Language: "+ mainGame.language + "               *\n" + 
    "*    Generous boggle: " + (mainGame.generousBoggle?"on ":"off") + "            *\n"+
    "*    Show solution: " + (mainGame.showSolution?"on ":"off") + "              *\n"+
    "*    Number of players: " + mainGame.numOfPlayers + "            *\n"+
    "*    Number of seconds per game: " + mainGame.timeSeconds + "     ".substring(0, 4-(String.valueOf(mainGame.timeSeconds).length())) +"*\n"+
    "**************************************\n" +
    "* Menu:                              *\n" +
    "* [1] Play standard boggle           *\n" +
    "* [2] Play battle-boggle             *\n" +
    "* [3] Play foggle-boggle             *\n" +
    "* [4] Settings                       *\n" +
    "* [?] Test (remove for final product)*\n" +
    "* [!] Quit                           *\n" +
    "**************************************\n");
    String settingMsg = ("Settings: \n" + 
    "   Board size (" + mainGame.boardsize + ") : [4x4 | 5x5]\n"+
    "   Language (" + mainGame.language + ") : [English | Spanish]\n"+
    "   Toggle generous boggle ("+mainGame.generousBoggle+") : [GenerousBoggle]\n"+
    "   Toggle show solution ("+mainGame.showSolution+") : [ShowSolution]\n"+
    "   Number of players ("+mainGame.numOfPlayers+") : [#]\n"+
    "   Number of seconds per game ("+mainGame.timeSeconds+") : [# seconds]");
    public void Menu(){


        System.out.println(menuMsg);
        Scanner in = new Scanner(System.in);
        playmode = in.nextLine();
        //************Game mode selection*************** */
        if(playmode.equals("1") || playmode.equals("2") || playmode.equals("3")) { //Standard Boggle || Battle Boggle || Foggle
            if(playmode.equals("1"))selectedMode = "Standard";
            else if(playmode.equals("2"))selectedMode = "Battle";
            else selectedMode = "Foggle";
        }
        //***************Settings selection*************** */
        else if(playmode.equals("4")){
            System.out.println(settingMsg);

            String settingChoice = in.nextLine();
            settingChoice.toLowerCase();
            switch(settingChoice){
                case "4x4":
                case "5x5":{
                    mainGame.boardsize = settingChoice;
                }
                case "english":
                case "spanish":{
                    mainGame.language = settingChoice;
                }
                case "generousboggle":{
                    mainGame.generousBoggle = !mainGame.generousBoggle;
                }
                case "showsolution":{
                    mainGame.showSolution = !mainGame.showSolution;
                }
                
            }
            if(settingChoice.endsWith("seconds")){
                try {
                    mainGame.timeSeconds = Integer.parseInt(settingChoice.substring(0, settingChoice.indexOf(" ")));
                    } catch (NumberFormatException e) {}
            }
            else if(settingChoice.contains("player")){
                mainGame.numOfPlayers = Integer.parseInt(settingChoice);
            }
            }
        }
    public void printGameBoard(){
        
    }
}
