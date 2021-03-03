package Game.States;

import Game.Entities.Entity;
import Game.Game;
import Game.Graphics.Assets;
import Game.DataBase.Levels.Levels;
import Game.UI.ClickListener;
import Game.UI.UIImageButton;
import Game.UI.UIManager;
import java.awt.*;

    /*! \class InGameMenuState
        \brief Clasa se comporta similar cu cea de MenuState, doar ca sunt adaugate functionalitati ce ii permite user-ului sa isi salveze progresul in baza de date,
        iar daca doreste, cand redeschide jocul sa poate continua cu datele salvate
    */
public class InGameMenuState extends State
{
    private final UIManager uiManager; /*!< Referinta manager de obiecte grafice*/

    /*! \fn public InGameMenuState()
        \brief Construcor ce initializeaza un nou manager de obiecte grafice, si adaugat butoane pentru restar, continuare joc, help&setting, salvare progres si iesire joc
     */
    public InGameMenuState()
    {
    super();
    uiManager = new UIManager();
    isActive = true;

    ///Adaugare Buton de continuare, daca numarul de inamici este 0 atunci, jocul va incarca nivelul urmator, altfel va continua de unde a ramas
    uiManager.addObject(new UIImageButton(500, 150, 175, 50, Assets.continue_button, new ClickListener()
    {
        @Override
        public void onClick() {
            isActive = false;
            if (Game.getInstance().gameState.getWorld().getEntityManager().getNumberOfEnemies() == 0)
            {
                Game.getInstance().gameState.nextLevel();
            }
            State.setState(Game.getInstance().gameState);
        }
    }));
    ///Adaugare buton de resetare nivel, reseteaza nivelul conform nivelului curent, daca nivelul curent este 2, detaliile caracterului vor fi aceleasi ca si dupa terminarea nivelului recent
    uiManager.addObject(new UIImageButton(500, 230, 175, 50, Assets.restart_button, new ClickListener()
    {
        @Override
        public void onClick() {
            isActive = false;
            if(Game.getInstance().gameState.getCurrentLevel() == Levels.Level.level1)
            {
                Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().setHealth(Entity.HEALTH);
                Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().setScore(0);
                Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().setExp(0);
                Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().setCreatureLevel(1);
            }
            else if(Game.getInstance().gameState.getCurrentLevel() == Levels.Level.level2)
            {
                Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().setExp(Game.getInstance().gameState.getExp());
                Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().setScore(Game.getInstance().gameState.getScore());
                Game.getInstance().gameState.getWorld().getEntityManager().getPlayer().setCreatureLevel(Game.getInstance().gameState.getLevel());
            }
            Game.getInstance().gameState.restartLevel(Game.getInstance().gameState.getCurrentLevel());
            State.setState(Game.getInstance().gameState);
        }
    }));
    ///Adaugare buton de setari care face tranzitia catre starea de help&settings
    uiManager.addObject(new UIImageButton(500, 310, 175, 50, Assets.settings_button, new ClickListener()
    {
        @Override
        public void onClick()
        {
            isActive = false;
            State.setState(Game.getInstance().helpAndSettingsState);
        }
    }));
    ///Adaugare buton de salvare lume, se incarca in baza de date detaliile curente legate de caracter, inamici si lume
    uiManager.addObject(new UIImageButton(500, 390, 175, 50, Assets.saveWorld_button, new ClickListener() {
        @Override
        public void onClick() {
            saveAndReloadGameState.setSaved();
            saveAndReloadGameState.savePlayerData();
            saveAndReloadGameState.saveEnemies();
        }
    }));
    ///Adaugare buton de iesire
    uiManager.addObject(new UIImageButton(500, 470, 175, 50, Assets.exit_button, new ClickListener()
    {
        @Override
        public void onClick()
        {
            isActive = false;
            System.exit(0);
        }
    }));
    ///Adaugare buton de orpire muzica
    uiManager.addObject(new UIImageButton(1000, 630, 100, 25, Assets.music_off_button, new ClickListener()
    {
        @Override
        public void onClick()
        {
            Game.getInstance().gameState.musicPlayer.stop();
        }
    }));
    ///Adaugare buton de pornire muzica
    uiManager.addObject(new UIImageButton(1000, 600, 100, 25,Assets.music_on_button, new ClickListener()
    {
        @Override
        public void onClick()
        {
            Game.getInstance().gameState.musicPlayer.loop();
        }
    }));

}

    /*! \fn public void update()
        \brief Actualizare stare daca jocul conform flag-ului isActive
    */
    @Override
    public void update()
    {
        if(isActive)
            uiManager.update();
    }
    /*! \fn public void render(Graphics g)
        \brief  Randeaza starea obiectelor grafice daca flagul isActive este setat
    */
    @Override
    public void render(Graphics g)
    {
        if(isActive)
            uiManager.render(g);
    }
    /*! \fn protected UIManager getUiManager()
        \brief  Returneaza managerul de obiecte grafice
    */
    @Override
    protected UIManager getUiManager()
    {
        return uiManager;
    }


}
