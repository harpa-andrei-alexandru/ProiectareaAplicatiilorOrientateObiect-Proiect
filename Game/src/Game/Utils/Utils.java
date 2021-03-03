package Game.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
    /*! \class Utils
        \brief Este o clasa de utilitati care se ocupa cu citirea din fisier si parsarea datelor din string la integer
    */
public class Utils {

        /*! \fn public static String loadFileAsString(String path)
            \brief Se citeste din fisierul indicat de path, iar datele citite sunt stocate intr-un string builder
        */
    public static String loadFileAsString(String path)
    {
            ///Creare stringBuilder
        StringBuilder builder = new StringBuilder();
            ///Deschiderea fisierului, si citirea din el se introduce intr-un block try - catch,
            ///deoarece in caz de erori la deschidere de fisier, sau citire de date din fisier, sa prindem eroarea si sa o afisam
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine() ) != null)
            {
                builder.append(line).append("\n");
            }
            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return builder.toString();
    }
    /*! \fn public static int parseInt(String number)
        \brief Are rolul de a parsa argumentul de tip text in valoare numerica, apeland metoda parseInt(String s) din clasa Integer
    */
    public static int parseInt(String number)
    {
        try
        {
            return Integer.parseInt(number);
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}
