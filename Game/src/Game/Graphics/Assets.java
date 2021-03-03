package Game.Graphics;

import java.awt.image.BufferedImage;
/*! \class Assets
    \brief Clasa are rolul de a incarca si stoca toate imaginile pentru harta si entitati
 */
public class Assets
{

    private static final int WIDTH = 64;                        /*!< Constanta ce reprezinta latimea imaginii */
    private static final int HEIGHT = 64;                       /*!< Constanta ce reprezinta inaltimea imaginii */

    public static BufferedImage[] player_front_idle;
    public static BufferedImage[] player_back_idle;
    public static BufferedImage[] player_left_idle;
    public static BufferedImage[] player_right_idle;

    public static BufferedImage[] player_down_running;
    public static BufferedImage[] player_up_running;
    public static BufferedImage[] player_left_running;
    public static BufferedImage[] player_right_running;

    public static BufferedImage[] player_down_attacking;
    public static BufferedImage[] player_up_attacking;
    public static BufferedImage[] player_left_attacking;
    public static BufferedImage[] player_right_attacking;
    public static BufferedImage[] player_die;

    public static BufferedImage[] enemy_front_idle;
    public static BufferedImage[] enemy_back_idle;
    public static BufferedImage[] enemy_left_idle;
    public static BufferedImage[] enemy_right_idle;

    public static BufferedImage[] enemy_down_running;
    public static BufferedImage[] enemy_up_running;
    public static BufferedImage[] enemy_left_running;
    public static BufferedImage[] enemy_right_running;

    public static BufferedImage[] enemy_down_attacking;
    public static BufferedImage[] enemy_up_attacking;
    public static BufferedImage[] enemy_left_attacking;
    public static BufferedImage[] enemy_right_attacking;
    public static BufferedImage[] enemy_die;

    public static BufferedImage[] tiles;
    public static BufferedImage[] arrow;

    public static BufferedImage tree1;
    public static BufferedImage tree2;
    public static BufferedImage tree3;
    public static BufferedImage bushes;

    public static BufferedImage new_game_button;
    public static BufferedImage help_button;
    public static BufferedImage settings_button;
    public static BufferedImage exit_button;
    public static BufferedImage back_button;
    public static BufferedImage menu_button;
    public static BufferedImage music_on_button;
    public static BufferedImage music_off_button;
    public static BufferedImage continue_button;
    public static BufferedImage next_level_button;
    public static BufferedImage restart_button;
    public static BufferedImage loadWorld_button;
    public static BufferedImage saveWorld_button;
    public static BufferedImage controls_set1_button;
    public static BufferedImage controls_set2_button;

    public static BufferedImage background_image1;


    public static void init()
    {
        player_front_idle = new BufferedImage[8];
        player_back_idle = new BufferedImage[8];
        player_left_idle = new BufferedImage[8];
        player_right_idle = new BufferedImage[8];

        player_up_running = new BufferedImage[9];
        player_down_running = new BufferedImage[9];
        player_left_running = new BufferedImage[9];
        player_right_running = new BufferedImage[9];

        player_up_attacking = new BufferedImage[13];
        player_down_attacking = new BufferedImage[13];
        player_left_attacking = new BufferedImage[13];
        player_right_attacking = new BufferedImage[13];

        player_die = new BufferedImage[6];

        enemy_front_idle = new BufferedImage[8];
        enemy_back_idle = new BufferedImage[8];
        enemy_left_idle = new BufferedImage[8];
        enemy_right_idle = new BufferedImage[8];

        enemy_up_running = new BufferedImage[9];
        enemy_down_running = new BufferedImage[9];
        enemy_left_running = new BufferedImage[9];
        enemy_right_running = new BufferedImage[9];

        enemy_up_attacking = new BufferedImage[13];
        enemy_down_attacking = new BufferedImage[13];
        enemy_left_attacking = new BufferedImage[13];
        enemy_right_attacking = new BufferedImage[13];

        enemy_die = new BufferedImage[6];

        arrow = new BufferedImage[4];
        tiles = new BufferedImage[53];

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tileset.png"));

        int k = 0;
        for(int row = 0; row < 7; row++)
        {
            for(int column = 0; column < 8; column++)
            {
                if(row == 6 && column == 5)
                    break;
                tiles[k] = sheet.crop(column * WIDTH / 2, row * HEIGHT / 2, WIDTH / 2 , HEIGHT / 2);
                k++;
            }
        }

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/player_sprite.png"));
        for(int i = 0; i < 126; i++)
        {
            if(i < 8)
                player_back_idle[i] = sheet.crop(i * WIDTH, 4 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 16)
                player_left_idle[i - 8] = sheet.crop((i - 8) * WIDTH, 5 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 24)
                player_front_idle[i - 16] = sheet.crop((i - 16) * WIDTH, 6 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 32)
                player_right_idle[i - 24] = sheet.crop((i - 24) * WIDTH, 7 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 41)
                player_up_running[i - 32] = sheet.crop( (i - 32) * WIDTH, 8 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 50)
                player_left_running[i - 41] = sheet.crop( (i - 41) * WIDTH, 9 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 59)
                player_down_running[i - 50] = sheet.crop( (i - 50) * WIDTH, 10 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 68)
                player_right_running[i - 59] = sheet.crop( (i - 59) * WIDTH, 11 * HEIGHT, WIDTH, HEIGHT);
            else if (i < 81)
                player_up_attacking[i - 68] = sheet.crop((i - 68) * WIDTH, 16 * HEIGHT, WIDTH, HEIGHT);
            else if (i < 94)
                player_left_attacking[i - 81] = sheet.crop((i - 81) * WIDTH, 17 * HEIGHT, WIDTH, HEIGHT);
            else if (i < 107)
                player_down_attacking[i - 94] = sheet.crop((i - 94) * WIDTH, 18 * HEIGHT, WIDTH, HEIGHT);
            else if (i < 120)
                player_right_attacking[i - 107] = sheet.crop((i - 107) * WIDTH, 19 * HEIGHT, WIDTH, HEIGHT);
            else
                player_die[i - 120] = sheet.crop((i - 120) * WIDTH, 20 * HEIGHT, WIDTH, HEIGHT);
        }

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemy.png"));
        for(int i = 0; i < 126; i++)
        {
            if(i < 8)
                enemy_back_idle[i] = sheet.crop(i * WIDTH, 4 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 16)
                enemy_left_idle[i - 8] = sheet.crop((i - 8) * WIDTH, 5 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 24)
                enemy_front_idle[i - 16] = sheet.crop((i - 16) * WIDTH, 6 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 32)
                enemy_right_idle[i - 24] = sheet.crop((i - 24) * WIDTH, 7 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 41)
                enemy_up_running[i - 32] = sheet.crop( (i - 32) * WIDTH, 8 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 50)
                enemy_left_running[i - 41] = sheet.crop( (i - 41) * WIDTH, 9 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 59)
                enemy_down_running[i - 50] = sheet.crop( (i - 50) * WIDTH, 10 * HEIGHT, WIDTH, HEIGHT);
            else if(i < 68)
                enemy_right_running[i - 59] = sheet.crop( (i - 59) * WIDTH, 11 * HEIGHT, WIDTH, HEIGHT);
            else if (i < 81)
                enemy_up_attacking[i - 68] = sheet.crop((i - 68) * WIDTH, 16 * HEIGHT, WIDTH, HEIGHT);
            else if (i < 94)
                enemy_left_attacking[i - 81] = sheet.crop((i - 81) * WIDTH, 17 * HEIGHT, WIDTH, HEIGHT);
            else if (i < 107)
                enemy_down_attacking[i - 94] = sheet.crop((i - 94) * WIDTH, 18 * HEIGHT, WIDTH, HEIGHT);
            else if( i < 120)
                enemy_right_attacking[i - 107] = sheet.crop((i - 107) * WIDTH, 19 * HEIGHT, WIDTH, HEIGHT);
            else
                enemy_die[i - 120] = sheet.crop((i - 120) * WIDTH, 20 * HEIGHT, WIDTH, HEIGHT);
        }

        background_image1 = sheet.crop(WIDTH, 3 * HEIGHT, WIDTH, HEIGHT);

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/arrow.png"));
        for(int i = 0; i < 4; i++)
        {
            arrow[i] = sheet.crop(i*128, 0, 2 * WIDTH, 2 * WIDTH);
        }

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tree1.png"));
        tree1 = sheet.crop(0, 0, 104, 135);

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tree2.png"));
        tree2 = sheet.crop(0, 0, 199, 183);

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tree3.png"));
        tree3 = sheet.crop(0, 0, 141, 168);

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/bushes.png"));
        bushes = sheet.crop(0, 0, 63, 28);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/newGame.png"));
        new_game_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/help.png"));
        help_button = sheet.crop(0, 0, 200, 47);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/settings.png"));
        settings_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/exit.png"));
        exit_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/back.png"));
        back_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/menu.png"));
        menu_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/musicOn.png"));
        music_on_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/musicOff.png"));
        music_off_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/continue.png"));
        continue_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/nextLevel.png"));
        next_level_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/restart.png"));
        restart_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/loadWorld.png"));
        loadWorld_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/saveWorld.png"));
        saveWorld_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/controls1.png"));
        controls_set1_button = sheet.crop(0, 0, 200, 48);

        sheet = new SpriteSheet(ImageLoader.loadImage("/menu/controls2.png"));
        controls_set2_button = sheet.crop(0, 0, 200, 48);
    }
}
