package Game.Entities.Objects;

import Game.Entities.Entity;

/*! \class MapObjects
    \brief Clasa mosteneste Entity si este utilizata pentru a crea entitati care nu se misca(copaci, tufisuri etc.)
 */
public abstract class MapObjects extends Entity
{
    /*! \fn public MapObjects(float x, float y, int width, int height)
        \brief Constructor care creaza entitatea, apeleaza constructorul clasei parinte
     */
    public MapObjects(float x, float y, int width, int height)
    {
        super(x, y, width, height);
    }
}

