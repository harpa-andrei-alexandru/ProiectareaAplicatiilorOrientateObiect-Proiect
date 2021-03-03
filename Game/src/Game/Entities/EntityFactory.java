package Game.Entities;

import Game.Entities.Creatures.Enemy;
import Game.Entities.Objects.Bushes;
import Game.Entities.Objects.Tree;
/*! \class EntityFactory
    \brief Clasa reprezinta o fabrica de entitati, acesta va crea entitati conform variabilei type
 */
public class EntityFactory
{
    /*! \fn public EntityFactory()
        \brief Constructor
     */
    public EntityFactory()
    {
    }
    /*! \fn public Entity CreateEntity(String type, int xPos, int yPos)
        \brief Metoda returneaza o entitate conform valorii argumentului type
     */
    public Entity CreateEntity(String type, int xPos, int yPos)
    {
        switch(type)
        {
            case "enemy":
                return new Enemy(xPos, yPos);
            case "tree1":
                return new Tree(xPos, yPos, 1);
            case "tree2":
                return new Tree(xPos, yPos, 2);
            case "tree3":
                return new Tree(xPos, yPos, 3);
            case "bushes":
                return new Bushes(xPos, yPos);
            default :
                return null;
        }
    }

}
