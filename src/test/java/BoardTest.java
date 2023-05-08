import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    static Board board;

    public BoardTest() {
    }

    @BeforeEach
    void setUpBoard() {
        board = new Board(10);
    }

    @Test
    void Board_InitialisedAsSquare_ShouldHaveCorrectFields() {
        Assertions.assertEquals(10, board.getWidth());
        Assertions.assertEquals(10, board.getHeight());
        Assertions.assertEquals(100, board.getNumSquares());
    }

    @Test
    void Board_InitialisedAsRectangle_ShouldHaveCorrectFields() {
        board = new Board(15, 20);
        Assertions.assertEquals(15, board.getWidth());
        Assertions.assertEquals(20, board.getHeight());
        Assertions.assertEquals(300, board.getNumSquares());
    }

    @Test
    void getNumMines_PositiveIntegerLessOrEqualToNumSquares_ReturnsCorrectNum() {
        board.setMines(13);
        Assertions.assertEquals(13, board.getNumMines());
        board.setMines(27);
        Assertions.assertEquals(27, board.getNumMines());
        board.setMines(100);
        Assertions.assertEquals(100, board.getNumMines());
        board.setMines(0);
        Assertions.assertEquals(0, board.getNumMines());
    }

    @Test
    void getMinesRemaining_PositiveIntegerLessOrEqualToNumSquares_ReturnsCorrectNum() {
        board.setMines(13);
        Assertions.assertEquals(13, board.getMinesRemaining());
        board.setMines(27);
        Assertions.assertEquals(27, board.getMinesRemaining());
        board.setMines(100);
        Assertions.assertEquals(100, board.getMinesRemaining());
        board.setMines(0);
        Assertions.assertEquals(0, board.getMinesRemaining());
    }

    @Test
    void setMines_PositiveIntegerLessOrEqualToNumSquares_SetsPlayableSquaresCorrectly() {
        board.setMines(13);
        Assertions.assertEquals(87, board.playableSquares);
        board.setMines(27);
        Assertions.assertEquals(73, board.playableSquares);
        board.setMines(100);
        Assertions.assertEquals(0, board.playableSquares);
        board.setMines(0);
        Assertions.assertEquals(100, board.playableSquares);
    }

    @Test
    void setMines_PositiveIntegerLessOrEqualToNumSquares_GeneratesCorrectSizeMineList() {
        board.setMines(13);
        Assertions.assertEquals(13, board.mineCellLocations.size());
        board.setMines(27);
        Assertions.assertEquals(27, board.mineCellLocations.size());
        board.setMines(100);
        Assertions.assertEquals(100, board.mineCellLocations.size());
        board.setMines(0);
        Assertions.assertEquals(0, board.mineCellLocations.size());
    }

    @Test
    void checkSquareForMine_CoordinatesInRange_ReturnsTrueWhenMine() {
        board = new Board(5);
        board.setMines(25);
        board.initialiseBoard();
        int[] coordinates = new int[]{0, 0};
        Assertions.assertEquals(true, board.checkSquareForMine(coordinates));
        coordinates = new int[]{3, 3};
        Assertions.assertEquals(true, board.checkSquareForMine(coordinates));
        coordinates = new int[]{1, 4};
        Assertions.assertEquals(true, board.checkSquareForMine(coordinates));
    }

    @Test
    void checkSquareForMine_CoordinatesInRange_ReturnsFalseWhenNotMine() {
        board = new Board(5);
        board.setMines(0);
        board.initialiseBoard();
        int[] coordinates = new int[]{2, 1};
        Assertions.assertEquals(false, board.checkSquareForMine(coordinates));
        coordinates = new int[]{4, 0};
        Assertions.assertEquals(false, board.checkSquareForMine(coordinates));
        coordinates = new int[]{3, 2};
        Assertions.assertEquals(false, board.checkSquareForMine(coordinates));
    }
}