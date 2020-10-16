package Homeexam.Main;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import Homeexam.GUI.GUI;
import Homeexam.Player.*;
import Homeexam.gameModules.*;
import Homeexam.networking.Server;

public class mainGame {

    public static Server server;
    public static ArrayList<String> dictionary;/*
    public static String boardsize;
    public static String language;
    public static boolean showSolution = false;
    public static boolean generousBoggle;
    public static int timeSeconds = 30;
    public static int numOfPlayers;*/
    public static boolean run = true;
    public static GUI gui;
    //public static StandardBoggle standardBoggle;


    public static void main(String[] args) throws Exception {
        /*
        boardsize = "4x4";
        language = "Eng";
*/
        // Pick game mode
        gui = new GUI();
        dictionary = getDictionary();
        gameLoop();
    }
    private static void createServer(int numberOfPlayers) throws IOException {

        System.out.println("Server setup running");
        server = new Server(2048, numberOfPlayers); //Port, number of players;
        server.start();
    }
    private static void sendGameOver() throws IOException {
        for (Player player: server.players) {
            server.sendMessage("Game over", player);
        }
    }
    public static void gameLoop() throws IOException, InterruptedException {
        gui.Menu();
        BoggleFactory factory = new BoggleFactory();
        String mode = gui.getSelectedMode();
        Boolean generousBoggle = gui.getGenetousBoggle();
        String boardSize = gui.getBoardSize();
        int gameTime = gui.getGameTime();
        int numberOfPlayers = gui.getNumberOfPlayers();
        System.out.println(mode);
        createServer(numberOfPlayers);
        IBoggleVariant game = factory.getGame(mode, boardSize, server, generousBoggle);
        ExecutorService threadpool = Executors.newFixedThreadPool(server.players.size());
        for (int i = 0; i < server.numberOfPlayers; i++) {
            Player player = server.players.get(i);
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    game.boggle(player);
                }
            };
            threadpool.execute(task);
        }
        threadpool.awaitTermination(gameTime, TimeUnit.SECONDS);
        threadpool.shutdownNow();
        sendGameOver();
        calculateScoreHelper();
        server.disconnect();
        server = null;
        gameLoop();
    }

    private static void calculateScoreHelper(){
        // Calculate score of all players and send it to all clients
        String msg = "";
        for (int j = 0; j < server.numberOfPlayers; j++) {
            Player player = server.players.get(j);
            player.calculateScore();
            System.out.println(player.score);
            msg += "\nPlayer " + j + " got " + player.score + " points";
        }
        for (int j = 0; j < server.numberOfPlayers; j++) {
            Player player = server.players.get(j);
            try{
                server.sendMessage(msg, player);
            }catch(Exception e){}
        }
    }

    /*
     * Get the dictionary from the supplied text file. Should be placed in the same
     * directory as main.
     */
    public static ArrayList<String> getDictionary() {
        ArrayList<String> dict = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader("Homeexam/Dictionary.txt");
            System.out.println("reading");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                dict.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Dictionary search failed with exception: " + e);
        }
        return dict;
    }

}
