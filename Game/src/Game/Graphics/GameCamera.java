package Game.Graphics;


import Game.Entities.Entity;
import Game.Game;
import Game.Tiles.Tile;
/*! \class GameCamera
    \brief Clasa care implementeaza functionalitatea camerei jocului
 */
public class GameCamera
{

    private float xOffset;  /*!< Variabila ce retine offset-ul pentru coordonata x */
    private float yOffset;  /*!< variabila ce retine offset-ul pentru coordonata y */

    /*! \fn public GameCamera(float xOffset, float yOffset)
        \brief Constructor ce initializeaza offset-urile
     */
    public GameCamera(float xOffset, float yOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    /*! \fn public void checkBlankSpace()
        \brief Metoda ce are rolul de a opri deplasarea camere daca la xOffset sau yOffset nu se mai afla mapa de randat
     */
    public void checkBlankSpace()
    {
        ///Daca xOffset este negativ, acesta este setat pe 0, intrucat harta este randata de la x = 0
        if(xOffset < 0)
        {
            xOffset = 0;
        }
        ///Altfel, daca xOffset este mai mare decat latimea hartii, atunci xOffset este setat cu diferenta dintre latimea hartii si cea a ferestrei
        else if(xOffset > Game.getInstance().gameState.getWorld().getWidth() * Tile.TILE_WIDTH - Game.getInstance().getWidth())
        {
            xOffset = Game.getInstance().gameState.getWorld().getWidth() * Tile.TILE_WIDTH - Game.getInstance().getWidth();
        }
        ///Daca yOffset este negativ, acesta este setat pe 0, intrucat harta este randata de la y = 0
        if(yOffset < 0)
        {
            yOffset = 0;
        }
        ///Altfel, daca yOffset este mai mare decat inaltimea hartii, atunci yOffset este setat cu diferenta dintre inaltimea hartii si cea a ferestrei
        else if(yOffset > Game.getInstance().gameState.getWorld().getHeight() * Tile.TILE_HEIGHT - Game.getInstance().getHeight())
        {
            yOffset = Game.getInstance().gameState.getWorld().getHeight() * Tile.TILE_HEIGHT - Game.getInstance().getHeight();
        }
    }

    /*! \fn public void centerOnEntity(Entity e)
        \brief Metoda are rolul de a mentine centrul camerei asupra jucatorului
     */
    public void centerOnEntity(Entity e)
    {
        xOffset = e.getX() - (float) Game.getInstance().getWidth() / 2 + (float) e.getWidth() / 2;
        yOffset = e.getY() - (float) Game.getInstance().getHeight() / 2 + (float) e.getHeight() / 2;
        checkBlankSpace();
    }
    /*! \fn public float getyOffset()
        \brief Metoda ce returneaza yOffset
     */
    public float getyOffset() {
        return yOffset;
    }
    /*! \fn public float getxOffset()
        \brief Metoda ce returneaza xOffset
     */
    public float getxOffset() {
        return xOffset;
    }

}
