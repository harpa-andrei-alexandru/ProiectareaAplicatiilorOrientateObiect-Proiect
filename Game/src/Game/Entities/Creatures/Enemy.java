package Game.Entities.Creatures;

import Game.Entities.Entity;
import Game.Entities.Projectiles.Projectile;
import Game.Game;
import Game.Graphics.Animation;
import Game.Graphics.Assets;
import Game.DataBase.Levels.Levels;
import java.awt.*;
import java.awt.image.BufferedImage;

/*! \fn \class Enemy
    \brief Clasa mosteneste Creature si are rolul de a implementa notiunea de inamic
 */
public class Enemy extends Creature
{


    private final Rectangle visibility;     /*!< Referinta catre Rectangle, reprezinta aria de vizibilitate a inamicului */
    private boolean attacking = false;      /*!< Flag care tine evidenta atacului */
    private int enemyHealth;                /*!< Variabila care retine cantitatea de viata a inamicului */
    /*! \fn public Enemy(float x, float y)
        \brief Constructorul initializeaza animatiile inamicului si aria de vizibilitate, si seteaza nivelul si viata
     */
    public Enemy(float x, float y)
    {
        ///Apelare constructor clasa de baza
        super(x, y, CREATURE_WIDTH, CREATURE_HEIGHT);
        ///Initializare arie de vizibilitate
        visibility = new Rectangle(0 ,0 , 400, 400);
        ///Initializare animatii de repaus
        idle_back = new Animation(150, Assets.enemy_back_idle);
        idle_front = new Animation(150, Assets.enemy_front_idle);
        idle_left = new Animation(150, Assets.enemy_left_idle);
        idle_right = new Animation(150, Assets.enemy_right_idle);
        ///Initializare animatii pentru miscarea inamicului
        running_up = new Animation(100, Assets.enemy_up_running);
        running_down = new Animation(100, Assets.enemy_down_running);
        running_left = new Animation(100, Assets.enemy_left_running);
        running_right = new Animation(100, Assets.enemy_right_running);
        ///Initializare animatii pentru atac
        attacking_up = new Animation(70, Assets.enemy_up_attacking);
        attacking_down = new Animation(70, Assets.enemy_down_attacking);
        attacking_left = new Animation(70, Assets.enemy_left_attacking);
        attacking_right = new Animation(70, Assets.enemy_right_attacking);

        dying = new Animation(70, Assets.enemy_die);
        ///Setare viata si nivelul inamicului
        if(Game.getInstance().gameState.getCurrentLevel() == Levels.Level.level2)
        {
            this.creatureLevel = 2;
            this.health = this.enemyHealth = 200;
        }
        this.enemyHealth = this.health;
    }
    /*! \fn public void update()
        \brief Metoda are rolul de a da update animatiilor si a pozitiei
     */
    @Override
    public void update()
    {
        ///update animatii de repaus
        idle_back.update();
        idle_front.update();
        idle_right.update();
        idle_left.update();
        ///update animatii de miscare
        running_up.update();
        running_down.update();
        running_left.update();
        running_right.update();
        ///update animatii de atac
        attacking_up.update();
        attacking_down.update();
        attacking_left.update();
        attacking_right.update();
        ///preluare inputuri
        getInput();
        ///actualizare pozitie
        move();
    }
    /*! \fn  public void render(Graphics g)
        \brief Metoda are rolul de a desena pe harta inamicul cu ajutorul contextului grafic g
     */
    @Override
    public void render(Graphics g)
    {
        ///desenare inamic
        g.drawImage(getCurrentAnimationFrame(), (int) (x - Game.getInstance().getGameCamera().getxOffset()),
                (int) (y - Game.getInstance().getGameCamera().getyOffset()), width, height, null);
        ///setare culoare a contextului grafic
        g.setColor(Color.red);
        ///desenare baza de viata a inamicului
        g.fillRect((int) (x - Game.getInstance().getGameCamera().getxOffset() + 12), (int) (y - Game.getInstance().getGameCamera().getyOffset()), (int) (health /(2.5 * creatureLevel)) , 5);
    }
    /*! \fn private void getInput()
        \brief Metda are rolul de a actualiza directia si viteza de miscare a inamicului, si de a ataca player-ul
     */
    private void getInput()
    {
        xMove = 0;
        yMove = 0;
        ///Iteram prin vectorul de entitati
        for(Entity entity : Game.getInstance().gameState.getWorld().getEntityManager().getEntities())
        {
            ///Daca entitatea este de tip Player
            if(entity instanceof Player)
            {
                ///Daca exista coliziune intre aria de vizibilitate a inamicului si Player, sau inamicul este atacat, inamicul isi actualizeaza pozitia
                if(entity.getCollisionBounds(0f , 0f).intersects(getVisibilityBounds()) || this.health < this.enemyHealth ) {
                    ///Daca coordonata x a inamicului este mai mica decat coordonata x a Player-ului, atunci se actualizeaza directia inamicului(4) si numarul de pixeli cu care se va de plasa pe axa x
                    if((x < entity.getX()))
                    {
                        if ((entity.getY() + entity.getHeight() <= y) || ( y + height >= entity.getY()))
                            direction = 4;
                        xMove = speed / 2;
                    }
                    ///Daca coordonata x a inamicului este mai mare decat coordonata x a Player-ului, atunci se actualizeaza directia inamicului(2) si numarul de pixeli cu care se va de plasa pe axa x
                    if(x > entity.getX())
                    {
                        if ((entity.getY() + entity.getHeight() <= y) || ( y + height >= entity.getY()))
                            direction = 2;
                        xMove = -speed / 2;
                    }
                    ///Daca coordonata y a inamicului este mai mare decat coordonata y a Player-ului, atunci se actualizeaza directia inamicului(1) si numarul de pixeli cu care se va de plasa pe axa y
                    if(y > entity.getY() )
                    {
                        if(((entity.getX() + entity.getWidth() <= x) || (x >= entity.getX())))
                            direction = 1;
                        yMove = -speed / 2;
                    }
                    ///Daca coordonata y a inamicului este mai mica decat coordonata y a Player-ului, atunci se actualizeaza directia inamicului(3) si numarul de pixeli cu care se va de plasa pe axa y
                    if(y < entity.getY())
                    {
                        if((entity.getX() + entity.getWidth() <= x) || (x >= entity.getX()))
                            direction = 3;
                        yMove = speed/2;
                    }
                    ///Inamicul ataca jucatorul
                    attack(entity);
                }
            }
        }
    }
    /*! \fn public void die()

     */
    @Override
    public void die()
    {

    }
    /*! \fn private void attack(Entity entity)
        \brief Metoda are rolul de a implementa actiunea de atac
     */
    private void attack(Entity entity)
    {
        if(((x <= (entity.getX() + entity.getWidth()) && ((x + width) >= entity.getX())) ||
                ((y <= (entity.getY() + entity.getHeight()) && ((y + height) >= entity.getY())))))
        {
            ///variabila attackTimer este incrementata cu cantitatea de timp calculata ca diferenta intre momentul actual si momentul de timp in care a avut loc ultimul atac
            attackTimer += System.currentTimeMillis() - lastAttackTimer;
            ///lastAttackTimer este setat cu momentul de timp actual
            lastAttackTimer = System.currentTimeMillis();
            ///daca cantitatea de timp de cand a atacat inamicul si pana in momentul actula este mai mic decat attackCooldown, inamicul nu va ataca
            if (attackTimer < attackCooldown)
                return;

            attacking = true;
            ///se da play la sunetul pentru tragerea cu arcul
            musicPlayer.play();
            Game.getInstance().gameState.getWorld().getProjectileManager().addProjectile(new Projectile( this, ProjectileType.enemy_projectile, this.creatureLevel));
            ///attackTimer este setat pe 0
            attackTimer = 0;
        }
        else
            attacking = false;
    }

    /*! \fn private BufferedImage getCurrentAnimationFrame()
        \brief Metoda returneaza frame-ul actual tinand cont de directia de miscare, de atac sau repaus
     */
    private BufferedImage getCurrentAnimationFrame()
    {
        if (xMove < 0)
        {
            return running_left.getCurrentFrame();
        }
        else if(xMove > 0)
        {
            return running_right.getCurrentFrame();
        }
        else if (yMove < 0)
        {
            return running_up.getCurrentFrame();
        }
        else if (yMove > 0)
        {
            return running_down.getCurrentFrame();
        }
        else {
            if (direction == 1)
            {
                return idle_back.getCurrentFrame();
            }
            if (direction == 3)
            {
                return  idle_front.getCurrentFrame();
            }
            if (direction == 2)
            {
                return  idle_left.getCurrentFrame();
            }
            if(attacking)
                return  attacking_down.getCurrentFrame();

            return idle_right.getCurrentFrame();
        }
    }

    /*! \fn private Rectangle getVisibilityBounds()
        \brief Returneaza un Rectangle ce reprezinta aria de vizibilitate a inamicului
     */
    private Rectangle getVisibilityBounds()
    {
        return new Rectangle((int) (x - (visibility.width - CREATURE_WIDTH) / 2), (int) (y - (visibility.height - CREATURE_HEIGHT) / 2), 400, 400 );
    }
    /*! \fn public int getCreatureLevel()
        \brief Returneaza nivelul inamicului
     */
    public int getCreatureLevel()
    {
        return this.creatureLevel;
    }
}
