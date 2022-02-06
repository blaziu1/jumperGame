package pl.blazej.jumper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

public class OptionsForm extends JFrame {
    private JButton gotoweButton;
    private JRadioButton wiekszaSzybkoscRadioButton;
    private JRadioButton mniejszaSzybkoscRadioButton;
    private JRadioButton wiekszaWysokoscRadioButton;
    private JRadioButton mniejszaWysokoscRadioButton1;
    private JRadioButton a6RadioButton;
    private JRadioButton a3RadioButton;
    private JPanel mainPanel;
    private int szybkosc;

    private int wysokosc;

    private int zycia;

    public OptionsForm() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(3);
        //pack();
        setSize(485, 400);
        setLocationRelativeTo(null);
        this.wiekszaSzybkoscRadioButton.setSelected(true);
        this.wiekszaWysokoscRadioButton.setSelected(true);
        this.a6RadioButton.setSelected(true);
        this.gotoweButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.getDane();
                OptionsForm.this.saveDateToFile();
                OptionsForm.this.dispose();
                MainMenu menu = new MainMenu();
            }
        });
        this.wiekszaSzybkoscRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.mniejszaSzybkoscRadioButton.setSelected(false);
            }
        });
        this.mniejszaSzybkoscRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.wiekszaSzybkoscRadioButton.setSelected(false);
            }
        });
        this.wiekszaWysokoscRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.mniejszaWysokoscRadioButton1.setSelected(false);
            }
        });
        this.mniejszaWysokoscRadioButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsForm.this.wiekszaWysokoscRadioButton.setSelected(false);
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
        if (this.wiekszaSzybkoscRadioButton.isSelected()) {
            this.szybkosc = 5;
        } else {
            this.szybkosc = 35;
        }
        if (this.wiekszaWysokoscRadioButton.isSelected()) {
            this.wysokosc = 21;
        } else {
            this.wysokosc = 18;
        }
        if (this.a6RadioButton.isSelected()) {
            this.zycia = 6;
        } else {
            this.zycia = 2;
        }
    }

    private void saveDateToFile() {
        try {
            PrintWriter save = new PrintWriter("files/difficulty.txt");
            save.println(this.szybkosc);
            save.println(this.wysokosc);
            save.println(this.zycia);
            save.close();
        } catch (IOException error) {
            System.out.println("IOException");
        }
    }

}
