package Homeexam;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class mainGame {

    public static Server server;
    public static ArrayList<String> dictionary;
    public static String boardsize;
    public static boolean generousBoggle = false;
    public static String language;
    public static boolean showSolution = false;
    public static int timeSeconds = 30;
    public static int numOfPlayers;
    public static boolean run = true;
    public static GUI gui;
    public static StandardBoggle standardBoggle;

    public static void main(String[] args) throws Exception {
        boardsize = "4x4";
        language = "Eng";

        // Pick game mode
        gui = new GUI();
        dictionary = getDictionary();
        System.out.println("hello");
        gameLoop();
    }
    public static Player getPlayer(){
        return server.players.get(0);
    }

    public static void gameLoop() throws IOException, InterruptedException {
        gui.Menu();
        String mode = GUI.selectedMode;
        // Create server:
        server = new Server(2048, 2);
        server.start();
        if (mode.equals("Standard")) {
            // Enter standard game
            System.out.println("**********Starting game**********");
            standardBoggle = new StandardBoggle(boardsize, server);
            ExecutorService threadpool = Executors.newFixedThreadPool(server.players.size());
            for (int i = 0; i < server.numberOfPlayers; i++) {
                Player player = server.players.get(i);
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        standardBoggle.boggle(player);
                        
                        }
                    };
                    threadpool.execute(task);
                }threadpool.awaitTermination(50, TimeUnit.SECONDS);
                threadpool.shutdownNow();
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
        ArrayList<String> dict = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader("Dictionary.txt");
            System.out.println("reading");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                dict.add(line);
            }
            bufferedReader.close();           
        } catch (IOException e) {}
        System.out.println(dict);
        return dict;
    }

}
