/* represents the items in each room */


public class Item {

    String itemName;
    int itemValue;
    int itemSize;

    public Item(String n, int v, int s) {
        itemName = n;
        itemValue = v;
        itemSize = s;
    }

    public String toString() {
        String stringOutput = itemName + " (size " + itemSize + ", value " + itemValue + ")";
        return stringOutput;
    }

    public int getSize() {
        return itemSize;
    }

    public int getValue() {
        return itemValue;
    }
}
