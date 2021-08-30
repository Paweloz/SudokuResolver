package com.sudoku;

public class Main {

    public static void main(String[] args) {
        IOService IOService = new IOService();
        boolean gameFinished = false;
        boolean sudokuFinished;

        while (!gameFinished) {
            int size = IOService.getBoardSize();
            Coordinates coordinates = new Coordinates(-1, -1, 0);
            SudokuBoard sudokuBoard = new SudokuBoard(size, coordinates);
            SudokuGame theGame = new SudokuGame(IOService, sudokuBoard);
            theGame.initalize();
            sudokuFinished = theGame.resolveSudoku();
            if (sudokuFinished) {
                gameFinished = IOService.playAgain();
            }
        }
    }
}
