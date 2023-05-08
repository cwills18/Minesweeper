import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrdinaryCellTest {
	
    static OrdinaryCell cell;
    
    @BeforeEach
    void setUpCell() {
        cell = new OrdinaryCell(1);
    }


    @Test
    void isMine_OrdinaryCell_ShouldReturnFalse() {
        assertEquals(false, cell.isMine());
    }

    @Test
    void isSelected_OrdinaryCellDefault_ShouldBeFalse(){
        assertEquals(false, cell.isSelected());
    }

    @Test
    void setSelected_SetToTrue_ShouldCorrectlyUpdateCell() {
        cell.setSelected();
        assertEquals(true, cell.isSelected());
    }

    @Test
    void isFlagged_OrdinaryCell_DefaultShouldBeFalse() {
        assertEquals(false, cell.isFlagged);
    }

    @Test
    void setFlagged_SetToTrue_ShouldUpdateField() {
        cell.setFlagged();
        assertEquals(true, cell.isFlagged);
    }

    @Test
    void setSurroundingMines_PositiveInteger_SetsFieldCorrectly() {
        cell.setSurroundingMines(7);
        int result = cell.surroundingMines;
        assertEquals(7, result);
        cell.setSurroundingMines(2);
        result = cell.surroundingMines;
        assertEquals(2, result);
        cell.setSurroundingMines(0);
        result = cell.surroundingMines;
        assertEquals(0, result);
    }

    @Test
    void getTrueValue_ValueFromZeroToEight_ShouldReturnCorrect() {
        cell.setSurroundingMines(5);
        assertEquals("5", cell.getTrueValue());
        cell.setSurroundingMines(8);
        assertEquals("8", cell.getTrueValue());
        cell.setSurroundingMines(0);
        assertEquals("0", cell.getTrueValue());
    }

    @Test
    void getCellFace_SelectedOrdinaryCell_ShouldReturnTrueValue() {
        cell.setSurroundingMines(4);
        cell.setSelected();
        assertEquals("4", cell.getCellFace());
    }
    @Test
    void getCellFace_NotSelectedOrdinaryCell_ShouldReturnBlank() {
        cell.setSurroundingMines(4);
        assertEquals(String.valueOf('\u00A0'), cell.getCellFace());
    }
    @Test
    void getCellFace_FlaggedAndNotSelectedOrdinaryCell_ShouldReturnFlag() {
        cell.setFlagged();
        assertEquals("⚑", cell.getCellFace());
    }
    @Test
    void getCellFace_FlaggedAndSelectedOrdinaryCell_ShouldReturnTrueValue() {
        cell.setSurroundingMines(3);
        cell.setFlagged();
        cell.setSelected();
        assertEquals("3", cell.getCellFace());
    }

}
