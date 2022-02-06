package pl.blazej.jumper;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTextArea;

class Results extends JFrame {
    Results() throws FileNotFoundException {
        super("Best scores");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Results.this.dispose();
                MainMenu mainMenu = new MainMenu();
            }
        });
        setSize(350, 500);
        setLocationRelativeTo((Component)null);
        Scanner fileIn = new Scanner(new File("files/highscore.txt"));
        JTextArea scores = new JTextArea();
        scores.setEditable(false);
        scores.setFocusable(false);
        while (fileIn.hasNext()) {
            scores.append(fileIn.nextLine());
            scores.append("\n");
        }
        setVisible(true);
        add(scores);
    }
}