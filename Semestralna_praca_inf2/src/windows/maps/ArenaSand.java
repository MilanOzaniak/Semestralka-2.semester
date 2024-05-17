package windows.maps;

import control.Controller;
import utils.Hero;
import control.Frame;
import utils.Wall;
import utils.enemies.Enemy;
import utils.enemies.EnemyMeelee;
import utils.enemies.EnemyRanged;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public class ArenaSand extends Arena {

    /**
     * Konštruktor triedy ArenaSand, nastaví pozadie a vytvorime nepriateľov a steny
     * @param frame
     * @param controller
     * @param hero
     */
    public ArenaSand(Frame frame, Controller controller, Hero hero) {
        super(frame, controller, hero);
        this.setBackgroundImage(new ImageIcon("pics/backgrounds/sand_floor.png").getImage());

        hero.setPosition(400, 200);
        this.startGameThread();
        setEnemies(this.generateEnemies());
        addWall(new Wall(550, 350, 5, "l", "rock"));
        addWall(new Wall(650, 400, 5, "d", "rock"));
        addWall(new Wall(300, 270, 5, "h", "rock"));

        for (var enemy : getEnemies()) {
            if (enemy instanceof EnemyMeelee) {
                ((EnemyMeelee)enemy).setEnemies(getEnemies());
            }
        }
    }

    /**
     * Metóda ktora vygeneruje nepriatelov
     * @return enemies
     */
    @Override
    public ArrayList<Enemy> generateEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        if (Math.random() <= 0.5) {
            enemies.add(new EnemyRanged(500, 600, 100));
            enemies.add(new EnemyRanged(200, 200, 100));
            enemies.add(new EnemyRanged(100, 100, 100));
            enemies.add(new EnemyRanged(100, 300, 100));
            enemies.add(new EnemyRanged(100, 700, 100));
            enemies.add(new EnemyMeelee(700, 200, 100));
            enemies.add(new EnemyMeelee(700, 300, 100));
            enemies.add(new EnemyMeelee(700, 400, 100));
            enemies.add(new EnemyMeelee(700, 500, 100));
        } else {
            enemies.add(new EnemyMeelee(700, 700, 100));
            enemies.add(new EnemyMeelee(700, 500, 100));
            enemies.add(new EnemyMeelee(700, 300, 100));
            enemies.add(new EnemyMeelee(700, 100, 100));
            enemies.add(new EnemyMeelee(200, 700, 100));
            enemies.add(new EnemyMeelee(200, 500, 100));
            enemies.add(new EnemyMeelee(200, 300, 100));
            enemies.add(new EnemyMeelee(200, 100, 100));
            enemies.add(new EnemyMeelee(500, 700, 100));
            enemies.add(new EnemyMeelee(250, 600, 100));
            enemies.add(new EnemyMeelee(100, 650, 100));
        }
        return enemies;
    }
}
