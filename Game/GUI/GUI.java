package Homeexam.Game.GUI;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class GUI {
    private static String playmode;
    private static String selectedMode;
    private static String boardSize;
    private static String language;
    private static Boolean generousBoggle;
    private static boolean showSolution;
    private static int numberOfPlayers;
    private static int gameTime;
    private String filepath = "Homeexam/Game/GUI/menu.xml";

    String menuMsg = ("**************************************\n" + "*      Welcome to VarietyBoggle      *\n"
            + "**************************************\n" + "* Menu:                              *\n"
            + "* [1] Play standard boggle           *\n" + "* [2] Play battle-boggle             *\n"
            + "* [3] Play foggle-boggle             *\n" + "* [4] Settings                       *\n"
            + "* [!] Quit                           *\n"
            + "**************************************\n");

    private Document getXML() {

        try {
            File inputFile = new File(filepath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private void printMenu() {
        System.out.println(menuMsg);
    }

    private void printSettings(Document doc) {
        String menuOutput = "";
        NodeList nList = doc.getElementsByTagName("menu");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\n * Current settings");
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                boardSize = eElement.getElementsByTagName("boardSize").item(0).getTextContent();
                language = eElement.getElementsByTagName("language").item(0).getTextContent();
                generousBoggle = Boolean
                        .parseBoolean(eElement.getElementsByTagName("generousBoggle").item(0).getTextContent());
                numberOfPlayers = Integer
                        .parseInt(eElement.getElementsByTagName("numberOfPlayers").item(0).getTextContent());
                gameTime = Integer.parseInt(eElement.getElementsByTagName("gameTime").item(0).getTextContent());
                showSolution = Boolean
                .parseBoolean(eElement.getElementsByTagName("showSolution").item(0).getTextContent());
                menuOutput += ("\n * Boardsize: " + boardSize);
                menuOutput += ("\n * Language: " + language);
                menuOutput += ("\n * GenerousBoggle: " + generousBoggle);
                menuOutput += ("\n * Number of players: " + numberOfPlayers);
                menuOutput += ("\n * Game time: " + gameTime);
                menuOutput += ("\n * Show solution: " + showSolution);

            }
        }
        System.out.println(menuOutput);
    }

    private void writeToXML(Document doc, String category, String choice) {

        try {
            String menuOutput = "";
            Node nList = doc.getElementsByTagName("menu").item(0);
            NodeList list = nList.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node nNode = list.item(i);
                if (category.equals(nNode.getNodeName())) {
                    nNode.setTextContent(choice);
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            System.out.println("----------------:Writing to XML:---------------");
            StreamResult consoleResult = new StreamResult(filepath);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Menu() {
        selectedMode=null;
        Document doc = getXML();
        printSettings(doc);
        printMenu();
        // System.out.println(menuMsg);
        Scanner in = new Scanner(System.in);
        playmode = in.nextLine();

        // ************Game mode selection*************** */
        if (playmode.equals("1") || playmode.equals("2") || playmode.equals("3")) { // Standard Boggle || Battle Boggle
                                                                                    // || Foggle
            if (playmode.equals("1"))
                selectedMode = "Standard";
            else if (playmode.equals("2"))
                selectedMode = "Battle";
            else
                selectedMode = "Foggle";
        }
        // ***************Settings selection*************** */
        else if (playmode.equals("4")) {
            printSettings(doc);
            String settingChoice = in.nextLine();
            settingChoice.toLowerCase();
            System.out.println(settingChoice);

            if (settingChoice.equals("4x4") || settingChoice.equals("5x5"))
                writeToXML(doc, "boardSize", settingChoice);
            else if (settingChoice.equals("english") || settingChoice.equals("spanish"))
                writeToXML(doc, "language", settingChoice);
            else if (settingChoice.equals("generousboggle"))
                writeToXML(doc, "generousBoggle", String.valueOf(!generousBoggle));
            else if (settingChoice.equals("showsolution"))
                writeToXML(doc, "showSolution", String.valueOf(!showSolution));

            if (settingChoice.endsWith("seconds")) {
                try {
                    String number = settingChoice.split(" seconds")[0];
                    writeToXML(doc, "gameTime", number);
                } catch (NumberFormatException e) {
                }
            } else if (settingChoice.contains("player")) {
                int number = Integer.parseInt(settingChoice.split(" player")[0]);
                if(number < 2)number=2; //Minimum of 2 players
                writeToXML(doc, "numberOfPlayers", String.valueOf(number));
            }
        }
        if(playmode.equals("!"));
        else if(selectedMode==null){
            System.out.println("Gamemode not selected");
            Menu();
        }
        //in.close();
    }

    public String getSelectedMode() {
        return this.selectedMode;
    }

    public String getBoardSize() {
        return this.boardSize;
    }

    public String getLanguage() {
        return this.language;
    }

    public Boolean getGenetousBoggle() {
        return this.generousBoggle;
    }

    public boolean getShowSolition() {
        return this.showSolution;
    }

    public int getGameTime() {
        return this.gameTime;
    }

    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }
}
