package com.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow {
    private final List<SudokuElement> row = new ArrayList<>();
    private int rowNumber;

    public SudokuRow(int size) {
        for(int i=0; i<size; i++) {
            SudokuElement sudokuElement = new SudokuElement(size);
            sudokuElement.setX(i);
            this.row.add(sudokuElement);
        }
    }

    public int getRowNumber() {
        return rowNumber;
    }
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    public List<SudokuElement> getElementsInRow() {
        return row;
    }

    public String toString(int line) {
        SudokuDrawer sudokuDrawer = SudokuDrawer.INSTANCE;
        return sudokuDrawer.drawRow(line, this);
    }
}
