package Game.States;
import Game.Audio.MusicPlayer;
import Game.DataBase.SaveAndReload.SaveAndReloadGameState;
import Game.Game;
import Game.UI.UIManager;
import java.awt.*;
    /*! \abstract class State
        \brief Clasa abstracta serveste drept baza pentru starile jocului,
        definind functionalitati de baza
    */
public abstract class State {

    private static State currentState = null;       /*!< Referinta catre State, are rolul de a retine starea actuala in care se afla jocul*/
    protected boolean isActive = false;             /*!< Flag care se seteaza daca starea este activa*/
    protected static boolean gameActive;            /*!< Flag care tine evidenta daca jocul este in curs de rulare sau nu*/
    protected final Font myFont;                    /*!< Referinta catre Font*/
    protected MusicPlayer musicPlayer;              /*!< Referinta catre MusicPlayer*/
    protected SaveAndReloadGameState saveAndReloadGameState;    /*!< Referinta catre clasa care se ocupa cu incarcarea si salvarea unei lumii, la o anumita stare, din baza de date */

    /*! \fn public State()
        \brief Constructor ce creaza obiectul ce tine de lucrul cu baza de date si obiectul responsabil de configuratia font-ului
    */
    public State()
    {
            ///Creare obiect lucru cu baza de date
        saveAndReloadGameState = new SaveAndReloadGameState();
            ///Creare obiect font
        myFont = new Font("Times New Roman", Font.BOLD, 15);
    }

    /*! \fn public static void setState(State state)
        \brief Seteaza starea data ca si argument pe activ si adauga managerul pentru mouse input starii respective
    */
    public static void setState(State state)
    {
            ///Seteaza flag-ul isActive daca jocul se afla in starea data ca si argument
        state.setActive(true);
        Game.getInstance().getMouseManager().setUiManager(state.getUiManager());
            ///Setare stare curenta
        currentState = state;
    }
    /*! \fn public static State getCurrentState()
        \brief Returneaza starea curenta
    */
    public static State getCurrentState()
    {
        return currentState;
    }
    /*! \fn public void setActive(boolean active)
        \brief Seteaza flag-ul isActive conform starii argumentului
    */
    public void setActive(boolean active) {
        isActive = active;
    }
    /*! \fn public MusicPlayer getMusicPlayer()
        \brief Returneaza musicPlayer-ul
    */
    public MusicPlayer getMusicPlayer()
    {
        return this.musicPlayer;
    }
    /*! \fn public abstract void update();
        \brief Actualizeaza starea curenta, totodata si toate elementele ce tin de ea
    */
    public abstract void update();
    /*! \fn public abstract void render(Graphics g);
        \brief Randeaza starea curenta, mai exact randeaza elementele continute de stare
    */
    public abstract void render(Graphics g);
    /*! \fn  protected abstract UIManager getUiManager();
        \brief Returneaza managerul de obiecte grafice
    */
    protected abstract UIManager getUiManager();
}
