package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class Controller implements KeyListener, MouseListener, MouseMotionListener {

    // od upPressed po rightPressed prevzaté z mojej minulej semestrálnej práce
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean eClicked;
    private boolean leftClickPressed;
    private boolean rightClickPressed;
    private double leftClickX;
    private double leftClickY;
    private int rightClickX;
    private int rightClickY;
    private int hoverX;
    private int hoverY;

    /**
     * Metoda ktora vracia stlačenie klaavesy W
     * @return upPressed
     */
    public boolean isUpPressed() {
        return this.upPressed;
    }

    /**
     * Metoda ktora vracia stlačenie klaavesy S
     * @return downPressed
     */
    public boolean isDownPressed() {
        return this.downPressed;
    }

    /**
     * Metoda ktora vracia stlačenie klaavesy A
     * @return leftPressed
     */
    public boolean isLeftPressed() {
        return this.leftPressed;
    }

    /**
     * Metoda ktora vracia stlačenie klaavesy D
     * @return rightPressed
     */
    public boolean isRightPressed() {
        return this.rightPressed;
    }

    /**
     * Metoda ktora vracia stlačenie laveho tlačidla myši
     * @return leftClickPressed
     */
    public boolean isLeftClickPressed() {
        return this.leftClickPressed;
    }

    /**
     * Metoda ktora vracia stlačenie klaavesy E
     * @return eClicked
     */
    public boolean iseClicked() {
        return this.eClicked;
    }

    /**
     * Metoda ktorá vracia pozíciu x po stlačení laveho tlačidla myši
     * @return leftClickX
     */
    public double getLeftClickX() {
        return this.leftClickX;
    }

    /**
     * Metoda ktorá vracia pozíciu y po stlačení laveho tlačidla myši
     * @return leftClickY
     */
    public double getLeftClickY() {
        return this.leftClickY;
    }

    /**
     * Metoda ktora vracia poziciu x po stlačení praveho tlačidla myši
     * @return rightClickX
     */
    public int getRightClickX() {
        return this.rightClickX;
    }

    /**
     * Metoda ktora vracia poziciu y po stlačení praveho tlačidla myši
     * @return rightClickY
     */
    public int getRightClickY() {
        return this.rightClickY;
    }

    /**
     * Metóda ktorá vracia pozíciu x kurzora myši pri prechádzani cez okno
     * @return hoverX
     */
    public int getHoverX() {
        return this.hoverX;
    }

    /**
     * Metóda ktorá vracia pozíciu y kurzora myši pri prechádzani cez okno
     * @return hoverY
     */
    public int getHoverY() {
        return this.hoverY;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metóda ktorá reaguje na stlačenie klávesy
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //hore
        if (code == KeyEvent.VK_W) {
            this.upPressed = true;
        }
        //dole
        if (code == KeyEvent.VK_S) {
            this.downPressed = true;
        }
        //do lava
        if (code == KeyEvent.VK_A) {
            this.leftPressed = true;
        }
        //do prava
        if (code == KeyEvent.VK_D) {
            this.rightPressed = true;
        }

        if (code == KeyEvent.VK_E) {
            this.eClicked = true;
        }
    }

    /**
     * Metóda ktorá reaguje na uvoľnenie klávesy
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        //hore
        if (code == KeyEvent.VK_W) {
            this.upPressed = false;
        }
        //dole
        if (code == KeyEvent.VK_S) {
            this.downPressed = false;
        }
        //do lava
        if (code == KeyEvent.VK_A) {
            this.leftPressed = false;
        }
        //do prava
        if (code == KeyEvent.VK_D) {
            this.rightPressed = false;
        }

        if (code == KeyEvent.VK_E) {
            this.eClicked = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Metóda ktorá reaguje na stlačenia tlačidla na myši a
     * vracia pozíciu x a y stlačenia
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();

        if (code == MouseEvent.BUTTON1) {
            this.leftClickPressed = true;
            this.leftClickX = e.getX();
            this.leftClickY = e.getY();
            System.out.println("x :" + this.leftClickX + ", y : " + this.leftClickY);
        }
        if (code == MouseEvent.BUTTON3) {
            this.rightClickPressed = true;
            this.rightClickX = e.getX();
            this.rightClickY = e.getY();
            System.out.println("x :" + e.getX() + ", y : " + e.getY());
        }
    }

    /**
     * Metóda ktorá reaguje na uvoľnenie tlačidla na myši
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();

        if (code == MouseEvent.BUTTON1) {
            this.leftClickPressed = false;
        }
        if (code == MouseEvent.BUTTON3) {
            this.rightClickPressed = false;
            this.rightClickX = -10;
            this.rightClickY = -10;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Metóda ktorá reaguje na pohyb kurzora myši po okne a vracia pozíciu x a y kurzora
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.hoverX = e.getX();
        this.hoverY = e.getY();

    }
}
