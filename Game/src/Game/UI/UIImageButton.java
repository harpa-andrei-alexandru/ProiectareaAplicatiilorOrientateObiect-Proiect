package Game.UI;

import Game.Audio.MusicPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;
    /* \class UIImageButton
       \brief Clasa are rolul de a oferi niste caracteristici grafice obiectului grafic, cum ar fi o imagine,
        anumite sunete si abilitatea de a receptiona evenimente de mouse input
    */
public class UIImageButton extends UIObject
{

    private final BufferedImage image;      /*!< Imaginea obiectului grafic */
    private final ClickListener clicker;    /*!< Referinta catre ClickListener */
    private final MusicPlayer musicPlayer;  /*!< Referinta catre MusicPlayer */


    /*! \fn public UIImageButton(float x, float y, int width, int height, BufferedImage image, ClickListener clicker)
        \brief Construtor ce are rolul de a initializa caracteristicile obiectuli, pozitie pe fereastra, dimensiuni, aparenta etc.
    */
    public UIImageButton(float x, float y, int width, int height, BufferedImage image, ClickListener clicker)
    {
            ///Apelare constructor clasa de baza
        super(x, y, width, height);
            ///Incarcare imagine
        this.image = image;
            ///Initializare ClickListener
        this.clicker = clicker;
            ///Creare obiect MusicPlayer
        musicPlayer = new MusicPlayer("/audio/mouseOverButton.wav");
    }

    /*! \fn public void update()
        \brief Are rolul de a actualiza obiectul
    */
    @Override
    public void update()
    {

    }
    /*! \fn public void render(Graphics g)
        \brief Are rolul de a randa obiectul
    */
    @Override
    public void render(Graphics g)
    {
            ///Daca mouse-ul se afla in aria de interactiune a obiectului,
            /// imaginea obiectului va fi randata cu o dimensiune sufiecient de mare pentru a putea fi vizibil,
            ///altfel imaginea este randata la dimensiunile implicite
        if(hovering)
        {
            g.drawImage(image, (int) x - 5, (int) y - 5, width + 10, height + 10, null);
        }
        else
            g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    /*! \fn public void onClick()
       \brief Atunci cand dam click pe obiect, se va da play la un sunet reprezentativ,
       si se vor realiza task-urile implementate in clicker.onClick()
    */
    @Override
    public void onClick()
    {
        musicPlayer.play();
        clicker.onClick();
    }
}
