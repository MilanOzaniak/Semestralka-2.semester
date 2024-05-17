package utils.items;

import enums.ItemType;
import utils.Hero;
import utils.Projectile;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class ItemWeapon extends Item {

    private int bulletSpeed = 10;
    private ArrayList<Projectile> bullets;
    private long lastShotTime = System.currentTimeMillis();

    /**
     * Konštruktor pre vytvorenie zbrane, podla mena zbrane sa nastaví aj jej obrázok
     * @param name
     * @param damage
     * @param attackSpeed
     */
    public ItemWeapon(String name, int damage, int attackSpeed) {
        super(name, damage, attackSpeed, ItemType.WEAPON);
        if ( (name.equals("AK47")) || (name.equals("AK47 - modified")) || (name.equals("AK47 - EZ WIN")) ) {
            setItemImage(new ImageIcon("pics/items/AK47.png").getImage());
        } else if ( (name.equals("M4")) || (name.equals("M4 - modified")) ) {
            setItemImage(new ImageIcon("pics/items/M4.png").getImage());
        } else {
            setItemImage(new ImageIcon("pics/items/Pistol.png").getImage());
        }
        this.bullets = new ArrayList<>();
    }

    /**
     * Metóda equip nastaví hráčovi túto zbraň ako aktívnu
     * @param hero
     */
    @Override
    public void equip(Hero hero) {
        hero.setWeapon(this);
    }

    /**
     * Metóda vráti informácie o zbrani
     * @return retazec s informáciami o zbrani
     */
    @Override
    public String getStats() {
        return getName() + ",  Damage: " + getParam1() + ",  Attk. speed: " + getParam2() + ",  Type: " + getItemType();
    }

    /**
     * Metóda vygeneruje projektil v strede hráča, ak ubehol dostatočný čas od posledného výstrelu
     * @param playerX
     * @param playerY
     * @param destX
     * @param destY
     */
    public void generateBullet(int playerX, int playerY, double destX, double destY) {

        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastShotTime >= getParam2()) {

            Projectile bullet = new Projectile(playerX + 24, playerY + 24, 5, 5, destX, destY, this.bulletSpeed);
            this.bullets.add(bullet);
            this.lastShotTime = currentTime;
        }
    }

    /**
     * Metóda ktorá vymazáva projektil ak je za hranicami obrazovky
     */
    public void update() {
        ArrayList<Projectile> bulletsToRemove = new ArrayList<>();

        for (Projectile bullet : new ArrayList<>(this.bullets)) {

            bullet.update();

            if (bullet.y + bullet.height < 0) {
                bulletsToRemove.add(bullet);
            }
            if (bullet.y + bullet.height > 768) {
                bulletsToRemove.add(bullet);
            }
            if (bullet.x + bullet.height < 0) {
                bulletsToRemove.add(bullet);
            }
            if (bullet.x + bullet.height > 1024) {
                bulletsToRemove.add(bullet);
            }
        }

        this.bullets.removeAll(bulletsToRemove);

    }

    /**
     * Metóda ktorá zistuje či sa projektil dostal do kolízie s objektom rectangle
     * @param rect
     * @return true/false
     */
    public boolean hasHit(Rectangle rect) {

        for (int i = 0; i < this.bullets.size(); i++) {
            if (this.bullets.get(i).intersects(rect)) {
                this.bullets.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Metóda pomocu ktorej sa vykreslia projektily
     * @param graphics
     */
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        for (Rectangle bullet : this.bullets) {
            graphics.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
        }
    }


}
