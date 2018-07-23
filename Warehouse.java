/*
This class holds a representaion of a warehouse of Room objects
*/
import java.util.*;

public class Warehouse {

    private Room[][] roomsInWarehouse;
    private int warehouseSize;
    private String[] compassDirection = {"n", "e", "s", "w"};

    // warehouse constructor
    public Warehouse (int s) {
        // warehouse array constructed with the intention of having an x, y map
        // with x as horizontal movement and y vertical: [x][y] - [0][0] = bottom left corner
        warehouseSize = s;
        roomsInWarehouse = new Room[warehouseSize][warehouseSize];
        for (int i = 0; i < warehouseSize; i++) {
            for (int j = 0; j < warehouseSize; j++) {
                roomsInWarehouse[j][i] = new Room();
            }
        }
        // create doors
        setOutsideWall();
        createDoors();


    }

    public void displayWareHouse() {


        for (int y = warehouseSize - 1; y >= 0; y--) {

            String row1 = "";
            String row2 = "";
            String row3 = "";
            String row4 = "";
            String row5 = "";
            for (int x = 0; x < warehouseSize; x++) {
                String roomToSplit = roomsInWarehouse[x][y].toString();
                String[] splitRoom = roomToSplit.split("\n");
                row1 += splitRoom[0];
                row2 += splitRoom[1];
                row3 += splitRoom[2];
                row4 += splitRoom[3];
                row5 += splitRoom[4];
            }
            System.out.println(row1);
            System.out.println(row2);
            System.out.println(row3);
            System.out.println(row4);
            System.out.println(row5);
        }
    }

    public void setOutsideWall() {

        // north wall
        for (int i = 0; i < warehouseSize; i++) {
            roomsInWarehouse[i][warehouseSize - 1].setOutsideWall("n");
        }
        // east wall
        for (int i = 0; i < warehouseSize; i++) {
            roomsInWarehouse[warehouseSize - 1][i].setOutsideWall("e");
        }
        // south wall
        for (int i = 0; i < warehouseSize; i++) {
            roomsInWarehouse[i][0].setOutsideWall("s");
        }
        // west wall
        for (int i = 0; i < warehouseSize; i++) {
            roomsInWarehouse[0][i].setOutsideWall("w");
        }
    }

    // loop through array of rooms and add doors to any rooms that have less than 2 doors
    public void createDoors() {
        for (int i = 0; i < warehouseSize; i++) {
            for (int j = 0; j < warehouseSize; j++) {
                if (roomsInWarehouse[i][j].numDoors() < 2) {
                    addRandomDoor(i, j);
                }
            }
        }
    }

    public void addRandomDoor(int i, int j) {
        Room room = roomsInWarehouse[i][j];
        String doorSide;
        boolean doorAdded = false;

        while (!doorAdded) {
            doorSide = compassDirection[(int)(Math.random() * 4)];
            if (!room.isDoor(doorSide) && !room.isOutSideWall(doorSide)) {
                room.setDoor(doorSide);
                doorAdded = true;
                switch(doorSide) {
                    case "n":
                        roomsInWarehouse[i][j + 1].setDoor("s");
                        break;
                    case "e":
                        roomsInWarehouse[i + 1][j].setDoor("w");
                        break;
                    case "s":
                        roomsInWarehouse[i][j - 1].setDoor("n");
                        break;
                    case "w":
                        roomsInWarehouse[i - 1][j].setDoor("e");
                        break;
                }
            }
        }
    }

    public Room getRoom(int x, int y) {
        return roomsInWarehouse[x][y];
    }

}
