package Game.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
    /*! \abstract class UIObject
        \brief  Este o clasa abstracta in care se vor afla caracteristicile de baza, dar si modul in care sa comporta un obiect grafic,
        cum ar fi un buton(coordonatele pozitiei pe ecran, latimea si inaltimea, aria de interactiune cu cursourl mouse-ului etc)
    */
public abstract class UIObject
{
    protected float x;      /*!< Coordonate x a pozitiei obiectului grafic*/
    protected float y;      /*!< Coordonate y a pozitiei obiectului grafic*/
    protected int width;    /*!< Latimea obiectului grafic*/
    protected int height;   /*!< Inaltimea obiectului grafic*/
    protected Rectangle bounds;     /*!< Aria de interactiune reprezentata de un obiect de tip Rectangle*/
    protected boolean hovering = false;     /*!< Flag ce indica daca cursorul se afla, sau nu in aria de interactiune a obiectului grafic*/

    /*! \fn public UIObject(float x, float y, int width, int height)
        \brief Constructorul care seteaza dimensiunile obiectului
    */
    public UIObject(float x, float y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }
    /*! \fn public abstract void update()
        \brief Metoda abstract ce are rolul de a actualiza starea obiectului
    */
    public abstract void update();
    /*! \fn public abstract void render(Graphics g)
        \brief Metoda abstract ce are rolul de a randa obiectul cu ajutorul contextului grafic g
    */
    public abstract void render(Graphics g);

    /*! \fn public abstract void onClick()
        \brief Metoda abstract ce are rolul de a realiza task-uri atunci cand se da click cu mouse-ul pe obiect
    */
    public abstract void onClick();

    /*! \fn public void onMouseMove(MouseEvent mouseEvent)
        \brief Are rolul de a seta flagul hovering daca cursorul mouse-ului se afla in aria de interactiune a obiectului
    */
    public void onMouseMove(MouseEvent mouseEvent)
    {
        hovering = bounds.contains(mouseEvent.getX(), mouseEvent.getY());
    }

    /*! \fn public void onMouseRelease(MouseEvent mouseEvent)
        \brief In momentul in care s-a realizat click-ul asupra obiectului grafic, se vor realiza task-urile
        indicate in metoda onClick();
    */
    public void onMouseRelease(MouseEvent mouseEvent)
    {
        if(hovering)
        {
            onClick();
        }
    }
    /*! \fn public float getX()
        \brief Returneaza coordonata x a pozitiei obiectului in fereastra
    */
    public float getX()
    {
        return x;
    }
    /*! \fn public float setX()
        \brief Seteaza coordonata x a pozitiei obiectului cu cea data ca si argument
    */
    public void setX(float x)
    {
        this.x = x;
    }
    /*! \fn public float getY()
        \brief Returneaza coordonata y a pozitiei obiectului in fereastra
    */
    public float getY()
    {
        return y;
    }
    /*! \fn public float setY()
        \brief Seteaza coordonata y a pozitiei obiectului cu cea data ca si argument
    */
    public void setY(float y)
    {
        this.y = y;
    }
    /*! \fn public int getWidth()
        \brief Returneaza latimea obiectului
    */
    public int getWidth()
    {
        return width;
    }
    /*! \fn public int setWidth()
        \brief Seteaza latimea obiectului conform argumentului
    */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /*! \fn public int getHeight()
        \brief Returneaza inaltimea obiectului
    */
    public int getHeight()
    {
        return height;
    }
    /*! \fn public int setHeight()
        \brief Seteaza inaltimea obiectului conform argumentului
    */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /*! \fn public boolean isHovering()
        \brief Returneaza starea flag-ului
    */
    public boolean isHovering()
    {
        return hovering;
    }
    /*! \fn public boolean setHovering()
        \brief Seteaza flag-ul conform starii date de argument
    */
    public void setHovering(boolean hovering)
    {
        this.hovering = hovering;
    }
}
