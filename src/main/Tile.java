package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Tile extends Pane
{
    boolean isShip;
    boolean isHit;
    ImageView image;
    String part;
    double rotation;

    public Tile(Image img)
    {
        image = new ImageView(img);
        image.setFitHeight(80);
        image.setFitWidth(80);
        getChildren().add(image);
    }

    public void update(String img)
    {
        image.setImage(new Image("file:src/main/resources/" + img + ".png"));
    }

    public void place(String part, double rotation) //gets called when placed - Map
    {
        this.part = part;
        this.rotation = rotation;
        image.setImage(new Image("file:src/main/resources/" + part + ".png"));
        image.setRotate(rotation);
        isShip = true;
    }

    public boolean attack() //gets called when player tile is attacked, return true if hit
    {
        if (isShip)
        {
            image.setImage(new Image("file:src/main/resources/" + part + "_hit.png"));
            image.setRotate(rotation);
            isHit = true;
            return true;
        }
        else
        {
            image.setImage(new Image("file:src/main/resources/splash.png"));
            return false;
        }
    }


}
