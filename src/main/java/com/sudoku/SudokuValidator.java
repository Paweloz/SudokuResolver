package com.sudoku;

import java.util.List;

public enum SudokuValidator {
    INSTANCE;
    private boolean correct = true;

    public boolean validateSize(int size) {
        boolean correct;

        correct = isNotToBig(size);
        if(correct) {
            correct = canBeBuild(size);
        }
        return correct;
    }

    private boolean canBeBuild(int size) {
        int counter = 0;
        boolean correct = false;
        for(int i = 1; i<10; i++) {
            if(size %i == 0) {
                counter++;
            }
        }
        if(counter > 2) {
            correct = true;
        } else {
            System.out.println("Incorrect size. Please enter size divisible by more than just itself");
        }
        return correct;
    }

    private boolean isNotToBig(int size) {
        boolean correct = false;
        if (size <= 24) {
            correct = true;
        } else {
            System.out.println("Size cannot be bigger than 24. Try again");
        }
        return correct;
    }

    public boolean validateBoard(SudokuGame sudokuGame, SudokuBoard sudokuBoard) {

        for (SudokuRow sudokuRow : sudokuBoard.getBoard()) {
            for (SudokuElement sudokuElement : sudokuRow.getElementsInRow()) {
                if(sudokuElement.getValue() != -1) {
                    if (validateRow(sudokuElement, sudokuRow) &&
                            validateColumn(sudokuElement, sudokuBoard) &&
                            validateBox(sudokuGame, sudokuElement)) {
                        correct = true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return correct;
    }

    public boolean validateBox(SudokuGame sudokuGame, SudokuElement sudokuElement) {
        boolean correct = true;
        List<SudokuElement> elementsInBox = sudokuGame.selectBox(sudokuElement);
        for (SudokuElement element : elementsInBox) {
            if (element.getValue() == sudokuElement.getValue() &&
                    element.getX() != sudokuElement.getX() &&
                    element.getY() != sudokuElement.getY()) {
                correct = false;
                break;
            }
        }
        return correct;
    }

    public boolean validateColumn(SudokuElement sudokuElement, SudokuBoard sudokuBoard) {
        boolean correct = true;
        for(SudokuRow sudokuRow : sudokuBoard.getBoard()) {
            int x = sudokuElement.getX();
            SudokuElement element = sudokuRow.getElementsInRow().get(x);
            if (element.getValue() == sudokuElement.getValue() && !element.equals(sudokuElement)) {
                correct = false;
                break;
            }
        }
        return correct;
    }

    public boolean validateRow(SudokuElement sudokuElement, SudokuRow sudokuRow) {
        boolean correct = true;
        for(SudokuElement element : sudokuRow.getElementsInRow()) {
            if (element.getValue() == sudokuElement.getValue() && !element.equals(sudokuElement)) {
                correct = false;
                break;
            }
        }
        return correct;
    }

    public boolean validateQuantity(String[] splitted) {
        boolean correct = true;
        if(splitted.length > 3) {
            System.out.println("Incorrect input. Input has to contain only 3 values :" +
                    " col, row and value\nTry again");
            correct = false;
        }
        return correct;
    }

    public void validateValues(SudokuBoard sudokuBoard, Coordinates coordinates, int size) {
        int col = coordinates.getY();
        int row = coordinates.getX();
        int value = coordinates.getValue();
        if(col <= size && row <= size && value <= size &&
                col != 0 && row != 0 && value !=0) {
            sudokuBoard.getBoard().get(col - 1).getElementsInRow().get(row - 1).setValue(value);
            sudokuBoard.getBoard().get(col - 1).getElementsInRow().get(row - 1).getPossibleValues().clear();
        } else {
            System.out.println("Incorrect input. Values cannot be greater than board size.\nTry again");
        }
    }
}
