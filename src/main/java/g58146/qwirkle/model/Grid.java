package g58146.qwirkle.model;
import java.io.Serializable;
/**
 *
 * @author Nour
 */
public class Grid implements Serializable {
    private Tile[][] tiles;
    private final int SIZE = 91;
    /**
     * Grid class constructor
     */
    public Grid() {
        this.tiles = new Tile[SIZE][SIZE];
    }
    /**
     * This method returns the tiles at the current position, returns null
     * if there is none.
     * @param row The row
     * @param col The column
     * @return returns the tile if there's one, null if there's none
     */
    public Tile get(int row,int col){
        if(isEmpty()||row<0||col<0||row>=SIZE||col>=SIZE){
            return null;
        }
        return tiles[row][col];
    }
    /**
     * This method is used to add the first tiles in the center of the grid.
     * If there are more than one tiles, it adds them up on the desired direction.
     * @param d     The direction
     * @param lines tile varargs
     */
    public int firstAdd(Direction d, Tile... lines) {
        if(!isEmpty()){
            throw new QwirkleException("First add method already used");
        }
        int CENTER = SIZE / 2;
        return addTiles(CENTER, CENTER,d,lines);
    }
    /**
     * This method is used to add a tile in the desired position
     * @param row  The row position of the first tile
     * @param col  The column position of the first tile
     * @param tile The tile
     */
    public int add(int row, int col, Tile tile) {
        if(isEmpty()){
            throw new QwirkleException("You must use the firstAdd method");
        }
        return addTiles(row,col,null,tile);
    }
    /**
     * This method is used to add a set of tile. Once in a set position, the other
     * one will follow the given direction
     * @param row   The row position of the first tile in line
     * @param col   The column position of the first tile in line
     * @param d     The direction of the tiles
     * @param lines tile varargs
     */
    public int add(int row, int col, Direction d, Tile... lines) {
        if(isEmpty()){
            throw new QwirkleException("You must use the firstAdd method");
        }
        return addTiles(row,col,d,lines);
    }
    /**
     * This method adds TileAtPosition object to the grid
     *
     * @param line TileAtPosition varargs
     * @return the score
     */
    public int add(TileAtPosition...line) {
        return addTiles(line);
    }
    /**
     * This method checks if the grid array is empty
     * @return A boolean value
     */
    public boolean isEmpty() {
        for (Tile[] tile : tiles) {
            for (int j = 0; j < tile.length; j++) {
                if (tile[j] != null) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * This method checks wether a tile can be added to the grid or not. It first narrow down the board to its smallest
     * size, then checks if the tile can be added to the board.
     * @param t The tile
     * @return A boolean value
     */
    public boolean checkPlayableMove(Tile t){
        int rowMin = SIZE, rowMax = 0;
        int colMin = SIZE, colMax = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] != null) {
                    rowMin = Math.min(rowMin, i);
                    rowMax = Math.max(rowMax, i);
                    colMin = Math.min(colMin, j);
                    colMax = Math.max(colMax, j);
                }
            }
        }
        //Adding a margin, necessary to check if the tile can be added to the board
        rowMin = Math.max(0, rowMin - 1);rowMax = Math.min(SIZE - 1, rowMax + 1);
        colMin = Math.max(0, colMin - 1);colMax = Math.min(SIZE - 1, colMax + 1);
        for (int i = rowMin; i <= rowMax; i++) {
            for (int j = colMin; j <= colMax; j++) {
                if (checkMove(i, j, t)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Adds a sequence of tiles to the game board in the specified direction, starting at the specified row and column.
     * @param row the starting row of the sequence
     * @param col the starting column of the sequence
     * @param d   the direction of the sequence
     * @param t   the sequence of tiles to add to the board
     * @return the score obtained from the move
     */
    private int addTiles(int row, int col, Direction d, Tile... t) {
        if(!checkPlayedDeck(t)){
            throw new QwirkleException("The deck you played has two tile with same color and shape attribute");
        }
        int i = 0;TileAtPosition[] tileScore=new TileAtPosition[t.length];
        if(isEmpty()){
            tiles[row][col]=t[i];
            tileScore[i]=new TileAtPosition(row,col,t[i]);
            i++;
        }for (;i<t.length;i++){
            if(i>0&&d!=null){
                row+=d.getDeltaRow();
                col+=d.getDeltaCol();
            }if(checkMove(row,col,t[i])){
                tiles[row][col]=t[i];
                tileScore[i]=new TileAtPosition(row,col,t[i]);
            }else{
                for(int j=i-1;j>=0;j--){
                    if(d!=null){
                        row-=d.getDeltaRow();
                        col-=d.getDeltaCol();
                    }
                    tiles[row][col]=null;
                    System.out.println(row);
                    System.out.println(col);
                    tileScore[j]=null;
                }
                throw new QwirkleException("Not a valid move");
            }
        }
        return computeScore(tileScore);
    }
    /**
     * Adds a sequence of TileAtPosition objects to given coordinates
     * @param t sequence of TileAtPosition to add to the board
     * @return the score obtained from the move
     */
    private int addTiles(TileAtPosition...t) {
        for (int i=0; i < t.length; i++) {
            int row = t[i].row();
            int col = t[i].col();
            Tile tile = t[i].tile();
            if (checkMove(row, col, tile)) {
                tiles[row][col]=tile;
            }else{
                for(int j=i-1;j>=0;j--){
                    row-=t[j].row();
                    col-=t[j].col();
                    tiles[row][col]=null;
                }
                throw new QwirkleException("Not a valid move");
            }
        }
        return computeScore(t);
    }
    /**
     * This method checks all the surroundings position of a tile with a loop. If it is included in the grid, is null or
     * has one and only one similar attribute as the tile, it is flagged as a valid move and increments the counter. If
     * all the position are valid and it is not surrounded by void,it returns true.
     * @param row The row
     * @param col The column
     * @param t   The tile
     * @return A boolean value
     */
    private boolean checkMove(int row, int col, Tile t) {
        if(tiles[row][col]!=null||adjascentTiles(row,col)==0){
            return false;
        }for(Direction d:Direction.values()){
            int nCol=col+d.getDeltaCol();
            int nRow=row+d.getDeltaRow();
            while((nCol>=0&&nCol<SIZE&&nRow>=0&&nRow<SIZE)&&(tiles[nRow][nCol]!=null)){
                if(tiles[nRow][nCol].shape()==t.shape()^tiles[nRow][nCol].color()==t.color()){
                    nCol+=d.getDeltaCol();nRow+=d.getDeltaRow();
                }else{
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * This method check if the played deck contains tiles with 2 same attribute. If it does, it return a false boolean.
     * @param lines The played deck
     * @return A boolean value
     */
    private boolean checkPlayedDeck(Tile...lines){
        if(lines.length>6){
            throw new QwirkleException("The deck you want to play with is bigger than 6");
        }for(int i=0;i<lines.length;i++){
            Tile curr=lines[i];
            for(int j=0;j<i;j++){
                Tile prev=lines[j];
                if(curr.color()==prev.color()&&curr.shape()==prev.shape()){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * This method is responsible of computing the score of a move. It take a sequence of TileAtPosition and check if
     * there's qwirkles or adjascent tiles. it checks all the adjascent tiles to the tiles in the sequence and return
     * the score.
     * @param t The sequence of TileAtPosition
     * @return The score
     */
    private int computeScore(TileAtPosition... t) {
        int score=0;
        boolean[][] qwkF=new boolean[SIZE][SIZE];
        boolean[][] cntX=new boolean[SIZE][SIZE];
        boolean[][] cntY=new boolean[SIZE][SIZE];
        for (TileAtPosition tile : t){ //First pass to see adjascent tiles and qwirkles
            int row=tile.row();
            int col=tile.col();
            int adjascent=adjascentTiles(row, col);
            if(adjascent<4){
                score+=(adjascent==3)?2:1;
                cntX[row][col]=(adjascent!=2);
                cntY[row][col]=(adjascent!=1);
            }else if(!qwkF[row][col]){
                score+=(adjascent==4||adjascent==5)?6:12;
                if(!(cntX[row][col] && cntY[row][col])){
                    score++;
                    if(adjascent==6){
                        score++;
                    }
                }
                cntX[row][col]=(adjascent!=5);
                cntY[row][col]=(adjascent!=4);
                qwirkleFlagger(row, col, qwkF,adjascent);
            }
        }
        for(TileAtPosition tile: t){ //Second pass to add all the adjascent tiles to the score
            for(Direction d:Direction.values()){
                int nRow=tile.row()+d.getDeltaRow();
                int nCol=tile.col()+d.getDeltaCol();
                while (nRow>=0&&nRow<SIZE&&nCol>=0&&nCol<SIZE&&tiles[nRow][nCol]!=null){
                    if(d==Direction.LEFT||d==Direction.RIGHT){
                        if(!cntX[nRow][nCol]){
                            score++;cntX[nRow][nCol]= true;
                        }
                    }else{
                        if(!cntY[nRow][nCol]){
                            score++;cntY[nRow][nCol] = true;
                        }
                    }
                    nRow+=d.getDeltaRow();nCol+=d.getDeltaCol();
                }
            }
        }
        return score;
    }
    /**
     * This method checks wheter or not there's adjascent tiles.
     * @param row The row
     * @param col The column
     * @return An integer value  to the number of adjascent tiles
     */
    private int adjascentTiles(int row, int col) {
        int vertical=0,horizontal=0;
        for(Direction d:Direction.values()){
            int nCol=col+d.getDeltaCol();
            int nRow=row+d.getDeltaRow();
            while(nCol>=0&&nCol<SIZE&&nRow>=0&&nRow<SIZE&&tiles[nRow][nCol]!=null){
                if(d==Direction.UP||d==Direction.DOWN){
                    vertical++;
                }else{
                    horizontal++;
                }
                nRow += d.getDeltaRow();
                nCol += d.getDeltaCol();
            }
        }
        if(horizontal==5&&vertical==5) return 6; // Double Qwirkle
        if(vertical==5) return 5; // Simple vertical qwirkle
        if(horizontal==5) return 4; // Simple horizontal qwirkle
        if(horizontal>0&&vertical>0) return 3; // If it has 2 adjascent tiles
        if(vertical>0) return 2; // If it has only vertical adjascent tiles
        if(horizontal>0) return 1; // If it has only horizontal adjascent tiles
        return 0;
    }
    /**
     * This method populates an array with true values if the tile surrounding a row and col position is part of a
     * qwirkle.
     * @param row the row
     * @param col the column
     * @param qwirkleFlag the qwirkleFlag array
     */
    private void qwirkleFlagger(int row, int col, boolean[][] qwirkleFlag, int adjacent) {
        qwirkleFlag[row][col]=true;
        for (Direction d:Direction.values()){
            int nCol=col+d.getDeltaCol();int nRow=row+d.getDeltaRow();
            while(nCol>=0&&nCol<SIZE&&nRow>=0&&nRow<SIZE&&tiles[nRow][nCol]!=null){
                switch(adjacent){
                    case 4: if(d==Direction.LEFT||d==Direction.RIGHT){
                        qwirkleFlag[nRow][nCol] = true;
                    }break;
                    case 5:
                        if(d==Direction.UP||d==Direction.DOWN){
                            qwirkleFlag[nRow][nCol]=true;
                        }break;
                    default:
                        qwirkleFlag[nRow][nCol] = true;
                        break;
                }
                nRow += d.getDeltaRow();
                nCol += d.getDeltaCol();
            }
        }
    }
}