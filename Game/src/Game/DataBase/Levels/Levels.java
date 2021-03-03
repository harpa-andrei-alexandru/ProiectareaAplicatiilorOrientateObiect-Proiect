package Game.DataBase.Levels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Levels
{

    public enum Level
    {
        level1, level2;
    }

    public static Level getNextLevel(Level level)
    {
        return Level.level2;
    }

    public static String getLevelMap(Level level)
    {
        if (level == Level.level1) {
            return getMapPath(1);
        }
        return getMapPath(2);
    }

    public static String getLevelMusic(Level level)
    {
        if (level == Level.level1) {
            return getMusicPath(1);
        }
        return getMusicPath(2);
    }

    private static String getMapPath(int level)
    {
        Connection c = null;
        Statement stmt = null;
        String path = "";

        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MapPath WHERE Level=" + level + ";");
            path = rs.getString("Path");

            rs.close();
            stmt.close();
            c.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully!");
        return path;
    }

    private static String getMusicPath(int level)
    {
        Connection c = null;
        Statement stmt = null;
        String path = "";

        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:myBase.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MusicPath WHERE Level=" + level + ";");
            path = rs.getString("Path");

            rs.close();
            stmt.close();
            c.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully!");
        return path;
    }
}
