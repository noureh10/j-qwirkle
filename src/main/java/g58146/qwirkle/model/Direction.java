package g58146.qwirkle.model;
/**
 * Direction enumeration
 * @author Nour
 */
public enum Direction {
    LEFT(0,-1),RIGHT(0,1),UP(-1,0),DOWN(1,0);
    private int deltaRow;
    private int deltaCol;
    /**
     * Direction constructor
     */
    private Direction(int deltaRow, int deltaCol){
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }
    /**
     * Getter for the deltaRow attribute
     */
    public int getDeltaRow(){
        return deltaRow;
    }
    /**
     * Getter for the deltaCol attribute
     */
    public int getDeltaCol(){
        return deltaCol;
    }
    /**
     * This method returns the opposite direction
     * @return The opposite direction
     */
    public Direction opposite(){
        return this == LEFT ? RIGHT:
               this == RIGHT ? LEFT:
               this == UP ? DOWN:
               UP;
    }
}