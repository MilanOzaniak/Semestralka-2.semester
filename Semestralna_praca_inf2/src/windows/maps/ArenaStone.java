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

public class ArenaStone extends Arena {

    /**
     * Konštruktor triedy ArenaStone, nastaví pozadie a vytvorime nepriateľov a steny
     * @param frame
     * @param controller
     * @param hero
     */
    public ArenaStone(Frame frame, Controller controller, Hero hero) {
        super(frame, controller, hero);
        this.setBackgroundImage(new ImageIcon("pics/backgrounds/stone_floor.png").getImage());
        this.startGameThread();
        setEnemies(this.generateEnemies());
        hero.setPosition(450, 350);
        addWall(new Wall(250, 250, 10, "p", "brick"));
        addWall(new Wall(250, 450, 10, "p", "brick"));
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
            enemies.add(new EnemyRanged(100, 50, 100));
            enemies.add(new EnemyRanged(850, 50, 100));
            enemies.add(new EnemyRanged(100, 650, 100));
            enemies.add(new EnemyRanged(850, 650, 100));
            enemies.add(new EnemyMeelee(50, 350, 100));
            enemies.add(new EnemyMeelee(800, 350, 100));
        } else {
            enemies.add(new EnemyRanged(300, 200, 100));
            enemies.add(new EnemyRanged(600, 200, 100));
            enemies.add(new EnemyRanged(300, 500, 100));
            enemies.add(new EnemyRanged(600, 500, 100));
            enemies.add(new EnemyRanged(900, 100, 100));
            enemies.add(new EnemyRanged(900, 600, 100));
            enemies.add(new EnemyRanged(100, 100, 100));
            enemies.add(new EnemyRanged(100, 600, 100));

        }
        return enemies;
    }
}
