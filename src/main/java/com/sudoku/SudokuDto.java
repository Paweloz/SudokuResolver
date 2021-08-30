package com.sudoku;

public class SudokuDto {
    private final SudokuBoard sudokuBoard;
    private final Coordinates coordinates;

    public SudokuDto(SudokuBoard sudokuBoard, Coordinates coordinates) {
        this.sudokuBoard = sudokuBoard;
        this.coordinates = coordinates;
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }
    public int getValue() {
        return coordinates.getValue();
    }
    public int getX() {
        return coordinates.getX();
    }
    public int getY() {
        return coordinates.getY();
    }
}
