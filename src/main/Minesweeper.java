import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Minesweeper {

    public static Board playingBoard;
    public static Scanner input;
    public static boolean gameInProgress;

    public static void main(String[] args) {
        boolean playerWantsToPlay = true;
        input = new Scanner(System.in);
        while (playerWantsToPlay) {
            System.out.println("\nWelcome to the minefield. Start by entering the grid size you want.\nFor a 10x10 square, enter \"10\".\nFor a 5 (wide) x 10 (high) rectangle, enter \"0510\".\nNote that the maximum width and height is 40.\n");
            configureBoardSize();
            configureMines();
            System.out.println("\nLet's play!\n");
            playingBoard.initialiseBoard();
            playingBoard.printBoard();
            System.out.println("\nStart by selecting your row and column.\n");
            gameInProgress = true;
            while (gameInProgress) {
                int[] coordinates = getCoordinates();
                playingBoard.selectCell(coordinates);
                playingBoard.printBoard();
                boolean gameOver = playingBoard.checkSquareForMine(coordinates);
                if (gameOver) {
                    gameInProgress = false;
                    System.out.println("\nOh no, you hit a mine. Better luck next time.\n");
                    break;
                }
                boolean victory = playingBoard.noMoreSquares();
                if (victory) {
                    gameInProgress = false;
                    System.out.println("\nWell done! You cleared the minefield.\n");
                    break;
                }
                continuePlay();
            }
            playerWantsToPlay = playAgain();
        }
        input.close();
    }

    public static void configureBoardSize() {
        String size = input.next();
        while (size.length() > 4 || size.equals("0") || size.endsWith("00") || size.startsWith("00")){
            System.out.println("Oops. Your grid size is in the wrong format. Please try again.");
            size = input.next();
        }
        while (size.length() == 3){
            System.out.println("Please use 4 digits so that the length and width can be determined. Numbers less than 10 should use a zero (eg: 05 instead of 5)");
            size = input.next();
        }
        if (size.length() == 2 || size.length() == 1){
            int sizeAsInt = Integer.parseInt(size);
            if (sizeAsInt <= 40){
                playingBoard = new Board(sizeAsInt);
            } else {
                playingBoard = new Board(40);
                System.out.println("That width wasn't possible. Setting board to maximum possible size.");
            }
        } else {
            String inputWidth = size.substring(0,2);
            String inputHeight = size.substring(2);
            int width = Integer.parseInt(inputWidth);
            int height = Integer.parseInt(inputHeight);
            if (width <= 40 && height <= 40){
                playingBoard = new Board(width, height);
            } else if (width > 40 && height <= 40){
                playingBoard = new Board(40, height);
                System.out.println("Width cannot be set that large. Defaulting to maximum width.");
            } else if (width <= 40 && height > 40){
                playingBoard = new Board(width, 40);
                System.out.println("Height cannot be set that large. Defaulting to maximum height.");
            } else {
                playingBoard = new Board(40, 40);
                System.out.println("Grid cannot be set that large. Defaulting to maximum size.");
            }

        }
    }

    public static void configureMines() {
        System.out.println("\nYour grid has "+ playingBoard.getNumSquares() + " squares.");
        System.out.println("Enter the number of mines you would like to place in your minefield.\n");
        int mineNo = input.nextInt();
        while (mineNo > playingBoard.getNumSquares()){
            System.out.printf("Sorry, you can't have that many mines in a field of %d squares. Please enter a smaller number.\n", playingBoard.getNumSquares());
            mineNo = input.nextInt();
        }
        playingBoard.setMines(mineNo);
        System.out.printf("You have set %d mines.\n", playingBoard.getNumMines());
    }

    public static int[] getCoordinates() {
        System.out.print("Row: ");
        int row = input.nextInt() - 1;
        //need to add error handling here.
        while (row < 0 || row >= playingBoard.getHeight()){
            System.out.print("\nSorry, that row doesn't exist. Please try again.\n\nRow: ");
            row = input.nextInt() - 1;
        }
        System.out.print("Column: ");
        int col = input.nextInt() - 1;
        while (col < 0 || col >= playingBoard.getWidth()){
            System.out.print("\nSorry, that column doesn't exist. Please try again.\n\nColumn: ");
            col = input.nextInt() - 1;
        }
        return new int[]{row, col};
    }

    public static void continuePlay() {
        boolean settingFlags = true;
        while (settingFlags) {
            System.out.println("\nEnter F to set a flag or S to select another square.\n");
            String answer = input.next().toLowerCase();
            while (!answer.equals("f") && !answer.equals("s")) {
                System.out.println("Oops, wrong input. Enter F to set a flag or S to select another square.");
                answer = input.next().toLowerCase();
            }
            if (answer.equalsIgnoreCase("s")) {
                settingFlags = false;
            } else {
                System.out.println("\nWhere would you like to set the flag?\n");
                System.out.print("\nRow: ");
                int flagRow = input.nextInt() - 1;
                System.out.print("Column: ");
                int flagCol = input.nextInt() - 1;
                playingBoard.setFlag(flagRow, flagCol);
                playingBoard.printBoard();
            }
        }
    }

    public static boolean playAgain(){
        System.out.println("Enter N to start a new game.\n");
        String answer = input.next().toLowerCase();
        return answer.equals("n");
    }


}

