package Game.Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*! \class KeyManager
    \brief Clasa implementeaza interfata KeyListener, si are rolul de a manipula inputurile primite de la tastatura
 */
public class KeyManager implements KeyListener {

    private final boolean[] keys;   /*!< Vector de flag-uri care tine evidenta tastelor */
    private int controlSet;         /*!< Variabila utilizata pentru a retine id-ul setului de controale pentru joc */
    public boolean up;              /*!< Flag utilizat pentru a misca jucatorul in sus*/
    public boolean down;            /*!< Flag utilizat pentru a misca jucatorul in jos */
    public boolean left;            /*!< Flag utilizat pentru a misca jucatorul in stanga */
    public boolean right;           /*!< Flag utilizat pentru a misca jucatorul in dreapta */
    public boolean attacking;       /*!< Flag utilizat pentru a ataca cu jucatorul */
    public boolean toMenu;          /*!< Flag utilizat pentru a accesa meniul */
    public boolean menu;            /*!< Un alt flag pentru a accesa meniul */

    /*! \fn public KeyManager()
        \brief Constructor ce are rolul de a crea vectorul de flaguri keys si de a seta controlSet cu id 0
     */
    public KeyManager()
    {
        keys = new boolean[256];
        controlSet = 0;
    }

    /*! \fn public void update()
        \brief Metoda pentru a seta flagurile toMenu si menu si controlSet
     */
    public void update()
    {
        ///seteaza controalele pentru jucator conform id-ului pe care il are controlSet
        setControls(controlSet);
        ///in toMenu stocam valoare din tabloul de flaguri de la pozitia indicata de catre id-ul tastei M
        toMenu = keys[KeyEvent.VK_M];
        ///in menu stocam valoarea din tabloul de flaguri de la pozitia indicata de catre id-ul tastei ESCAPE
        menu = keys[KeyEvent.VK_ESCAPE];
    }

    /*! \fn public void keyTyped(KeyEvent keyEvent)

     */
    @Override
    public void keyTyped(KeyEvent keyEvent)
    {

    }
    /*! \fn public void keyPressed(KeyEvent keyEvent)
        \brief Metoda suprascrisa ce seteaza tabloul de flag-uri de la pozitia data de catre id-ul evenimentului generat de catre tastatura
     */
    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        ///Daca id-ul este negativ sau mai mare decat lungimea tabloului de flaguri, metoda nu seteaza nici un flag
        if(keyEvent.getKeyCode() < 0 || keyEvent.getKeyCode() >= keys.length)
            return;
        keys[keyEvent.getKeyCode()] = true;
    }

    /*! \fn public void keyReleased(KeyEvent keyEvent)
        \brief Metoda suprascrisa care reseteaza tabloul de flaguri de la pozitia data de catre id-ul evenimentului generat de catre tastatura
     */
    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
        ///Daca id-ul este negativ sau mai mare decat lungimea tabloului de flaguri, metoda nu reseteaza nici un flag
        if(keyEvent.getKeyCode() < 0 || keyEvent.getKeyCode() >= keys.length)
            return;
        keys[keyEvent.getKeyCode()] = false;
    }
    /*! \fn public void setControls(int controlsSet)
        \brief Metoda ce are rolul de a seta flag-urile pentru controlul jucatorului conford id-ului stocat in controlSet
     */
    public void setControls(int controlsSet)
    {
        if(controlsSet == 1)
        {
            up = keys[KeyEvent.VK_UP];
            down = keys[KeyEvent.VK_DOWN];
            left = keys[KeyEvent.VK_LEFT];
            right = keys[KeyEvent.VK_RIGHT];
            attacking = keys[KeyEvent.VK_SPACE];
            this.controlSet = controlsSet;
        }
        else
        {
            up = keys[KeyEvent.VK_W];
            down = keys[KeyEvent.VK_S];
            left = keys[KeyEvent.VK_A];
            right = keys[KeyEvent.VK_D];
            attacking = keys[KeyEvent.VK_ENTER];
            this.controlSet = 0;
        }
    }
    /*! \fn public int getControlSet()
        \brief Returneaza variabila controlSet
     */
    public int getControlSet()
    {
        return this.controlSet;
    }

}
