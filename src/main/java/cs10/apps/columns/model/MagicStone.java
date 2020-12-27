package cs10.apps.columns.model;

import cs10.apps.columns.view.ItemMagicStone;

public class MagicStone {
    private ItemMagicStone[] items;
    public static final int OFFSET = 20;

    public MagicStone() {
        items = new ItemMagicStone[3];
        items[0] = ItemMagicStone.UP_TRIANGLE;
        items[1] = ItemMagicStone.SQUARE;
        items[2] = ItemMagicStone.DOWN_TRIANGLE;
    }

    public ItemMagicStone getItem(int number){
        return items[number-1];
    }

    public void rotate(){
        ItemMagicStone[] result = new ItemMagicStone[3];
        result[0] = items[2];
        result[1] = items[0];
        result[2] = items[1];
        items = result;
    }
}
