/**
 * This abstract class provides a method and template for how to apply the Game Of Life rules.
 * In order to extend this class, classes must implement the shouldBeBorn and shouldSurvive
 * methods.
 */
public abstract class Rules { //so general that it can't be instantiated. but it invites other classes to subclass from it that do make sense if they instantiate it
    public abstract boolean shouldBeBorn(int liveNeighbors);
    public abstract boolean shouldSurvive(int liveNeighbors); // no implementation. forces subclasses to override this.

    /**
     * This method applies the rules on a cell based on its current cellState and the number of live neighbors it has (it must be called on
     * a Rules object in the Cell class but it ultimately get returned back into a Cell object's variable "cellState").
     * @param cellState This parameter is passed into the method so that the method can check whether the number of live neighbors it has
     *                  will change or maintain its current cell state.
     * @param liveNeighbors This parameter is passed into the method so that the method can use shouldBeBorn and shouldSurive to see if
     *                      a cell should be born or should survive given the number of live neighbors it has.
     * @return This method returns a cell state of will_die, will_revive, dead, or alive so that the cell knows what it will have to do in
     * the next evolution
     */
    public CellState applyRules(CellState cellState, int liveNeighbors){
        if (cellState == CellState.DEAD && shouldBeBorn(liveNeighbors)){ //if you're dead but you should be born, you're going to be alive in the next evolution
            return CellState.WILL_REVIVE;
        } else if (cellState == CellState.ALIVE && shouldSurvive(liveNeighbors) == false){ //if you were alive and had too many neighbors, you die of overpopulation. if you have too few neighbors, you die of starvation
            return CellState.WILL_DIE;
        } else{
            return cellState;
        }
    }
}

