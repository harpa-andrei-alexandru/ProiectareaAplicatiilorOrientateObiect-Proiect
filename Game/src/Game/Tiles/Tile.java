package Game.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

/*! \class Tile
    \brief Clasa ce implementeaza dalele hartii
*/
public class Tile {

    public static Tile[] tiles = new Tile[256];
    public static Tile landTile = new LandTile(0, 0);
    public static Tile landTile2 = new LandTile(1,1);
    public static Tile landTile3 = new LandTile(2,2);
    public static Tile landTile4 = new LandTile(3, 3);
    public static Tile landTile5 = new LandTile(4,4);
    public static Tile landTile6 = new LandTile(5,5);
    public static Tile landTile7 = new LandTile(6,6);
    public static Tile landTile8 = new LandTile(7,7);
    public static Tile landTile9 = new LandTile(8,8);
    public static Tile landTile10 = new LandTile(9,9);
    public static Tile landTile11 = new LandTile(10,10);
    public static Tile landTile12 = new LandTile(11,11);
    public static Tile landTile13 = new LandTile(12,12);
    public static Tile landTile14 = new LandTile(13,13);
    public static Tile landTile15 = new LandTile(14,14);
    public static Tile landTile16 = new LandTile(15,15);
    public static Tile landTile17 = new LandTile(16,16);
    public static Tile landTile18 = new LandTile(17,17);
    public static Tile landTile19 = new LandTile(18,18);
    public static Tile landTile20 = new LandTile(19,19);
    public static Tile landTile21 = new LandTile(20,20);
    public static Tile landTile22 = new LandTile(21,21);
    public static Tile landTile23 = new LandTile(22,22);
    public static Tile landTile24 = new LandTile(23,23);
    public static Tile landTile25 = new LandTile(24,24);
    public static Tile landTile26 = new LandTile(25,25);
    public static Tile landTile27 = new LandTile(26,26);
    public static Tile landTile28 = new LandTile(27,27);
    public static Tile landTile29 = new LandTile(28,28);
    public static Tile landTile30 = new LandTile(29,29);
    public static Tile landTile31 = new LandTile(30,30);
    public static Tile landTile32 = new LandTile(31,31);
    public static Tile landTile33 = new LandTile(32,32);
    public static Tile landTile34 = new LandTile(33,33);
    public static Tile landTile35 = new LandTile(34,34);
    public static Tile landTile36 = new LandTile(35,35);
    public static Tile landTile37 = new LandTile(36,36);
    public static Tile landTile38 = new LandTile(37,37);
    public static Tile landTile39 = new LandTile(38,38);
    public static Tile landTile40 = new LandTile(39,39);
    public static Tile landTile41 = new LandTile(40,40);
    public static Tile landTile42 = new LandTile(41,41);
    public static Tile landTile43 = new LandTile(42,42);
    public static Tile landTile44 = new LandTile(43,43);
    public static Tile landTile45 = new LandTile(44,44);
    public static Tile landTile46 = new LandTile(45,45);
    public static Tile landTile47 = new LandTile(46,46);
    public static Tile landTile48 = new LandTile(47,47);
    public static Tile landTile49 = new LandTile(48,48);
    public static Tile landTile50 = new LandTile(49,49);
    public static Tile landTile51 = new LandTile(50,50);
    public static Tile landTile52 = new LandTile(51,51);
    public static Tile landTile53 = new LandTile(52,52);

    public static final int TILE_WIDTH = 64;    /*!< Constanta ce reprezinta latimea unei dae in pixeli*/
    public static final int TILE_HEIGHT = 64;   /*!< Constanta ce reprezinta inaltimea unei dae in pixeli*/
    protected BufferedImage texture;            /*!< Imaginea dalei*/
    protected final int id;                     /*!< Id-ul dalei*/

    /*! \fn public Tile(BufferedImage texture, int id)
        \ Constructor care incarca imaginea dalei si ii seteaza un id unic
    */
    public Tile(BufferedImage texture, int id)
    {
            /// Incarcare imagine
        this.texture = texture;
            ///Setare id unic
        this.id = id;
            ///Adaugare dala in tabloul de dale
        tiles[id] = this;
    }

    /*! \fn public void render(Graphics g, int x, int y)
        \brief Dala este randata la o anumita pozitie indicata de coordonatele x si y,
         cu ajutorul contextului grafic g
    */
    public void render(Graphics g, int x, int y)
    {
            ///Desenare dala cu ajutorul contextului grafic
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /*! \fn public boolean isSolid()
        \brief returneaza starea dalei, daca este solida sau nu, mai exact,
        daca entitatile pot sa treaba prin, sau pe deasupra ei,
         metoda necesara pentru implementarea coliziunilor
    */
    public boolean isSolid()
    {
        return false;
    }
    /*! \fn public int getId()
        \brief returneaza id-ul dalei
    */
    public int getId()
    {
        return id;
    }
}
