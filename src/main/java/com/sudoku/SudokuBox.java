package com.sudoku;

public class SudokuBox {
    private final Coordinates coordinates;

    public SudokuBox(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getBoxX() {
        return coordinates.getX();
    }
    public int getBoxY() {
        return coordinates.getY();
    }
    public int getBoxNumber() {
        return coordinates.getValue();
    }
    public void setBoxNumber(int boxNumber) {
        coordinates.setValue(coordinates.getY() * boxNumber + coordinates.getX());
    }
}
