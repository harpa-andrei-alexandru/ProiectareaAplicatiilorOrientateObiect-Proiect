package Game.States;

import Game.Game;
import Game.Graphics.Assets;
import Game.UI.ClickListener;
import Game.UI.UIImageButton;
import Game.UI.UIManager;
import java.awt.*;

    /*! \class HelpAndSettingsState
        \brief Clasa se ocupa cu informarea jucatorului cu privire la detaliile jocului, inamicilor si a caracterului principal,
        si ii o fera o]posibilitatea de a-si configura controalele dupa doua seturi prestabilite
    */
public class HelpAndSettingsState extends State {

    private final UIManager uiManager;      /*!< Referinta catre un manager de obiecte grafice*/

        /*! \fn  public HelpAndSettingsState()
            \brief Constructorul creaza un nou manager de obiecte grafice,
            si adaugat butoane cu configuratii diferite ce ii permite user-ului sa navigheze intre stari(menu <-> help&settings)
            si sa isi aleaga setul de controale
        */
    public HelpAndSettingsState()
    {
        super();
        ///Creare manager de obiecte grafice
        uiManager = new UIManager();
        isActive = true;
        ///Adaugare de buton intoarece menu principal
        uiManager.addObject(new UIImageButton(50, 600, 175, 50, Assets.back_button, new ClickListener() {
            @Override
            public void onClick() {
                isActive = false;
                ///Daca starea de joc nu este activa, apasarea butonului va activa starea de menu principal,
                ///altfel, va activa starea de menu intermediar
                if(!gameActive) {
                    Game.getInstance().menuState.setActive(true);
                    State.setState(Game.getInstance().menuState);
                }
                else
                {
                    Game.getInstance().inGameMenuState.setActive(true);
                    State.setState(Game.getInstance().inGameMenuState);
                }

            }
        }));
        ///Adaugare buton pentru selectarea setului principal de controale
        uiManager.addObject(new UIImageButton(300, 500, 175, 50, Assets.controls_set1_button, new ClickListener() {
            @Override
            public void onClick() {

                Game.getInstance().getKeyManager().setControls(0);
            }
        }));
        ///Adaugare buton pentru selectarea setului secundar de controale
        uiManager.addObject(new UIImageButton(700, 500, 175, 50, Assets.controls_set2_button, new ClickListener() {
            @Override
            public void onClick() {
                Game.getInstance().getKeyManager().setControls(1);
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
            \brief  Randeaza detaliile jocului si starea obiectelor grafice
        */
    @Override
    public void render(Graphics g)
    {
        if(isActive)
            uiManager.render(g);
        g.setColor(Color.yellow);
        g.setFont(myFont);
        printControls(g, 30, 50);
        printHeroDetails(g, 300, 50);
        printEnemyDetails(g, 750, 50);
        printRewards(g, 50, 300);
        printLevelSystem(g, 650, 300);
    }
        /*! \fn protected UIManager getUiManager()
            \brief  Returneaza managerul de obiecte grafice
        */
    @Override
    protected UIManager getUiManager() {
        return uiManager;
    }
        /*! \fn private void printControls(Graphics g, int x, int y)
            \brief Afiseaza detaliile controalelor cu ajutorul contextului grafic,
             pe fereastra in pozitia indicata de coordonatele x si y
        */
    private void printControls(Graphics g, int x, int y)
    {
        if(Game.getInstance().getKeyManager().getControlSet() == 0) {
            g.drawString("Controls", x + 50, y);
            g.drawString("W - for moving up", x + 20, y + 15);
            g.drawString("S - for moving down", x + 20, y + 30);
            g.drawString("D - for moving right", x + 20, y + 45);
            g.drawString("A - for moving left", x + 20, y + 60);
            g.drawString("ENTER - for attacking", x + 20, y + 75);
            g.drawString("ESC - for menu", x + 20, y + 90);
        } else if(Game.getInstance().getKeyManager().getControlSet() == 1)
        {
            g.drawString("Controls", x + 70, y);
            g.drawString("UP-ARROW", x, y + 15);
            g.drawString(" - for moving up", x + 140, y + 15);
            g.drawString("DOWN-ARROW", x, y + 30);
            g.drawString(" - for moving down", x + 140, y + 30);
            g.drawString("RIGHT-ARROW", x, y + 45);
            g.drawString(" - for moving right", x + 140, y + 45);
            g.drawString("LEFT-ARROW", x, y + 60);
            g.drawString(" - for moving left", x + 140, y + 60);
            g.drawString("SPACE", x, y + 75);
            g.drawString(" - for attacking", x + 140, y + 75);
            g.drawString("ESC", x, y + 90);
            g.drawString(" - for menu", x + 140, y + 90);
        }
        g.drawString("CONTROLS SET:", 540, 480);
    }
    /*! \fn private void printHeroDetails(Graphics g, int x, int y)
        \brief Afiseaza detaliile eroului principal
    */
    private void printHeroDetails(Graphics g, int x, int y)
    {
        g.setColor(Color.green);
        g.drawString("Hero Details ", x + 50, y);
        g.drawImage(Assets.player_right_attacking[0], x, y, 100, 100, null);
        g.drawString("-> Attack Type: Range", x + 100, y + 20);
        g.drawString("-> Health: 100 HP", x + 100, y + 35);
        g.drawString("-> Attack damage: 20 - LVL 1", x + 100, y + 50);
        g.drawString("+5 - EACH LVL UP", x + 225, y + 65);

    }
    /*! \fn private void printEnemyDetails(Graphics g, int x, int y)
        \brief Afiseaza detaliile inamicilor
    */
    private void printEnemyDetails(Graphics g, int x, int y)
    {
        g.setColor(Color.red);
        g.drawString("Enemy Details ", x + 50, y);
        g.drawImage(Assets.enemy_right_attacking[0], x, y, 100, 100, null);
        g.drawString("-> Attack Type: Range", x + 100, y + 20);
        g.drawString("-> Health: 100 HP - LVL 1", x + 100, y + 35);
        g.drawString("200 HP - LVL 2", x + 170, y + 50);
        g.drawString("-> Attack damage: 20 - LVL 1", x + 100, y + 65);
        g.drawString("30 - LVL 2", x + 225, y + 80);
    }
    /*! \fn private void printRewards(Graphics g, int x, int y)
        \brief Afiseaza detaliile legate de recompense pentru uciderea inamicilor,
        sau cresterea in nivel
    */
    private void printRewards(Graphics g, int x, int y)
    {
        g.setColor(Color.yellow);
        g.drawString("Rewards", x + 150, y);
        g.drawString("If you kill an enemy you will be healed for 20 HP", x, y + 20);
        g.drawString("If you kill an enemy while having 100 HP you will earn 20 score points", x, y + 40);
        g.drawString("If you kill an enemy while having less than 100 HP you will earn 15 score points", x, y + 60);
        g.drawString("Killing an enemy will grant you 20 exp points", x, y + 80);
    }
    /*! \fn private void printLevelSystem(Graphics g, int x, int y)
        \brief Afiseaza delatii legate de sistemul de level-up al eroului
    */
    private void printLevelSystem(Graphics g, int x, int y)
    {
        g.drawString("Hero Leveling", x + 150, y);
        g.drawString("You will need 75 exp point for level up", x, y + 20);
        g.drawString("At level up, your health will be fully restored", x, y + 40);
        g.drawString("At each level up, your damage will be increased by 5 points", x, y + 60);

    }
}
