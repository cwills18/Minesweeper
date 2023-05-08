
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MineTest {
    static Mine cell;

    public MineTest() {
    }

    @BeforeEach
    void setUpCell() {
        cell = new Mine(1);
    }

    @Test
    void isMine_MineCell_ShouldReturnTrue() {
        Assertions.assertEquals(true, cell.isMine());
    }

    @Test
    void isSelected_MineCellDefault_ShouldBeFalse() {
        Assertions.assertEquals(false, cell.isSelected());
    }

    @Test
    void setSelected_SetToTrue_ShouldCorrectlyUpdateCell() {
        cell.setSelected();
        Assertions.assertEquals(true, cell.isSelected());
    }

    @Test
    void isFlagged_OrdinaryCell_DefaultShouldBeFalse() {
        Assertions.assertEquals(false, cell.isFlagged);
    }

    @Test
    void setFlagged_SetToTrue_ShouldUpdateField() {
        cell.setFlagged();
        Assertions.assertEquals(true, cell.isFlagged);
    }

    @Test
    void getTrueValue_MineCell_ShouldReturnCorrect() {
        Assertions.assertEquals("⛯", cell.getTrueValue());
    }

    @Test
    void getCellFace_SelectedMineCell_ShouldReturnMine() {
        cell.setSelected();
        Assertions.assertEquals("⛯", cell.getCellFace());
    }

    @Test
    void getCellFace_NotSelectedMineCell_ShouldReturnBlank() {
        Assertions.assertEquals(String.valueOf('\u00A0'), cell.getCellFace());
    }

    @Test
    void getCellFace_FlaggedAndNotSelectedMineCell_ShouldReturnFlag() {
        cell.setFlagged();
        Assertions.assertEquals("⚑", cell.getCellFace());
    }

    @Test
    void getCellFace_FlaggedAndSelectedMineCell_ShouldReturnMineValue() {
        cell.setFlagged();
        cell.setSelected();
        Assertions.assertEquals("⛯", cell.getCellFace());
    }
}
