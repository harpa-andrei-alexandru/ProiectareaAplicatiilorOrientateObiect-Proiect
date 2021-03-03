package Game;

import Game.Graphics.Assets;
import Game.Graphics.GameCamera;
import Game.Graphics.ImageLoader;
import Game.Inputs.KeyManager;
import Game.Inputs.MouseManager;
import Game.States.*;
import Game.Window.Display;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game Loop(Update -> Render)
    Implementeaza interfata Runable:

        public interface Runnable {
            public void run();
        }
 */
public class Game implements Runnable{

    private Display display;            /*!< Fereastra in care se va desena tabla jocului*/
    private final String title;         /*!< Numele jocului*/
    private final int width;            /*!< Lungimea ferestrei*/
    private final int height;           /*!< Latimea ferestrei*/

    private Thread thread;              /*!< Referinta catre thread-ul de update si render al ferestrei*/
    private boolean running = false;    /*!< Flag de stare al firului de executie*/

    private BufferStrategy bs;          /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas*/
                                        /// Scopul este acela de a elimina fenomenul de flickering
    private Graphics g;                 /*!< Referinta catre un context grafic*/
    private BufferedImage background;   /*!< Imagine ce va servi pentru imaginea de background a meniului*/

    public GameState gameState;         /*!< Referinta catre joc*/
    public MenuState menuState;         /*!< Referinta catre meniul principal*/
    public HelpAndSettingsState helpAndSettingsState;   /*!< Referinta catre setari si help*/
    public InGameMenuState inGameMenuState;             /*!< Referinta catre meniul secundar*/
    public LoseState loseState;                         /*!< Referinta catre starea de joc pierdut*/
    public WinState winState;                           /*!< Referinta catre starea de joc castigat*/

    private final KeyManager keyManager;                /*!< Referinta catre obiectul ce gestioneaza intrarile de la tastatura*/
    private final MouseManager mouseManager;            /*!< Referinta catre obiectul ce gestioneaza intrarile de la mouse*/

    private GameCamera gameCamera;                      /*!< Referinta catre camera jocului*/

    /*! \fn private Game(String title, int width, int height)
        \brief Constructor de initializare a clasei Game.

        Acest constructor primeste ca parametru titlul ferestrei, latimea si inaltimea acesteia,
        avand in vedere ca fereastra va fi construita/ creata in cadrul clasei Game.

        \param title Titlul ferestrei
        \param width Latimea ferestrei
        \param height Inaltimea ferestrei
     */
    private Game(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
            ///Resetarea flag-ului running ce indica starea firului de executie
        running = false;
            ///Construirea obectului de gestiune a evenimentelor de la tastatura
        keyManager = new KeyManager();
            ///Construirea obiectului de gestiune a evenimentelor de la mouse
        mouseManager = new MouseManager();
    }
    /*!< Metoda privata pentru design-ul Singleton ce are rolul de a crea o sungura instanta a jocului*/
    private static final Game INSTANCE = new Game("The next Robin Hood",1175, 675);
    /*!< Metoda publica care face posibila accesul la instanta jocului*/
    public static Game getInstance()
    {
        return INSTANCE;
    }
    /*! \fn public String getTitle()
        \brief Returneaza numele jocului
    */
    public String getTitle()
    {
        return this.title;
    }
    /*! \fn public KeyManager getKeyManager()
        \brief Returneaza obiectul care gestioneaza inputul de la tastatura
    */
    public KeyManager getKeyManager()
    {
        return keyManager;
    }
    /*! \fn public MouseManager getMouseManager()
        \brief Returneaza obiectul care gestioneaza inputul de la mouse
    */
    public MouseManager getMouseManager()
    {
        return mouseManager;
    }
    /*! \fn public GameCamera getGameCamera()
        \brief Returneaza obiectul care gestioneaza camera jocului
    */
    public GameCamera getGameCamera()
    {
        return gameCamera;
    }
    /*! \fn public int getWidth()
        \brief Returneaza latimea ferestrei
    */
    public int getWidth()
    {
        return width;
    }
    /*! \fn public int getHeight()
        \brief Returneaza inaltimea ferestrei
    */
    public int getHeight()
    {
        return height;
    }
    /*! \fn private void init()
        \brief Metoda construieste fereastra jocului, inializeaza asset-urile, listener-ul de tastatura si cel de mouse etc.
    */
    private void init()
    {
            /// Este construita fereastra grafica
        display = new Display(title, width, height);
            /// Se ataseaza ferestrei managerul de tastatura pentru a primi evenimente furnizate de fereastra
        display.getFrame().addKeyListener(keyManager);
            /// Se ataseaza ferestrei managerul de mouse pentru a primi evenimente furnizate de fereastra
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
            /// Este incarcata imaginea de background a meniului
        background = ImageLoader.loadImage("/textures/Background.png");
            /// Se incarca elementele grafile (tiles)
        Assets.init();
            /// Se construieste obiectul handler al jocului
            /// Se construieste obiectul camera al jocului
        gameCamera = new GameCamera(0, 0);
            /// Definirea starilor jocuui
        menuState = new MenuState();
        gameState = new GameState();
        helpAndSettingsState = new HelpAndSettingsState();
        inGameMenuState = new InGameMenuState();
        loseState = new LoseState();
        winState = new WinState();
            /// Setarea starii implicite cu care va fi lansat programul in executie
        State.setState(menuState);
    }
    /*! \fn private void update()
        \brief Actualizeaza elementele jocului
    */
    private void update()
    {
            ///Actualizare a starii tastelor
        keyManager.update();
            ///Se verifica daca jocul se afla intr-o oarecare starea pentru a se putea face actualizarea ei
        if(State.getCurrentState() != null)
        {
            ///Actualizarea propriu zisa a starii actuale
            State.getCurrentState().update();
        }
    }
    /*! \fn private void render()
        \brief Randeaza/Deseneaza elementele grafice in fereastra corespunzator starilor actualizate ale elementelor
    */
    private void render()
    {
            ///Returneaza bufferStrategy pentru canvasul existent
        bs = display.getCanvas().getBufferStrategy();
            ///Verifica daca bufferStrategy a fost creat
        if(bs == null)
        {
                ///Se construieste triplul buffer
            display.getCanvas().createBufferStrategy(3);
            return;
        }
            ///Se obtine contextul grafic curent in care se poate desena
        g = bs.getDrawGraphics();
            ///Se deseneaza imaginea de background
        g.fillRect(0,0, width, height);
        g.drawImage(background,0,0, 1175, 675, this.display);
            ///Se verifica daca starea acuala a jocului este activa si se randeaza
        if(State.getCurrentState() != null)
        {
                ///Randarea propriuzisa a starii curente a jocului
            State.getCurrentState().render(g);
        }
            ///Se afiseaza pe ecran
        bs.show();
            ///Elibereaza resursele de memorie aferente contextului grafic curent
        g.dispose();
    }

    /*! \fn public synchronized void start()
        \brief Creaza si da start firului de executie al jocului
    */
    public synchronized void start()
    {
        if(!running)
        {
                ///Se actualizaeaza flag-ul de stare a firului de executie
            running = true;
                ///Se construieste thread-ul
            thread = new Thread(this);
                ///Thread-ul creat este lansat in executie
            thread.start();
        }
    }
    /*! \fn public synchronized void stop()
        \brief Opreste executia thread-ului
    */
    public synchronized  void stop()
    {
        if(running)
        {
                ///Se actualizeaza starea flag-ului
            running = false;
            try
            {
                    ///Se incearca orpirea thread-ului
                thread.join();
            }
            catch (InterruptedException e)
            {
                    ///Daca apar erori in momentul opririi, se afiseaza erorile
                e.printStackTrace();
            }
        }
    }
    /*! \fn public void run()
        \brief Va rula in thread-ul creat
        Metoda va actualiza starea jocului si va redesena tabla de joc
    */
    public void run()
    {
            ///Se apeleaza metoda pentru a initializa jocul
        init();
        final int fps = 60;
        final double timePerTick = 1000000000.0 / fps;
        long currentTime;
        long lastTime = System.nanoTime();
            ///Cat timp thread-ul este pornit, se face update si se randeaza
        while(running)
        {
            currentTime = System.nanoTime();

            if((currentTime - lastTime) > timePerTick)
            {
                    ///Actualizeaza elementele jocului
                update();
                    ///Deseneaza elementele pe tabla dejoc
                render();
                lastTime = currentTime;
            }
        }
            ///Este apelata metoda de stop, pentru a opri executia thread-ului
        stop();
    }
}