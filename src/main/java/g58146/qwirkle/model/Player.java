package g58146.qwirkle.model;
import java.io.Serializable;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Nour
 */
public class Player implements Serializable {
    private String name;
    private List<Tile> tiles;
    private int score;
    /**
     * Constructor of the player class.
     */
    public Player(String name) {
        this.name=name;
        this.tiles=new ArrayList<>();
        this.score=0;
    }
    /**
     * Getter for the name attribute
     */
    public String getName() {
        return name;
    }
    /**
     * Getter for the hand attribute
     * @return returns an unmodifiable list
     */
    public List<Tile> getHand() {
        return Collections.unmodifiableList(tiles);
    }
    /**
     * Getter for the score attribute
     * @return returns the score
     */
    public int getScore() {
        return score;
    }
    /**
     * This method refills the player hand
     */
    public void refill(){
        List<Tile> t = Bag.getInstance().getRandomTiles(6-tiles.size());
        System.out.println();
        tiles.addAll(t);
    }
    /**
     * This method removes tiles from the player hands
     */
    public void remove(Tile...ts){
        for (Tile t : ts) {
            tiles.remove(t);
        }
    }
    /**
     * This method adds score for the player
     * @param score
     */
    public void addScore(int score){
        this.score+=score;
    }
}
