import processing.core.PApplet;

// this class is going to be the PApplet extension
public class GameOfLifeApp extends PApplet{
    private static GameOfLifeApp app;
    private Cell[][] cells; // has-a relationship. PApplet has a "cells", which is a 2d array of type Cell (instances of Cell class)

    public static void main(String[] args){
        PApplet.main("GameOfLifeApp");
    }

    public GameOfLifeApp(){
        app = this; // this refers back to the object that was created in memory. somewhere behind the scenes there's a creation of a new GameOfLifeApp (GameOfLifeApp newapp = new GameOfLifAapp()) so it's being made in the computers memory. "this" refers to that.
    }

    @Override
    public void settings() {
        super.settings();
        size(1000, 500);
    }

    @Override
    public void setup() {
        super.setup();
        // instantiating Cell objects
    }

    @Override
    public void draw() {
        super.draw();
        // displaying Cell objects
    }

    @Override
    public void mouseClicked() {
        super.mouseClicked();
        // having Cell object handle clicks
    }

    @Override
    public void keyPressed() {
        super.keyPressed();
        // pausing and restarting Cell evolution
    }

    public static GameOfLifeApp getApp(){
        return app;
    }
}