package Game.Graphics;

import java.awt.image.BufferedImage;
/*! \class SpriteSheet
    \brief Clasa are rolul de a stoca panoul cu imagini utilizat pentru a desena mapa si entitatile
 */
public class SpriteSheet
{

    private final BufferedImage sheet;          /*!< Referinta catre un BufferedImage care va retine panoul cu imagini */

    /*! \fn public SpriteSheet(BufferedImage sheet)
        \brief Constructor ce are rolul de a initializa variabila sheet cu cea primita  ca si argument
     */
    public SpriteSheet(BufferedImage sheet)
    {
        this.sheet = sheet;
    }

    /*! \fn public BufferedImage crop(int x, int y, int width, int height)
        \brief Metoda ce are rolul de a decupa o subimagine din panoul cu imagini de la pozitia data de reprezentata de coordonatele
        x si y, care va avea o latime data de width si o inaltime date de height
     */
    public BufferedImage crop(int x, int y, int width, int height)
    {
        ///Returneaza un BufferedImage care reprezinta subimaginea decupata conform argumentelor x, y, width si height
        return sheet.getSubimage(x, y, width, height);
    }
}
