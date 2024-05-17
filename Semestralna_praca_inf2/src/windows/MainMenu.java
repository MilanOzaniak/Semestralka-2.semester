package windows;

import control.Controller;
import control.Frame;
import utils.Hero;
import utils.items.ItemConsumable;
import utils.items.ItemWeapon;
import utils.items.ItemWearable;
import windows.maps.ArenaGrass;
import windows.maps.ArenaSand;
import windows.maps.ArenaStone;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu extends JPanel implements Runnable {

    private JButton button;
    private control.Frame frame;
    private Controller controller;
    private Hero hero;
    private Image backgroundImage;
    private ArrayList<Rectangle> items;
    private Thread thread;
    private ArrayList<Rectangle> equippedItems;
    private JLabel invLabel;
    private JLabel equppedLabel;
    private JLabel statsLabel;
    private JLabel logo;

    /**
     * Konštruktor triedy MainMenu
     * @param frame - pridávame panel do framu
     * @param controller - vstupy z klávesnice a myši
     * @param hero - hráč
     */
    public MainMenu(Frame frame, Controller controller, Hero hero) {
        this.controller = controller;
        this.hero = hero;
        this.frame = frame;
        this.items = new ArrayList<>();
        this.equippedItems = new ArrayList<>();
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        this.startGameThread();

        this.setLayout(null);
        this.setPreferredSize(new Dimension(1024, 768));
        this.backgroundImage = new ImageIcon("pics/backgrounds/background.jpg").getImage();

        this.button = new JButton();
        this.button.setBounds(600, 400, 286, 117);
        this.button.setIcon(new ImageIcon("pics/buttons/start1.png"));
        this.button.setOpaque(false);
        this.button.setContentAreaFilled(false);
        this.button.setBorderPainted(false);
        this.button.setEnabled(true);
        this.button.addActionListener(this.startArena());
        this.button.setVisible(true);

        this.logo = new JLabel("ArenaBrawl");
        this.logo.setVisible(true);
        this.logo.setBounds(350, -150, 1000, 500);
        this.logo.setFont(new Font("Arial", Font.BOLD, 50));
        this.logo.setForeground(Color.WHITE);
        this.logo.setEnabled(true);

        this.invLabel = new JLabel("Inventory");
        this.invLabel.setVisible(true);
        this.invLabel.setBounds(185, 165, 1000, 500);
        this.invLabel.setFont(new Font("Arial", Font.BOLD, 30));
        this.invLabel.setForeground(Color.WHITE);
        this.invLabel.setEnabled(true);

        this.equppedLabel = new JLabel("WEAPON         ARMOR      CONSUMABLE");
        this.equppedLabel.setVisible(true);
        this.equppedLabel.setBounds(155, 30, 1000, 500);
        this.equppedLabel.setFont(new Font("Arial", Font.BOLD, 10));
        this.equppedLabel.setForeground(Color.WHITE);
        this.equppedLabel.setEnabled(true);

        this.statsLabel = new JLabel("Health: " + hero.getBaseHealth() + ", Damage: " + hero.computeDamage() + ", Attk. speed: " + hero.getWeapon().getParam2());
        this.statsLabel.setVisible(true);
        this.statsLabel.setBounds(130, 0, 1000, 500);
        this.statsLabel.setFont(new Font("Arial", Font.BOLD, 15));
        this.statsLabel.setForeground(Color.WHITE);
        this.statsLabel.setEnabled(true);

        this.add(this.button);
        this.add(this.invLabel);
        this.add(this.equppedLabel);
        this.add(this.logo);
        this.add(this.statsLabel);

        for (int i = 0; i < 24; i++) {
            int row = i / 6;
            int col = i % 6;
            this.items.add(new Rectangle(50 + (70 * col), 450 + (70 * row), 50, 50));
        }

        for (int i = 0; i < 3; i++) {
            this.equippedItems.add(new Rectangle(150 + (70 * i), 300, 50, 50));
        }

        hero.addToInventory(new ItemConsumable("Bandage", 50));
    }

    /**
     * Metóda ktorá spúšťa thread, aby bolo možné
     * vykonávať viacej veci naraz
     */
    public void startGameThread() {
        this.thread = new Thread(this) ;
        this.thread.start();
    }

    /**
     * Metóda ktorá všetko vykresluje obrazky a texty na panel
     * @param graphics
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        graphics2D.drawImage(this.backgroundImage, 0, 0, null);

        for (int i = 0; i < this.items.size(); i++) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(this.items.get(i).x, this.items.get(i).y, this.items.get(i).width, this.items.get(i).height);

            if (this.items.get(i).contains(this.controller.getHoverX(), this.controller.getHoverY()) && this.hero.getInventory().size() > i) {
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString(this.hero.getInventory().get(i).getStats(), this.items.get(i).x - 40, this.items.get(i).y - 5);
            }

        }

        for (int i = 0; i < this.equippedItems.size(); i++) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(this.equippedItems.get(i).x, this.equippedItems.get(i).y, this.equippedItems.get(i).width, this.equippedItems.get(i).height);

            if (this.equippedItems.get(i).contains(this.controller.getHoverX(), this.controller.getHoverY())) {
                graphics2D.setColor(Color.WHITE);
                if (this.hero.getWeapon() != null && i == 0) {
                    graphics2D.drawString(this.hero.getWeapon().getStats(), this.equippedItems.get(i).x - 40, this.equippedItems.get(i).y - 5);
                }
                if (this.hero.getWearable() != null && i == 1) {
                    graphics2D.drawString(this.hero.getWearable().getStats(), this.equippedItems.get(i).x - 40, this.equippedItems.get(i).y - 5);
                }
                if (this.hero.getConsumable() != null && i == 2) {
                    graphics2D.drawString(this.hero.getConsumable().getStats(), this.equippedItems.get(i).x - 40, this.equippedItems.get(i).y - 5);
                }
            }
        }

        if (this.hero.getWeapon() != null) {
            graphics2D.drawImage(this.hero.getWeapon().getItemImage(), 150, 300, null);
        }
        if (this.hero.getWearable() != null) {
            graphics2D.drawImage(this.hero.getWearable().getItemImage(), 220, 300, null);
        }
        if (this.hero.getConsumable() != null) {
            graphics2D.drawImage(this.hero.getConsumable().getItemImage(), 290, 300, null);
        }

        for (int i = 0; i < this.hero.getInventory().size(); i++) {
            int row = i / 6;
            int col = i % 6;
            graphics2D.drawImage(this.hero.getInventory().get(i).getItemImage(), 50 + (70 * col), 450 + (70 * row), null);
        }
    }


    /**
     *  metóda, ktorá vyberie arénu, každá ma 33% šancu
     * @return ActionListener
     */
    private ActionListener startArena() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double rand = Math.random();
                if (rand <= 0.33) {
                    MainMenu.this.frame.startArena(new ArenaGrass(MainMenu.this.frame, MainMenu.this.controller, MainMenu.this.hero));
                } else if (rand <= 0.66) {
                    MainMenu.this.frame.startArena(new ArenaSand(MainMenu.this.frame, MainMenu.this.controller, MainMenu.this.hero));
                } else {
                    MainMenu.this.frame.startArena(new ArenaStone(MainMenu.this.frame, MainMenu.this.controller, MainMenu.this.hero));
                }
            }
        };
    }

    /**
     * metóda, ktorá čaká na klikanie myši, ak je kliknuté na nejaký item, tak sa zobrazia jeho štatistiky
     * ak je kliknuté pravým tlačidlom, tak sa zobrazí kontextové menu s možnosťami equip a remove.
     * Po kliknutí na equip sa item nasadí, po kliknutí na remove sa item odstráni z inventára.
     * Po klinuti na nasadený item pravým tlačidlom, tak sa zobrazí kontextové menu s možnosťou uneqiup, ktroá
     * item vráti do inventára.
     */
    @Override
    public void run() {

        double drawInterval = 1000000000 / 10;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (this.thread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                for (int i = 0; i < this.hero.getInventory().size(); i++) {
                    if (this.items.get(i).contains(this.controller.getHoverX(), this.controller.getHoverY())) {
                        this.hero.getInventory().get(i).getStats();
                    }

                    if (this.items.get(i).contains(this.controller.getRightClickX(), this.controller.getRightClickY())) {
                        int itemI = i;
                        new JPopupMenu() {
                            {
                                JMenuItem equip = new JMenuItem("EQUIP");
                                JMenuItem remove = new JMenuItem("REMOVE");

                                setBackground(Color.BLACK);
                                setForeground(Color.WHITE);
                                equip.setBackground(Color.BLACK);
                                equip.setForeground(Color.WHITE);
                                remove.setBackground(Color.BLACK);
                                remove.setForeground(Color.WHITE);
                                add(equip);
                                add(remove);
                                equip.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (MainMenu.this.hero.getInventory().get(itemI) instanceof ItemWeapon) {
                                            if (MainMenu.this.hero.getWeapon() != null) {
                                                MainMenu.this.hero.addToInventory(MainMenu.this.hero.getWeapon());
                                            }
                                            MainMenu.this.hero.setWeapon((ItemWeapon)MainMenu.this.hero.getInventory().get(itemI));
                                            MainMenu.this.hero.getInventory().remove(itemI);
                                            MainMenu.this.statsLabel.setText("Health: " + MainMenu.this.hero.getBaseHealth() + ", Damage: " + MainMenu.this.hero.computeDamage() + ", Attk. speed: " + MainMenu.this.hero.getWeapon().getParam2());
                                        } else if (MainMenu.this.hero.getInventory().get(itemI) instanceof ItemWearable) {
                                            if (MainMenu.this.hero.getWearable() != null) {
                                                MainMenu.this.hero.addToInventory(MainMenu.this.hero.getWearable());
                                            }
                                            MainMenu.this.hero.setWearable((ItemWearable)MainMenu.this.hero.getInventory().get(itemI));
                                            MainMenu.this.hero.getInventory().remove(itemI);
                                            MainMenu.this.hero.computeHealth();
                                            MainMenu.this.statsLabel.setText("Health: " + MainMenu.this.hero.getBaseHealth() + ", Damage: " + MainMenu.this.hero.computeDamage() + ", Attk. speed: " + MainMenu.this.hero.getWeapon().getParam2());
                                        } else if (MainMenu.this.hero.getInventory().get(itemI) instanceof ItemConsumable) {
                                            if (MainMenu.this.hero.getConsumable() != null) {
                                                MainMenu.this.hero.addToInventory(MainMenu.this.hero.getConsumable());
                                            }
                                            MainMenu.this.hero.setConsumable((ItemConsumable)MainMenu.this.hero.getInventory().get(itemI));
                                            MainMenu.this.hero.getInventory().remove(itemI);
                                            MainMenu.this.statsLabel.setText("Health: " + MainMenu.this.hero.getBaseHealth() + ", Damage: " + MainMenu.this.hero.computeDamage() + ", Attk. speed: " + MainMenu.this.hero.getWeapon().getParam2());
                                        }
                                        repaint();
                                    }
                                });
                                remove.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        MainMenu.this.hero.getInventory().remove(itemI);
                                        repaint();
                                    }
                                });
                            }
                        }.show(this, MainMenu.this.controller.getRightClickX(), MainMenu.this.controller.getRightClickY());
                    }

                    for (int j = 0; j < MainMenu.this.equippedItems.size(); j++) {
                        if (MainMenu.this.equippedItems.get(j).contains(MainMenu.this.controller.getRightClickX(), MainMenu.this.controller.getRightClickY())) {
                            int itemJ = j;
                            new JPopupMenu() {
                                {
                                    JMenuItem unequip = new JMenuItem("UNEQUIP");

                                    setBackground(Color.BLACK);
                                    setForeground(Color.WHITE);
                                    unequip.setBackground(Color.BLACK);
                                    unequip.setForeground(Color.WHITE);

                                    add(unequip);
                                    unequip.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (itemJ == 0) {
                                                MainMenu.this.hero.getInventory().add(MainMenu.this.hero.getWeapon());
                                                MainMenu.this.hero.setWeapon(null);
                                                MainMenu.this.statsLabel.setText("Health: " + MainMenu.this.hero.getBaseHealth() + ", Damage: " + MainMenu.this.hero.computeDamage() + ", Attk. speed: " + MainMenu.this.hero.getWeapon().getParam2());
                                            } else if (itemJ == 1) {
                                                MainMenu.this.hero.getInventory().add(MainMenu.this.hero.getWearable());
                                                MainMenu.this.hero.setWearable(null);
                                                MainMenu.this.hero.computeHealth();
                                                MainMenu.this.statsLabel.setText("Health: " + MainMenu.this.hero.getBaseHealth() + ", Damage: " + MainMenu.this.hero.computeDamage() + ", Attk. speed: " + MainMenu.this.hero.getWeapon().getParam2());

                                            } else if (itemJ == 2) {
                                                MainMenu.this.hero.getInventory().add(MainMenu.this.hero.getConsumable());
                                                MainMenu.this.hero.setConsumable(null);
                                                MainMenu.this.statsLabel.setText("Health: " + MainMenu.this.hero.getBaseHealth() + ", Damage: " + MainMenu.this.hero.computeDamage() + ", Attk. speed: " + MainMenu.this.hero.getWeapon().getParam2());
                                            }
                                            repaint();
                                        }
                                    });
                                }
                            }.show(this, MainMenu.this.controller.getRightClickX(), MainMenu.this.controller.getRightClickY());
                        }
                    }
                }

                this.repaint();
                delta--;
            }
        }
    }
}
