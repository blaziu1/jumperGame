package pl.blazej.jumper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

public class OptionsForm extends JFrame {
    private JButton readyButton;
    private JRadioButton fasterRadioButton;
    private JRadioButton slowerRadioButton;
    private JRadioButton heigherRadioButton;
    private JRadioButton shorterRadioButton1;
    private JRadioButton a6RadioButton;
    private JRadioButton a3RadioButton;
    private JPanel mainPanel;
    private int speed, jumpHeight, lives;

    public OptionsForm() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(3);
        //pack();
        setSize(485, 400);
        setLocationRelativeTo(null);
        this.fasterRadioButton.setSelected(true);
        this.heigherRadioButton.setSelected(true);
        this.a6RadioButton.setSelected(true);
        this.readyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.getDane();
                OptionsForm.this.saveDateToFile();
                OptionsForm.this.dispose();
                MainMenu menu = new MainMenu();
            }
        });
        this.fasterRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.slowerRadioButton.setSelected(false);
            }
        });
        this.slowerRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.fasterRadioButton.setSelected(false);
            }
        });
        this.heigherRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.shorterRadioButton1.setSelected(false);
            }
        });
        this.shorterRadioButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.heigherRadioButton.setSelected(false);
            }
        });
        this.a6RadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.a3RadioButton.setSelected(false);
            }
        });
        this.a3RadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.a6RadioButton.setSelected(false);
            }
        });
        setVisible(true);
    }

    private void getDane() {
        if (this.fasterRadioButton.isSelected()) {
            this.speed = 5;
        } else {
            this.speed = 35;
        }
        if (this.heigherRadioButton.isSelected()) {
            this.jumpHeight = 21;
        } else {
            this.jumpHeight = 18;
        }
        if (this.a6RadioButton.isSelected()) {
            this.lives = 6;
        } else {
            this.lives = 2;
        }
    }

    private void saveDateToFile() {
        try {
            PrintWriter save = new PrintWriter("files/difficulty.txt");
            save.println(this.speed);
            save.println(this.jumpHeight);
            save.println(this.lives);
            save.close();
        } catch (IOException error) {
            System.out.println("IOException");
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
