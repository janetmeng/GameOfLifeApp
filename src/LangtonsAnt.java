public class LangtonsAnt {

    private int row; //of ant
    private int column; //of ant
    private String direction; //of ant


    public LangtonsAnt(int row, int column, String direction){
        this.row = row;
        this.column = column;
        this.direction = direction;
    }

    private void orientation(Cell[][] cells){
        CellState oldState = cells[row][column].getCellState();
        int c=0;
        int r=0;

        if (direction.equals("Up") && oldState == CellState.ALIVE) { //black square -- reorients the ant
            c = -1;
            direction = "Left";
        } else if (direction.equals("Up") && oldState == CellState.DEAD){ //white square
            c = 1;
            direction = "Right";
        } else if (direction.equals("Right") && oldState == CellState.ALIVE) { //black square
            r = -1;
            direction = "Up";
        } else if (direction.equals("Right") && oldState == CellState.DEAD){ //white square
            r = 1;
            direction = "Down";
        } else if (direction.equals("Down") && oldState == CellState.ALIVE){ //black square
            c = 1;
            direction = "Right";
        } else if (direction.equals("Down") && oldState == CellState.DEAD){ //white square
            c = -1;
            direction = "Left";
        } else if (direction.equals("Left") && oldState == CellState.ALIVE){ //black square
            r= 1;
            direction = "Down";
        } else if (direction.equals("Left") && oldState == CellState.DEAD) { //white square
            r = -1;
            direction = "Up";
        }
        evolve(cells); //changes oldState's color
        column += c;
        row += r;
    }

    public void applyRules(Cell[][] cells){ // rotating CW/CCW & moving one space forward based on current cell's state
        orientation(cells);
    }

    private void evolve(Cell[][] cells) { //switching color at the end
        if (cells[row][column].getCellState() == CellState.ALIVE){
            cells[row][column].setCellState(CellState.DEAD);
        }
        else if (cells[row][column].getCellState() == CellState.DEAD){
            cells[row][column].setCellState(CellState.ALIVE);
        }
    }

    public void display(Cell[][] cells){
        GameOfLifeApp app = GameOfLifeApp.getApp(); //setting the board to red
        Cell antCell = cells[row][column];
        app.fill(255,0,0); //affecting the antCell- telling line 64 to draw a rectangle in red
        app.stroke(255,0,0);
        app.rect(antCell.getX(), antCell.getY(), antCell.getSize(), antCell.getSize()); //draws a rectangle at the antCell location that's red
    }
}
