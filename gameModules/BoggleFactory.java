package Homeexam.gameModules;

//import org.graalvm.compiler.lir.BailoutAndRestartBackendException;

import Homeexam.gameModules.*;
import Homeexam.networking.Server;


public class BoggleFactory {
    private String boardSize;
    private Server server;
    private Boolean generousBoggle;
    public BoggleFactory(){//String boardSize, Server server, Boolean generousBoggle){
        // this.boardSize = boardSize;
        // this.server = server;
        // this.generousBoggle = generousBoggle;
    }

    public IBoggleVariant getGame(String gameMode, String boardSize, Server server, Boolean generousBoggle ){
        if(gameMode.equals("Standard")){
            return getStandardBoggle(boardSize, server, generousBoggle);
        }else if(gameMode.equals("Battle")){
            return getBattleBoggle(boardSize, server, generousBoggle);
        }else{
            return getFoggleBoggle(boardSize, server, generousBoggle);
        }
    }
    private StandardBoggle getStandardBoggle(String boardSize, Server server, Boolean generousBoggle){
        return new StandardBoggle(boardSize, server, generousBoggle); //Generous boggle??

    }
    private BattleBoggle getBattleBoggle(String boardSize, Server server, Boolean generousBoggle){
        return new BattleBoggle(boardSize, server, generousBoggle);

    }
    private FoggleBoggle getFoggleBoggle(String boardSize, Server server, Boolean generousBoggle){
        return new FoggleBoggle(boardSize, server, generousBoggle); //Generous boggle??

    }
}
