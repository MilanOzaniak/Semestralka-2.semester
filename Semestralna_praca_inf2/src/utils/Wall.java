package utils;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

public class Wall {

    // prevzate z mojej predošlej semestrálnej práce

    private Rectangle rectangle;
    private ArrayList<Rectangle> rects;
    private Image image;

    /**
     *  Getter
     * @return vracia hodnotu zoznamu rects
     */
    public ArrayList<Rectangle> getRects() {
        return this.rects;
    }

    /**
     * Konštruktor
     * @param x - pozicia x
     * @param y - pozicia y
     * @param z - počet stien za sebou
     * @param strana - strana do ktorej budu vykreslené
     * @param obj - obrazok ktory bude prideleny stene
     */
    public Wall(int x, int y, int z, String strana, String obj) {
        this.rects = new ArrayList();

        if (obj.equals("tree")) {
            this.image = new ImageIcon("pics/objects/tree.png").getImage();
        } else if (obj.equals("brick")) {
            this.image = new ImageIcon("pics/objects/bricks.png").getImage();
        } else if (obj.equals("cactus")) {
            this.image = new ImageIcon("pics/objects/cactus.png").getImage();
        } else if (obj.equals("rock")) {
            this.image = new ImageIcon("pics/objects/rock.png").getImage();
        }

        for (int i = 0; i < z; i++) {
            if (strana.equals("h")) {
                this.rectangle = new Rectangle(x, y + i * -48, 16 * 3, 16 * 3);
                this.rects.add(this.rectangle);
            } else if (strana.equals("d")) {
                this.rectangle = new Rectangle(x, y + i * 48, 16 * 3, 16 * 3);
                this.rects.add(this.rectangle);
            } else if (strana.equals("p")) {
                this.rectangle = new Rectangle(x + i * 48, y, 16 * 3, 16 * 3);
                this.rects.add(this.rectangle);
            } else if (strana.equals("l")) {
                this.rectangle = new Rectangle(x + i * -48, y, 16 * 3, 16 * 3);
                this.rects.add(this.rectangle);
            }
        }
    }

    /**
     * metóda pomocou ktorej vykreslujeme objekt na JPanel
     * @param graphics kreslí na JPanel
     */
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics.setColor(Color.WHITE);

        for (int i = 0; i < this.rects.size(); i++) {
            graphics2D.drawImage(this.image , this.rects.get(i).x, this.rects.get(i).y, 16 * 3, 16 * 3, null);
        }
    }
}
