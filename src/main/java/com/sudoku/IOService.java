package com.sudoku;

import java.util.Scanner;

public class IOService {
    private static final String SUDOKU = "SUDOKU";
    private static SudokuValidator validator;
    private int size = 0;

    public boolean playAgain() {
        boolean finished = true;
        boolean correct = false;
        System.out.println(" If You would like to play another sudoku press 'Y' if not press 'N'");
        while (!correct) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if(input.equals("Y") || input.equals("y")) {
                correct = true;
                finished = false;
            }else if(input.equals("N") || input.equals("n")) {
                correct = true;
                System.out.println("Thank you for playing");
            }else {
                System.out.println("Incorrect input. Try Again");
            }
        }
        return finished;
    }

    public int getBoardSize() {
        validator = SudokuValidator.INSTANCE;
         boolean correct = false;

         System.out.println("\nWelcome to Sudoku Solver\nPlease enter size of a board X by X");
         while(!correct) {
             Scanner scanner = new Scanner(System.in);
             String input = scanner.nextLine();
             correct = processInput(input);
         }
         return size;
    }

    private boolean processInput(String input) {
        boolean correct = false;
        try {
           size = Integer.parseInt(input);
           correct = true;
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format. Size has to be given as one number. Try again");
        }
        if(correct) {
            correct = validator.validateSize(size);
        }
        return correct;
    }

    private boolean processInput(SudokuBoard sudokuBoard, Coordinates coordinates,
                                 String input, String[] splitted) {
        validator = SudokuValidator.INSTANCE;
        boolean done = false;
        boolean correct;

        if(input.equals(SUDOKU)) {
            done = true;
        }else {
            correct = validator.validateQuantity(splitted);
            convertInputToValues(sudokuBoard, coordinates, correct, splitted);
            System.out.println(sudokuBoard);
        }
        return done;
    }

    public void getInitialValues(SudokuBoard sudokuBoard) {
        Coordinates coordinates = new Coordinates(-1,-1,0);
        boolean done = false;

        printInstruction(sudokuBoard);
        while (!done) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] splitted = input.split(",");
            done = processInput(sudokuBoard, coordinates, input, splitted);
        }
    }

    private void convertInputToValues(SudokuBoard sudokuBoard, Coordinates coordinates,
                                      boolean correct, String[] splitted) {
        validator = SudokuValidator.INSTANCE;

        if(correct) {
            try {
                coordinates.setY(Integer.parseInt(splitted[0]));
                coordinates.setX(Integer.parseInt(splitted[1]));
                coordinates.setValue(Integer.parseInt(splitted[2]));
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input format. Try again");
            }
            validator.validateValues(sudokuBoard, coordinates,size);
        }
    }

    private void printInstruction(SudokuBoard sudokuBoard) {
        System.out.println( sudokuBoard + "\nPlease enter value for Sudoku" +
                " in format : col,row,value eg: '5,2,6' \n" +
                "Invalid delimiter will couse values to be recjected\n" +
                "Type 'SUDOKU' to finish input and start resolver");
    }
}
