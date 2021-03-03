package Game.Entities.Objects;

import Game.Game;
import Game.Graphics.Assets;

import java.awt.*;
/*! \class Bushes
    \brief Clasa implementeaza tufisurile
 */
public class Bushes extends MapObjects
{
    /*! \fn public Bushes(float x, float y)
        \brief Constructorul apeleaza constructorul clasei parinte, si actualizeaza aria de coliziune
     */
    public Bushes(float x, float y)
    {
        super(x, y, 63, 28);

        bounds.x = 15;
        bounds.y = 5;
        bounds.width = 33;
        bounds.height = 13;
        health = 5000;
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
        \brief Metoda are rolul de a randa obiectul cu ajutorul contextului grafic g
     */
    @Override
    public void render(Graphics g)
    {
        g.drawImage(Assets.bushes, (int) (x - Game.getInstance().getGameCamera().getxOffset()),
                (int) (y - Game.getInstance().getGameCamera().getyOffset()), width, height, null);
    }
}
