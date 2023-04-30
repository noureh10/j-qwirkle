package g58146.qwirkle.model;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Nour
 */
public class Game {
    private Grid grid;
    private Player[] players;
    private int currentPlayer;
    /**
     * Constructor of the Game class
     * @param playersNames 
     */
    public Game(String[] playersNames){
        this.players = new Player[playersNames.length];
        for (int i=0;i<playersNames.length;i++){
            this.players[i] = new Player(playersNames[i]);
        }
        this.grid = new Grid();
        this.currentPlayer=0;  
    }
     /**
     * This method allow the player to make the first move. It selects the cards
     * from is hands in a specific order given by the is "indexes"
     * @param d The direction applied to those tiles
     * @param is The specific order
     */
    public void first(Direction d, int... is) {
        Tile[] tilesToAdd = new Tile[is.length];
        for (int i : is){
            tilesToAdd[i] = getCurrentPlayerHand().get(i);
        }
        grid.firstAdd(d, tilesToAdd);
        getCurrentPlayerHand().removeAll(Arrays.asList(tilesToAdd));
        currentPlayerRefill();
        pass();
    }
    /**
     * This method allow the player to make a move. It selects a specific card
     * from the hand and place it on a desired row and column.
     * @param row The desired row
     * @param col The desired column
     * @param index The desired card on the player hand
     */
    public void play(int row,int col,int index){
        Tile tileToAdd = getCurrentPlayerHand().get(index);
        grid.add(row, col, tileToAdd);
        getCurrentPlayerHand().remove(index);
        currentPlayerRefill();
        pass();
    }
    /**
     * This method allow the player to make one or multiples moves. It selects 
     * one or multiple cards in a specific order from the player hand. It places 
     * the tile at a specified position and continues to the desired direction
     * @param row The desired row
     * @param col The desired column
     * @param d The desired direction
     * @param is The specific order
     */
    public void play(int row,int col,Direction d,int...is){
        Tile[] tilesToAdd = new Tile[is.length];
        for (int i : is){
            tilesToAdd[i] = getCurrentPlayerHand().get(i);
        }
        grid.add(row, col, d, tilesToAdd);
        getCurrentPlayerHand().removeAll(Arrays.asList(tilesToAdd));
        currentPlayerRefill();
        pass();
    }
    /**
     * This method allow the player to make one or multiples moves. In groups of
     * 3, the "is" parameter gives the row,column and the index of the card in 
     * the current player hand. This method is used for tileAtPosition object
     * @param is gives in group of 3 the desired row, column and index of the card.
     */
    public void play(int...is){
        if(is.length%3!=0){
            throw new QwirkleException("The 'is' parameter should always have a "
                    + "length that is a multiple of 3.");
        }
        TileAtPosition[] tilesToAdd=new TileAtPosition[is.length];
        Tile[] tilesToRemove = new Tile[is.length/3];
        int tilesCounter=0;
        for (int i=0;i<is.length;i+=3){
            int row=is[i];
            int col=is[i+1];
            Tile index=getCurrentPlayerHand().get(i+2);
            tilesToAdd[tilesCounter]=new TileAtPosition(row,col,index);
            tilesToRemove[i] = index;
        }
        grid.add(tilesToAdd);
        getCurrentPlayerHand().removeAll(Arrays.asList(tilesToRemove));
        currentPlayerRefill();
        pass(); 
    }
    /**
     * This method is used to get the current player name.
     */
    public String getCurrentPlayerName(){
        return this.players[this.currentPlayer].getName();
    }
        /**
     * This method is used to get the current hand.
     */
    public List<Tile> getCurrentPlayerHand(){
        return this.players[this.currentPlayer].getHand();
    }
    /**
     * This method is used to refill the hand of the current player
     */
    private void currentPlayerRefill(){
        this.players[this.currentPlayer].refill();
    }
    /**
     * This method pass the turn to the next player
     */
    public void pass(){
        currentPlayer+=1;
        currentPlayer%= players.length;
    }
    
}
