package Game.Graphics;

import java.awt.image.BufferedImage;
/*! \class Animation
    \brief Clasa are rolul de a se ocupa cu animatiile entitatilor
 */
public class Animation
{

    private final int cooldown;                  /*!< Variabila in care stocheaza timpul pentru care va fi randat un anumit frame al animatiei */
    private int index;                  /*!< Variabila in care se stocheaza index-ul frame-ului animatiei ce urmeaza a fi randat */
    private long lastTime;              /*!< Variabila in care se stocheaza momentul de timp la care a fost randat un frame ultima data */
    private long timer;                 /*!< Variabila in care se stocheaza timpul de cand a fost randat un frame */
    private final BufferedImage[] frames;     /*!< Tablou de BufferedImage in care se retine frame-urile animatie */

    /*! \fn public Animation(int speed, BufferedImage[] frames)
        \brief Constructor ce are rolul de a initializa variabilele cooldown, si frames
     */
    public Animation(int cooldown, BufferedImage[] frames)
    {
        this.cooldown = cooldown;
        this.frames = frames;
        ///indexul este setat pe 0
        index = 0;
        ///timer-ul este setat pe 0
        timer = 0;
        ///lastTime este initializat cu momentul de timp actual
        lastTime = System.currentTimeMillis();
    }
    /*! \fn public void update()
        \brief Metoda de update care se ocupa cu acualizarea index-ului frame-ului ce trebuie randat
     */
    public void update()
    {
        ///timer este incrementat cu o cantitate de timp egala cu diferenta dintre momentul actual de timp
        /// si momementul in care a fost randat prima data frame-ul actual
        timer += System.currentTimeMillis() - lastTime;
        ///lastTime este actualizat la momentul actual de timp
        lastTime = System.currentTimeMillis();
        ///Daca cantitatea de timp pentru care a fost randat frame-ul actual este mai mare decat timpul de asteptare pentru randarea urmatorului frame
        ///, indexul este incrementat, iar timer este resetat pe 0
        if(timer > cooldown)
        {
            index++;
            timer = 0;
            ///Daca index-ul este mai mare de cat numarul de frame-uri pentru animatie, atunci acesta este resetat pe 0
            ///astfel, animatia o va lua de la inceput
            if(index >= frames.length)
            {
                index = 0;
            }
        }
    }
    /*! \fn public BufferedImage getCurrentFrame()
        \brief Metoda ce returneaza frame-ul actual corespunzator index-ului
     */
    public BufferedImage getCurrentFrame()
    {
        return frames[index];
    }

}
