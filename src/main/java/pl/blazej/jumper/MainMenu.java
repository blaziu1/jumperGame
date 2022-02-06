package pl.blazej.jumper;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenu extends JFrame implements ActionListener {
    private JButton start, exit, options, results;

    MainMenu() {
        super("Jumper game");
        setDefaultCloseOperation(3);
        setSize(350, 500);
        setLocationRelativeTo((Component)null);
        setLayout(new GridLayout(4, 1));
        this.start = new JButton("Start");
        this.options = new JButton("Options");
        this.results = new JButton("Scores");
        this.exit = new JButton("Exit");
        this.start.addActionListener(this);
        this.options.addActionListener(this);
        this.results.addActionListener(this);
        this.exit.addActionListener(this);
        add(this.start);
        add(this.options);
        add(this.results);
        add(this.exit);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.start)
            try {
                dispose();
                Map map = new Map(new File("levels/lvl1.txt"));
                EventQueue.invokeLater(() -> map.setVisible(true));
                Map.score = 0;
            } catch (IOException error) {
                System.out.println("IOException");
            }
        if (e.getSource() == this.results)
            try {
                dispose();
                Results results = new Results();
            } catch (IOException error) {
                System.out.println("ERROR: IOException");
            }
        if (e.getSource() == this.options) {
            dispose();
            //Options opcje = new Options();
            OptionsForm options = new OptionsForm();
        }
        if (e.getSource() == this.exit)
            System.exit(0);
    }
}