package Homeexam.Game.gameModules;

//import org.graalvm.compiler.lir.BailoutAndRestartBackendException;

import Homeexam.Game.networking.Server;


public class BoggleFactory {
    /*
    private String boardSize;
    private Server server;
    private Boolean generousBoggle;
    public BoggleFactory(){//String boardSize, Server server, Boolean generousBoggle){
        // this.boardSize = boardSize;
        // this.server = server;
        // this.generousBoggle = generousBoggle;
    }*/

    public IBoggleVariant getGame(String gameMode, String boardSize, Server server, Boolean generousBoggle, Boolean showSolution){
        if(gameMode.equals("Standard")){
            return new StandardBoggle(boardSize, server, generousBoggle, showSolution); //Generous boggle??
        }else if(gameMode.equals("Battle")){
            return new BattleBoggle(boardSize, server, generousBoggle, showSolution);
        }else{
            return new FoggleBoggle(boardSize, server, generousBoggle, showSolution); //Generous boggle??
        }
    }
    /*
    private StandardBoggle getStandardBoggle(String boardSize, Server server, Boolean generousBoggle){
        return new StandardBoggle(boardSize, server, generousBoggle); //Generous boggle??

    }
    private BattleBoggle getBattleBoggle(String boardSize, Server server, Boolean generousBoggle){
        return new BattleBoggle(boardSize, server, generousBoggle);

    }
    private FoggleBoggle getFoggleBoggle(String boardSize, Server server, Boolean generousBoggle){
        return new FoggleBoggle(boardSize, server, generousBoggle); //Generous boggle??

    }*/
}
