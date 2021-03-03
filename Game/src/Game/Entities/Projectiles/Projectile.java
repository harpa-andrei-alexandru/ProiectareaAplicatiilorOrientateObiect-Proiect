package Game.Entities.Projectiles;

import Game.Entities.Creatures.Creature;
import Game.Entities.Creatures.Enemy;
import Game.Entities.Entity;
import Game.Game;
import Game.Graphics.Assets;
import Game.Tiles.Tile;
import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Projectile
    \brief Clasa mosteneste Entity si are rolul de a implementa proiectilele
 */
public class Projectile extends Entity {


    private int direction;                              /*!< Variabila ce retine directia proiectilului */
    private float xMove;                                /*!< Variabila ce retine numarul de pixeli cu care se va misca proiectilul pe axa x */
    private float yMove;                                /*!< Variabila ce retine numarul de pixeli cu care se va misca proiectilul pe axa y */
    private final float damage;                         /*!< Variabila ce retine cantitatea de daune pe care le va realiza proiectilul asupra entitatilor */
    private final Creature creature;                    /*!< Referinta catre Creature, proiectilul va fi detinut de catre o creatura */
    private BufferedImage currentDirection;             /*!< Referinta catre un BufferedImage */
    private final ProjectileType projectileType;        /*!< Constanta ce retine tipul proiectilului */


    /*! \fn public Projectile(Creature creature, ProjectileType projectileType, int creatureLevel)
        \brief Constructorul are rolul de a crea proiectilul si de a-i seta caracteristicile generale(damage, directie, viteza de deplasare)
     */
    public Projectile(Creature creature, ProjectileType projectileType, int creatureLevel)
    {
        ///Apelare constructor clasa de baza
        super(creature.getX(), creature.getY(), 32, 8);
        ///Setare damage corespunzator nivelului creaturii
        damage = 15 + 5 * creatureLevel;
        ///Setare viteza de deplasare
        float speed = 6f;
        ///Setare xMove pe 0
        ///Setare yMove pe 0
        xMove = 0;
        yMove = 0;
        this.creature = creature;
        this.projectileType = projectileType;
        ///Setarea directiei proiectilului conform directiei creaturii care a lansat proiectilul
        switch(creature.getDirection())
        {
            case 1:
                currentDirection = Assets.arrow[0];
                direction = 1;
                bounds.x = 14;
                bounds.y = 9;
                bounds.width = 3;
                bounds.height = 28;
                yMove = -speed;
                this.x += 20;
                this.y -= 27;
                break;
            case 3:
                currentDirection = Assets.arrow[2];
                direction = 3;
                bounds.x = 14;
                bounds.y = 9;
                bounds.width = 3;
                bounds.height = 28;
                yMove = speed;
                this.x += 20;
                this.y += 27;
                break;
            case 2:
                currentDirection = Assets.arrow[1];
                direction = 2;
                bounds.y = 14;
                bounds.x = 11;
                bounds.height = 3;
                xMove = -speed;
                this.x -= 30;
                this.y += 20;
                break;
            case 4:
                currentDirection = Assets.arrow[3];
                direction = 4;
                bounds.y = 14;
                bounds.x = 11;
                bounds.height = 3;
                xMove = speed;
                this.x += 30;
                this.y += 20;
                break;
        }
    }
    /*! \fn public void update()
        \brief Metoda are rolul de a da update proiectilului
     */
    @Override
    public void update()
    {
        ///Daca este activ, atunci proiectilul se va misca
        if(active)
        {
            move();
            ///Daca exista coliziune cu entitati, atunci proiectilul va disparea, flag-ul active se seteaza pe false
            if(checkEntityCollisions(xMove, 0f) || checkEntityCollisions(0f, yMove))
            {
                active = false;
            }
        }
    }
    /*! \fn public void render(Graphics g)
        \brief Metoda are rolul de a randa proiectilul cu ajutorul contextului grafic g
     */
    @Override
    public void render(Graphics g)
    {
        ///Conform directiei, se va hotara si dimensiunile ariei de coliziunea
        if(direction == 1 || direction == 3)
        {
            g.drawImage(currentDirection, (int) (x - Game.getInstance().getGameCamera().getxOffset()),
                    (int) (y - Game.getInstance().getGameCamera().getyOffset()), 32, 54, null);
        }
        else {
            g.drawImage(currentDirection, (int) (x - Game.getInstance().getGameCamera().getxOffset()),
                    (int) (y - Game.getInstance().getGameCamera().getyOffset()), 54, 32, null);
        }
    }
    /*! \fn public void die()

     */
    @Override
    public void die()
    {

    }
    /*! \fn public boolean checkEntityCollisions(float xOffset, float yOffset)
        \brief Metoda are rolul de a verifica daca proiectilul intra in coliziune cu alte entitati
     */
    @Override
    public boolean checkEntityCollisions(float xOffset, float yOffset)
    {
        ///Iteram prim tabloul de proiectile
        for(Projectile projectile : Game.getInstance().gameState.getWorld().getProjectileManager().getProjectiles())
        {
            if(projectile.equals(this))
            {
                continue;
            }
            ///Daca exista coliziune cu un alt proiectil, flagul active este setat pe false
            if(projectile.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
            {
                projectile.setActive(false);
                return true;
            }
        }
        ///Iteram prim tabloul de entitati
        for(Entity entity : Game.getInstance().gameState.getWorld().getEntityManager().getEntities())
        {
            if(entity.equals(creature))
            {
                continue;
            }
            ///Daca proiectilul intra in colizune cu o entitate, apelam metoda de hurt a entitatii cu cantitatea de daune a proiectilului
            if(entity.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
            {
                if(!(entity instanceof Enemy && this.projectileType == ProjectileType.enemy_projectile))
                    entity.hurt((int) damage);
                return true;
            }
        }
        return false;
    }
    /*! \fn public void moveX()
        \brief Metoda are rolul de a actualiza coordonata x a proiectilului, verificand si daca acesta se afla in coliziune cu tile-uri de land
     */
    public void moveX()
    {
        ///Daca xMove este mai mare decat 0, verificam daca la coordonata la care se va misca proiectilul exista sau nu coliziune
        if(xMove > 0)
        {
            ///Calculam coordonata x la care trebuie sa se miste proiectilul
            int tempX = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
            if(!collisionWithTile(tempX, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                !collisionWithTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
            {
                ///Daca nu exista colizune, atunci coordonata x a proiectilului este incrementata cu xMove
                x += xMove;
            }
            ///Altfel, daca exista coliziune, atunci flagul active este setat pe false
            else
            {
                active = false;
            }
        }
        ///Pentru xMove negativ se procedeaza la fel
        else if(xMove < 0)
        {
            int tempX = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
            if(!collisionWithTile(tempX, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionWithTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
            {
                x += xMove;
            }
            else
            {
                active = false;
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
            if(!collisionWithTile((int) ((x + bounds.x) / Tile.TILE_WIDTH), tempY) &&
                !collisionWithTile((int) ((x + bounds.x + bounds.width) / Tile.TILE_WIDTH), tempY))
            {
                y += yMove;
            }
            else
            {
                active = false;
            }
        }
        else if(yMove > 0)
        {
            int tempY = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
            if(!collisionWithTile((int) ((x + bounds.x) / Tile.TILE_WIDTH), tempY) &&
                    !collisionWithTile((int) ((x + bounds.x + bounds.width) / Tile.TILE_WIDTH), tempY))
            {
                y += yMove;
            }
            else
            {
                active = false;
            }
        }
    }
    /*! \fn public void move()
        \brief Metoda are rolul de a actualiza pozitia proiectilului, apeland metodele moveX() si moveY()
     */
    public void move()
    {
        moveX();
        moveY();
    }
    /*! \fn protected boolean collisionWithTile(int x, int y)
        \brief Metoda returneaza flag-ul solid a tile-ului de la pozitia x si y
     */
    protected boolean collisionWithTile(int x, int y)
    {
        return Game.getInstance().gameState.getWorld().getTile(x, y).isSolid();
    }
}
