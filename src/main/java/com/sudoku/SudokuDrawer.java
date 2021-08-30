package com.sudoku;

public enum SudokuDrawer {

    INSTANCE;

    public String drawBoard(SudokuBoard sudokuBoard,Coordinates coordinates) {
        StringBuilder board = new StringBuilder();
        int col = coordinates.getY();
        int row = coordinates.getX();
        String spacer = drawSpacer(col, row);

        int counter = 1;
        for(SudokuRow sudokuRow : sudokuBoard.getBoard()) {
            if (counter == col) {
                counter = 0;
                board.append(sudokuRow.toString(row)).append("\n\n");
            }else {
                board.append(sudokuRow.toString(row)).append("\n").append(spacer).append("\n");
            }
            counter++;
        }
        return board.toString();
    }

    public String drawRow(int line, SudokuRow row) {
        StringBuilder result = new StringBuilder();
        int counter = 0;
        for(SudokuElement sudokuElement : row.getElementsInRow()) {
            if(counter == line) {
                counter = 0;
                result.append("|    ");
            }
            result.append(sudokuElement.toString());
            counter++;
        }
        return result+"|";
    }

    public String drawElement(SudokuElement sudokuElement) {
        if(sudokuElement.getValue() == SudokuElement.EMPTY) {
            return "| - ";
        } else if(sudokuElement.getValue() >= 10) {
            return "|"+sudokuElement.getValue()+" ";
        }
        return "| "+sudokuElement.getValue()+" ";
    }

    private String drawSpacer(int col, int row) {
        StringBuilder spacer = new StringBuilder();
        String dash = "-";
        String space = "    ";
        String line = dash.repeat(row * 4 + 1);
        for(int n=0; n<col; n++) {
            spacer.append(line).append(space);
        }
        return spacer.toString();
    }
}
