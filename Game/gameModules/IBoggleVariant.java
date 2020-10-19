package Homeexam.Game.gameModules;

<<<<<<< Updated upstream:Game/gameModules/IBoggleVariant.java
import Homeexam.Game.networking.*;
import Homeexam.Game.gameModules.*;
import Homeexam.Game.Player.*;
public interface IBoggleVariant {

    public String[][] boggle16 = { { "R", "I", "F", "O", "B", "X" }, { "I", "F", "E", "H", "E", "Y" },
            { "D", "E", "N", "O", "W", "S" }, { "U", "T", "O", "K", "N", "D" }, { "H", "M", "S", "R", "A", "O" },
            { "L", "U", "P", "E", "T", "S" }, { "A", "C", "I", "T", "O", "A" }, { "Y", "L", "G", "K", "U", "E" },
            { "Qu", "B", "M", "J", "O", "A" }, { "E", "H", "I", "S", "P", "N" }, { "V", "E", "T", "I", "G", "N" },
            { "B", "A", "L", "I", "Y", "T" }, { "E", "Z", "A", "V", "N", "D" }, { "R", "A", "L", "E", "S", "C" },
            { "U", "W", "I", "L", "R", "G" }, { "P", "A", "C", "E", "M", "D" } };
=======
import Homeexam.networking.*;
import Homeexam.gameModules.*;
import Homeexam.language.IEnglish;
>>>>>>> Stashed changes:gameModules/IBoggleVariant.java

import java.util.ArrayList;

import Homeexam.*;
import Homeexam.Player.*;
public interface IBoggleVariant extends IEnglish{

    //public Static String findWordOnGrid(String[][] boggle, boolean visited[][], int i, int j, String str);

    public void boggle(Player player);
    public String checkWord(String[][] boggle, String word, Player player, Boolean generousBoggle);
    public void showSolution(ArrayList<String> dictionary);
}
