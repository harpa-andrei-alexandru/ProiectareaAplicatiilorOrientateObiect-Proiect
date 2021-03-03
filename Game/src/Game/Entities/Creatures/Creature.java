package Game.Entities.Creatures;

import Game.Audio.MusicPlayer;
import Game.Entities.Entity;
import Game.Game;
import Game.Graphics.Animation;
import Game.Tiles.Tile;

/*! \class Creature
    \brief Clasa mosteneste Entity si are rolul de a implementa creaturi
 */
public abstract class Creature extends Entity
{

    public static final float SPEED = 2.0f;             /*!< Constanta care reprezinta valoare default a vitezei de miscare a creaturii */
    public static final int CREATURE_WIDTH = 64;        /*!< Constanta ce reprezinta latimea creaturii */
    public static final int CREATURE_HEIGHT = 64;       /*!< Constanta ce reprezinta inaltimea creaturii */
    protected float speed;
    protected float xMove;      /*!< Variabila ce retinen umarul de pixel cu care sa va deplasa creatura pe axa x */
    protected float yMove;      /*!< Variabila ce retinen umarul de pixel cu care sa va deplasa creatura pe axa y */
    protected int direction;    /*!< Directia creaturii */

    protected Animation idle_back;
    protected Animation idle_front;
    protected Animation idle_left;
    protected Animation idle_right;

    protected Animation running_up;
    protected Animation running_down;
    protected Animation running_left;
    protected Animation running_right;

    protected Animation attacking_up;
    protected Animation attacking_down;
    protected Animation attacking_left;
    protected Animation attacking_right;

    protected Animation dying;

    protected long lastAttackTimer;                 /*!< Variabila ce stocheaza durata de timp care a trecut de la ultimul moment in care a atacat creatura si pana im momentul actual */
    protected final long attackCooldown = 1000;     /*!< Variabila care stocheaza cantitatea de timp pentru care o creatura trebuie sa astepte pentru a ataca din nou */
    protected long attackTimer = attackCooldown;

    protected int creatureLevel = 1;    /*!< Niveulul creaturii */
    protected MusicPlayer musicPlayer = new MusicPlayer("/audio/arrowSound.wav");   /*!< Referinta catre MusicPlayer*/

    /*! \fn public Creature(float x, float y, int width, int height)
        \brief Constructorul are rolul de a seta dimensiunea creaturii, viteza, viata si aria de coliziune
     */
    public Creature(float x, float y, int width, int height)
    {
        super(x, y, width, height);
        health = HEALTH;
        speed = SPEED;
        xMove = 0;
        yMove = 0;
        direction = 4;

        bounds.x = 16;
        bounds.y = 28;
        bounds.width = 32;
        bounds.height = 30;
    }
    /*! \fn public void move()
        \brief Metoda are rolul de a actualiza pozitia creaturii
     */
    public void move()
    {
        ///Daca nu exista coliziune la coordonata la care urmeaza sa se miste creatura pe axa x, apelam moveX()
        if(!checkEntityCollisions(xMove, 0f))
        {
            moveX();
        }
        ///Daca nu exista coliziune la coordonata la care urmeaza sa se miste creatura pe axa y, apelam moveY()
        if(!checkEntityCollisions(0f, yMove))
        {
            moveY();
        }
    }
    /*! \fn public void moveX()
        \brief Metoda are rolul de a actualiza coordonata x a creaturii, verificand si daca acesta se afla in coliziune
     */
    public void moveX()
    {
        ///Daca xMove este mai mare decat 0, verificam daca la coordonata la care se va misca creatura exista sau nu coliziune
        if(xMove > 0)
        {
            ///Calculam coordonata x la care trebuie sa se miste creatura
            int tempX = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
            if(!collisionWithTile(tempX, (int)(y + bounds.y)/ Tile.TILE_HEIGHT) &&
                !collisionWithTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
            {
                ///Daca nu exista colizune, atunci coordonata x a creaturii este incrementata cu xMove
                x += xMove;
            }
            ///Altfel, daca exista coliziune, atunci coordonata x va fi setata astfel incat creatura sa fie cat mai aproape posibil de obiectul cu care face coliziune
            else
            {
                x = tempX * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
            }
        }
        ///Pentru xMove negativ se procedeaza la fel
        else if(xMove < 0) {
            int tempX = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
            if(!collisionWithTile(tempX, (int)(y + bounds.y)/ Tile.TILE_HEIGHT) &&
                    !collisionWithTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
            {
                x += xMove;
            }
            else
            {
                x = tempX * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
            }
        }
    }
    /*! \fn public void moveY()
        \brief Metoda are rolul de a actualiza coordonata y a proiectilului, verificand si daca acesta se afla in coliziune cu tile-uri de land
     */
    public void moveY()
    {
        ///Logica si verificarile sunt la fel ca si pentru moveX
        if(yMove < 0)
        {
            int tempY = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;
            if(!collisionWithTile((int)(x + bounds.x)/ Tile.TILE_WIDTH, tempY) &&
                    !collisionWithTile((int)(x + bounds.x + bounds.width)/ Tile.TILE_WIDTH, tempY))
            {
                y += yMove;
            }
            else
            {
                y = tempY * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
            }
        }
        else if( yMove > 0)
        {
            int tempY = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
            if(!collisionWithTile((int)(x + bounds.x)/ Tile.TILE_WIDTH, tempY) &&
                    !collisionWithTile((int)(x + bounds.x + bounds.width)/ Tile.TILE_WIDTH, tempY))
            {
                y += yMove;
            }
            else
            {
                y = tempY * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
            }
        }
    }
    /*! \fn protected boolean collisionWithTile(int x, int y)
        \brief Metoda returneaza flag-ul solid a tile-ului de la pozitia x si y
     */
    protected boolean collisionWithTile(int x, int y)
    {
        return Game.getInstance().gameState.getWorld().getTile(x, y).isSolid();
    }
    /*! \fn public int getHealth()
        \brief Metoda returneaza viata creaturii
     */
    public int getHealth() {
        return health;
    }
    /*! \fn public void setHealth(int health)
        \brief Metoda seteaza viata creaturii conform argumentului primit
     */
    public void setHealth(int health) {
        this.health = health;
    }
    /*! \fn public int getDirection()
        \brief Metoda returneaza directia actuala a creaturii
     */
    public int getDirection() {
        return direction;
    }
    /*! \fn public void setCreatureLevel(int creatureLevel)
        \brief Metoda are rolul de a seta nivelul creaturii conform argumentului primit
     */
    public void setCreatureLevel(int creatureLevel) {
        this.creatureLevel = creatureLevel;
    }
}
