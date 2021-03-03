package Game.Entities.Objects;

import Game.Game;
import Game.Graphics.Assets;
import Game.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
/*! \class Tree
 */
public class Tree extends MapObjects
{

    private final int type;   /*!< Variabila care retine tipul copacului */

    /*! \fn public Tree(float x, float y, int type)
        \brief Constructorul creaza o entitatea si ii seteaza dimensiunile si aria de coliziune
     */
    public Tree(float x, float y, int type)
    {
        super(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2);

        bounds.x = 20;
        bounds.y = 80;
        bounds.width = 14;
        bounds.height = 48;
        health = 5000;
        this.type = type;
    }
    /*! \fn public void update()

     */
    @Override
    public void update()
    {

    }
    /*! \fn public void die()

     */
    @Override
    public void die()
    {

    }
    /*! \fn public void render(Graphics g)
        \brief Metoda are rolul de a randa copacul cu ajutorul contextului grafic g
     */
    @Override
    public void render(Graphics g)
    {
        g.drawImage(getAssetByType(type), (int) (x - Game.getInstance().getGameCamera().getxOffset()),
                (int) (y - Game.getInstance().getGameCamera().getyOffset()), width, height, null);
    }

    /*! \fn private BufferedImage getAssetByType(int type)
        \brief Metoda returneaza imaginea copacului ce urmeaza a fi randata, conform valorii argumentului type
     */
    private BufferedImage getAssetByType(int type)
    {
        switch(type)
        {
            case 1:
                return Assets.tree1;
            case 2:
                return Assets.tree2;
            case 3:
            default:
                return Assets.tree3;
        }
    }

}
