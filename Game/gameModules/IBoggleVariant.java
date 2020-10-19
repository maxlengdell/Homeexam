package Homeexam.Game.gameModules;

import Homeexam.Game.networking.*;
import Homeexam.Game.gameModules.*;
import Homeexam.language.english.IEnglish;

import java.util.ArrayList;

import Homeexam.*;
import Homeexam.Game.Player.*;
public interface IBoggleVariant extends IEnglish{

    //public Static String findWordOnGrid(String[][] boggle, boolean visited[][], int i, int j, String str);

    public void boggle(Player player);
    public String checkWord(String[][] boggle, String word, Player player, Boolean generousBoggle);
    public void showSolution(ArrayList<String> dictionary);
}
