package Game.States;

import Game.Game;
import Game.UI.UIManager;
import java.awt.*;

    /*! \class WinState
        \brief Clasa afiseaza detaliile legate de victorie, creaza un nou GameState, si face tranzitia catre meniul principal
    */
public class WinState extends State
{
    /*! \fn public WinState()
        \brief Constructorul apeleaza constructorul superclasei, si seteaza flagul isActive
    */
    public WinState()
    {
        super();
        isActive = true;
    }
    /*! \fn public void update()
        \brief Dace butonul de menu este apasat, atunci se face tranzitia catre meniu principal,
        totodata starea de joc este actualizata cu una noua, flag-ul de gameActive este resetat
    */
    @Override
    public void update()
    {

        if(Game.getInstance().getKeyManager().toMenu)
        {
            gameActive = false;
            isActive = false;
            Game.getInstance().gameState = new GameState();
            State.setState(Game.getInstance().menuState);
        }
    }
    /*! \fn public void render(Graphics g)
        \brief Se afiseaza mesajul de confirmare ca jocul a fost castigat si scorul, cu ajutorul contextului grafic si al font-ului reconfigurat
    */
    @Override
    public void render(Graphics g)
    {
        Font fnt = new Font("Times New Roman", Font.BOLD, 40);
        g.setFont(fnt);
        g.setColor(Color.green);
        g.drawString("You Won, But your journey will not end here, my hero!", 100, Game.getInstance().getHeight() / 2 - 50);
        g.setColor(Color.yellow);
        g.drawString("TOTAL SCORE: " + Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getScore(), 400, Game.getInstance().getHeight() / 2 - 50 + 80 );
        g.drawString("*** Press M to return to menu ***", 270, 500);
    }
    /*! \fn protected UIManager getUiManager()
        \brief Returneaza managerul de obiecte grafice, in cazul de fata se returneaza null
    */
    @Override
    protected UIManager getUiManager() {
        return null;
    }
}
