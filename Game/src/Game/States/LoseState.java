package Game.States;

import Game.Game;
import Game.UI.UIManager;
import java.awt.*;
    /*! \class LoseState
        \brief Opreste starea de joc si afiseaza scorul obtinut
    */
public class LoseState extends State
{
    /*! \fn public LoseState()
        \brief Constructorul apeleaza constructorul superclasei, si seteaza flagul isActive
    */
    public LoseState()
    {
        super();
        isActive = true;
    }

    /*! \fn public void update()
        \brief Dace butonul de menu este apasat, atunci se face tranzitia catre meniu principal,
        totodata starea de joc este actualizata cu una noua
    */
    @Override
    public void update()
    {
        if(Game.getInstance().getKeyManager().toMenu)
        {
            isActive = false;
            gameActive = false;
            Game.getInstance().gameState.musicPlayer.stop();
            Game.getInstance().gameState = new GameState();
            State.setState(Game.getInstance().menuState);
        }
    }
    /*! \fn public void render(Graphics g)
        \brief Se afiseaza mesajul de confirmare ca jocul a fost pierdut si scorul cu ajutorul contextului grafic si al font-ului reconfigurat
    */
    @Override
    public void render(Graphics g)
    {
        Font fnt = new Font("Times New Roman", Font.BOLD, 40);
        g.setFont(fnt);
        g.setColor(Color.red);
        g.drawString("You Lost, but this is not the END, rise and conquer", 150,Game.getInstance().getHeight() / 2 - 50);
        g.setColor(Color.yellow);
        g.drawString("TOTAL SCORE: " + Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().getScore(), 400, Game.getInstance().getHeight() / 2 - 50 + 90 );
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
