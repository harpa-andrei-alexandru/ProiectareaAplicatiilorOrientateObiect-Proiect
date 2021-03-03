package Game.Inputs;

import Game.UI.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

    /*! \class MouseManager
        \brief Clasa are rolul de a manipula input-urile de la mouse
    */
public class MouseManager implements MouseListener, MouseMotionListener
{

    private boolean leftPressed;    /*!< Flag ce tine evidenta click-ului stanga*/
    private boolean rightPressed;   /*!< Flag ce tine evidenta click-ului dreapta*/
    private UIManager uiManager;    /*!< Referinta catre managerul ce se ocupa cu User Interface*/

    /*! \fn public MouseManager()
        \brief Constructor
     */
    public MouseManager()
    {

    }
    /*! \fn public void setUiManager(UIManager uiManager)
        \brief Seteaza variabila uiManager cu argumentul primit
     */
    public void setUiManager(UIManager uiManager)
    {
        this.uiManager = uiManager;
    }

    /*! \fn public void mouseClicked(MouseEvent mouseEvent)

     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    /*! \fn public void mousePressed(MouseEvent mouseEvent)
        \brief Metoda suprascrisa ce are rolul de a seta flag-urie daca exista evenimente

     */
    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        ///Daca evenimentul provine de la cick stanga, este setat flagul leftPressed
        if(mouseEvent.getButton() == MouseEvent.BUTTON1)
        {
            leftPressed = true;
        }
        ///Altfe, daca evenimentul provine de la click dreapta, este setat flagul rightPressed
        else if (mouseEvent.getButton() == MouseEvent.BUTTON3)
        {
            rightPressed = true;
        }
    }
    /*! \fn public void mouseReleased(MouseEvent mouseEvent)
        \brief Metoda suprascrisa ce are rolul de a reseta flagurile leftPressed
        si rightPressed, dar si de a apela metoda onMouseRelease din managerul User Interface
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        ///Daca nu mai este apasat click stanga, atunci se reseteaza flagul leftPressed
        if(mouseEvent.getButton() == MouseEvent.BUTTON1)
        {
            leftPressed = false;
        }
        ///Altfel, daca nu mai este apasat click dreapta, atunci se reseteaza flagul rightPressed
        else if (mouseEvent.getButton() == MouseEvent.BUTTON2)
        {
            rightPressed = false;
        }
        ///Daca avem un uiManager de la care primim input, atunci se apeleaza onMouseRelease,
        ///astfel se va parcurge prin toate obiectele grafice si se va apela metoda onMouseRelease,
        ///care la randul sau va apela metoda onClick care va realiza task-ul implementat pentru obiectul respectiv
        if(uiManager != null)
        {
            uiManager.onMouseRelease(mouseEvent);
        }
    }
    /*! \fn public void mouseEntered(MouseEvent mouseEvent)

     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    /*! \fn public void mouseExited(MouseEvent mouseEvent)

     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
    /*! \fn public void mouseDragged(MouseEvent mouseEvent)

     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }
    /*! \fn public void mouseMoved(MouseEvent mouseEvent)
        \brief Metoda suprascrisa ce are rolul de a salva coordonatele mouse-lui
        si de a apela metoda onMouseMove din managerul User Interface
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        int mouseX = mouseEvent.getX(); /*! <Variabila ce retine coordonata x a cursorului */
        int mouseY = mouseEvent.getY(); /*! <Variabila ce retine coordonata y a cursorului */
        ///
        ///Daca avem un uiManager de la care primim input, atunci se apeleaza onMouseMove,
        ///astfel se va parcurge prin toate obiectele grafice si se va apela metoda onMouseMove
        if(uiManager != null)
        {
            uiManager.onMouseMove(mouseEvent);
        }
    }
}
