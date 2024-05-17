package utils.enemies;

import utils.Hero;
import utils.Wall;
import utils.items.ItemWeapon;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyRanged extends Enemy {
    private double angle;

    /**
     * Konštruktor pre vytvorenie nepriateľa typu ranged
     * @param enemyX - pozícia na osi X
     * @param enemyY - pozícia na osi Y
     * @param health - životy nepriateľa
     */
    public EnemyRanged(int enemyX, int enemyY, int health) {
        super(enemyX, enemyY, health);
        setEnemyImage(new ImageIcon("./pics/models/enemy_ranged.png").getImage());
        setWeapon(this.generateWeapon());
        setDamage(getWeapon().getParam1());
    }

    /**
     * Metóda pomocou ktorej generujeme zbran pre nepriatela
     * @return ItemWeapon
     */
    @Override
    ItemWeapon generateWeapon() {
        Random random = new Random();
        int probs = random.nextInt(100);
        if (probs <= 50) {
            return new ItemWeapon("M4", 10, 500);
        } else if (probs <= 95) {
            return new ItemWeapon("AK47", 20, 500);
        } else {
            return new ItemWeapon("Sniper", 50, 2000);
        }
    }

    /**
     * Metóda pomocou ktorej vygenerujeme projektil v strede nepriatela
     * @param destX - cieľová pozícia na osi X
     * @param destY - cieľová pozícia na osi Y
     */
    private void enemyShoot(double destX, double destY) {
        this.getWeapon().generateBullet(this.getEnemyRectangle().x, this.getEnemyRectangle().y, destX, destY);
    }

    /**
     * Metóda pomocou ktorej updatujeme nepriatela po osi x a y,
     * nastavujeme uhol
     * @param hero - hráč
     */
    @Override
    public void updateEnemy(Hero hero) {
        this.angle = Math.atan2(hero.getPlayerRectangle().y - (getEnemyRectangle().y + 24), hero.getPlayerRectangle().x - (getEnemyRectangle().x + 24));
        this.angle += Math.PI / 2;

        setAngle(this.angle);
        getWeapon().update();
        this.enemyShoot(hero.getPlayerRectangle().x + 24, hero.getPlayerRectangle().y + 24);
        this.hitPlayer(hero);
    }

    /**
     * Metóda pomocou ktorej zistíme či nepriatel narazil do hráča
     * @param hero - hráč
     */
    @Override
    public void hitPlayer(Hero hero) {
        Rectangle rect = hero.getPlayerRectangle();
        if (getWeapon().hasHit(rect)) {
            hero.setBaseHealth(hero.getBaseHealth() - this.getDamage());
        }
    }

    /**
     * Metóda pomocou ktorej zistíme či nepriatel narazil do steny
     * @param wall - stena
     */
    @Override
    public void hitWall(Wall wall) {
        wall.getRects().forEach(rect -> {
            if (getWeapon().hasHit(rect)) {
                System.out.println("Hit Wall");
            }
        });
    }
}
