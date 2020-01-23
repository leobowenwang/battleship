package main;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Random;

public class GameController
{

    static boolean isMultiplayer = false;
    static boolean isReady = false;
    private Map map_player;
    private Map map_attack;


    private int pos1row; // first tile row clicked
    private int pos1col; // first tile col clicked
    private int pos2row; // second tile row clicked
    private int pos2col; // second tile col clicked
    private boolean isPos1 = true; //which position gets saved, gets changed with each click, true = pos1, false = pos 2
    private static Random random = new Random(); // static to prevent same random numbers


    boolean[][] botChecked = new boolean[10][10]; // how many surrounding tiles have been checked
    int r;
    int c;

    @FXML
    private GridPane grid_player; //player map

    @FXML
    private GridPane grid_attack; //attack map

    @FXML
    private Label log1 = new Label(); //shows enemy ship tiles hit & length of the placing ship
    @FXML
    private Label log2 = new Label(); //shows your tiles hit

    public void initialize()
    {
        log1.setText("");
        log2.setText("");
        map_player = new Map();
        map_attack = new Map();

        for (int i = 0; i < Map.ROWS; i++)
        {
            for (int j = 0; j < Map.ROWS; j++)
            {
                grid_player.add(map_player.tiles[i][j], i, j); //Zellen werden in den Maps kreiiert! - hier werden sie zu dem Grid dazugeaddet
                grid_attack.add(map_attack.tiles[i][j], i, j);
                botChecked[i][j] = false;
            }
        }
        if (!isMultiplayer) //bot logic - Sz
        {
            map_attack.isBot = true; // important for place method
            while (map_attack.shipPoints != 0) // brute force, could be improved
            {
                map_attack.newShip(getRandomNumberInts(0, 9), getRandomNumberInts(0, 9), getRandomNumberInts(0, 9), getRandomNumberInts(0, 9));
            }
        }

            log1.setText("Place a " + map_player.ships[map_player.currentship] + " long ship.");

    }

    void checkIfWon(){ //method gets called in onMouseClickAttack
        if (map_player.shipsTilesHit == 30)
        {
            isReady = false;
            alert("You lost!");
            initialize();
        }
        else if (map_attack.shipsTilesHit == 30)
        {
            isReady = false;
            alert("You won!");
            initialize();
        }
    }

    @FXML
    void onMouseClickAttack(MouseEvent event) throws IOException {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED && isReady)
        {
            int row = (int) event.getX() / 80; // 80 = cells size
            int col = (int) event.getY() / 80;

            if (event.getButton() == MouseButton.PRIMARY)
            {

                if (map_attack.tiles[row][col].isShip) //Left Click on enemy map - if is Ship load image & mark it
                {
                    map_attack.tiles[row][col].update("hit");
                    if (!map_attack.tiles[row][col].isHit)
                    {
                        map_attack.tiles[row][col].isHit = true; //marked
                        map_attack.shipsTilesHit++; //counting towards 30
                    }
                }
                else
                {
                    map_attack.tiles[row][col].update("splash"); // if its not a ship - load image & can be improved by another variable
            }
            checkIfWon();
                if (!isMultiplayer && map_player.shipPoints == 0) // bot logic
                {
                    do
                    {
                        r = getRandomNumberInts(0, 9); //checks if tile was already attacked
                        c = getRandomNumberInts(0, 9);
                    } while (botChecked[r][c]);

                    if (map_player.tiles[r][c].attack()) {
                        map_player.shipsTilesHit++;
                    }

                    botChecked[r][c] = true;

                }
                checkIfWon();
                log1.setText(map_player.shipsTilesHit + " / 30 friendly ships hit");
                log2.setText(map_attack.shipsTilesHit + " / 30 enemy ships hit");
            }


        }
        else
        {
            alert("Not ready yet!");
        }

    }

    @FXML
    void onMouseClickPlayer(MouseEvent event) //method for the placing player ships - Sz
    {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED && map_player.shipPoints != 0)
        {
            int row = (int) event.getX() / 80; // 80 = cells size
            int col = (int) event.getY() / 80;

            if (event.getButton() == MouseButton.PRIMARY)
            {
                if (isPos1) // set pos1
                {
                    pos1row = row;
                    pos1col = col;
                    isPos1 = false;
                }
                else // set po2
                {

                    pos2row = row;
                    pos2col = col;
                    map_player.newShip(pos1row, pos1col, pos2row, pos2col);
                    isPos1 = true;
                    if (map_player.currentship == 10) {
                        log1.setText("Press Ready!");
                    }else {
                        log1.setText("Place a " + map_player.ships[map_player.currentship] + " long ship.");
                    }
                }
            }
        }
    }


    private int getRandomNumberInts(int min, int max) // from minesweeper
    {
        return random.ints(min, (max + 1)).findFirst().getAsInt();
    }

    private void alert(String text) //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void onClickReady()
    {
        if (map_player.shipPoints == 0)
        {
            alert("You can start now by clicking on the right field!");
            isReady = true;
        }
        else
        {
            alert("You need to place " + map_player.shipPoints + " more ship tile(s)");
        }
    }

    public void help()
    {
        alert("Place your ships by clicking 2 tiles. Diagonal ships and ships shorter than 2 and longer than 5 tiles are not allowed. Press ready when you have placed 30 tiles.");
    }
}

