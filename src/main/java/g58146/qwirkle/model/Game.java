package g58146.qwirkle.model;
import java.util.List;
import java.io.*;
/**
 *
 * @author Nour
 */
public class Game implements Serializable {
    private Grid grid;
    private Player[] players;
    private int currentPlayer;
    /**
     * Game class constructor
     * @param playersNames The names of the players
     */
    public Game(String[] playersNames) {
        this.players = new Player[playersNames.length];
        for (int i = 0; i < playersNames.length; i++) {
            this.players[i] = new Player(playersNames[i]);
            this.players[i].refill();
        }
        this.grid = new Grid();
        this.currentPlayer = 0;
    }
    /**
     * This method allow the player to make the first move. It selects the cards from is hands in a specific order given
     * by the is "indexes"
     * @param d The direction applied to those tiles
     * @param is The specific order
     */
    public void first(Direction d, int...is){
        Tile[] tilesToAdd = getTiles(is);
        int score=grid.firstAdd(d,tilesToAdd);
        nextTurn(score,tilesToAdd);
    }
    /**
     * This method allow the player to make a move. It selects a specific card from the hand and place it on a desired
     * row and column.
     * @param row The desired row
     * @param col The desired column
     * @param index The desired card on the player hand
     */
    public void play(int row,int col,int index){
        Tile tileToAdd = getTiles(index)[0];
        int score=grid.add(row, col, tileToAdd);
        nextTurn(score,tileToAdd);
    }
    /**
     * This method allow the player to make one or multiples moves. It selects one or multiple cards in a specific order
     * from the player hand. It places the tile at a specified position and continues to the desired direction
     * @param row The desired row
     * @param col The desired column
     * @param d The desired direction
     * @param is The specific order
     */
    public void play(int row,int col,Direction d,int...is){
        Tile[] tilesToAdd = getTiles(is);
        int score=grid.add(row, col, d, tilesToAdd);
        nextTurn(score,tilesToAdd);
    }
    /**
     * This method allow the player to make one or multiples moves. In groups of 3, the "is" parameter gives the row,
     * column and the index of the card in the current player hand. This method is used for tileAtPosition object
     * @param is gives in group of 3 the desired row, column and index of the card.
     */
    public void play(int...is){
        if(is.length%3!=0){
            throw new QwirkleException("The 'is' parameter should always have a "
                    + "length that is a multiple of 3.");
        }
        TileAtPosition[] tilesToAdd=new TileAtPosition[is.length/3];
        Tile[] tilesToRemove = new Tile[is.length/3];
        for (int i=0,j=0;i<is.length;i+=3,j++){
            int row=is[i];
            int col=is[i+1];
            Tile index=getCurrentPlayerHand().get(is[i+2]);
            tilesToAdd[j]=new TileAtPosition(row,col,index);
            tilesToRemove[j] = index;
        }
        int score=grid.add(tilesToAdd);
        nextTurn(score,tilesToRemove);
    }
    /**
     * This method is used to see if the game is over. It checks if the bag is empty. If it is, it checks one of the
     * player hand is empty. If it is, it adds 6 points to the player score and returns true. If it is not, it checks
     * if all the players have at least one valid move. If it does, it returns false. If it does not, it returns true.
     * @return true if the game is over, false if it is not.
     */
    public boolean isOver() {
        if(Bag.getInstance().size()==0){
            for(Player p : players) {
                if(p.getHand().isEmpty()) {
                    p.addScore(6);
                    return true;
                }else{
                    boolean hasValidMove=false;
                    for(Tile t:p.getHand()){
                        if(grid.checkPlayableMove(t)){
                            hasValidMove=true;
                            break;
                        }
                    }if(hasValidMove){
                        return false;
                    }
                }
            }
            return true; // All players have no possible moves and the bag is empty
        }
        return false; // Bag is not empty yet
    }
    /**
     * This method pass the turn to the next player
     */
    public void pass(){
        this.currentPlayer+=1;
        this.currentPlayer%= this.players.length;
    }
    /**
     * This method renews the person hands and pass its turn
     */
    public void renew(){
        this.players[this.currentPlayer].remove(getCurrentPlayerHand().toArray(Tile[]::new));
        this.players[this.currentPlayer].refill();
        pass();
    }
    /**
     * This method is used to get the card from the player hand at indicated indexes
     * @param is the indexes of the cards
     * @return the cards at the indicated indexes
     */
    private Tile[] getTiles(int...is){
        Tile[] tilesToAdd=new Tile[is.length];
        for(int i=0;i<is.length;i++){
            tilesToAdd[i]=getCurrentPlayerHand().get(is[i]);
        }
        return tilesToAdd;
    }
    /**
     * This method is used to increment the score of the player, remove the tiles from the player hand, refill if
     * necessary and pass the turn to the next player.
     * @param score The score to add to the player
     * @param t The tiles to remove from the player hand
     */
    private void nextTurn(int score,Tile...t){
        players[currentPlayer].addScore(score);
        players[currentPlayer].remove(t);
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
     * This method is used to get the current player.
     */
    public Player getCurrentPlayer(){
        return this.players[this.currentPlayer];
    }
    /**
     * This method is used to refill the hand of the current player.
     */
    private void currentPlayerRefill(){
        this.players[this.currentPlayer].refill();
    }
    /**
     * This method is used to get the current player score.
     */
    public void getCurrentPlayerScore(){
        this.players[this.currentPlayer].getScore();
    }
    /**
     * This method is used to get the current state of the grid.
     */
    public GridView getGrid() {
        return new GridView(this.grid);
    }

    public Player[] getPlayers() {
        return players;
    }
}
