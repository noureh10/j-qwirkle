package g58146.qwirkle.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
/**
 * Bag contains the tiles
 * @author Nour
 */
public class Bag {
    private List<Tile> tiles;
    private static Bag instance=null;

    /**
     * Constructor of Bag class
     */
    private Bag(){
        tiles = new ArrayList<>();
        for (int i=0;i<3;i++){
            for (Color color:Color.values()){
                for (Shape shape:Shape.values()){
                    Tile tile = new Tile(color,shape);
                    tiles.add(tile);
                }
            }  
        }
        Collections.shuffle(tiles);
    }
    /**
     * This method will return a singleton instance of the bag class, if the
     * intance variable is null it will create a Bag instance.
     * @return an instance of the bag class
     */
    public static Bag getInstance(){
        if(instance==null){
            instance= new Bag();
        }
        return instance;
    }
    /**
     * This method will return an array of randomly picked tiles. If n exceed 
     * the number of available tiles, it will return null. If not, it will return
     * the desired array. It will also make sure that it does not pick the same tile twice.
     * @param n Number of tiles wanted
     * @return an array of randomly picked tiles
     */
    public List<Tile> getRandomTiles(int n){
        if(n>tiles.size()){
            return null;
        }
        Random ran = new Random();
        List<Tile> ranTilesArray = new ArrayList<>(n);
        for (int i = 0; i < n; i++){
            int randomIndex;
            do {
                randomIndex = ran.nextInt(tiles.size());
            }while(ranTilesArray.contains(tiles.get(randomIndex)));
            ranTilesArray.add(tiles.get(randomIndex));
            tiles.remove(randomIndex);
        }
        return ranTilesArray;
    }
    /**
     * This method returns the size of the tiles attribute
     * @return 
     */
    public int size(){
        return tiles.size();
    }
}