package Game.States;
import Game.Audio.MusicPlayer;
import Game.Game;
import Game.DataBase.Levels.Levels;
import Game.UI.UIManager;
import Game.Worlds.World;
import java.awt.*;

    /*! \class GameState
        \brief Clasa are rolul de a initializa lumea, de a porni jocul si de a jongla intre nivelele jocului
    */
public class GameState extends State {

    private final World world;      /*!< Referinta catre lumea jocului*/
    private Levels.Level currentLevel = Levels.Level.level1;    /*!< Referinta catre Level, tine evidenta nivelului curent si este initializat cu level1*/
    private int score;      /*!< membru care care ajuta la gestiunea scorului jucatorului in momentul de restart nivel*/
    private int exp;        /*!< membru care care ajuta la gestiunea experientei caracterului in momentul de restart nivel*/
    private int level;      /*!< membru care care ajuta la gestiunea nivelului caracterului in momentul de restart nivel*/

    /*! \fn public GameState()
        \brief Constructor care initializeaza lumea si musicPlayer-ul
    */
    public GameState()
    {
        ///Apelare constructor superclasa
        super();
        ///Creare lume noua
        world = new World();
        ///Creare MusicPlayer nou
        musicPlayer = new MusicPlayer(Levels.getLevelMusic(currentLevel));
    }

    /*! \fn public void startGame()
        \brief Metoda care da start jocului, seteaza nivelul curent al jocului,
        seteaza lumea conform nivelului, si porneste muzica de fundal
    */
    public void startGame()
    {
        ///Setare nivel lume curent
        currentLevel = Levels.Level.level1;
        ///Setare lume
        world.setWorld(Levels.getLevelMap(currentLevel));
        ///Setare muzica conform nivelului lumii si pornirea acesteia
        musicPlayer.close();
        musicPlayer.setClip(Levels.getLevelMusic(currentLevel));
        musicPlayer.loop();
    }
    /*! \fn public void nextLevel()
        \brief Are rolul de a transfera jocul la urmatorul sau nivel,
        implica aceleasi configuratii ca si la startGame()
    */
    public void nextLevel()
    {
        currentLevel = Levels.getNextLevel(currentLevel);
        musicPlayer.close();
        musicPlayer.setClip(Levels.getLevelMusic(currentLevel));
        musicPlayer.loop();
        world.setWorld(Levels.getLevelMap(currentLevel));
    }

    /*! \fn public void restartLevel(Levels.Level level)
        \brief Restarteaza nivelul lumii in concordanta cu argumentul
    */
    public void restartLevel(Levels.Level level)
    {
        ///Setare nivel curent
        currentLevel = level;
        ///Setare lume conform nivelului dat ca si argument
        world.setWorld(Levels.getLevelMap(level));
    }

    /*! \fn public void update()
        \brief Actualizeaza starea de joc, accesare meniu intermediar, trecere la nivel urmator,
        setare starii jocului la starea de victorie
    */
    @Override
    public void update()
    {
        ///Actualizare lume
        world.update();
        ///Daca este apasata tasta pentru meniu, se face tranzitia intre stari, meniul intermediar devenind starea activa
        if(Game.getInstance().getKeyManager().menu)
        {
            State.setState(Game.getInstance().inGameMenuState);
        }
        ///Daca numarul de inamici aflati pe mapa este 0 tine evidenta scorului, experientei si a nivelului,
        ///si facem tranzitia spre meniul intermediar sau catre starea de victorie daca nivelul curent al lumii este level2
        if(world.getEntityManager().getNumberOfEnemies() == 0)
        {
            if(currentLevel == Levels.Level.level1) {
                score = world.getEntityManager().getPlayer().getScore();
                exp = world.getEntityManager().getPlayer().getExp();
                level = world.getEntityManager().getPlayer().getLevel();
                State.setState(Game.getInstance().inGameMenuState);
            }
            else if(currentLevel == Levels.Level.level2)
            {
                musicPlayer.stop();
                State.setState(Game.getInstance().winState);
            }


        }
    }
    /*! \fn public void render(Graphics g)
        \brief randeaza lumea si afiseaza detalii live legate de scorul si situatia caracterului,
        cat si numarul de inamici ramasi in viata
    */
    @Override
    public void render(Graphics g)
    {
        world.render(g);
        g.setColor(Color.green);
        g.setFont(myFont);
        printScore(g, 20, 20);
        g.setColor(Color.red);
        g.drawString("NO.ENEMIES: " + world.getEntityManager().getNumberOfEnemies(), 20, 650);
    }
    /*! \fn protected UIManager getUiManager()
        \brief Returneaza managerul de obiecte grafice, in cazul de fata,
         starea de joc nu detine un manager de obiecte grafice
    */
    @Override
    protected UIManager getUiManager() {
        return null;
    }
    /*! \fn public Levels.Level getCurrentLevel()
        \brief Returneaza nivelul actual al jocului
    */
    public Levels.Level getCurrentLevel()
    {
        return currentLevel;
    }
    /*! \fn private void printScore(Graphics g, int x, int y)
        \brief Afiseaza detalii legate de scorul si situatia caracterului
    */
    private void printScore(Graphics g, int x, int y)
    {
        g.drawString("SCORE: " + world.getEntityManager().getPlayer().getScore(), x, y);
        g.drawString("HP: " + world.getEntityManager().getPlayer().getHealth(), x, y + 20);
        g.drawString("EXP: " + world.getEntityManager().getPlayer().getExp(), x, y + 40);
        g.drawString("LEVEL: " + world.getEntityManager().getPlayer().getLevel(), x, y + 60);
    }
    /*! \fn public int getScore()
        \brief Returneaza scorul jucatorului
    */
    public int getScore() {
        return score;
    }
    /*! \fn public int getExp()
        \brief Returneaza experienta caracterului
    */
    public int getExp() {
        return exp;
    }
    /*! \fn public int getLevel()
        \brief Returneaza nivelul caracterului
    */
    public int getLevel() {
        return level;
    }
    /*! \fn public void setCurrentLevel(Levels.Level currentLevel)
        \brief Seteaza nivelul curent al lumii conform argumentului
    */
    public void setCurrentLevel(Levels.Level currentLevel) {
        this.currentLevel = currentLevel;
    }
    /*! \fn public World getWorld()
        \brief Returneaza lumea
    */
    public World getWorld()
    {
        return this.world;
    }

}
