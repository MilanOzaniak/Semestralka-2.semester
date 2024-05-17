package windows.maps;

import control.Controller;
import utils.enemies.Enemy;
import utils.Hero;
import utils.Wall;
import control.Frame;
import utils.enemies.EnemyMeelee;
import utils.enemies.EnemyRanged;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class ArenaGrass extends Arena {

    /**
     * Konštruktor triedy ArenaGrass, nastaví pozadie a vytvorime nepriateľov a steny
     * @param frame
     * @param controller
     * @param hero
     */
    public ArenaGrass(Frame frame, Controller controller, Hero hero) {
        super(frame, controller, hero);
        this.setBackgroundImage(new ImageIcon("pics/backgrounds/grass_floor.png").getImage());
        this.startGameThread();
        hero.setPosition(450, 350);
        setEnemies(this.generateEnemies());
        addWall(new Wall(359, 262, 1, "l", "tree"));
        addWall(new Wall(559, 262, 1, "l", "tree"));
        addWall(new Wall(359, 462, 1, "l", "tree"));
        addWall(new Wall(559, 462, 1, "l", "tree"));

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
            enemies.add(new EnemyRanged(200, 700, 100));
            enemies.add(new EnemyRanged(700, 200, 100));
            enemies.add(new EnemyRanged(100, 500, 100));
            enemies.add(new EnemyRanged(700, 700, 100));
            enemies.add(new EnemyRanged(300, 100, 100));
            enemies.add(new EnemyRanged(300, 200, 100));
            enemies.add(new EnemyRanged(100, 100, 100));
            enemies.add(new EnemyRanged(800, 700, 100));
        } else {
            enemies.add(new EnemyMeelee(700, 700, 100));
            enemies.add(new EnemyMeelee(700, 500, 100));
            enemies.add(new EnemyMeelee(700, 300, 100));
            enemies.add(new EnemyMeelee(700, 100, 100));
            enemies.add(new EnemyMeelee(200, 700, 100));
            enemies.add(new EnemyMeelee(200, 500, 100));
            enemies.add(new EnemyMeelee(200, 300, 100));
            enemies.add(new EnemyMeelee(200, 100, 100));
        }
        return enemies;
    }
}
