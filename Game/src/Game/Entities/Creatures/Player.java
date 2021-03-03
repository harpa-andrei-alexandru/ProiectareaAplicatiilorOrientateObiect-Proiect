package Game.Entities.Creatures;

import Game.Entities.Projectiles.Projectile;
import Game.Game;
import Game.Graphics.Animation;
import Game.Graphics.Assets;
import Game.States.State;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Player
    \brief Clasa mosteneste Creature si implementeaza notiuna de jucator
 */
public class Player extends Creature {

    private int score;     /*!< Variabila ce retine scorul */
    private int exp;       /*!< Variabila ce retine experienta acumulata */

    /*! \fn public Player(float x, float y)
        \brief Constructorul creaza entitatea si initializeaza animatiile jucatorului, seteaza scorul si experienta
     */
    public Player(float x, float y) {
        ///Apelare Constructor de baza
        super(x, y, CREATURE_WIDTH, CREATURE_HEIGHT);
        ///Initializare animatii pentru starea de repaus
        idle_back = new Animation(150, Assets.player_back_idle);
        idle_front = new Animation(150, Assets.player_front_idle);
        idle_left = new Animation(150, Assets.player_left_idle);
        idle_right = new Animation(150, Assets.player_right_idle);
        ///Initializare animatii pentru starea de miscare
        running_up = new Animation(100, Assets.player_up_running);
        running_down = new Animation(100, Assets.player_down_running);
        running_left = new Animation(100, Assets.player_left_running);
        running_right = new Animation(100, Assets.player_right_running);
        ///Initializare animatii pentru starea de atac
        attacking_up = new Animation(70, Assets.player_up_attacking);
        attacking_down = new Animation(70, Assets.player_down_attacking);
        attacking_left = new Animation(70, Assets.player_left_attacking);
        attacking_right = new Animation(70, Assets.player_right_attacking);
        ///Initializare animatie pentru starea de moarte
        dying = new Animation(70, Assets.player_die);
        ///Setare scor pe 0
        score = 0;
        ///Setare experienta pe 0
        exp = 0;

    }
    /*! \fn public void update()
        \brief Metoda are rolul de a actualiza eniamtiile, miscarea si atacul jucatorului
     */
    @Override
    public void update() {
        ///Actualizare animatii pentru starea de repaus
        idle_back.update();
        idle_front.update();
        idle_right.update();
        idle_left.update();
        ///Actualizare animatii pentru starea de miscare
        running_up.update();
        running_down.update();
        running_left.update();
        running_right.update();
        ///Actualizare animatii pentru starea de atack
        attacking_up.update();
        attacking_down.update();
        attacking_left.update();
        attacking_right.update();
        ///Actualizare date de intrare pentru miscare, atac
        getInput();
        ///Actualizare pozitie
        move();
        ///Actualizarea starii de atac
        Game.getInstance().getGameCamera().centerOnEntity(this);
        attack();
    }
    /*! \fn private void attack()
        \brief Metoda are rolul de a implementa actiunea de atac
     */
    private void attack() {
        ///attackTimer este incrementat cu cantitatea de timp egala cu diferenta dintre momentul actual de timp
        ///si momentul de timp la care a avut loc ultimul atac
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        ///lastAttackTimer este setat cu momentul de timp actual
        lastAttackTimer = System.currentTimeMillis();
        ///daca attackTimer este mai mic decat cantitatea de timp de asteptare necesar pentru a ataca(attackCooldown)
        ///jucatorul nu va ataca
        if (attackTimer < attackCooldown) {
            return;
        }
        ///Daca de la tastatura s-a primit input pentru atac, se da play la sunetul pentru tragerea cu arcul si se lanseaza proiectilul
        if (Game.getInstance().getKeyManager().attacking) {
            musicPlayer.play();
            Game.getInstance().gameState.getWorld().getProjectileManager().addProjectile(new Projectile( this, ProjectileType.hero_projectile, this.creatureLevel));
        }
        ///Daca nu se primeste input de atac, atunci jucatorul nu va ataca
        else {
            return;
        }
        ///attackTimer va fi setat pe 0
        attackTimer = 0;
    }

    /*! \fn public void die()
        \brief Daca jucatorul va muri, atunci se va face tranzitia catre starea de lose
     */
    @Override
    public void die()
    {
        State.setState(Game.getInstance().loseState);
    }

    /*! \fn public void render(Graphics g)
        \brief Metoda are rolul de a desena jucatorul si bara de viata a acestuia cu ajutorul contextului grafic g
     */
    @Override
    public void render(Graphics g) {
        ///Desenare jucator pe harta
        g.drawImage(getCurrentAnimationFrame(), (int) (x - Game.getInstance().getGameCamera().getxOffset()),
                (int) (y - Game.getInstance().getGameCamera().getyOffset()), width, height, null);

        ///Daca viata jucatorului este mai mare de 50, bara de viata va avea culoarea verde
        if(this.health >= 50)
            ///Setare culoarea contextului grafic pe verde
            g.setColor(Color.green);
        ///Altfel, daca viata jucatorului este mai mare sau egala cu 30, bara de viata va avea culoarea portocalie
        else if(this.health >= 30)
            ///Setare culoarea contextului grafic pe portocaliu
            g.setColor(Color.orange);
        ///Altfel, culoarea barei de viata a jucatorului va fi rosie
        else
            ///Setare culoarea contextului grafic pe rosu
            g.setColor(Color.red);
        ///Desenare bara de viata
        g.fillRect((int) (x - Game.getInstance().getGameCamera().getxOffset() + 12), (int) (y - Game.getInstance().getGameCamera().getyOffset()), (int) (health /2.5), 5);
    }

    /*! \fn private BufferedImage getCurrentAnimationFrame()
        \brief Metoda are rolul de a returna frame-ul actual al animatiei determinate de pozitie si input
     */
    private BufferedImage getCurrentAnimationFrame() {
        if (Game.getInstance().getKeyManager().right) {
            return running_right.getCurrentFrame();
        } else if (Game.getInstance().getKeyManager().left) {
            return running_left.getCurrentFrame();
        } else if (Game.getInstance().getKeyManager().up) {
            return running_up.getCurrentFrame();
        } else if (Game.getInstance().getKeyManager().down) {
            return running_down.getCurrentFrame();
        } else if (direction == 4) {
            return Game.getInstance().getKeyManager().attacking ? attacking_right.getCurrentFrame() : idle_right.getCurrentFrame();
        } else if (direction == 2) {
            return Game.getInstance().getKeyManager().attacking ? attacking_left.getCurrentFrame() : idle_left.getCurrentFrame();
        } else if (direction == 1) {
            return Game.getInstance().getKeyManager().attacking ? attacking_up.getCurrentFrame() : idle_back.getCurrentFrame();
        } else {
            return Game.getInstance().getKeyManager().attacking ? attacking_down.getCurrentFrame() : idle_front.getCurrentFrame();
        }
    }

    /*! \fn private void getInput()
        \brief Metoda are rolul de a seta variabilele xMove si yMove, care reprezinta numarul de pixeli cu care se va deplasa jucatorul(sus, s]jos, stanga, dreapta), si directia de deplasare
     */
    private void getInput() {
        ///xMove si yMove se seteaza pe 0
        xMove = 0;
        yMove = 0;
        ///Daca se primeste input pentru miscare in sus, yMove va si egal cu -speed si direction = 1
        if (Game.getInstance().getKeyManager().up) {
            yMove = -speed;
            direction = 1;
        }
        ///Daca se primeste input pentru miscare in jos, yMove va si egal cu speed si direction = 3
        if (Game.getInstance().getKeyManager().down) {
            yMove = speed;
            direction = 3;
        }
        ///Daca se primeste input pentru miscare in stanga, xMove va si egal cu -speed si direction = 2
        if (Game.getInstance().getKeyManager().left) {
            xMove = -speed;
            direction = 2;
        }
        ///Daca se primeste input pentru miscare in dreapta, xMove va si egal cu speed si direction = 4
        if (Game.getInstance().getKeyManager().right) {
            xMove = speed;
            direction = 4;
        }
    }
    /*! \fn  public void addHealth(int newHealth)
        \brief Metoda are scopul de a adauga viata jucatorului
     */
    public void addHealth(int newHealth) {
        ///Daca cantitatea de viata totala a jucatorului va depasi 100, atunci viata jucatorului va fi setata la 100
        if(this.health + newHealth >= 100)
        {
            this.health = 100;
        }
        ///altfel, viata jucatorului va fi setate cu cantitatea totala calculata
        else
        {
            this.health += newHealth;
        }
    }
    /*! \fn public void addExp(int newExp)
        \brief Metoda are rolul de a incrementa cantitatea de experienta adunata de jucator
     */
    public void addExp(int newExp)
    {
        ///Daca cantitatea totala de experienta este mai mare sau egala cu 75, atunci jucatorul va face leve up,
        ///iar nivelul de experienta va fi setat cu surplusul de peste 75 de puncte de experienta
        if(this.exp + newExp >= 75)
        {
            creatureLevel++;
            this.health = HEALTH;
            this.exp += newExp - 75;
        }
        ///altfel, cantitatea de experienta va fi egala cu totalul calculat
        else
        {
            this.exp += newExp;
        }
    }
    /*! \fn public int getScore()
        \brief Metoda returneaza scorul jucatorului
     */
    public int getScore() {
        return score;
    }
    /*! \fn public void addScore(int newScore)
        \brief Metoda incrementeaza scorul jucatorului cu cantitatea data ca si argument
     */
    public void addScore(int newScore) {
        this.score += newScore;
    }
    /*! \fn public int getExp()
        \brief Returneaza cantitatea de experienta a jucatorului
     */
    public int getExp()
    {
        return this.exp;
    }
    /*! \fn public int getLevel()
        \brief returneaza nivelul jucatorului
     */
    public int getLevel()
    {
        return creatureLevel;
    }
    /*! \fn public void setScore(int score)
        \brief Seteaza scorul jucatorului cu cantitatea trimisa ca si argument
     */
    public void setScore(int score) {
        this.score = score;
    }
    /*! \fn public void setExp(int exp)
        \brief Seteaza experienta jucatorului cu cantitatea trimisa ca si argument
     */
    public void setExp(int exp) {
        this.exp = exp;
    }
}
