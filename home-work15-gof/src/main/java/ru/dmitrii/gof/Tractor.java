package ru.dmitrii.gof;

public class Tractor {
    int[] position = new int[] { 0, 0 };
    int[] field = new int[] { 5, 5 };
    Orientation orientation = Orientation.NORTH;

    /**
     * Метод управления трактором
     * @param command String
     */
    public void move(String command) {
        if (command.equalsIgnoreCase("F")) {
            moveForwards();
        } else if (command.equalsIgnoreCase("T")) {
            turnClockwise();
        }
    }

    /**
     * Метод двигающий трактор на одну клетку
     */
    public void moveForwards() {
        if (position[0] >= field[0]-1 || position[1] >= field[1]-1) {
            throw new TractorInDitchException();
        }
        position = orientation.move(position);
    }

    /**
     * Метод поворачивающий трактор
     */
    public void turnClockwise() {
        orientation = orientation.orientations();
    }

    public int getPositionX() {
        return position[0];
    }

    public int getPositionY() {
        return position[1];
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
