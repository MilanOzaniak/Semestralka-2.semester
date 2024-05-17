package utils.items;

import enums.ItemType;
import utils.Hero;
import javax.swing.ImageIcon;

public class ItemConsumable extends Item {

    /**
     * Konstruktor pre vytvorenie inštancie triedy ItemConsumable.
     * Podľa mena nastaví obrázok predmetu.
     * @param name
     * @param health
     */
    public ItemConsumable(String name, int health) {
        super(name, 0, health, ItemType.CONSUMABLE);
        if (name.equals("Medkit")) {
            setItemImage(new ImageIcon("pics/items/MedKit.png").getImage());
        } else if (name.equals("Bandage")) {
            setItemImage(new ImageIcon("pics/items/Bandage.png").getImage());
        } else {
            setItemImage(new ImageIcon("pics/items/Bandage.png").getImage());
        }
    }

    /**
     * Metóda equip nastaví hráčovi tento predmet ako aktívny.
     * @param hero
     */
    @Override
    public void equip(Hero hero) {
        hero.setConsumable(this);
    }

    /**
     * Metóda vráti informácie o predmete.
     * @return retazec s informáciami o predmete
     */
    @Override
    public String getStats() {
        return getName() +  ",  Health: " + getParam2() + ",  Type: " + getItemType();
    }

    /**
     * Metóda consume zvýši hráčovi zdravie o hodnotu parametra 2.
     * @param hero
     */
    public void consume(Hero hero) {
        hero.setBaseHealth(hero.getBaseHealth() + getParam2());
        if (hero.getBaseHealth() > hero.getMaxHealth()) {
            hero.setBaseHealth(hero.getMaxHealth());
        }
    }
}
