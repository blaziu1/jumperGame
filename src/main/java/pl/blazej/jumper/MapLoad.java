package pl.blazej.jumper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class MapLoad {
    int MAP_HEIGHT, MAP_WIDTH;

    ArrayList<Block> blocks = new ArrayList<>();

    Jumper jumper = new Jumper();

    int JUMP_HEIGHT;

    int JUMPER_SPEED;

    int NUM_OF_LIVES;

    void loadFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                if (line.contains("properties")) {
                    String[] parameters = line.split("\\s+");
                    this.MAP_WIDTH = Integer.parseInt(parameters[1]);
                    this.MAP_HEIGHT = Integer.parseInt(parameters[2]);
                    line = br.readLine();
                    continue;
                }
                try {
                    line = loadBlocks(br, line);
                } catch (NullPointerException e) {
                    break;
                }
            }
        }
    }

    private String loadBlocks(BufferedReader br, String line) throws IOException {
        if (line.contains("%")) {
            line = br.readLine();
        } else if (line.contains("$")) {
            loadJumperLine(line);
            line = br.readLine();
        } else {
            loadBlockLine(line);
            line = br.readLine();
        }
        return line;
    }

    private void loadBlockLine(String line) {
        String[] blockString = line.split("\\s+");
        try {
            int x = Integer.parseInt(blockString[0]) * 50;
            int y = Integer.parseInt(blockString[1]) * 50;
            Block block = new Block(x, y);
            this.blocks.add(block);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException");
        }
    }

    private void loadJumperLine(String line) {
        String[] blockString = line.split("\\s+");
        try {
            int lx = Integer.parseInt(blockString[1]);
            int ly = Integer.parseInt(blockString[2]) - 1;
            this.jumper.setCoordinates(lx, ly);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException");
        }
    }

    void readOpcje() throws FileNotFoundException {
        Scanner in = new Scanner(new File("files/difficulty.txt"));
        String speed = in.nextLine();
        this.JUMPER_SPEED = Integer.parseInt(speed);
        String height = in.nextLine();
        this.JUMP_HEIGHT = Integer.parseInt(height);
        String num_of_lives = in.nextLine();
        this.NUM_OF_LIVES = Integer.parseInt(num_of_lives);
    }
}