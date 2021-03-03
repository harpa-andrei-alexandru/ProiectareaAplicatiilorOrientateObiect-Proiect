package Game.States;

import Game.Game;
import Game.Graphics.Assets;
import Game.UI.ClickListener;
import Game.UI.UIImageButton;
import Game.UI.UIManager;

import java.awt.*;

    /*! \fn \class MenuState
        \brief  Clasa adauga butoane pentru inceperea unui joc nou, incarcarea unei stari de lume din abaza de date, buton pentru help&settings, configurarea sunetului
    */
public class MenuState extends State {

    private final UIManager uiManager;  /*!< Referinta catre un manager de obiecte grafice */
    /*! \fn

    */
    public MenuState()
    {
        super();
        uiManager = new UIManager();
        isActive = true;


        uiManager.addObject(new UIImageButton(500, 200, 175, 50, Assets.new_game_button, new ClickListener()
        {
            @Override
            public void onClick()
            {
                isActive = false;
                gameActive = true;
                if(saveAndReloadGameState.getSaved() == 1)
                    saveAndReloadGameState.deleteSavedGameState();
                saveAndReloadGameState.setUnsaved();

                Game.getInstance().gameState.musicPlayer.close();
                Game.getInstance().gameState.startGame();
                State.setState(Game.getInstance().gameState);
            }
        }));

        uiManager.addObject(new UIImageButton(500, 280, 175, 50, Assets.loadWorld_button, new ClickListener()
        {
            @Override
            public void onClick()
            {
                    if(saveAndReloadGameState.getSaved() == 1)
                    {
                        isActive = false;
                        gameActive = true;
                        saveAndReloadGameState.loadSavedWorldState();
                        saveAndReloadGameState.deleteSavedGameState();
                        saveAndReloadGameState.setUnsaved();
                        State.setState(Game.getInstance().gameState);
                    }
            }
        }));

        uiManager.addObject(new UIImageButton(500, 360, 175, 50, Assets.settings_button, new ClickListener() {
            @Override
            public void onClick() {
                isActive = false;
                State.setState(Game.getInstance().helpAndSettingsState);
            }
        }));

        uiManager.addObject(new UIImageButton(500, 440, 175, 50, Assets.exit_button, new ClickListener() {
            @Override
            public void onClick() {
                isActive = false;
                System.exit(0);
            }
        }));

        uiManager.addObject(new UIImageButton(1000, 630, 100, 25, Assets.music_off_button, new ClickListener() {
            @Override
            public void onClick() {
                Game.getInstance().gameState.musicPlayer.stop();
            }
        }));

        uiManager.addObject(new UIImageButton(1000, 600, 100, 25, Assets.music_on_button, new ClickListener() {
            @Override
            public void onClick() {
                Game.getInstance().gameState.musicPlayer.loop();
            }
        }));

    }

    @Override
    public void update()
    {
        if(isActive)
            uiManager.update();
    }

    @Override
    public void render(Graphics g)
    {
        if(isActive)
            uiManager.render(g);
        g.drawImage(Assets.player_right_attacking[0], 200, 200, 200, 200, null);
        g.drawImage(Assets.enemy_left_attacking[0], 750, 200, 200, 200, null);
        Font fnt = new Font("Times New Roman", Font.BOLD, 30);
        g.setFont(fnt);
        g.setColor(Color.orange);
        g.drawString(Game.getInstance().getTitle(), 450, 100);
    }

    @Override
    protected UIManager getUiManager()
    {
        return uiManager;
    }


}
