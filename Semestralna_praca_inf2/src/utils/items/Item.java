package utils.items;

import enums.ItemType;
import utils.Hero;
import java.awt.Image;

public abstract class Item {

    private String name;
    private int param1;
    private int param2;
    private ItemType itemType;
    private Image itemImage;

    public Item(String name, int param1, int param2, ItemType itemType) {
        this.name = name;
        this.param1 = param1;
        this.param2 = param2;
        this.itemType = itemType;
    }

    /**
     * Vracia obrazok predmetu
     * @return itemImage
     */
    public Image getItemImage() {
        return this.itemImage;
    }

    /**
     * Nastavi obrazok predmetu
     * @param itemImage
     */
    public void setItemImage(Image itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * Vracia meno predmetu
     * @return name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Vracia prvy parameter predmetu
     * @return param1
     */
    public int getParam1() {
        return this.param1;
    }


    /**
     * Vracia druhy parameter predmetu
     * @return param2
     */

    public int getParam2() {
        return this.param2;
    }

    /**
     * Vracia typ predmetu
     * @return itemType
     */
    public ItemType getItemType() {
        return this.itemType;
    }

    /**
     * Metoda ktora nastavi predmet hračovi
     */
    public abstract void equip(Hero hero);

    /**
     * Vracia informacie o predmete
     * @return retazec s informaciami
     */
    public abstract String getStats();
}
