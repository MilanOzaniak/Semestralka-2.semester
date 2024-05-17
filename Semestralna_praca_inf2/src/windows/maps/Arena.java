package windows.maps;

import control.Controller;
import utils.enemies.Enemy;
import utils.Hero;
import utils.Wall;
import control.Frame;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Arena extends JPanel implements Runnable {

    private Frame frame;
    private Thread thread;
    private final int fps = 60;
    private Hero hero;
    private Controller controller;
    private Image backgroundImage;
    private ArrayList<Enemy> enemies;
    private ArrayList<Wall> walls;

    /**
     * Konštruktor triedy Arena,
     * @param frame
     * @param controller
     * @param hero
     */
    public Arena(Frame frame, Controller controller, Hero hero) {
        this.controller = controller;
        this.frame = frame;
        this.hero = hero;
        this.setVisible(true);
        this.setLayout(null);
        hero.setBaseHealth(100);
        this.enemies = new ArrayList<>();
        this.walls = new ArrayList<>();
        hero.computeHealth();
    }

    /**
     * Metóda pomocou ktorej pridame nepriatelov na mapu
     * @param enemies
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Metóda ktora vrati zoznam nepriatelov
     * @return enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    /**
     * Metóda pomocou ktorej pridame steny na mapu
     * @param wall
     */
    public void addWall(Wall wall) {
        this.walls.add(wall);
    }

    /**
     * Metóda ktora nastavi pozadie mapy
     * @param backgroundImage
     */
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * Metóda ktorá zapína herný thread
     */
    public void startGameThread() {
        this.thread = new Thread(this) ;
        this.thread.start();

    }

    /**
     * Metóda ktorá pokiaľ je thread spustený, tak vykonáva všetku logiku hry,
     * nastavíme fps hry a zavoláme metódu update a repaint
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / this.fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (!Thread.interrupted()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                this.update();
                this.repaint();
                delta--;
            }
        }
    }

    /**
     * Metóda ktorá vykonáva všetku logiku hry
     */
    private void update() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        this.hero.playerHandler(this.controller);
        for (Enemy enemy : this.enemies) {
            enemy.updateEnemy(this.hero);
            this.hero.hitEnemy(enemy);
            this.hero.collided(enemy.getEnemyRectangle());
            if (enemy.getHealth() <= 0) {
                enemiesToRemove.add(enemy);
                enemy.generateDrop(this.hero);
            }

            //System.out.println(enemy.getHealth());
        }

        this.enemies.removeAll(enemiesToRemove);

        for (int i = 0; i < this.walls.size(); i++) {
            this.hero.hitWall(this.walls.get(i));
            for (Enemy enemy : this.enemies) {
                enemy.hitWall(this.walls.get(i));
            }
            for (int j = 0; j < this.walls.get(i).getRects().size(); j++) {
                this.hero.collided(this.walls.get(i).getRects().get(j));

            }
        }

        if (this.enemies.isEmpty()) {
            this.frame.returnToMenu(this);
            this.thread.interrupt();
            this.hero.setBaseHealth(100);

        }

        if (this.hero.getBaseHealth() <= 0) {
            this.frame.returnToMenu(this);
            this.thread.interrupt();
            this.hero.setBaseHealth(100);
        }
        System.out.println(this.enemies.size());

        this.hero.getPlayerRectangle().x = Math.max(0, Math.min(this.hero.getPlayerRectangle().x, 1024 - 48));
        this.hero.getPlayerRectangle().y = Math.max(0, Math.min(this.hero.getPlayerRectangle().y, 768 - 48));

    }

    /**
     * Metóda ktorá generuje nepriatelov
     * @return enemies
     */
    public abstract ArrayList<Enemy> generateEnemies();

    /**
     * Metóda ktorá vykresľuje všetky komponenty na obrazovku
     * @param graphics
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.drawImage(this.backgroundImage, 0, 0, null);

        this.hero.paintComponent(graphics2D);

        for (Enemy enemy : this.enemies) {
            enemy.paintComponent(graphics2D);
        }

        for (Wall wall : this.walls) {
            wall.paintComponent(graphics2D);
        }

        if (this.hero.getConsumable() != null) {
            graphics2D.drawImage(this.hero.getConsumable().getItemImage(), 500, 700, null);
        }
        graphics2D.drawString("Consumable", 490, 690);
        graphics2D.drawRect(500, 700, 50, 50);

        graphics2D.drawString("HP: " + this.hero.getBaseHealth(), this.hero.getPlayerRectangle().x, this.hero.getPlayerRectangle().y);

        graphics2D.dispose();
    }



}
