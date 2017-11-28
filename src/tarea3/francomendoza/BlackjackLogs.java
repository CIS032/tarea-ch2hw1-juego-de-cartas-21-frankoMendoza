package tarea3.francomendoza;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class BlackjackLogs {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("logs.txt");

        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);

        System.setOut(ps);

        Blackjack.main(args);
    }

}
