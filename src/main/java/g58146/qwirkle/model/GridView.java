package g58146.qwirkle.model;
/**
 * The GridView class represents a view of a Qwirkle game grid.
 * @author Nour
 */
public class GridView{
    private Grid grid;
    /**
     * Constructor of the grid view class
     */
    public GridView(Grid grid){
        this.grid = grid;
    }
    /**
     * Returns a tile from a desired row and column
     */
    public Tile get(int row,int col){
        return grid.get(row, col);
    }
    /**
     * Checks if the grid is empty
     */
    public boolean isEmpty(){
        return grid.isEmpty();
    }
}
