package utils;

import control.Controller;
import utils.enemies.Enemy;
import utils.items.Item;
import utils.items.ItemConsumable;
import utils.items.ItemWeapon;
import utils.items.ItemWearable;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

public class Hero {
    private int baseHealth = 100;
    private int maxHealth = 150;
    private int damage = 10;
    private int playerX = 100;
    private int playerY = 100;
    private double playerSpeed = 4;
    private Image playerImage;
    private Rectangle playerRectangle;
    private ArrayList<Item> inventory;
    private ItemWeapon weapon;
    private ItemWearable wearable;
    private ItemConsumable consumable;
    private double angle;

    /**
     * Konštruktor pre triedu Hero, nastaví pozíciu hráča, jeho životy a vytvorí obdĺžnik, ktorý reprezentuje hráča
     */
    public Hero() {
        this.playerRectangle = new Rectangle(this.playerX, this.playerY, 48, 48);
        this.playerImage = new ImageIcon("pics/models/player_ak.png").getImage();
        this.inventory = new ArrayList<>();
        this.weapon = new ItemWeapon("Pistol", 15, 400);
    }

    /**
     * Metóda, ktorá vracia Objekt Rectangle
     * @return playerRectangle
     */
    public Rectangle getPlayerRectangle() {
        return this.playerRectangle;
    }

    /**
     * Metóda, ktorá nastavuje hráčovi zbraň, ktorá
     * mu zvyšuje poškodenie
     * @param weapon
     */
    public void setWeapon(Item weapon) {
        this.weapon = (ItemWeapon)weapon;
    }

    /**
     * Metóda, ktorá nastavuje hráčovi armor, ktorý mu zvyšuje
     * body života
     * @param wearable
     */

    public void setWearable(Item wearable) {
        this.wearable = (ItemWearable)wearable;
    }

    /**
     * Metóda, ktorá nastavuje hráčovi použiteľný predmet, ktorý mu jednorázovo
     * doplní body života
     * @param consumable
     */
    public void setConsumable(Item consumable) {
        this.consumable = (ItemConsumable)consumable;
    }

    /**
     * Metóda, ktorá vracia body života hráča
     * @return baseHealth - body života hráča bez armoru
     */

    public int getBaseHealth() {
        return this.baseHealth;
    }

    /**
     * Metóda, ktorá nastavuje body života hráčovi,
     * kvôli resetovaniu bodov života po hre
     * @param baseHealth - body života hráča
     */
    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    /**
     * Metóda, ktorá vracia body života hráča s armorm
     * @return maxHealth - body života hráča s armorom
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Metóda, ktora vracia položky v inventári
     * @return inventory - inventár hráča
     */
    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    /**
     * Metóda, ktorá vracia objekt zbraň, ktorú ma hráč
     * @return weapon - zbraň hráča
     */
    public Item getWeapon() {
        return this.weapon;
    }

    /**
     * Metóda, ktorá vracia objekt armor, ktorý ma hráč
     * @return wearable - armor hráča
     */
    public Item getWearable() {
        return this.wearable;
    }

    /**
     * Metóda, ktorá vracia objekt použiteľného predmetu, ktorý ma hráč
     * @return consumable - použitrľný predmet hráča
     */
    public Item getConsumable() {
        return this.consumable;
    }

    /**
     * Metóda, ktorá posúva hráča po osi x, y
     * @param x - os X
     * @param y - os Y
     */
    private void updatePlayer(double x, double y) {
        this.playerRectangle.x += x;
        this.playerRectangle.y += y;
        //System.out.println(playerRectangle.x + " , " + playerRectangle.y);
    }

    /**
     * Metóda, ktorá nastaví začiatočnú pozíciu hráča
     * @param x - pozícia na osi X
     * @param y - pozícia na osi Y
     */
    public void setPosition(int x, int y) {
        this.playerRectangle.x = x;
        this.playerRectangle.y = y;
    }

    /**
     * Metóda, ktorá vytvorí objekt Projectile, v strede hráča
     * a nastaví cieľ projektilu
     * @param destX - ciel projektilu na osi X
     * @param destY - ciel projektilu na osi Y
     */
    private void playerShoot(double destX, double destY) {
        this.weapon.generateBullet(this.playerRectangle.x, this.playerRectangle.y, destX, destY);
    }

    /**
     * Metóda, ktorou ovládame hráča, pomocou vstupov z klávesnice.
     * Vykonáva sa tu kalkulacia pohybu a uhol na ktorý sa ma model
     * hráča otočiť.
     * @param controller - objekt ktorý prijima vstupy z klávesnice
     */
    public void playerHandler(Controller controller) {
        if (controller.isUpPressed() && controller.isRightPressed()) {
            this.updatePlayer(Math.sqrt(this.playerSpeed + this.playerSpeed), -Math.sqrt(this.playerSpeed + this.playerSpeed));
        } else if (controller.isUpPressed() && controller.isLeftPressed()) {
            this.updatePlayer(-Math.sqrt(this.playerSpeed + this.playerSpeed), -Math.sqrt(this.playerSpeed + this.playerSpeed));
        } else if (controller.isDownPressed() && controller.isRightPressed()) {
            this.updatePlayer(Math.sqrt(this.playerSpeed + this.playerSpeed), Math.sqrt(this.playerSpeed + this.playerSpeed));
        } else if (controller.isDownPressed() && controller.isLeftPressed()) {
            this.updatePlayer(-Math.sqrt(this.playerSpeed + this.playerSpeed), Math.sqrt(this.playerSpeed + this.playerSpeed));
        } else {
            if (controller.isUpPressed()) {
                this.updatePlayer(0, -this.playerSpeed);
            }
            if (controller.isDownPressed()) {
                this.updatePlayer(0, this.playerSpeed);
            }
            if (controller.isLeftPressed()) {
                this.updatePlayer(-this.playerSpeed, 0);
            }
            if (controller.isRightPressed()) {
                this.updatePlayer(this.playerSpeed, 0);
            }
        }

        if (controller.isLeftClickPressed()) {
            this.playerShoot(controller.getLeftClickX() - 8, controller.getLeftClickY() - 31);
        }

        if (controller.iseClicked()) {
            if (this.consumable != null) {

                this.consumable.consume(this);
                this.consumable = null;
            }
        }

        this.angle = Math.atan2(controller.getHoverY() - (this.playerRectangle.y + 24), controller.getHoverX() - (this.playerRectangle.x + 24));
        this.angle += Math.PI / 2;

        this.weapon.update();
    }

    /**
     * Metóda pomocou ktorej detekujeme zasiahnutie nepriateľa
     * @param enemy - nepriatel, ktory sa ma zasiahnut
     */
    public void hitEnemy(Enemy enemy) {

        Rectangle rect = enemy.getEnemyRectangle();
        if (this.weapon.hasHit(rect)) {
            enemy.setHealth(enemy.getHealth() - this.computeDamage());
        }
    }

    /**
     * Metóda pomocou ktorej detekujeme zasiahnutie steny
     * @param wall - stena, ktora sa ma zasiahnut
     */
    public void hitWall(Wall wall) {
        wall.getRects().forEach(rect -> {
            if (this.weapon.hasHit(rect)) {
                System.out.println("Hit Wall");
            }
        });
    }

    /**
     * Metóda, pomocou ktorej vykreslujeme hráča na plátno a aktualizujeme ho
     * @param graphics
     */
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics.setColor(Color.BLACK);
        this.weapon.paintComponent(graphics2D);
        graphics2D.rotate(this.angle, this.playerRectangle.x + this.playerRectangle.width / 2, this.playerRectangle.y + this.playerRectangle.height / 2);
        graphics2D.drawImage(this.playerImage , this.playerRectangle.x, this.playerRectangle.y, 16 * 3, 16 * 3, null);
        graphics2D.rotate(-this.angle, this.playerRectangle.x + this.playerRectangle.width / 2, this.playerRectangle.y + this.playerRectangle.height / 2);
        graphics2D.drawRect(this.playerRectangle.x, this.playerRectangle.y, this.playerRectangle.width, this.playerRectangle.height);
    }

    /**
     * Metóda, pomocou ktorej detekujeme náraz hráča do steny alebo nepriateľa.
     * Podľa toho z ktorej strany hráč narazil do steny alebo nepriateľa, tak ho posunieme
     * do opačnej strany
     * @param rect
     * @noinspection checkstyle:Indentation
     */
    public void collided(Rectangle rect) {
        int dx = 0;
        int dy = 0;
        if (this.playerRectangle.intersects(rect)) {
            if (this.playerRectangle.x < rect.x) {
                dx = rect.x - (this.playerRectangle.x + this.playerRectangle.width);
            } else {
                dx = rect.x + rect.width - this.playerRectangle.x;
            }
            if (this.playerRectangle.y < rect.y) {
                dy = rect.y - (this.playerRectangle.y + this.playerRectangle.height);
            } else {
                dy = rect.y + rect.height - this.playerRectangle.y;
            }
            if (Math.abs(dx) < Math.abs(dy)) {
                this.playerRectangle.x += dx;
            } else {
                this.playerRectangle.y += dy;
            }
        }
    }

    /**
     * Metóda pomocou ktorej pridávame predmet do inventára
     * @param item - predmet, ktory sa ma pridat do inventara
     */
    public void addToInventory(Item item) {
        this.inventory.add(item);
    }

    /**
     * Metóda, pomocou ktorej vypočítavame poškodenie hráča
     * @return  - poškodenie hráča, ak má zbraň tak sa pridáva aj poškodenie zbrane
     */
    public int computeDamage() {
        if (this.weapon == null) {
            return this.damage;
        }
        return this.damage + this.weapon.getParam1();
    }

    /**
     * Metóda, pomocou ktorej vypočítavame životy hráča.
     * Ak má hráč na sebe nejaký armor, tak sa mu pridávajú životy aj z neho
     */
    public void computeHealth() {
        if (this.wearable != null) {
            this.maxHealth = this.baseHealth + this.wearable.getParam2();
            this.setBaseHealth(this.baseHealth + this.wearable.getParam2());
        } else {
            this.maxHealth = 100;
            this.setBaseHealth(this.maxHealth);
        }
    }
}
