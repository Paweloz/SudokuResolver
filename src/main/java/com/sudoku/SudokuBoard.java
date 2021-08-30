package com.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard extends Prototype<SudokuBoard> {
    private final int size;
    private List<SudokuRow> board = new ArrayList<>();
    private final Coordinates coordinates;


    public SudokuBoard(int size, Coordinates coordinates) {
        this.coordinates = coordinates;
        this.size = size;
        int counter = 0;
        for(int i=0; i<size; i++) {
            SudokuRow sudokuRow = new SudokuRow(size);
            sudokuRow.setRowNumber(i);
            for (SudokuElement sudokuElement : sudokuRow.getElementsInRow()) {
                sudokuElement.setY(i);
                counter++;
            }
            this.board.add(sudokuRow);
        }
        coordinates.setValue(counter);
        buildBoard();
        assingBoxes();
    }

    public List<SudokuRow> getBoard() {
        return board;
    }

    public void setBoard(List<SudokuRow> board) {
        this.board = board;
    }

    public void assingBoxes() {
        assignBoxCoordinates();
        for (SudokuRow sudokuRow : board) {
            for (SudokuElement sudokuElement : sudokuRow.getElementsInRow()) {
                sudokuElement.getSudokuBox().setBoxNumber(coordinates.getY());
            }
        }
    }

    private void assignBoxCoordinates() {
        int boxY = 0;
        for (SudokuRow sudokuRow : board) {
            int boxX = 0;
            if(sudokuRow.getRowNumber()%coordinates.getY() == 0 && sudokuRow.getRowNumber() != 0) boxY++;
            for (SudokuElement sudokuElement : sudokuRow.getElementsInRow()) {
                if (sudokuElement.getX() % coordinates.getX() == 0 && sudokuElement.getX() != 0) {
                    boxX++;
                }
                Coordinates BoxCoordinates = new Coordinates(boxX,boxY,-1);
                SudokuBox sudokuBox = new SudokuBox(BoxCoordinates);
                sudokuElement.setSudokuBox(sudokuBox);
            }
        }
    }

    private void buildBoard() {
        List<Integer> divisiable = new ArrayList<>();

        for(int n=2; n<10; n++) {
            if(size%n == 0) {
                divisiable.add(n);
            }
        }
        int prev = divisiable.get(0);
        for(Integer num : divisiable) {
            if(num * num == size) {
                coordinates.setY(num);
                coordinates.setX(num);
                break;
            }else if( prev * num == size) {
                coordinates.setY(prev);
                coordinates.setX(num);
                break;
            }else {
                prev = num;
            }
        }
    }

    public SudokuBoard deepCopy() {
        Coordinates coordinates = new Coordinates(-1, -1, 0);
        SudokuBoard clonedBoard = new SudokuBoard(size, coordinates);
        try {
            clonedBoard = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        clonedBoard.setBoard(new ArrayList<>());

        for(SudokuRow sudokuRow : board) {
            SudokuRow clonedRow = new SudokuRow(size);
            clonedRow.getElementsInRow().clear();
            for(SudokuElement sudokuElement : sudokuRow.getElementsInRow()) {
                SudokuElement copiedElement = new SudokuElement(size);
                copiedElement.setValue(sudokuElement.getValue());
                copiedElement.setX(sudokuElement.getX());
                copiedElement.setY(sudokuElement.getY());
                Coordinates ElementCoordinates = new Coordinates(sudokuElement.getSudokuBox().getBoxX(),
                        sudokuElement.getSudokuBox().getBoxY(),
                        sudokuElement.getSudokuBox().getBoxNumber());
                copiedElement.setSudokuBox(new SudokuBox(ElementCoordinates));
                copiedElement.getPossibleValues().clear();
                for(Integer i : sudokuElement.getPossibleValues()) {
                    copiedElement.getPossibleValues().add(i);
                }
                clonedRow.getElementsInRow().add(copiedElement);
            }
            clonedBoard.getBoard().add(clonedRow);
        }
        return  clonedBoard;
    }

    public int getSize() {
        return coordinates.getValue();
    }


    @Override
    public String toString() {
        SudokuDrawer sudokuDrawer = SudokuDrawer.INSTANCE;
        return sudokuDrawer.drawBoard(this, coordinates);
    }
}
