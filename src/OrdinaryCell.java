public class OrdinaryCell extends Cell{

    protected int surroundingMines;


    protected OrdinaryCell(int identifier){
        super(identifier, false, " ");
    }

    @Override
    public void setSurroundingMines(int numMines) {
        this.surroundingMines = numMines;
        super.trueValue = Integer.toString(numMines);
    }

}
