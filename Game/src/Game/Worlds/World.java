package Game.Worlds;

import Game.Entities.Creatures.Player;
import Game.Entities.EntityFactory;
import Game.Entities.EntityManager;
import Game.Entities.Projectiles.ProjectileManager;
import Game.Game;
import Game.DataBase.Levels.Levels;
import Game.Tiles.Tile;
import Game.Utils.Utils;
import java.awt.*;

    /*! \class World
        \brief Aceasta clasa are rolul de a incarca harta nivelului si de a pozitiona toate entitatile corespunzatoare nivelului
     */
public class World {

    private int width;          /*!< Latimea hartii in tiles */
    private int height;         /*!< Inaltimea hartii in tiles */
    public static int spawnX;   /*!< Coordonata x a jucatorului cand este incarcata lumea*/
    public static int spawnY;   /*!< Coordonata y a jucatorului cand este incarcata lumea*/
    private int[][] tiles;      /*!< Referinta catre o matrice ce contine tode id-urile dalelor hartii*/

    private EntityManager entityManager;             /*!< Referinta catre managerul de entitati */
    private ProjectileManager projectileManager;     /*!< Referinta catre managerul de proiectile */
    private final EntityFactory entityFactory;       /*!< Referinta catre fabrica de entitati */

    private Player player;       /*!< Referinta catre player */

        /*! \fn public World()
            \brief Constructorul lumii/hartii
         */
    public World()
    {
            ///Crearea unui nou jucator
        player = new Player( 0, 0);
            ///Crearea unui nou manager de proiectile
        projectileManager = new ProjectileManager();
            ///Crearea unui nou manager de entitati
        entityManager = new EntityManager(new Player(0, 0));
            ///Crearea unei noi fabrici de entitati
        entityFactory = new EntityFactory();
    }

    /*! \fn public void setWorld(String path)
        \brief Seteaza harta indicata de path-ul fisierului
     */
    public void setWorld(String path)
    {
            ///Se reinitializeaza managerul de proiectile
        projectileManager = new ProjectileManager();
            ///Se reinitializeaza managerul de entitati, in caz de restartare a nivelului, sau a trecerii la nivel nou,
            ///trebuie sa reinitializam managerul de entitati corespunzator nivelului, sau starii jocului
        entityManager = new EntityManager(player);
            ///Incarcarea propriuzisa a id-urilor dalelor
        loadWorld(path);
    }

    /*! \fn public void update()
        \brief Sunt actualizate entitatile si proiectilele
    */
    public void update()
    {       ///Se actualizeaza proiectilele
        projectileManager.update();
            ///Se actualizeaza entitatile
        entityManager.update();
    }
    /*! \fn public void render(Graphics g)
        \brief Se randeaza harta, proiectilele si entitatile cu ajutorul metodei render si a contextului grafic g
    */
    public void render(Graphics g)
    {
            ///Se calculeaza limitele de incarcare a dalelor conform dimensiunii ferestrei si pozitia camerei,
            /// astfel incat sa nu se randeze toata mapa de fiecare data cand se misca camere, ci doar cat este necesat
        int xStart = (int) Math.max(0, Game.getInstance().getGameCamera().getxOffset() / Tile.TILE_WIDTH - 1);
        int xEnd = (int) Math.min(width, (Game.getInstance().getGameCamera().getxOffset() + Game.getInstance().getWidth()) / Tile.TILE_WIDTH + 1) ;
        int yStart = (int) Math.max(0, Game.getInstance().getGameCamera().getyOffset()/Tile.TILE_HEIGHT - 1);
        int yEnd = (int) Math.min(height, (Game.getInstance().getGameCamera().getyOffset() + Game.getInstance().getHeight()) / Tile.TILE_HEIGHT + 1);
            ///Randarea propriuzisa a dalelor conform id-urilor
        for(int y = yStart; y < yEnd; y++)
        {
            for(int x = xStart; x < xEnd; x++)
            {
                    ///randarea dalei de data de coordonata x si coordonata y, aceasta este determinata de pozitia camerei
                getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - Game.getInstance().getGameCamera().getxOffset()), (int) ( y * Tile.TILE_HEIGHT - Game.getInstance().getGameCamera().getyOffset()));
            }
        }
            ///randarea entitatilor
        entityManager.render(g);
            ///randarea proiectilelor
        projectileManager.render(g);
    }
        /*! \fn public Tile getTile()
           \brief Returneaza dale corespunzator id-ului dat de coordonata x si coordonata y a hartii
       */
    public Tile getTile(int x, int y)
    {
            ///Daca avem indexi negativi, sau care depasesc dimensiunea hartii, vom returna o dala indicata de noi
        if(x < 0 || y < 0 || x >= width || y >= height)
        {
            return Tile.landTile;
        }
            ///Dala ce va fi returnata se fva initializa cu dala corespunzatoare id-ului aflat pa pozitia (x, y) din matricea de id-uri
        Tile t = Tile.tiles[tiles[x][y]];
            ///In cazul in care initializarea nu a avut loc, vom returna o dala indicata de noi
        if(t == null)
        {
            return Tile.landTile;
        }
        else
        {
            return t;
        }
    }
        /*! \fn private void loadWorld(String path)
           \brief Se face citirea din fisierul indicat de path-ul dat ca si argument, datele citite din fisier se refera la dimensiunea hartii in dale,
           pozitia de inceput a jucatorului si id-urile dalelor, care vor fi salvate in matricea de id-uri
       */
    private void loadWorld(String path)
    {
        String file = Utils.loadFileAsString(path);
            ///Datele sunt despartite de spatii, astfel folosid un regex, vom putea incarca fiecare data individual intr-un vector de stringuri
        String[] tokens = file.split("\\s+");
            ///Deoarece datele sunt citite sub forma de text, trebuie sa facem parsarea lor la integer
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);
            ///Initializam matricea de id-uri
        tiles = new int[width][height];
            ///Id-urile talelor sunt trecute in matrice
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                    ///In cazul in care vrem sa adaugam entitati la pozitia(x, y), trebuie sa tinem cont de id-ul dalei de pe pozitia respectiva,
                    /// pentru a randa atat dala, cat entitatea dorita in aceasi zona a hartii
                int entityId = Utils.parseInt(tokens[(x + y * width) + 4]);
                int xPos = x * Tile.TILE_WIDTH;
                int yPos = y * Tile.TILE_HEIGHT;
                    ///daca id-ul este -1 sau -2 se va crea un inamic la pozitia (xPos, yPos)
                if(entityId == -1)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("enemy",xPos, yPos));
                    entityId = Tile.landTile.getId();
                }
                else if(entityId == -2)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("enemy",xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }
                    ///daca id-ul este -3, -4, sau -5, se ca crea o entitate de tip Tree care va avea diferite infatisari
                else if(entityId == -3)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("tree1", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }
                else if(entityId == -4)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("tree2", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }
                else if(entityId == -5)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("tree3", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }
                    ///daca id-ul este -6 se va crea o entitate de tip Bushes
                else if(entityId == -6)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("bushes", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }

                tiles[x][y] = entityId;
            }
        }
            ///Se seteaza coordonata x si coordonata y a jucatorului la coordonatele citite
        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }
    /*! \fn private void loadWorldFromDataBase(String path)
       \brief Are aceasi utilitate ca si loadWorld(), dar nu mai creeaza inamici conform id-urilor -1, -2 si -3, deoarece
        acea sarcina este realizata de alta metoda care are acces la data de baze in care se afla noile pozitii ale inamicilor
   */
    private void loadWorldFromDataBase(String path)
    {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                int entityId = Utils.parseInt(tokens[(x + y * width) + 4]);
                int xPos = x * Tile.TILE_WIDTH;
                int yPos = y * Tile.TILE_HEIGHT;

                if(entityId == -1)
                {
                    entityId = Tile.landTile.getId();
                }
                else if(entityId == -2)
                {
                    entityId = Tile.landTile7.getId();
                }
                else if(entityId == -3)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("tree1", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }
                else if(entityId == -4)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("tree2", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }
                else if(entityId == -5)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("tree3", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }
                else if(entityId == -6)
                {
                    entityManager.addEntity(entityFactory.CreateEntity("bushes", xPos, yPos));
                    entityId = Tile.landTile7.getId();
                }

                tiles[x][y] = entityId;
            }
        }
    }
    /*! \fn public void setWorldFromDataBase(String path)
       \brief Are aceasi utilitate ca si setWorld(String path), doar ca apeleaza metoda loadWorldFromDataBase(String path)
       aceste metode sunt folosite cand vrem sa incarcam o lume care se aflta intr-o stare intermediara de joc, similara, sau diferita fata de cea initiala, si a fost salvata intr-o baza de date
   */
    public void setWorldFromDataBase(String path)
    {
        projectileManager = new ProjectileManager();
        entityManager = new EntityManager(player);

        loadWorldFromDataBase(path);
    }
    /*! \fn public int getWidth()
       \brief Returneaza latimea in dale a hartii
   */
    public int getWidth()
    {
        return width;
    }
    /*! \fn public int getHeight()
          \brief Returneaza inaltimea in dale a hartii
    */
    public int getHeight()
    {
        return height;
    }
    /*! \fn public EntityManager getEntityManager()
        \brief Returneaza managerul de entitati
    */
    public EntityManager getEntityManager()
    {
        return entityManager;
    }
    /*! \fn public ProjectileManager getProjectileManager()
        \brief Returneaza managerul de proiectile
    */
    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }
    /*! \fn public void setPlayer(Player player)
        \brief Seteaza jucatorul cu cel dat ca si argument
    */
    public void setPlayer(Player player)
    {
        this.player = player;
    }
}
