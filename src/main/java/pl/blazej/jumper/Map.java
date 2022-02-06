package pl.blazej.jumper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Map extends JFrame implements KeyListener {
    private MapLoad load = new MapLoad();

    private Timer timer;

    private Jumper jumper;

    private ArrayList<Block> Blocks = new ArrayList<>();

    private boolean pause;

    private boolean jump;

    private boolean defeat;

    private static int lives;

    private static int score = 0;

    private Image blockImage;

    private Image jumperImage;



    private int counter = 0;

    Map(final File level) throws IOException {
        this.load.loadFile(level);
        this.load.readOpcje();
        setSize(this.load.MAP_WIDTH * 50, this.load.MAP_HEIGHT * 50);
        setTitle("Jumper game");
        setLocationRelativeTo((Component)null);
        addKeyListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Map.this.dispose();
                MainMenu mainmenu = new MainMenu();
            }
        });
        this.Blocks = this.load.bloki;
        this.jumper = new Jumper(this.load.jumper.getX() * 50, this.load.jumper.getY() * 50);
        final Game game = new Game();
        this.jumper.asc = this.load.JUMP_HEIGHT;
        lives = this.load.NUM_OF_LIVES;
        this.pause = false;
        this.jump = false;
        this.defeat = false;

        blockImage = new ImageIcon("graphics/block.jpg").getImage();
        jumperImage = new ImageIcon("graphics/jumper.png").getImage();
        class TimeListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (!Map.this.pause) {
                    Map.this.repaint();
                    Map.this.jumper.update();
                    if (!Map.this.checkIfCanLeft())
                        Map.this.jumper.left = false;
                    if (!Map.this.checkIfCanRight())
                        Map.this.jumper.right = false;
                    Map.this.ascending();
                    if (Map.this.jumper.getY() > Map.this.getHeight())
                        Map.this.fallen(level);
                    if (Map.this.Blocks.isEmpty()) {
                        Map.this.timer.stop();
                        game.nextLevel(Map.score);
                        Map.this.dispose();
                    }
                    if (Map.this.defeat) {
                        game.ending(Map.score);
                        Map.this.timer.stop();
                        Map.this.dispose();
                    }
                }
            }
        };
        ActionListener listener = new TimeListener();
        int delay = this.load.JUMPER_SPEED;
        this.timer = new Timer(delay, listener);
        this.timer.start();
    }

    private void fallen(File level) {
        if (lives > 0) {
            this.Blocks.clear();
            this.jumper = new Jumper(this.load.jumper.getX() * 50, this.load.jumper.getY() * 50);
            try {
                this.load.loadFile(level);
            } catch (IOException error) {
                System.out.println("Error");
            }
            this.Blocks = this.load.bloki;
            lives--;
            score -= this.counter;
            this.counter = 0;
        } else {
            this.defeat = true;
        }
    }

    private boolean checkIfOnBlock() {
        for (Block b : this.Blocks) {
            if (b.getY() <= this.jumper.getY() + 65 && b.getY() >= this.jumper.getY() + 40 && this.jumper.getX() >= b.getX() - 25 && this.jumper.getX() <= b.getX() + 40) {
                this.jumper.setCoordinates(this.jumper.getX(), b.getY() - 50);
                return false;
            }
        }
        return true;
    }

    private boolean checkIfCanLeft() {
        for (Block b : this.Blocks) {
            if (this.jumper.getX() <= b.getX() + 51 && this.jumper.getX() >= b.getX() + 49 && this.jumper.getY() >= b.getY() - 48 && this.jumper.getY() <= b.getY() + 50)
                return false;
        }
        return true;
    }

    private boolean checkIfCanRight() {
        for (Block b : this.Blocks) {
            if (this.jumper.getX() >= b.getX() - 42 && this.jumper.getX() <= b.getX() - 40 && this.jumper.getY() >= b.getY() - 48 && this.jumper.getY() <= b.getY() + 50)
                return false;
        }
        return true;
    }

    private void ascending() {
        if (!checkIfOnBlock()) {
            this.jumper.down = false;
            this.jumper.up = false;
            this.jumper.asc = this.load.JUMP_HEIGHT;
        } else {
            this.jumper.down = true;
        }
    }

    private void disappearing() {
        int i = 0, a = -1;
        for (Block b : this.Blocks) {
            if (b.getY() <= this.jumper.getY() + 55 && b.getY() >= this.jumper.getY() + 45 && this.jumper.getX() >= b.getX() - 25 && this.jumper.getX() <= b.getX() + 40 &&
                    this.jump) {
                a = i;
                this.jump = false;
            }
            i++;
        }
        if (a != -1)
            this.Blocks.remove(a);
        this.counter++;
        score++;
    }

    private void paintComponent(Graphics g) {
        paintComponents(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.load.MAP_WIDTH, this.load.MAP_HEIGHT);
        for (Block b : this.Blocks) {
            b.setBlockImage(blockImage);
            g.drawImage(b.getBlockImage(), b.getX(), b.getY(), null);
        }
        this.jumper.setJumperImage(jumperImage);
        g.drawImage(this.jumper.getJumperImage(), this.jumper.getX(), this.jumper.getY(), null);
    }

    public void paint(Graphics g) {
        BufferedImage dbImage = new BufferedImage(this.load.MAP_WIDTH * 50, this.load.MAP_HEIGHT * 50, 2);
        Graphics dbg = dbImage.getGraphics();
        paintComponent(dbg);
        BufferedImage scaled = new BufferedImage(getWidth(), getHeight(), 2);
        Graphics2D gg = scaled.createGraphics();
        gg.drawImage(dbImage, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(scaled, 0, 0, this);
    }

    private void togglePause() {
        this.pause = !this.pause;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 39)
            this.jumper.right = true;
        if (e.getKeyCode() == 37)
            this.jumper.left = checkIfCanLeft();
        if (e.getKeyCode() == 38) {
            this.jumper.up = true;
            this.jump = true;
            disappearing();
        }
        if (e.getKeyCode() == 80)
            togglePause();
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 39)
            this.jumper.right = false;
        if (e.getKeyCode() == 37)
            this.jumper.left = false;
    }

    public void keyTyped(KeyEvent e) {}
}