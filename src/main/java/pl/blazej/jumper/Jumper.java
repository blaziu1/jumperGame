package pl.blazej.jumper;

import java.awt.Image;

class Jumper {
    private int xCoordinate, yCoordinate;
    private Image jumperImage;

    boolean right, left, up, down;
    int asc;

    Jumper() {
        this.xCoordinate = 5;
        this.yCoordinate = 5;
    }

    Jumper(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    void setJumperImage(Image image) {
        this.jumperImage = image;
    }

    void setCoordinates(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    int getX() {
        return this.xCoordinate;
    }

    int getY() {
        return this.yCoordinate;
    }

    Image getJumperImage() {
        return this.jumperImage;
    }

    void update() {
        int speed = 3;
        if (this.right)
            this.xCoordinate += speed;
        if (this.left)
            this.xCoordinate -= speed;
        if (this.up)
            jump();
        if (this.down)
            this.yCoordinate += speed;
    }

    private void jump() {
        this.yCoordinate -= this.asc;
        this.asc--;
    }
}
