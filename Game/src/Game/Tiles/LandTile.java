package Game.Tiles;

import Game.Graphics.Assets;
    /*! \class LandTile
        \brief Clasa mosteneste Tile si ofera functionalitati diferite,
        corespunzator tipului de dala
    */
public class LandTile extends Tile {

    /*! \fn public LandTile(int id, int type)
        \breif Constructor ce seteaza id-ul si tipul dalei
        prin apelarea constructorului din casa de baza
    */
    public LandTile(int id, int type)
    {
        super(Assets.tiles[type], id);
    }

    /*! \fn public boolean isSolid()
        \brief returneaza starea dalei, daca este solida sau nu
    */
    @Override
    public boolean isSolid()
    {
        return ((id == 3) || (id == 8) || (id == 9) || (id == 10) || (id == 17) || (id == 28) ||
                (id == 29) || (id ==30) || (id == 32) || (id == 35) || (id == 36) || (id == 37) ||
                (id == 40) || (id == 41) || (id == 42) || (id == 47) || (id == 49) || (id == 50) || (id == 51) || (id == 52));
    }
}
