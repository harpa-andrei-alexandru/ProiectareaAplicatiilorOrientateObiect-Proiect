package Game.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

    /*! \class UIManager
        \brief Clasa va avea ca si membru un ArrayList, in care vom tine diferite obiecte grafice,
        comune undei stari de joc, cum ar fi menuState, helpAndSettingState etc.
    */
public class UIManager
{

    private ArrayList<UIObject> objects;    /*!< Colectia de obiecte grafice*/

    /*! \fn public UIManager()
        \brief Constructor ce initializeaza Colectia de obiecte
    */
    public UIManager()
    {
            ///Creare lista de obiecte
        objects = new ArrayList<>();
    }

    /*! \fn public void update()
        \brief Iteram prin Colectia de obiecte si le actualizam
    */
    public void update()
    {
        for(UIObject object : objects)
            object.update();
    }
    /*! \fn public void render(Graphics g)
        \brief Iteram prin Colectia de obiecte si randam
    */
    public void render(Graphics g)
    {
        for(UIObject object : objects)
            object.render(g);
    }
    /*! \fn public void onMouseMove(MouseEvent mouseEvent)
        \brief Iteram prin Colectia de obiecte si se va seta flag-ul hovering pe true,
        daca cursrul se afla in suprafata de interactiune a obiectului/obiectelor
    */
    public void onMouseMove(MouseEvent mouseEvent)
    {
        for(UIObject object : objects)
            object.onMouseMove(mouseEvent);
    }
    /*! \fn public void onMouseRelease(MouseEvent mouseEvent)
    */
    public void onMouseRelease(MouseEvent mouseEvent)
    {
        for(UIObject object : objects)
            object.onMouseRelease(mouseEvent);
    }
    /*! \fn public void addObject(UIObject object)
        \brief Adaugam obiecte noi la Colectia actuala
     */
    public void addObject(UIObject object)
    {
        objects.add(object);
    }
}
