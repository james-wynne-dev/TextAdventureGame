import java.util.*;
public class Backpack{


    LinkedList<Item> backpack = new LinkedList<Item> ();
    String newLine = System.lineSeparator();

    public void insertItem(Item newItem) {
        /* items stored in size order by putting new items in just before the first
        item in the list to which they are small than or equal to*/
        if (backpack.size() > 0) {
            // check last item, if new item is bigger put it at the end
            if(backpack.getLast().getSize() < newItem.getSize()) {
                backpack.addLast(newItem);
            }
            else {
                ListIterator<Item> itemList = backpack.listIterator();
                while (itemList.hasNext()) {
                    if (newItem.getSize() <= itemList.next().getSize()) {
                        itemList.previous();
                        itemList.add(newItem);
                        break;
                    }
                }
            }

        }
        else {
            backpack.addFirst(newItem);
        }
    }

    public String toString() {
        ListIterator<Item> backpackIter = backpack.listIterator();
        String backpackString = "";
        int itemNum = 1;
        while (backpackIter.hasNext()) {
            backpackString += itemNum + ": " + backpackIter.next().toString();
            if (backpackIter.hasNext())
                backpackString += newLine;
            itemNum++;
        }
        return backpackString;
    }

    public Item dropItem(int i) {
        Item itemToDrop = backpack.get(i);
        backpack.remove(i);
        return itemToDrop;
    }

    public int getTotalSize() {
        int totalSize = 0;
        ListIterator<Item> backpackIter = backpack.listIterator();
        if (backpackIter.hasNext()) {
            while (backpackIter.hasNext()) {
                totalSize += backpackIter.next().getSize();
            }
        }
        return totalSize;
    }

    public int getTotalValue() {
        int totalValue = 0;
        ListIterator<Item> backpackIter = backpack.listIterator();
        if (backpackIter.hasNext()) {
            while (backpackIter.hasNext()) {
                totalValue += backpackIter.next().getValue();
            }
        }
        return totalValue;
    }

    public int numItems() {
        return backpack.size();
    }

}
