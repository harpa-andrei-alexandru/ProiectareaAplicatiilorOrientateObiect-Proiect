package Game.Window;

import javax.swing.*;
import java.awt.*;
    /*! \class Display
        \brief Creeaza fereastra jocului, creeaza un canvas si il aduga ferestrei pentru a putea randa/desena pe aceasta

     */
public class Display extends Canvas{

    private JFrame frame;           /*!< Referinta catre fereastra*/
    private Canvas canvas;          /*!< Referinta catre canvas*/

    private final String title;     /*!< Numele jocului */
    private final int width;        /*!< Latimea ferestrei in pixeli*/
    private final int height;       /*!< Inaltimea ferestrei in pixeli*/

        /*! \fn public Display(String title, int width, int height)
            \brief seteaza numele jocului si dimensiunile ferestrei, totodata, apeleaza si metoda createDisplay() pentru a crea fereastra jocului
        */
    public Display(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

        /*! \fn private void createDisplay()
            \brief creaza fereastra jocului
        */
    private void createDisplay()
    {
            ///Se creaza obiectul ferestrei
        frame = new JFrame(title);
            ///Se seteaza dimensiunea ferestrei
        frame.setSize(width, height);
            ///Se asigura ca fereastra este inchisa corespunzator
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ///Se seteaza fereastra astfel incat sa nu poata minimizata sau maximizata, ea pastrandu-si dimensiunile initiale
        frame.setResizable(false);
            ///Se pozitioneaza in centrul ecranului
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

            ///Se creeaza obiectul canvas pe care vom randa harta si toate entitatile
        canvas = new Canvas();
            ///Se seteaza dimensiunea dorita
        canvas.setPreferredSize(new Dimension(width, height));
            ///Se seteaza dimensiunea maxima a canvas-ului
        canvas.setMaximumSize(new Dimension(width, height));
            ///Se seteaza dimensiunea minima a canvas-ului
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
            ///Se adauga canvas-ul ferestrei
        frame.add(canvas);
        frame.pack();
    }
    /*! \fn public Canvas getCanvas()
        \brief Returneaza canvas-ul ferestrei
     */
    public Canvas getCanvas()
    {
        return canvas;
    }
    /*! \fn public JFrame getFrame()
        \brief Returneaza fereastra jocului
    */
    public JFrame getFrame()
    {
        return frame;
    }
}
