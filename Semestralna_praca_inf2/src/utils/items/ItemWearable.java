package utils.items;

import enums.ItemType;
import utils.Hero;
import javax.swing.ImageIcon;

public class ItemWearable extends Item {

    /**
     * Konstruktor pre vytvorenie inštancie triedy ItemWearable.
     * Podľa mena nastaví obrázok predmetu.
     * @param name
     * @param health
     */
    public ItemWearable(String name, int health) {
        super(name, 0, health, ItemType.WEARABLE);
        setItemImage(new ImageIcon("pics/items/Armor.png").getImage());
    }

    /**
     * Metóda equip nastaví hráčovi tento predmet ako aktívny.
     * @param hero
     */
    @Override
    public void equip(Hero hero) {
        hero.setWearable(this);
    }

    /**
     * Metóda vráti informácie o predmete.
     * @return retazec s informáciami o predmete
     */
    @Override
    public String getStats() {
        return getName() + ",  Health: " + getParam2() + ",  Type: " + getItemType();
    }
}
