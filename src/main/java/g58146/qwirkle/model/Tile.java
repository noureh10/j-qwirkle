package g58146.qwirkle.model;
import java.io.Serializable;
/**
 * Tiles are used in the game, they have a color and a shape
 * @author Nour
 */
public record Tile(Color color, Shape shape) implements Serializable {}
