package control;

import utils.Hero;
import windows.MainMenu;
import windows.maps.Arena;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class Frame extends JFrame {

    private JFrame frame;
    private ArrayList<JPanel> panels;
    private Controller controller;
    private Hero hero;
    private MainMenu mainMenu;

    /**
     * Konštruktor triedy Frame
     */
    public Frame() {
        this.hero = new Hero();
        this.frame = new JFrame("ArenaBrawl");
        this.controller = new Controller();
        this.panels = new ArrayList<>();
        this.mainMenu = new MainMenu(this, this.controller, this.hero);

        this.panels.add(this.mainMenu);

        this.frame.addKeyListener(this.controller);
        this.frame.addMouseListener(this.controller);
        this.frame.addMouseMotionListener(this.controller);
        this.frame.setFocusable(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.panels.get(0));
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
    }

    /**
     * Metóda ktorá spustí arénu, pridá ju do zoznamu panelov a zobrazí ju a zároveň skryje aktuálny panel
     * @param arena
     */
    public void startArena(Arena arena) {
        this.panels.add(arena);
        this.panels.get(0).setVisible(false);
        this.frame.add(arena);
        this.frame.revalidate();
        this.frame.repaint();
    }

    /**
     * Metóda ktorá odstráni panel z listu panelov a zobrazí hlavné menu
     * @param panel
     */
    public void returnToMenu(JPanel panel) {
        this.panels.remove(panel);
        this.panels.get(0).setVisible(true);
        this.frame.revalidate();
        this.frame.repaint();
    }




}
