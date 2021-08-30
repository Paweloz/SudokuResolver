package com.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuElement{
    public static int EMPTY = -1;
    private final List<Integer> possibleValues = new ArrayList<>();
    private final Coordinates coordinates;
    private SudokuBox sudokuBox;


    public SudokuElement(int size) {
        this.coordinates = new Coordinates(-1,-1,EMPTY);
        for(int i = 0; i<size; i++) {
            possibleValues.add(i+1);
        }
    }

    @Override
    public String toString() {
        SudokuDrawer sudokuDrawer = SudokuDrawer.INSTANCE;
        return sudokuDrawer.drawElement(this);
    }

    public int getY() {
        return coordinates.getY();
    }
    public void setY(int y) {
        coordinates.setY(y);
    }
    public int getX() {
        return coordinates.getX();
    }
    public void setX(int x) {
        coordinates.setX(x);
    }
    public List<Integer> getPossibleValues() {
        return possibleValues;
    }
    public void setValue(int value) {
        coordinates.setValue(value);
    }
    public int getValue() {
        return coordinates.getValue();
    }
    public SudokuBox getSudokuBox() {
        return sudokuBox;
    }
    public void setSudokuBox(SudokuBox sudokuBox) {
        this.sudokuBox = sudokuBox;
    }
}
