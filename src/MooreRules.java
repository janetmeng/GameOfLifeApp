/**
 * This class applies Moore Rules to the cells in the Game of Life. It extends the
 * abstract class Rules and implements the two abstract methods shouldBeBorn and
 * shouldSurvive.
 */
public class MooreRules extends Rules{
    private static final int NUM_NEIGHBORS = 9;
    private boolean[] birthRules;
    private boolean[] survivalRules;

    /**
     * This constructor initializes and populates a birthRules boolean array and a survivalRules boolean array based on the rules.
     * This constructor also populates the birthRules with booleans which correspond to the birthNeighbors and survivalNeighbors (which are
     * determined by the rules that the user chooses to play the game with).
     * @param birthNeighbors In each set of rules, there is a list of liveNeighbors for which a cell can be born. When you instantiate
     *                       MooreRules objects, the specified values are passed in as an int array (birthNeighbors).
     * @param survivalNeighbors In each set of rules, there is a list of liveNeighbors for which a cell can survive. When you instantiate
     *                          MooreRules objects, the specified values are passed in as an int array (survivalNeighbors).
     */
    public MooreRules(int[] birthNeighbors, int[] survivalNeighbors){
        super();
        birthRules = new boolean[NUM_NEIGHBORS]; // making an array of 9 elements which all start out as false. then we're going to change birthRules[3] (for example) to true, and same for survival rules
        survivalRules = new boolean[NUM_NEIGHBORS];

        for(int neighbors: birthNeighbors){
            birthRules[neighbors] = true; //boolean at 3 becomes true her, since B3/S23 is a set of moorerules which researchers found work for these rules
        }
        for (int neighbors: survivalNeighbors){
            survivalRules[neighbors] = true; // boolean at indices 2 and 3 become true;
        }
    }

    /**
     * This method returns a boolean value true if the
     * cell has enough live neighbors to be born, and
     * a boolean value false if the cell does not
     * have enough live neighbors to be born.
     * @param liveNeighbors   the number of live
     *                        neighbors a cell has.
     * @return                boolean value for
     *                        whether the cell should be born in the next generation.
     */
    public boolean shouldBeBorn(int liveNeighbors){
        //implement later, # of LiveNeighbors corresponds with the index into the array. that index tells you if it's true or false
        return birthRules[liveNeighbors];
        //should be born if the number of liveNeighbors is 2
    }

    /**
     * This method determines whether a cell should survive based on the number of liveNeighbors around it.
     * @param liveNeighbors This parameter is passed so that the function can index into the survivalRules array to see whether
     *                      there is a true or false at that index. This array was populated in the constructor based on the birthNeighbors
     *                      and survivalNeighbors passed.
     * @return This method returns a boolean of true or false, which dictates whether the cell should survive in the next evolution.
     */
    public boolean shouldSurvive(int liveNeighbors){
        return survivalRules[liveNeighbors];
    }
}
