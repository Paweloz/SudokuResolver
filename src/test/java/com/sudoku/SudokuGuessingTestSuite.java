package com.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SudokuGuessingTestSuite {

    private static int counter = 0;
    private static int copiedCounter =0;
    private static SudokuBoard copiedBoard;

    @Test
    void testCopingBoard() {
        //Given
        Coordinates coordinates = new Coordinates(-1, -1, 0);
        SudokuBoard sudokuBoard = new SudokuBoard(9, coordinates);

        //When
        copiedBoard = sudokuBoard.deepCopy();
        copiedBoard.getBoard().get(0).getElementsInRow().get(8).setValue(1);
        SudokuElement originalElement = sudokuBoard.getBoard().get(0).getElementsInRow().get(8);
        SudokuElement copiedElement = copiedBoard.getBoard().get(0).getElementsInRow().get(8);

        //Then
        assertEquals(sudokuBoard.getSize(), copiedBoard.getSize());
        assertNotEquals(copiedElement, originalElement);
    }
}
