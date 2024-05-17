package utils.enemies;

import utils.Hero;
import utils.Wall;
import utils.items.Item;
import utils.items.ItemConsumable;
import utils.items.ItemWeapon;
import utils.items.ItemWearable;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.Color;

public abstract class Enemy {

    private int enemyX;
    private int enemyY;
    private int damage;
    private int health;
    private Rectangle enemyRectangle;
    private Image enemyImage;
    private double angle;
    private ItemWeapon weapon;

    /**
     * Konštruktor pre triedu Enemy, nastaví pozíciu nepriateľa, jeho životy a vytvorí obdĺžnik, ktorý reprezentuje nepriateľa
     * @param enemyX - pozicia na osi X
     * @param enemyY - pozicia na osi Y
     * @param health - životy nepriateľa
     */
    public Enemy(int enemyX, int enemyY, int health) {
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.health = health;
        this.enemyRectangle = new Rectangle(this.enemyX, this.enemyY, 16 * 3, 16 * 3);
    }

    /**
     * Metóda, ktora vracia objekt Rectangle, ktorý reprezentuje nepriateľa
     * @return enemyRectangle
     */
    public Rectangle getEnemyRectangle() {
        return this.enemyRectangle;
    }

    /**
     * Metóda, ktorá vracia body životov nepriateľa
     * @return
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Metóda, ktorá nastavuje životy nepriateľa
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Metóda, ktorá nastavuje obrázok nepriateľa
     * @param enemyImage
     */
    public void setEnemyImage(Image enemyImage) {
        this.enemyImage = enemyImage;
    }

    /**
     * Metóda, ktorá vracia zbraň nepriateľa
     * @return
     */
    public ItemWeapon getWeapon() {
        return this.weapon;
    }

    /**
     * Metóda, ktorá nastavuje zbraň nepriateľa
     * @param weapon
     */
    public void setWeapon(Item weapon) {
        this.weapon = (ItemWeapon)weapon;
    }

    /**
     * Metóda, ktorá vracia poškodenia nepriateľa
     * @return
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Metóda ktorá vracia úhol na ktorý sa nepriatel pozerá
     * @param angle
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Metóda, ktorá nastavuje poškodenia nepriateľa
     * @param damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Metóda, pomocou ktorej generujeme zbraň nepriaťela
     * @return ItemWeapon
     */
    abstract Item generateWeapon();

    /**
     * Metóda, ktorá aktualizuje pohyb alebo strelbu nepriateľa
     * @param hero
     */
    public abstract void updateEnemy(Hero hero);

    /**
     * Metóda, ktorá zaznamenáva zásah hráča
     */
    public abstract void hitPlayer(Hero hero);

    /**
     * Metóda, ktorá zaznamenáva zásah steny
     * @param wall
     */
    public abstract void hitWall(Wall wall);

    /**
     * Metóda, pomocou ktorej generujeme aký predmet
     * vypadne po zabití nepriateľa
     * @param hero
     */
    public void generateDrop(Hero hero) {
        double rand = Math.random();

        if (rand <= 0.33) {
            rand = Math.random();
            if (rand <= 0.10) {
                hero.addToInventory(new ItemWeapon("Pistol", 15, 700));
            } else if (rand <= 0.18) {
                hero.addToInventory(new ItemWeapon("M4", 20, 600));
            } else if (rand <= 0.24) {
                hero.addToInventory(new ItemWeapon("M4 - modified", 25, 600));
            } else if (rand <= 0.30) {
                hero.addToInventory(new ItemWeapon("AK47", 25, 500));
            } else if (rand <= 0.35) {
                hero.addToInventory(new ItemWeapon("AK47 - modified", 35, 600));
            }  else if (rand <= 0.38) {
                hero.addToInventory(new ItemWeapon("AK47 - EZ WIN", 50, 500));
            }
        } else if (rand <= 0.45) {
            rand = Math.random();
            if (rand <= 0.03) {
                hero.addToInventory(new ItemWearable("Big Vest", 100));
            } else if (rand <= 0.13) {
                hero.addToInventory(new ItemWearable("Mid Vest", 50));
            }  else if (rand <= 0.28) {
                hero.addToInventory(new ItemWearable("Small Vest", 30));
            }
        } else {
            rand = Math.random();
            if (rand <= 0.10) {
                hero.addToInventory(new ItemConsumable("Medkit", 200));
            } else if (rand <= 0.20) {
                hero.addToInventory(new ItemConsumable("Bandage", 50));
            } else if (rand <= 0.40) {
                hero.addToInventory(new ItemConsumable("Small bandage", 30));
            }
        }
    }

    /**
     * Metóda, ktorá vykresľuje nepriateľa a otáča ho podľa uhlu
     * @param graphics
     */
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;

        graphics.setColor(Color.BLACK);
        this.weapon.paintComponent(graphics2D);

        graphics2D.drawString("HP: " + this.health, this.enemyRectangle.x, this.enemyRectangle.y - 10);
        graphics2D.rotate(this.angle, this.enemyRectangle.x + 24, this.enemyRectangle.y + 24);
        graphics2D.drawImage(this.enemyImage, this.enemyRectangle.x, this.enemyRectangle.y, null);
        graphics2D.rotate(-this.angle, this.enemyRectangle.x + 24, this.enemyRectangle.y + 24);
    }
}
