package Game.DataBase.SaveAndReload;

import Game.DataBase.Levels.Levels;
import Game.Entities.Creatures.Enemy;
import Game.Entities.Creatures.Player;
import Game.Entities.Entity;
import Game.Entities.EntityFactory;
import Game.Game;
import java.sql.*;



public class SaveAndReloadGameState {

    public SaveAndReloadGameState() {
    }

    public void saveEnemies() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS EnemiesSavedData " +
                    " (health INTEGER, level INTEGER, x INTEGER, y INTEGER)");
            statement.execute("DELETE FROM EnemiesSavedData");
            for (Entity tempEntity : Game.getInstance().gameState.getWorld().getEntityManager().getEntities()) {
                if (tempEntity instanceof Enemy) {
                    statement.execute("INSERT INTO EnemiesSavedData (health, level, x, y) " +
                            "VALUES(" + tempEntity.getHealth() + ", " + ((Enemy) tempEntity).getCreatureLevel() + ", " + (int) tempEntity.getX() + ", " + (int) tempEntity.getY() + ");");
                }
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void savePlayerData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS PlayerSavedData " +
                    " (health INTEGER, exp INTEGER, level INTEGER, score INTEGER, x INTEGER, y INTEGER, mapLevel INTEGER)");
            statement.execute("DELETE FROM PlayerSavedData");
            statement.execute("INSERT INTO PlayerSavedData (health, exp, level, score, x, y, mapLevel) " +
                    "VALUES(" + Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getHealth() +
                    ", " + Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getExp() +
                    ", " + Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getLevel() +
                    ", " + Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getScore() +
                    ", " + (int) Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getX() +
                    ", " + (int) Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getY() +
                    "," + (Game.getInstance().gameState.getCurrentLevel() == Levels.Level.level1 ? 1 : 2) + ");");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void loadSavedWorldState() {
        Player player = new Player(0, 0);
        int mapLevel = 1;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();

            statement.execute("SELECT * FROM PlayerSavedData");
            ResultSet results = statement.getResultSet();

            player.setHealth(results.getInt("health"));
            player.setExp(results.getInt("exp"));
            player.setScore(results.getInt("score"));
            player.setCreatureLevel(results.getInt("level"));
            player.setX((float) results.getInt("x"));
            player.setY((float) results.getInt("y"));
            mapLevel = results.getInt("mapLevel");

            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        if (mapLevel == 1) {
            Game.getInstance().gameState.setCurrentLevel(Levels.Level.level1);
        } else
            Game.getInstance().gameState.setCurrentLevel(Levels.Level.level2);

        Game.getInstance().gameState.getWorld().setPlayer(player);
        Game.getInstance().gameState.getWorld().setWorldFromDataBase(Levels.getLevelMap(Game.getInstance().gameState.getCurrentLevel()));
        loadEnemiesState();
        Game.getInstance().gameState.getMusicPlayer().setClip(Levels.getLevelMusic(Game.getInstance().gameState.getCurrentLevel()));
        Game.getInstance().gameState.getMusicPlayer().loop();
    }

    private void loadEnemiesState() {
        EntityFactory factory = new EntityFactory();
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();

            statement.execute("SELECT * FROM EnemiesSavedData");
            ResultSet results = statement.getResultSet();
            while (results.next()) {
                Enemy myEnemy = (Enemy) factory.CreateEntity("enemy", results.getInt("x"), results.getInt("y"));
                myEnemy.setHealth(results.getInt("health"));
                myEnemy.setCreatureLevel(results.getInt("level"));
                Game.getInstance().gameState.getWorld().getEntityManager().addEntity(myEnemy);
            }

            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void deleteSavedGameState()
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM PlayerSavedData");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM EnemiesSavedData");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void setSaved()
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS IsSavedWorld " +
                    " (saved INTEGER, controlSet INTEGER)");

            statement.execute("UPDATE IsSavedWorld SET saved=1, controlSet=" + Game.getInstance().getKeyManager().getControlSet() +"");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void setUnsaved()
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS IsSavedWorld " +
                    " (saved INTEGER, controlSet)");

            statement.execute("UPDATE IsSavedWorld SET saved=0, controlSet=0");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public int getSaved()
    {
        int saved = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            Statement statement = conn.createStatement();

            statement.execute("SELECT * FROM IsSavedWorld");
            ResultSet results = statement.getResultSet();

            saved = results.getInt("saved");
            Game.getInstance().getKeyManager().setControls(results.getInt("controlSet"));

            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return saved;
    }

}