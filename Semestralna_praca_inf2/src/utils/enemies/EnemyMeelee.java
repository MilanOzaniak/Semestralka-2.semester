package utils.enemies;

import utils.Hero;
import utils.Wall;
import utils.items.ItemWeapon;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class EnemyMeelee extends Enemy {

    private double angle;
    private double deltaX;
    private double deltaY;
    private double distance;
    private double enemySpeed = 2;
    private long lastShotTime = System.currentTimeMillis();
    private ArrayList<Enemy> enemies;


    /**
     * Konštruktor pre vytvorenie nepriateľa typu meelee
     * @param enemyX - pozícia na osi X
     * @param enemyY - pozícia na osi Y
     * @param health - životy nepriateľa
     */
    public EnemyMeelee (int enemyX, int enemyY, int health) {
        super(enemyX, enemyY, health);
        setEnemyImage(new ImageIcon("./pics/models/enemy_meelee.png").getImage());
        setWeapon(this.generateWeapon());
    }

    /**
     * Metóda pomocou ktorej, nepriatelovi nastavíme list nepriatelov
     * do ktorých môže nabúrať
     * @param enemies
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Metóda pomocou ktorej generujeme zbran pre nepriatela
     * @return ItemWeapon
     */
    @Override
    ItemWeapon generateWeapon() {
        Random random = new Random();
        int probs = random.nextInt(100);
        if (probs <= 40) {
            this.enemySpeed = 3;
            return new ItemWeapon("Knife", 5, 300);

        } else if (probs <= 80) {
            this.enemySpeed = 2;
            return new ItemWeapon("Axe", 15, 500);
        } else {
            this.enemySpeed = 1.5;
            return new ItemWeapon("Chainsaw", 45, 2000);
        }
    }

    /**
     * Metóda pomocou ktorej aktualizujeme pohyb hráča po osi x a y,
     * vypočítame vzdialenosť od hráča a zistíme či je hráč v dosahu útoku
     * a nastavujeme uhol pohladu nepriatela
     * @param hero
     */
    @Override
    public void updateEnemy(Hero hero) {
        this.angle = Math.atan2(hero.getPlayerRectangle().y - (getEnemyRectangle().y + 24), hero.getPlayerRectangle().x - (getEnemyRectangle().x + 24));
        this.angle += Math.PI / 2;
        setAngle(this.angle);

        this.deltaX = hero.getPlayerRectangle().x - getEnemyRectangle().x;
        this.deltaY = hero.getPlayerRectangle().y - getEnemyRectangle().y;

        this.distance = Math.sqrt(this.deltaX * this.deltaX + this.deltaY * this.deltaY);

        if (this.distance > 50) {
            double directionX = this.deltaX / this.distance;
            double directionY = this.deltaY / this.distance;

            getEnemyRectangle().x += directionX * this.enemySpeed;
            getEnemyRectangle().y += directionY * this.enemySpeed;
        } else {
            this.hitPlayer(hero);
        }

        this.avoidOtherEnemies(this.enemies);
    }

    /**
     * Metóda pomocou ktorej sa Enemy vyhýba iným nepriateľom
     * @param enemies
     */
    private void avoidOtherEnemies(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy != this && getEnemyRectangle().intersects(enemy.getEnemyRectangle())) {
                Rectangle intersection = getEnemyRectangle().intersection(enemy.getEnemyRectangle());

                if (intersection.width < intersection.height) {
                    if (getEnemyRectangle().x < enemy.getEnemyRectangle().x) {
                        getEnemyRectangle().x -= intersection.width;
                    } else {
                        getEnemyRectangle().x += intersection.width;
                    }
                } else {
                    if (getEnemyRectangle().y < enemy.getEnemyRectangle().y) {
                        getEnemyRectangle().y -= intersection.height;
                    } else {
                        getEnemyRectangle().y += intersection.height;
                    }
                }
            }
        }
    }



    /**
     * Metóda pomocou ktorej kontrolujeme rychlosť útoku nepriateľa
     * @param hero
     */
    @Override
    public void hitPlayer(Hero hero) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastShotTime >= getWeapon().getParam2()) {
            hero.setBaseHealth(hero.getBaseHealth() - getWeapon().getParam1());
            this.lastShotTime = currentTime;
        }
    }

    /**
     * Metóda pomocou ktorej kontrolujeme kolíziu nepriateľa s objektom wall
     * @param wall
     */
    @Override
    public void hitWall(Wall wall) {
        wall.getRects().forEach(rect -> {
            if (getEnemyRectangle().intersects(rect)) {
                int dx = 0;
                int dy = 0;

                if (getEnemyRectangle().x < rect.x) {
                    dx = rect.x - (getEnemyRectangle().x + getEnemyRectangle().width);
                } else {
                    dx = rect.x + rect.width - getEnemyRectangle().x;
                }

                if (getEnemyRectangle().y < rect.y) {
                    dy = rect.y - (getEnemyRectangle().y + getEnemyRectangle().height);
                } else {
                    dy = rect.y + rect.height - getEnemyRectangle().y;
                }

                if (Math.abs(dx) < Math.abs(dy)) {
                    getEnemyRectangle().x += dx;
                } else {
                    getEnemyRectangle().y += dy;
                }
            }
        });
    }
}
