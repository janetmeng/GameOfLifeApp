import processing.core.PApplet;
import java.util.Random;

/**
 * This class is the PApplet extension (this is the PApplet subclass) and is what runs the program
 */
public class GameOfLifeApp extends PApplet{
    private static GameOfLifeApp app; //reference to ourself
    private Cell[][] cells; // has-a relationship. PApplet has a "cells", which is a 2d array of type Cell (instances of Cell class)
    private boolean evolve;
    private static final int CELL_SIZE = 10; //change this to something else (everything should still work, assumign the size-1000 is a multiple of cell_size)
    private Rules rules;
    private LangtonsAnt ant;
    private static final Random RANDOM = new Random();
    private boolean isAnt;

    /**
     * This method creates a GameOfLifeApp object and runs a sketch to start the program.
     * @param args  sequence of Strings passed into the program
     */
    public static void main(String[] args){
        app = new GameOfLifeApp(); //assigning new instance of GameOfLifeApp to this instance variable "app", instantiation
        app.runSketch(); // sketch brings out the window, tell it to run
    }

    /**
     * this constructor instantiates GameOfLifeApp objects. Since evolve is the only instance variable in this class, it is the
     * only variable we instantiate it in the constructor.
     */
    public GameOfLifeApp(){
        evolve = false;
        isAnt = false;
    }

    /**
     * This method sets the size of the canvas to
     * predetermined width and height instance
     * variables.
     */
    @Override
    public void settings() {
        super.settings();
        size(1000, 500);
    }

    /**
     * This method sets up the board and assigns the rules that the app will start with. In addition to assigning the rules, it
     * creates all the cells in the cells array (the board)
     */
    @Override
    public void setup() {
        super.setup();
        rules = new MooreRules(new int[]{3}, new int[]{2, 3}); //B3/S23 - Game of Life
        cells = new Cell[height/CELL_SIZE][width/CELL_SIZE];
        //frameRate(10);
        //in a nested loop, instantiate Cell objects and insert into cells (the 2d arrays)
        for (int i = 0; i<cells.length; i++){
            for (int j = 0; j<cells[i].length; j++){
                if (i==0 || j==0 || i==cells.length-1 || j==cells[i].length-1){
                    //border
                    Cell c = new Cell(j*CELL_SIZE, i*CELL_SIZE, CELL_SIZE, i, j, CellState.DEAD);
                    cells[i][j] = c;
                }else {
                    Cell c = new Cell(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, i, j, CellState.DEAD); //CellState.randomState()
                    cells[i][j] = c;
                }
            }
        }
    }

    /**
     * If the boolean evolve is true, this method calls the evolve method to
     * proceed to the next stage of life.
     * This method then calls the display() method to display the updated cells.
     */
    @Override
    public void draw() { //controls display & evolution
        if (isAnt){
            ant.applyRules(cells);
        } else {
            if (evolve == true) {
                applyRules(); //goes through all the cells and calculate their next state in the next life
                evolve(); //once they've calculated it and saved it in their instance variables, they evolve
            }
             //draw themselves again (now they look diff)
            // displaying Cell objects
        }
        display();
        if (isAnt){
            ant.display(cells);
        }
    }

    /**
     * In this method, we figure out where the mouse was clicked, and ask the cell (Cell object) to handle clicks (aka
     * turn itself to the opposite state (alive or dead)
     */
    @Override
    public void mouseClicked() { // having Cell object handle clicks
        super.mouseClicked();
        int col = mouseX/CELL_SIZE; // mouse X goes horizontally so it's telling you what column it goes in. if mouseX is 450, then the cell it's in is in the 45th column. if you move to mouseX 600, it's in the 60th column
        int row = mouseY/CELL_SIZE;
        cells[row][col].handleClick();
    }

    /**
     * If key 'e' is pressed, this method pauses or starts evolution by switching
     * boolean evolve.
     * If key '1' is pressed, this method switches rules of the game to Replicator
     * (B1357/S1357)
     * If key '2' is pressed, this method switches rules of the game to Seeds (B2/S)
     * If key '3' is pressed, this method switches rules of the game to Life Without
     * Death (B3/S012345678)
     * If key '4' is pressed, this method switches rules of the game to Day and Night
     * (B3678/S34678)
     */

    @Override
    public void keyPressed() {
        super.keyPressed();
        // pausing and restarting Cell evolution
        if (key == ' '){
            evolve = !evolve;
        } else{
            if (key == '7'){
                ant = new LangtonsAnt(25, 50, "Right");
                isAnt = true;
            }
            else {
                if (key == '1'){
                    rules = new MooreRules(new int[]{1, 3, 5, 7}, new int[]{1, 3, 5, 7});
                } else if (key == '2'){
                    rules = new MooreRules(new int[]{2}, new int[]{});
                } else if (key == '3'){
                    rules = new MooreRules(new int[]{3}, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
                } else if (key == '4'){
                    rules = new MooreRules(new int[]{3, 6, 7, 8}, new int[]{3, 4, 6, 7, 8});
                } else if (key == '5'){
                    rules = new MooreRules(new int[]{3, 6, 8}, new int[]{2, 4, 5});
                } else if (key == '6'){
                    rules = new MooreRules(new int[]{4, 6, 7, 8}, new int[]{3, 5, 6, 7, 8});
                }
                isAnt = false;
            }

        }
    }



    /**
     * This method is used when the Cell needs to do things that are related to Processing. In order to call a
     * Processing method, it needs to call an instance of PApplet
     * @return This method returns app, an instance of GameOfLifeApp, and therefore an instance of PApplet
     */
    public static GameOfLifeApp getApp(){
        return app;
    }

    //This method calls the applyRules() method in the Cell class
    //to apply the rules to all of the cell objects on the screen. It iterates through
    // the entire board to call the applyRules() method in the Cell class on
    // all the cell objects.
    private void applyRules(){
        for (int i=1; i<cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                cells[i][j].applyRules(cells, rules);
            }
        }
    }

    // After the cells have gone through applyRules and figured out what their next state is based on the rules and their live
    // neighbors, this method runs through each cell on the board and tells it to evolve (change to it's next state of Alive or Dead)
    private void evolve(){
        for (int i=1; i<cells.length-1; i++){
            for (int j=1; j<cells[i].length-1; j++){
                cells[i][j].evolve();
            }
        }
    }

    // This method calls the display() method in the Cell class
    // to display all of the cell objects on the screen. It iterates
    // over the entire board and calls display() on each cell object.
    private void display(){
        for (int i = 1; i<cells.length-1; i++){
            for (int j = 1; j<cells[i].length-1; j++) {
                cells[i][j].display();
            }
        }
    }
}