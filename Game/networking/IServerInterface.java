package Homeexam.Game.networking;

import java.io.IOException;

import Homeexam.Game.Player.Player;

public interface IServerInterface {
    public void start() throws IOException;
    public void serverSetup() throws Exception;
    public void disconnect() throws IOException;
    public void sendMessage(Object message, Player player) throws IOException;
    public String readMessage(Player player) throws IOException;


}
