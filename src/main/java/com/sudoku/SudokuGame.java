package com.sudoku;

import java.util.*;

public class SudokuGame {
    private final IOService IOService;
    private final SudokuBoard sudokuBoard;
    private final Stack<SudokuDto> backTrack = new Stack<>();
    private final static String ROW = "ROW";
    private final static String COL = "COL";
    private final static String BOX = "BOX";
    private boolean actionOccured = false;
    private boolean finished = false;
    private boolean isAvaliable = true;
    private int elementsWithTheSamePossibleNumber = 0;
    private SudokuBoard workingBoard;

    public SudokuGame(IOService IOService, SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        this.IOService = IOService;
    }

    public void initalize() {
        IOService.getInitialValues(sudokuBoard);
    }

    public boolean resolveSudoku() {
        int filledElements = 0;
        SudokuDto sudokuDto = new SudokuDto(sudokuBoard.deepCopy(), new Coordinates(-1,-1,0));
        backTrack.push(sudokuDto);
        workingBoard = sudokuBoard.deepCopy();
        checkIncorrectValues();

        while (!finished) {
            if(filledElements == workingBoard.getSize()) {
                finished = true;
            } else  {
                filledElements = resolveElements();
                guessValueIfNoActionOccured();
            }
        }
        System.out.println(workingBoard);
        return true;
    }

    private int resolveElements() {
        int filledElements = 0;
        for(SudokuRow sudokuRow : workingBoard.getBoard()) {
            for(SudokuElement sudokuElement : sudokuRow.getElementsInRow() ) {
                if(sudokuElement.getValue() == -1) {
                    HashSet<Integer> value = collectValues(sudokuElement);
                    setValueToElement(sudokuElement, value);
                }else {
                    filledElements++;
                }
            }
        }
        return filledElements;
    }

    private void guessValueIfNoActionOccured() {
        if(!actionOccured) {
            guessing();
        }
        actionOccured = false;
    }

    private HashSet<Integer> collectValues(SudokuElement sudokuElement) {
        HashSet<Integer> possibleValues = new HashSet<>();
        possibleValues.addAll(resolvePossibilites(sudokuElement,ROW));
        possibleValues.addAll(resolvePossibilites(sudokuElement,COL));
        possibleValues.addAll(resolvePossibilites(sudokuElement, BOX));
        return possibleValues;
    }

    private void setValueToElement(SudokuElement sudokuElement, HashSet<Integer> value) {
        if(value.size() == 1) {
            Object[] table = value.toArray();
            int valueToSet = (int) table[0];
            sudokuElement.setValue(valueToSet);
            sudokuElement.getPossibleValues().clear();
            actionOccured = true;
        }
    }

    private void checkIncorrectValues() {
        SudokuValidator validator = SudokuValidator.INSTANCE;
        if(!validator.validateBoard(this, sudokuBoard)) {
            System.out.println("Given sudoku is incorrect");
            finished = true;
        }
    }

    private List<Integer> resolvePossibilites(SudokuElement sudokuElement, String choice) {
        List<Integer> possibleValues = new ArrayList<>();
        List<Integer> takenValues = new ArrayList<>();
        List<SudokuElement> elementsInBox = selectBox(sudokuElement);

        for(Integer i : sudokuElement.getPossibleValues()) {
            switch (choice) {
                case BOX :
                    for(SudokuElement element : elementsInBox) {
                        checkElement(sudokuElement, takenValues, i, element);
                    }
                    addPossibleValues(possibleValues, i);
                    break;
                case ROW :
                    for (SudokuElement element: workingBoard.getBoard().get(sudokuElement.getY()).getElementsInRow()) {
                        checkElement(sudokuElement, takenValues, i, element);
                    }
                    addPossibleValues(possibleValues, i);
                    break;
                case COL :
                    for(SudokuRow sudokuRow : workingBoard.getBoard()) {
                        int x = sudokuElement.getX();
                        SudokuElement element = sudokuRow.getElementsInRow().get(x);
                        checkElement(sudokuElement, takenValues, i, element);
                    }
                    addPossibleValues(possibleValues, i);
                    break;
                default:
                    break;
            }
        }
        removePossibleElements(sudokuElement, takenValues);
        return possibleValues;
    }

    private void checkElement(SudokuElement sudokuElement,
                              List<Integer> takenValues,
                              Integer i,
                              SudokuElement element) {
        if (!element.equals(sudokuElement)) {
            if (element.getValue() == i) {
                isAvaliable = false;
                takenValues.add(i);
            } else if (element.getPossibleValues().contains(i) && element.getValue() == -1) {
                elementsWithTheSamePossibleNumber++;
            }
        }
    }

    private void addPossibleValues(List<Integer> possibleValues, Integer i) {
        if (elementsWithTheSamePossibleNumber == 0 && isAvaliable) {
            possibleValues.add(i);
        }
        isAvaliable = true;
        elementsWithTheSamePossibleNumber = 0;
    }

    public List<SudokuElement> selectBox(SudokuElement sudokuElement) {
        List<SudokuElement> elementsInBox = new ArrayList<>();
        for( SudokuRow sudokuRow : workingBoard.getBoard()) {
            for( SudokuElement elementToCheck : sudokuRow.getElementsInRow()) {
                if(sudokuElement.getSudokuBox().getBoxNumber() == elementToCheck.getSudokuBox().getBoxNumber()) {
                    elementsInBox.add(elementToCheck);
                }
            }
        }
        return elementsInBox;
    }

    public void removePossibleElements(SudokuElement sudokuElement, List<Integer> elementsToRemove) {
        for(Integer value : elementsToRemove) {
            sudokuElement.getPossibleValues().remove(value);
        }
        if (sudokuElement.getPossibleValues().isEmpty() && sudokuElement.getValue() == -1) {
            try {
                workingBoard = resumeBoard();
            } catch (InvalidSudokuException e) {
                System.out.println("Given sudoku is incorrect");
                finished = true;
            }
        }
    }

    public void guessing() {
        int x, y, gueesingNumber;
        Random gen = new Random();
        SudokuBoard copiedBoard = workingBoard.deepCopy();

        for(SudokuRow sudokuRow : workingBoard.getBoard()) {
            for(SudokuElement sudokuElement : sudokuRow.getElementsInRow()) {
                if(sudokuElement.getValue() == -1 && !sudokuElement.getPossibleValues().isEmpty()) {
                    int randomValue = gen.nextInt(sudokuElement.getPossibleValues().size());
                    x = sudokuElement.getX();
                    y = sudokuElement.getY();
                    gueesingNumber = sudokuElement.getPossibleValues().get(randomValue);
                    sudokuElement.setValue(gueesingNumber);
                    backTrack.push(new SudokuDto(copiedBoard,new Coordinates(x,y,gueesingNumber)));
                    return;
                }
            }
        }
    }

    public SudokuBoard resumeBoard() throws InvalidSudokuException {
        SudokuDto clonedBoard;
        if(backTrack.empty()) {
            throw new InvalidSudokuException();
        } else {
           clonedBoard = backTrack.pop();
           workingBoard = clonedBoard.getSudokuBoard();
           removeGuessedNumberFromPossibleValues(clonedBoard);
        }
        return clonedBoard.getSudokuBoard();
    }

    private void removeGuessedNumberFromPossibleValues(SudokuDto clonedBoard) {
        for(SudokuRow sudokuRow : workingBoard.getBoard()) {
            for(SudokuElement sudokuElement : sudokuRow.getElementsInRow()) {
                if(sudokuElement.getX() == clonedBoard.getX() &&
                sudokuElement.getY() == clonedBoard.getY() &&
                        clonedBoard.getValue() != 0 &&
                sudokuElement.getPossibleValues().contains(clonedBoard.getValue())) {
                    sudokuElement.getPossibleValues().remove((Object) clonedBoard.getValue());
                    return;
                }
            }
        }
    }
}
