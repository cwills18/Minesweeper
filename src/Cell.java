import java.util.Arrays;

public abstract class Cell {

    private int identifier;
    private boolean isMine;
    protected String trueValue;
    private boolean isSelected;
    private boolean isFlagged;


    protected Cell(int identifier, boolean isMine, String trueVal){
        this.identifier = identifier;
        this.isMine = isMine;
        this.trueValue = trueVal;
        this.isSelected = false;
        this.isFlagged = false;
    }

    protected boolean isMine() {
        return this.isMine;
    }
    protected String getTrueValue(){
        return this.trueValue;
    };
    private void setTrueValue(String value){
        this.trueValue = value;
    }
    protected boolean isSelected(){
        return this.isSelected;
    }
    protected void setSelected(){
        this.isSelected = true;
    }
    protected void setFlagged() {
        this.isFlagged = true;
    }


    //this is the method that will be called when the board is printing
    protected String getCellFace() {
        if (this.isFlagged && this.isSelected){
            return this.trueValue;
        } else if (this.isFlagged){
            //flag
            return String.valueOf('\u2691');
        } else if (this.isSelected){
            //mine icon if mine, or number of surrounding bombs if ordinary cell
            return this.trueValue;
        } else {
            //whitespace
            return String.valueOf('\u00A0');
        }
    }

    //this method will set the surrounding mine number for normal cells only
    protected void setSurroundingMines(int numMines) {
        if (!this.isMine){
            this.trueValue = Integer.toString(numMines);
        }
    }

}
