package g58146.qwirkle.model;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Nour
 */
public class Player {
    private String name;
    private List<Tile> tiles;
    /**
     * Constructor of the player class.
     */
    public Player(String name, List<Tile> tiles) {
        this.name = name;
        this.tiles = new ArrayList<Tile>();
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
     * This method refills the player hand
     */
    public void refill(){
        List<Tile> t = Bag.getInstance().getRandomTiles(6-tiles.size());
        tiles.addAll(t);
    }
     /**
     * This method removes tiles from the player hands
     */
    public void remove(Tile...ts){
        for (Tile t : ts){
            tiles.remove(t);
        }
    } 
}
