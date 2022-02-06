package pl.blazej.jumper;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Game {
    private static int map_num = 0;

    private int NUM_OF_FILES = 2;

    void nextLevel(int score) {
        File[] files = new File[this.NUM_OF_FILES];
        files[0] = new File("levels/lvl2.txt");
        files[1] = new File("levels/lvl3.txt");
        try {
            Map map = new Map(files[map_num]);
            map_num++;
            EventQueue.invokeLater(() -> map.setVisible(true));
        } catch (IOException error) {
            System.out.println("IOException");
        } catch (ArrayIndexOutOfBoundsException err) {
            String[] options = { "Game over" };
            JPanel panel = new JPanel();
            JLabel lbl = new JLabel("You finished all maps ");
            panel.add(lbl);
            int selectedOption = JOptionPane.showOptionDialog(null, panel, "Congratulations", 1, 1, null, (Object[])options, options[0]);
            if (selectedOption == 0) {
                ending(score);
                map_num = 0;
            }
        }
    }

    void ending(int score) {
        String[] options = { "OK" };
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Your score: " + score + ". Type your name: ");
        JTextField txt = new JTextField(10);
        panel.add(lbl);
        panel.add(txt);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, "Game over", 1, 1, null, (Object[])options, options[0]);
        if (selectedOption == 0) {
            String nick = txt.getText();
            if (nick.contains(" ")) {
                String[] divided2 = nick.split("\\s+");
                nick = String.join("", (CharSequence[])divided2);
            }
            if (nick.contains("-"))
                nick = nick.replace('-', '_');
            if (!nick.isEmpty())
                try {
                    scoresUpdate(nick, score);
                } catch (IOException error) {
                    System.out.println("ERROR: IOException");
                }
            MainMenu mainMenu = new MainMenu();
        }
    }

    private void scoresUpdate(String nickname, int scr) throws FileNotFoundException {
        int[] scores = new int[5];
        int i = 0;
        String[] lines = new String[5];
        String[] names = new String[5];
        Scanner in = new Scanner(new File("files/highscore.txt"));
        while (in.hasNext()) {
            lines[i] = in.nextLine();
            String[] divided = lines[i].split("-");
            String[] divided2 = lines[i].split("\\s+");
            names[i] = divided2[1];
            scores[i] = Integer.parseInt(divided[1]);
            i++;
        }
        for (int j = 0; j < 5; j++) {
            if (scores[j] < scr) {
                switch (j) {
                    case 4:
                        names[4] = nickname;
                        scores[4] = scr;
                        break;
                    case 3:
                        names[4] = names[3];
                        scores[4] = scores[3];
                        names[3] = nickname;
                        scores[3] = scr;
                        break;
                    case 2:
                        names[4] = names[3];
                        scores[4] = scores[3];
                        names[3] = names[2];
                        scores[3] = scores[2];
                        names[2] = nickname;
                        scores[2] = scr;
                        break;
                    case 1:
                        names[4] = names[3];
                        scores[4] = scores[3];
                        names[3] = names[2];
                        scores[3] = scores[2];
                        names[2] = names[1];
                        scores[2] = scores[1];
                        names[1] = nickname;
                        scores[1] = scr;
                        break;
                    case 0:
                        names[4] = names[3];
                        scores[4] = scores[3];
                        names[3] = names[2];
                        scores[3] = scores[2];
                        names[2] = names[1];
                        scores[2] = scores[1];
                        names[1] = names[0];
                        scores[1] = scores[0];
                        names[0] = nickname;
                        scores[0] = scr;
                        break;
                }
                break;
            }
        }
        PrintWriter save = new PrintWriter("files/highscore.txt");
        for (int k = 0; k < 5; k++)
            save.println((k + 1) + ". " + names[k] + " -" + scores[k]);
        save.close();
    }
}