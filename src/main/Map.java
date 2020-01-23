//test
package main;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class Map
{
    public static final int NUM_IMAGES = 10;

    public static int ROWS = 10;
    public static int COLS = 10;
    public Tile[][] tiles;
    public int shipPoints = 30; // how many ship tiles must be placed;
    boolean isBot = false; //if map is a bot map
    int shipsTilesHit = 0; //test
    int[] ships = {5,4,4,3,3,3,2,2,2,2};
    int currentship = 0; 

    public Map()
    {
        tiles = new Tile[ROWS][COLS];
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                tiles[j][i] = new Tile(new Image("file:src/main/resources/water.png"));
            }
        }
    }

    // newShip() and newBotShip() can be combined into one method

    public void newShip(int pos1Row, int pos1Col, int pos2Row, int pos2Col)
    {
        int dR = pos1Row - pos2Row; // difference
        int dC = pos1Col - pos2Col;


        if (dR != 0 && dC != 0) // check if pos1 and pos2 are in the same row/col -> both differences are not zero
        {
            alert("Selection must be in the same row or column");
            return; // leaves method
        }
        else
        {
            int nrTiles = Math.abs(dR + dC) + 1; // calculates absolute nr of tiles selected
            if (nrTiles != ships[currentship])
            {
                alert("Place a " + ships[currentship] + " long ship.");
                return;
            }
            else
            {
                int rotation = 0;
                int r = 0; // where the tiles get placed
                int c = 0; // where the tiles get placed
                if (dR == 0 && dC > 0) // ship is oriented upwards
                {
                    rotation = 0;
                    r = 0;
                    c = -1;
                }
                else if (dR == 0 && dC < 0) // ship is oriented downwards
                {
                    rotation = 180;
                    r = 0;
                    c = 1;
                }
                else if (dR < 0 && dC == 0) // ship is oriented right
                {
                    rotation = 90;
                    c = 0;
                    r = 1;
                }
                else if (dR > 0 && dC == 0) // ship is oriented left
                {
                    rotation = 270;
                    c = 0;
                    r = -1;
                }
                //check if there a ships in the way or if a ship is too close, before ships are being placed
                for (int i = 0; i < nrTiles; i++)
                {
                    if (tiles[pos1Row][pos1Col].isShip) // check stern
                    {
                        alert("There is a ship in the way!");
                        return;
                    }
                    else if (tiles[pos2Row][pos2Col].isShip) //check bow
                    {
                        alert("There is a ship in the way!");
                        return;
                    }
                    else if (tiles[pos1Row + (i * r)][pos1Col + (i * c)].isShip)// check hulls
                    {
                        alert("There is a ship in the way!");
                        return;
                    }
                    try //gepfuscht, could be optimised
                    {
                        if (tiles[pos1Row + (i * r) + 1][pos1Col + (i * c)].isShip) //check if neighbouring cells are ships
                        {
                            alert("Too close to another ship!");
                            return;
                        }
                    }
                    catch(Exception e){}
                    try
                    {
                        if (tiles[pos1Row + (i * r) - 1][pos1Col + (i * c)].isShip) //check if neighbouring cells are ships
                        {
                            alert("Too close to another ship!");
                            return;
                        }
                    }
                    catch(Exception e){}
                    try
                    {
                        if (tiles[pos1Row + (i * r)][pos1Col + (i * c) + 1].isShip) //check if neighbouring cells are ships
                        {
                            alert("Too close to another ship!");
                            return;
                        }
                    }
                    catch(Exception e){}
                    try
                    {
                        if (tiles[pos1Row + (i * r)][pos1Col + (i * c) - 1].isShip) //check if neighbouring cells are ships
                        {
                            alert("Too close to another ship!");
                            return;
                        }
                    }
                    catch(Exception e){}
                }
                for (int i = 0; i < nrTiles; i++)
                {
                    if (i == 0) //first tile is the stern
                    {
                        tiles[pos1Row][pos1Col].place(isBot ? "water" : "stern", rotation);
                    }
                    else if (i == nrTiles - 1) //last tile is the bow
                    {
                        tiles[pos2Row][pos2Col].place(isBot ? "water" : "bow", rotation);
                    }
                    else // inbetween parts are hulls
                    {
                        tiles[pos1Row + (i * r)][pos1Col + (i * c)].place(isBot ? "water" : "hull", rotation);
                    }
                }
                shipPoints -= nrTiles;
                currentship++;
                return;
            }
        }
    }

    private void alert(String text) //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
    {
        if(isBot)
        {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Notification");
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}