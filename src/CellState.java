import java.util.Random;

/**
 * This enum stores a list of constants that are used as "states" of life in the game. It is a combination of a class,
 * methods, variables, objects, and constants that belong together.
 */
public enum CellState {
    ALIVE,
    DEAD,
    WILL_DIE,
    WILL_REVIVE;

    private static final CellState[] STATES = {ALIVE, DEAD};

    private static final Random RANDOM = new Random();

    /**
     * This method returns a random CellState.
     * @return random CellState
     */
    public static CellState randomState(){
        return STATES[RANDOM.nextInt(2)];
    }
}
