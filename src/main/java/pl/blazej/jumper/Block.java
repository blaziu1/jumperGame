package pl.blazej.jumper;

import java.awt.Image;

public class Block {
    private int xCoordinate, yCoordinate;
    private Image blockImage;

    Block(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    void setBlockImage(Image image) {
        this.blockImage = image;
    }

    int getX() {
        return this.xCoordinate;
    }

    int getY() {
        return this.yCoordinate;
    }

    Image getBlockImage() {
        return this.blockImage;
    }
}