package g58146.qwirkle.model;
/**
 *
 * @author Nour
 */
public class Grid {
    private Tile[][] tiles;
    private boolean isEmpty;
    private final int SIZE=91;    
    /*
    * Constructor of the grid class
    */
    public Grid(){
        this.tiles = new Tile[SIZE][SIZE];
        this.isEmpty = true;
    }
    /**
     * This method returns the tiles at the current position, returns null
     * if there is none.
     * @param row The row
     * @param col The column
     * @return returns the tile if there's one, null if there's none
     */
    public Tile get(int row,int col){
        if(row<0||col<0){
            return null;
        }
        return tiles[row][col];
    }
    /**
     * This method is used to add the first tiles in the center of the grid.
     * If there are more than one tiles, it adds them up on the desired direction.
     * @param d The direction
     * @param lines tile varargs
     */
    public void firstAdd(Direction d, Tile...lines){
        int x = 45, y = 45;
        if(tiles[x][y]!=null){
                throw new QwirkleException("First add method already used");
        }
        if(!checkPlayedDeck(lines)){
            throw new QwirkleException("The deck you played has two tile"
                    + "with same color and shape attribute");
        }
        tiles[x][y] = lines[0];
        this.isEmpty = false;
        for (int i = 1; i < lines.length; i++){
            x+=d.getDeltaRow();
            y+=d.getDeltaCol();
            if(checkMove(x,y,lines[i])){
                tiles[x][y] = lines[i];
            }else{
                throw new QwirkleException("Not a valid move");
            }
        }
    }
    /**
     * This method is used to add a tile in the desired position
     * @param row The row position of the first tile
     * @param col The column position of the first tile
     * @param tile The tile
     */
    public void add(int row,int col,Tile tile){
        if(row==45&&col==45){
            throw new QwirkleException("You must use the firstAdd method");
        }
        if(!checkPlayedDeck(tile)){
            throw new QwirkleException("The deck you played has two tile"
                    + "with same color and shape attribute");
        }
        if(checkMove(row,col,tile)){
            tiles[row][col]=tile;
        }else{
            throw new QwirkleException("Not a valid move");
        }
    }
    /**
     * This method is used to add a set of tile. Once in a set position, the other
     * one will follow the given direction
     * @param row The row position of the first tile in line
     * @param col The column position of the first tile in line
     * @param d The direction of the tiles
     * @param lines tile varargs
     */
    public void add(int row,int col,Direction d, Tile...lines){
        if(row==45&&col==45){
                throw new QwirkleException("You must use the firstAdd method");
        }
        if(!checkPlayedDeck(lines)){
            throw new QwirkleException("The deck you played has two tile"
                    + "with same color and shape attribute");
        }
        for (int i = 0; i < lines.length; i++){
            row += d.getDeltaRow();
            col += d.getDeltaCol();
            if(checkMove(row,col,lines[i])){
                tiles[row][col] = lines[i];
            }else{
                throw new QwirkleException("Not a valid move");
            }
        }
    }
    /**
     * This method is used to add a set of tile on a specified position. Once
     * in a set position, the other tile will follow the given coordinates.
     * @param line The set of tile
     */
    public void add(TileAtPosition...line){
        if(!checkPlayedDeck(line)){
            throw new QwirkleException("The deck you played has two tile"
                    + "with same color and shape attribute");
        }
        for (TileAtPosition t : line){
            int row=t.row(),col=t.col();
            if(tiles[row][col]!=null){
                throw new QwirkleException("Piece already present"
                        + "at that position");
            }if(checkMove(row,col,t)){
                tiles[row][col] = new Tile(t.tile().color(),t.tile().shape());
            }else{
                throw new QwirkleException("Not a valid move");
            }
        }
    }
    /**
     * This method checks if the grid array is empty
     * @return A boolean value
     */
    public boolean isEmpty(){
        return this.isEmpty;
    }
    /**
     * This method checks all the surroundings position of a tile with a loop.
     * If it is included in the grid, is null or has one and only one similar
     * attribute as the tile, it is flagged as a valid move and increments
     * the counter. If all the position are valid and it is not surrounded by
     * void,it returns true.
     * @param row The row
     * @param col The column
     * @param t The tile
     * @return A boolean value
     */
    private boolean checkMove(int row, int col, Object t){
        if(tiles[row][col]!=null){
            return false;
        }
        for(Direction d:Direction.values()){
            int nCol=col+d.getDeltaCol();
            int nRow=row+d.getDeltaRow();
            Tile curr=(t instanceof Tile)?(Tile) t:
                    new Tile(((TileAtPosition)t).tile().color(),
                           ((TileAtPosition) t).tile().shape());
            while((nCol>=0&&nCol<SIZE&&nRow>=0&&nCol<SIZE)
                &&(tiles[nRow][nCol]!=null)){
                    if(tiles[nRow][nCol].shape()== curr.shape()
                        ^tiles[nRow][nCol].color()== curr.color()){
                            nCol+=d.getDeltaCol();
                            nRow+=d.getDeltaRow();
                    }else{
                        return false;
                    }
            }
        }
        return true;
    }
     /**
     * This method check if the played deck contains tiles with 2 same attribute.
     * If it does, it return a false boolean.
     * @param lines The played deck
     * @return A boolean value
     */
    private boolean checkPlayedDeck(Object...lines){
        if(lines.length>6){
            throw new QwirkleException("The deck you want to play with is"
                    + "bigger than 6");
        }
        for(int i=0;i<lines.length;i++){
            // Ternary operation to either cast the object as line or create
            // a new Tile object with the object attribute
            Tile curr=(lines[i] instanceof Tile)?(Tile) lines[i]:
                    new Tile(((TileAtPosition)lines[i]).tile().color(),
                           ((TileAtPosition) lines[i]).tile().shape());
             for(int j=0;j<i;j++){
                Tile prev=(lines[j] instanceof Tile)?(Tile) lines[j]:
                    new Tile(((TileAtPosition)lines[j]).tile().color(),
                           ((TileAtPosition) lines[j]).tile().shape());
                if(curr.color()==prev.color()
                   &&curr.shape()==prev.shape()){
                        return false;
                }
            }
        }
        return true;
    }
}