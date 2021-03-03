package Game.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
/*! \class ImageLoader
    \brief Clasa are rolul de a incarca un panou cu imagini
 */
public class ImageLoader
{
    /*! \fn public static BufferedImage loadImage(String path)
        \brief Metoda statica care citeste/ incarca o imagine de la locatia data de catre path
     */
    public static BufferedImage loadImage(String path)
    {
        ///Aceasta operatie este inclusa intr-ul block try- cath in cazul in care la locatia data de path nu exista o imagine de incarcat
        try
        {
            ///Returneaza imaginea citita daca nu apare exceptie
            return ImageIO.read(ImageLoader.class.getResource(path));
        }
        ///Exceptia aruncata este de tipui IOException, iar in catch afisam mesajul exceptiei si apelam System.exit() pentru a iesi din program
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
