package Homeexam;

import java.io.*;
import java.util.*;



public class mainGame {

    public static Server server;
    public static ArrayList<String> dictionary;
    //public ArrayList<Player> players = new ArrayList<Player>();
    public static String boardsize;
    public static boolean generousBoggle = false;
    public static String language;
    public static boolean showSolution = false;
    public static int timeSeconds = 30;
    public static int numOfPlayers;
    public static boolean run = true;
    public static GUI gui;
    public static StandardBoggle standardBoggle;
    //public static Player player = new Player(1, null, null, null);


    public static void main(String[] args) throws IOException {
        boardsize = "4x4";
        language = "Eng";

        //Pick game mode
        gui = new GUI();
        dictionary = getDictionary();

        gameLoop();
    }
    public static void gameLoop() throws IOException {
            gui.Menu();
            String mode = gui.selectedMode;
            //Create server:
            server = new Server(2048, 2);
            server.start();
            if(mode.equals("Standard")){
                //Enter standard game
                System.out.println("**********Starting game**********");
                standardBoggle = new StandardBoggle(boardsize, server);

                for(int i = 0; i < server.numberOfPlayers; i++){
                    server.sendMessage(("test: " + i), server.players.get(i));
                    standardBoggle.boggle(server.players.get(i));
                }
            }
            else if(mode.equals("Battle")){
                //Enter battle
            }
            else{
                //Enter foggle
            };
            System.out.println(gui.selectedMode);
            //gameLoop();
    }
    /*
    Get the dictionary from the supplied text file. Should be placed in the same directory as main. 
    */
    public static ArrayList<String> getDictionary (){
        ArrayList<String> dictionary = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader("CollinsScrabbleWords2019.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                dictionary.add(line);
            }
            bufferedReader.close();           
        } catch (IOException e) {}

        return dictionary;
    }

}
