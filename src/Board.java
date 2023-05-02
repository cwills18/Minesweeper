import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    private final int width;
    private final int height;
    private final int numSquares;
    private int numMines;
    private int playableSquares;
    private ArrayList<Integer> mineCellLocations;
    private Cell[][] boardConfiguration;
    private int selectedSquares;
    //note that like the real game, "minesRemaining" will reduce by 1 each time the player
    //plays a flag, regardless if they have placed their flag correctly.
    private int minesRemaining;


    //constructor for a square playing grid
    protected Board(int width){
        this.width = width;
        this.height = width;
        this.numSquares = width * width;
        this.selectedSquares = 0;
    }
    //constructor for a rectangle playing grid
    protected Board(int columns, int rows){
        this.width = columns;
        this.height = rows;
        this.numSquares = rows * columns;
        this.selectedSquares = 0;
    }

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public int getNumSquares() {
        return this.numSquares;
    }
    public int getNumMines(){
        return this.numMines;
    }
    protected void setMines(int mines){
        this.numMines = mines;
        this.minesRemaining = mines;
        this.playableSquares = this.numSquares - mines;
        this.mineCellLocations = new ArrayList<>(this.numMines);
        //randomly pick cells to be assigned a mine
        //number generated will correspond to cell's unique id when cells are initialised
        int minesAdded = 0;
        while (minesAdded < this.numMines) {
            int randomNum = (int) Math.ceil(Math.random() * this.numSquares);
            if (this.mineCellLocations.contains(randomNum)) {
                continue;
            } else {
                this.mineCellLocations.add(randomNum);
                minesAdded++;
            }
        }
    }
    protected Cell[][] getBoard(){
        return this.boardConfiguration;
    }
    protected void setBoard(Cell[][] newBoard) {
        this.boardConfiguration = newBoard;
    }
    protected int getMinesRemaining() {
        return this.minesRemaining;
    }
    protected void initialiseBoard() {
        Cell[][] board = new Cell[this.height][this.width];
        //set up cells within the grid
        int cellIdentifier = 1;
        for (int i = 0; i<this.height; i++){
            for (int j = 0; j<this.width; j++) {
                if (mineCellLocations.contains(cellIdentifier)){
                    board[i][j] = new Mine(cellIdentifier);
                    cellIdentifier++;
                } else {
                    board[i][j] = new OrdinaryCell(cellIdentifier);
                    cellIdentifier++;
                }
            }
        }
        this.setBoard(board);
        //set the true values of ordinary cells
        for (int i = 0; i < this.height; i++){
            for (int j = 0; j < this.width; j++){
                if (!(boardConfiguration[i][j].isMine())){
                    int[] theseCoordinates = new int[] {i, j};
                    int noMines = surroundingCellsWithMines(theseCoordinates);
                    boardConfiguration[i][j].setSurroundingMines(noMines);
                }
            }
        }
    }

    protected boolean checkSquareForMine(int[] coordinates){
        int row = coordinates[0];
        int col = coordinates[1];
        return this.boardConfiguration[row][col].isMine();
    }

    protected void selectCell(int[] coordinates){
        int row = coordinates[0];
        int col = coordinates[1];
        Cell selectedCell = this.boardConfiguration[row][col];
        if (selectedCell.isSelected()){
            return;
        } else {
            selectedCell.setSelected();
            this.selectedSquares++;
            if (selectedCell.getTrueValue().equals("0")){
                cascadeCells(row, col);
            }
        }
    }

    protected void printBoard() {
        System.out.printf("\n Mines Remaining: %d\n\n     ", this.getMinesRemaining());
        for (int i = 1; i <= this.width; i++){
            if (i < 10){
                System.out.print("  " + (i) + " ");
            } else {
                System.out.print(" " + (i) + " ");
            }
        }
        String dashes = "---+";
        System.out.print("\n     +" + dashes.repeat(this.width) + "\n");
        for (int i = 0; i < this.height; i++){
            if (i < 9){
                System.out.print("  " + (i+1) + "  ");
            } else {
                System.out.print(" " + (i+1) + "  ");
            }
            for (int j = 0; j < this.width; j++){
                System.out.print("| ");
                String face = boardConfiguration[i][j].getCellFace();
                System.out.print(face);
                System.out.print(" ");
            }
            System.out.print("|\n     +" + dashes.repeat(this.width)+ "\n");
        }
    }

    protected void setFlag(int row, int col){
        this.boardConfiguration[row][col].setFlagged();
        this.minesRemaining--;
    }

    protected boolean noMoreSquares() {
        return this.selectedSquares >= this.playableSquares;
    }

    private int surroundingCellsWithMines(int[] coordinates){
        int cellRow = coordinates[0];
        int cellCol = coordinates[1];
        int mineCount = 0;
        //check row above
        if (cellRow - 1 >= 0){
            for (int col = (cellCol - 1); col <= (cellCol + 1); col++){
                if (col >= 0 && col < width) {
                    boolean isMine = this.boardConfiguration[cellRow-1][col].isMine();
                    if (isMine){
                        mineCount++;
                    }
                }
            }
        }
        //check current row
        for (int col = (cellCol - 1); col <= (cellCol + 1); col++){
            if (col == cellCol || col < 0 || col >= width){
                continue;
            } else {
                boolean isMine = this.boardConfiguration[cellRow][col].isMine();
                if (isMine){
                    mineCount++;
                }
            }
        }
        //check row below
        if (cellRow + 1 < height) {
            for (int col = (cellCol - 1); col <= (cellCol + 1); col++) {
                if (col >= 0 && col < width) {
                    boolean isMine = this.boardConfiguration[cellRow + 1][col].isMine();
                    if (isMine) {
                        mineCount++;
                    }
                }
            }
        }
        return mineCount;
    }

    private void cascadeCells (int initialRow, int initialCol) {
        //this function will start with the initial coordinates passed in. As the function
        //selects new cells, it will add any that have a zero to an array to come back to
        //later and repeat the same steps
        //function will continue in while loop while array is not empty;
        int[] startingVals = new int[]{initialRow, initialCol};
        ArrayList<int[]> cellsAwaitingCascade = new ArrayList<>();
        cellsAwaitingCascade.add(startingVals);
        boolean cascading = true;
        while (cascading) {
            if (cellsAwaitingCascade.size() == 0){
                cascading = false;
            } else {
                int[] coordinates = cellsAwaitingCascade.get(0);
                int cellRow = coordinates[0];
                int cellCol = coordinates[1];
                //select row above
                if (cellRow - 1 >= 0) {
                    for (int col = (cellCol - 1); col <= (cellCol + 1); col++) {
                        if (col >= 0 && col < this.width) {
                            Cell thisCell = this.boardConfiguration[cellRow - 1][col];
                            if (!thisCell.isSelected()) {
                                thisCell.setSelected();
                                selectedSquares++;
                                if (thisCell.getTrueValue().equals("0")){
                                    int[] newSquareToCascade = new int[]{cellRow - 1, col};
                                    cellsAwaitingCascade.add(newSquareToCascade);
                                }
                            }
                        }
                    }
                }
                //select current row
                for (int col = (cellCol - 1); col <= (cellCol + 1); col++) {
                    if (col == cellCol || col < 0 || col >= this.width) {
                        continue;
                    } else {
                        Cell thisCell = this.boardConfiguration[cellRow][col];
                        if (!thisCell.isSelected()) {
                            thisCell.setSelected();
                            selectedSquares++;
                            if (thisCell.getTrueValue().equals("0")){
                                int[] newSquareToCascade = new int[]{cellRow, col};
                                cellsAwaitingCascade.add(newSquareToCascade);
                            }
                        }
                    }
                }
                //select row below
                if (cellRow + 1 < this.height) {
                    for (int col = (cellCol - 1); col <= (cellCol + 1); col++) {
                        if (col >= 0 && col < this.width) {
                            Cell thisCell = this.boardConfiguration[cellRow + 1][col];
                            if (!thisCell.isSelected()) {
                                thisCell.setSelected();
                                selectedSquares++;
                                if (thisCell.getTrueValue().equals("0")){
                                    int[] newSquareToCascade = new int[]{cellRow + 1, col};
                                    cellsAwaitingCascade.add(newSquareToCascade);
                                }
                            }
                        }
                    }
                }
                //remove cell that has just been cascaded from array list
                cellsAwaitingCascade.remove(0);
            }
        }
    }
}

