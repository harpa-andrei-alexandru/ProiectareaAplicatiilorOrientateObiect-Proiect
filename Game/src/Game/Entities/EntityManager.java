package Game.Entities;

import Game.Entities.Creatures.Enemy;
import Game.Entities.Creatures.Player;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
/*! \class EntityManager
    \brief Clasa se ocupa cu managementul tuturor entitatilor in ceea ce priveste actiune de update si randare
 */
public class EntityManager
{

    private final Player player;                /*!< Referinta catre Player */
    private final ArrayList<Entity> entities;   /*!< Vector cu entitati */
    private final Comparator<Entity> renderSorter = (entity, entity2) -> Float.compare(entity.getY() + entity.getHeight(), entity2.getY() + entity2.getHeight()); /*!< Referinta catre un comparator */
    private int numberOfEnemies;                /*!< Variabila ce tine evidenta numarului de inamici activi */

    /*! \fn public EntityManager(Player player)
        \brief Constructor care creaza tabloul de entitati, jucatorul si numarul de inamici
     */
    public EntityManager(Player player)
    {
        entities = new ArrayList<>();
        this.player = player;
        entities.add(player);
        numberOfEnemies = 0;
    }

    /*! \fn public void update()
        \brief Metoda itereaza prin tabloul de entitati si apeleaza metoda de update a fiecarei entitati
     */
    public void update()
    {
        ///Cream un interator pentru a putea parcurge vectorul cu entitati
        Iterator<Entity> it = entities.iterator();
        while(it.hasNext())
        {
            ///tempEntity stocheaza entitatea actuala a iterarii prin tablou
            Entity tempEntity = it.next();
            ///apelam metoda de update a entitatii
            tempEntity.update();
            ///Verificam daca entitatea nu este activa
            if(!tempEntity.isActive())
            {
                ///Daca entitatea este un inamic
                if(tempEntity instanceof Enemy)
                {
                    ///scadem numarul de inamici
                    numberOfEnemies--;
                    ///jucatorul va primi 20 de experienta
                    this.player.addExp(20);
                    ///daca viata jucatorului este maxima, atunci va primi un scor de 20 pentru unciderea inamicului
                    if(this.player.getHealth() >= 100)
                        this.player.addScore(20);
                    else
                        ///altfel, va primi un scor de 15 puncte
                        this.player.addScore(15);
                    ///pentru uciderea inamicului, jucatorul primeste 20 de puncte de viata
                    this.player.addHealth(20);
                }
                ///scoatem entitatea din tabloul de entitati
                it.remove();
            }
        }
        ///Apealm metoda de sortare care va hotara ordinea randarii entitatilr
        entities.sort(renderSorter);
    }

    /*! \fn public void render(Graphics g)
        \brief Metoda are rolul de a itera prin tabloul de entitati si de a apela metoda de randare a fiecarei entitati
     */
    public void render(Graphics g)
    {
        for (Entity entity : entities) {
            entity.render(g);
        }
    }
    /*! \fn public void addEntity(Entity e)
        \brief Metoda are rolul de a adauga in tabloul de entitati entitatea trimisa ca si argument
     */
    public void addEntity(Entity e)
    {
        ///Daca entitatea este de tip Enemy, atunci numarul de inamici va fi incrementat cu 1
        if(e instanceof Enemy)
            numberOfEnemies++;
        entities.add(e);
    }
    /*! \fn public Player getPlayer()
        \brief Returneaza referinta catre Player
     */
    public Player getPlayer() {
        return player;
    }
    /*! \fn public ArrayList<Entity> getEntities()
        \brief Returneaza tabloul cu entitati
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    /*! \fn public int getNumberOfEnemies()
        \brief Returneaza numarul de inamici
     */
    public int getNumberOfEnemies()
    {
        return numberOfEnemies;
    }
}
