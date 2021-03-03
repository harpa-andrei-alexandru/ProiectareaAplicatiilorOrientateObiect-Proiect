package Game.Entities;

import Game.Game;
import java.awt.*;
/*! \class Entity
    \brief Clasa ce reprezinta structura de baza a entitatilor
 */
public abstract class Entity
{

    public enum ProjectileType{
        hero_projectile, enemy_projectile
    }

    public static final int HEALTH = 100;   /*!< Constanta ce retine valoare default pentru viata entitatii */
    protected float x;                      /*!< Coordonata x a pozitiei entitatii */
    protected float y;                      /*!< Coordonata y a pozitiei entitatii */
    protected int width;                    /*!< Latimea entitatii */
    protected int height;                   /*!< Inaltimea entitatii */
    protected int health;
    protected boolean active = true;        /*!< Flag care tine evidenta starii entitatii, daca este in viata sau nu */
    protected Rectangle bounds;             /*!< Referinta catre Rectangle ce reprezinta aria de coliziune a entitatii */

    /*! \fn public Entity(float x, float y, int width, int height)
        \brief Constructor ce are roulul de a seta pozitia si dimensiunea entitatii, cat si aria de coliziune
     */
    public Entity(float x, float y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = HEALTH;
        bounds = new Rectangle(0, 0, width, height);
    }

    /*! \fn public abstract void update();
        \brief Metoda abstracta care se ocupa cu update-ul entitatii
     */
    public abstract void update();
    /*! \fn public abstract void render(Graphics g);
        \brief Metoda abstracta care se ocupa cu randarea entitatii
     */
    public abstract void render(Graphics g);
    /*! \fn public abstract void die();
        \breif Metoda care se ocupa cu actiunea de moarte a entitatii
     */
    public abstract void die();

    /*! \fn public void hurt(int amount)
        \brief Metoda are rolul de a actualiza nivelul de viata al entitatii si flagul active
     */
    public void hurt(int amount)
    {
        ///viata va scade cu cantitatea amount
        health -= amount;
        ///daca viata entitatii este mai mica sau egala cu 0, atunci entitatea va muri, deci active se seteaza pe false si se apeleaza metoda die() a entitatii
        if(health <= 0)
        {
            active = false;
            die();
        }
    }
    /*! \fn public boolean checkEntityCollisions(float xOffset, float yOffset)
        \brief Metoda are rolul de a verifica daca o entitate se afla in coliziune cu alta entitate
     */
    public boolean checkEntityCollisions(float xOffset, float yOffset)
    {
        ///Parcurgem toate entitatile de pe harta
        for(Entity entity : Game.getInstance().gameState.getWorld().getEntityManager().getEntities())
        {
            ///Daca entitatea este cea care apeleaza metda, sarim peste verificarea coliziunii
            if(entity.equals(this))
                continue;
            ///Daca aria de coliziune a entitatii se intersecteaza cu alta arie de coliziune a altei entitati, se returneaza true, inseamna ca exista coliziune
            if(entity.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
            {
                return true;
            }
        }
        ///Daca nu exista coliziune intre entitati, returnam false
        return false;
    }
    /*! \fn public Rectangle getCollisionBounds(float xOffset, float yOffset)
        \brief Metoda care returneaza un Obiect de tip Rectangle ce reprezinta aria de coliziune a entitatii
     */
    public Rectangle getCollisionBounds(float xOffset, float yOffset)
    {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height );
    }
    /*! \fn public Rectangle getBounds()
        \breif Returneaza aria de coliziune a entitatii
     */
    public Rectangle getBounds() {
        return bounds;
    }
    /*! \fn public float getX()
        \brief Returneaza coordonata x a entitatii
     */
    public float getX() {
        return x;
    }
    /*! \fn public void setX(float x)
        \brief Seteaza coordonata x a entitatii cu cea primita ca si argument
     */
    public void setX(float x) {
        this.x = x;
    }
    /*! \fn public float getY()
        \brief Returneaza coordonata y a entitatii
     */
    public float getY() {
        return y;
    }
    /*! \fn public void setY(float y)
        \brief Seteaza coordonata y a entitatii cu cea primita ca si argument
     */
    public void setY(float y) {
        this.y = y;
    }
    /*! \fn public int getWidth()
        \brief Returneaza latimea entitatii
     */
    public int getWidth() {
        return width;
    }
    /*! \fn public void setWidth(int width)
        \brief Seteaza latimea entitatii cu cea primita ca si argument
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /*! \fn public int getHeight()
        \brief Returneaza inaltimea entitatii
     */
    public int getHeight() {
        return height;
    }
    /*! \fn public void setHeight(int height)
        \brief Seteaza inaltimea entitatii cu cea primita ca si argument
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /*! \fn public int getHealth()
        \brief Returneaza viata entitatii
     */
    public int getHealth() {
        return health;
    }
    /*! \fn public void setHealth(int health)
        \brief Seteaza viata entitaii cu cea primita ca si argument
     */
    public void setHealth(int health) {
        this.health = health;
    }
    /*! \fn public boolean isActive()
        \brief Returneaza starea entitatii, daca este activa sau inactiva
     */
    public boolean isActive() {
        return active;
    }
    /*! \fn public void setActive(boolean active)
        \brief Seteaza flagul de stare al entitatii
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
