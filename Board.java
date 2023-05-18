import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Board {

    private Cell[][] cells;
    private Cell[][] saveBoard;

    public Board(int numRows, int numCols){
        cells = new Cell[numRows][numCols];
        saveBoard = new Cell[numRows][numCols];
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell();
                setLocation(i, j);
            }
        }
    }

    public Board(String inputFile){
        //make a Board that can take an input file
        //assumes the input file is valid
        ArrayList<String> stepper = new ArrayList<String>();
        try {
            File myObj = new File(inputFile);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              stepper.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        cells = new Cell[stepper.size()][stepper.get(1).length()];
        saveBoard = new Cell[stepper.size()][stepper.get(1).length()];

        for(int i = 0; i < stepper.size(); i++){
            for(int j = 0; j < stepper.get(1).length(); j++){
                cells[i][j] = new Cell((stepper.get(i).substring(j, j + 1).equals("1")) ? true : false);
                setLocation(i, j);
            }
        }
    }

    public boolean checkCell(int row, int col){
        int happiness = numLiveNeighbors(row, col);
        boolean life = saveBoard[row][col].getState();
        if(life && (happiness == 2 || happiness == 3)){
            return true;
        }else if(happiness == 3){
            return true;
        }
        return false;
    }

    public void setLocation(int row, int col){
        if(row == 0){
            if(col == 0){
                cells[row][col].changeLocation("UpperLeft");
            }else if(col == cells[0].length - 1){
                cells[row][col].changeLocation("UpperRight");
            }else{
                cells[row][col].changeLocation("Upper");
            }
        }else if(row == cells.length - 1){
            if(col == 0){
                cells[row][col].changeLocation("LowerLeft");
            }else if(col == cells[0].length - 1){
                cells[row][col].changeLocation("LowerRight");
            }else{
                cells[row][col].changeLocation("Lower");
            }
        }else if(col == 0){
            cells[row][col].changeLocation("Left");
        }else if(col == cells[0].length - 1){
            cells[row][col].changeLocation("Right");
        }else{
            cells[row][col].changeLocation("Center");
        }
    }

    private int neighborCheck(int row, int col){
        int num = (saveBoard[row][col].getState()) ? 1 : 0;
        return num;
    }

    public int numLiveNeighbors(int r, int c){
        int count = 0;
        switch(saveBoard[r][c].getLocation()){
            case "UpperLeft":
                count += neighborCheck(r, c + 1) + neighborCheck(r + 1, c + 1) + neighborCheck(r + 1, c);
                break;
            case "UpperRight":
                count += neighborCheck(r, c - 1) + neighborCheck(r + 1, c - 1) + neighborCheck(r + 1, c);
                break;
            case "LowerLeft":
                count += neighborCheck(r - 1, c) + neighborCheck(r - 1, c + 1) + neighborCheck(r, c + 1);
                break;
            case "LowerRight":
                count += neighborCheck(r, c - 1) + neighborCheck(r - 1, c - 1) + neighborCheck(r - 1, c);
                break;
            case "Left":
                count += neighborCheck(r - 1, c) + neighborCheck(r - 1, c + 1) + neighborCheck(r, c + 1) + neighborCheck(r + 1, c + 1) + neighborCheck(r + 1, c);
                break;
            case "Right":
                count += neighborCheck(r - 1, c) + neighborCheck(r - 1, c - 1) + neighborCheck(r, c - 1) +  neighborCheck(r + 1, c - 1) + neighborCheck(r + 1, c);
                break;
            case "Upper":
                count += neighborCheck(r, c - 1) + neighborCheck(r + 1, c - 1) + neighborCheck(r + 1, c) + neighborCheck(r + 1, c + 1) + neighborCheck(r, c + 1);
                break;
            case "Lower":
                count += neighborCheck(r, c - 1) + neighborCheck(r - 1, c - 1) + neighborCheck(r - 1, c) + neighborCheck(r - 1, c + 1) + neighborCheck(r, c + 1);
                break;
            case "Center":
                count += neighborCheck(r - 1, c) + neighborCheck(r - 1, c - 1) + neighborCheck(r, c - 1) + neighborCheck(r + 1, c - 1) + neighborCheck(r + 1, c) + neighborCheck(r + 1, c + 1) + neighborCheck(r, c + 1) + neighborCheck(r - 1, c + 1);
                break;
            default:
                break;
        }
        return count;
    }

    public void copyBoard(){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                saveBoard[i][j] = new Cell(cells[i][j].getState());
                saveBoard[i][j].changeLocation(cells[i][j].getLocation());
            }
        }
    }

    //get working, then try to use threading so its faster
    public void updateBoard(){
        copyBoard();
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                cells[i][j].changeState(checkCell(i, j));
            }
        }
    }

    public void setCell(int row, int col, boolean value){
        cells[row][col].changeState(value);
    }

    public void printBoard(){
        for(Cell[] x: cells){
            for(Cell y: x){
                String output = (y.getState()) ? "0" : " ";
                System.out.print(output + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
