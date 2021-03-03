package Game.Entities.Projectiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/*! \class ProjectileManager
    \brief Clasa are rolul de a updata si randa toate proiectilele
 */
public class ProjectileManager
{
    private final ArrayList<Projectile> projectiles;    /*!< Tablou in care vor fi tinute toate proiectilele */

    /*! \fn  public ProjectileManager()
        \brief Constructor care initializeaza tabloul de proiectile cu unul gol
     */
    public ProjectileManager()
    {
        projectiles = new ArrayList<>();
    }
    /*! \fn public void update()
        \brief Metoda va itera prin tabloul de proiectile si va apela metoda de update a fiecarui proiectil
     */
    public void update()
    {
        ///Cream un iterator pentru a parcurge tabloul de proiectile
        Iterator<Projectile> it = projectiles.iterator();

        while(it.hasNext())
        {
            ///in tempP vom retine proiectilul actual in urma iterarii prin tablou
            Projectile tempP = it.next();
            ///apleam metoda de update
            tempP.update();
            ///daca flag-ul active este false, atunci proiectilul este scos din tablou
            if(!tempP.isActive())
            {
                it.remove();
            }
        }
    }
    /*! \fn public void render(Graphics g)
        \brief Metoda are rolul de a itera prin tabloul de proiectile si de a apela metoda render a fiecarui proiectil
     */
    public void render(Graphics g)
    {
        for(Projectile projectile : projectiles)
        {
            projectile.render(g);
        }
    }
    /*! \fn public void addProjectile(Projectile projectile)
        \brief Metoda are rolul de a adauga un proiectil, dat ca si argument, in tabloul de proiectile
     */
    public void addProjectile(Projectile projectile)
    {
        projectiles.add(projectile);
    }
    /*! \fn public ArrayList<Projectile> getProjectiles()
        \brief Metoda returneaza tabloul de proiectile
     */
    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }
}
