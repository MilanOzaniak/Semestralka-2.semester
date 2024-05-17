package utils;

import java.awt.Rectangle;

public class Projectile extends Rectangle {

    private double deltaX;
    private double deltaY;
    private double distance;
    private int bulletSpeed;

    /**
     * Konštruktor projektilu
     * @param x pozicia x strely
     * @param y pozicia y strely
     * @param width sirka strely
     * @param height vyska strely
     * @param destX cielova pozicia x
     * @param destY cielova pozicia y
     * @param bulletSpeed rychlost strely
     */
    public Projectile(int x, int y, int width, int height, double destX, double destY, int bulletSpeed) {
        super(x, y, width, height);
        this.deltaX = destX - x;
        this.deltaY = destY - y;
        this.bulletSpeed = bulletSpeed;
        this.distance = Math.sqrt(this.deltaX * this.deltaX + this.deltaY * this.deltaY);
    }

    /**
     * Metoda vrati poziciu x strely
     * @return poziciu x strely
     */
    public double getX() {
        return x;
    }

    /**
     * Metoda vrati poziciu y strely
     * @return poziciu y strely
     */
    public double getY() {
        return y;
    }

    /**
     * Metoda ktorá posúva strelu po osi x a y
     */
    public void update() {
        x += this.deltaX / this.distance * this.bulletSpeed;
        y += this.deltaY / this.distance * this.bulletSpeed;
    }
}
