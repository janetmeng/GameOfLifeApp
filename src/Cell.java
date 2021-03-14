/**
 * The Cell class encapsulates behavior for Cell objects, of which there are many. It contains variables, a constructor,
 * and methods.
 */
public class Cell {
    private final int x; //upper left x (of rectangle/square)
    private final int y; //upper left y
    private final int size; //both width and height of the square
    private CellState cellState; // if it's alive or death, if it's going to be alive or death in the next evolution
    private final int row;
    private final int column;

    /**
     * This method creates a Cell object
     * @param x             the x coordinate of the cell
     * @param y             the y coordinate of the cell
     * @param size          the size of the cell
     * @param row           the row of the cells[] array that the cell is in
     * @param column           the column of the cells[] array that the cell is in
     * @param cellState     the cellState of the cell
     */
    public Cell(int x, int y, int size, int row, int column, CellState cellState){
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.cellState = cellState;
    }

    /**
     * This method tells a cell to draw itself at the x and y (row and column) it is at by filling it with a certain
     * color that represents dead or alive.
     */
    public void display(){
        GameOfLifeApp app = GameOfLifeApp.getApp();
        if (cellState == CellState.ALIVE){
            app.fill(127,66,142);
            app.stroke(255);
        } else if (cellState == CellState.DEAD) {
            app.fill(255);
            app.stroke(127,66,142);
        }
        app.rect(x, y, size, size);
    }

    /**
     * This method switches the cellState of the cell
     * If the cell is ALIVE, this method switches its cellState to DEAD.
     * If the cell is DEAD, this method switches its cellState to ALIVE.
     */
    public void handleClick(){
        if (cellState == CellState.ALIVE){
            cellState = CellState.DEAD;
        } else{
            cellState = CellState.ALIVE;
        }
    }

    /**
     * This method tells a Cell object to count it's live neighbors, and apply the rules chosen by the user.
     * The state that is determined by the applying of the rules is the Cell's new cellState.
     * @param cells This parameter says that the method will pass in the entire board; however, it is only
     *              needed because in the countLiveNeighbors method, it needs access to a cell's neighbors.

     */
    public void applyRules(Cell[][] cells, Rules rules){ // the little 3*3 grid surrounding the cell is passed in by the PApplet (PApplet says: look at your immediate neighbors and decide if you will die or live)
        int liveNeighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, liveNeighbors); //encapsulated rule behavior in Rules class
    }

    /**
     * This method determines the cellState of the cell after the
     * rules have been applied.
     * If the cell WILL_DIE, the cellState becomes DEAD.
     * If the cell WILL_REVIVE, the cellState becomes ALIVE.
     * This method then calls the display() method to display the
     * evolved cells
     */
    public void evolve(){ // the moment when PApplet says "you've calculated your next move (live or die), now move"
        if (cellState == CellState.WILL_REVIVE){ //if you're dead but you should be born, you're going to be alive in the next evolution
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE) {
            cellState = CellState.DEAD;
        }
        //info is in will_revive and will_die cellestate - if cellstate is alive (currently alive to alive) or dead, that's what it's next cellstate is going to be. if it's willrevive (special state for something that is dead and needs to be alive) or willdie (kill it), you have to convert them
    }


     //This method determines how many of the 8 neighbors around a cell are alive.
     // cells: This parameter takes in the whole board, but it is only needed so the given cell can access it's 8 neighbors to
     //     check their state (alive/dead)
     // return: This method returns an integer that represents how many live neighbors a given cell has.
    private int countLiveNeighbors(Cell[][] cells){ //how many of the 8 neighbors around it. index into the cells 2d array to get at the cells above it, below it, 4 corners
        //a neighbor is alive if it is in cellstate alive or it's in will die, if it's in the latter, by the time this cell gets to looking at it, it won't have died YET. so it'll still be alive.
        //have to let every single cell figure out how they will evolve (next step) before they move
        int counter = 0;
        for (int i = -1; i<2; i++) {
            for (int j = -1; j < 2; j++) {
                if (cells[row+i][column+j].cellState == CellState.ALIVE || cells[row+i][column+j].cellState == CellState.WILL_DIE) {
                    counter++;
                }
            }
        }
        if (this.cellState == CellState.ALIVE) { // no need to consider willdie because it is not in the transitional state yet (so it can only be alive or dead)
            counter = counter - 1;
        }
        return counter;
    }

    public CellState getCellState(){
        return cellState;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return size;
    }

    public void setCellState(CellState cellState){
        this.cellState = cellState;
    }
}
