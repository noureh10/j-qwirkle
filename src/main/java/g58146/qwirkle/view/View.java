package g58146.qwirkle.view;
import g58146.qwirkle.model.*;
import java.util.List;
/**
 *
 * @author Nour
 */
public class View{
    /**
     * This method displays the current player name and his hand
     * @param player
     */
    public static void display(Player player){
        System.out.println("-----------------------------");
        System.out.println(" Player's name : " + player.getName());
        System.out.println(" Player's score : " + player.getScore());
        System.out.println("-----------------------------");
        List<Tile> playerHand = player.getHand();
        System.out.println("Player's hand : ");
        for (int i=0;i<playerHand.size();i++){
            System.out.print((i+1) + "   ");
        }
        System.out.println();
        for (Tile tile : playerHand) {
            System.out.print(tileColor(tile)+tileShape(tile) + "  ");
        }
        System.out.println("\u001B[39m");
    }
    /**
     * This method diplays the help prompt
     */
    public static void displayHelp() {
        System.out.println("""
                Q W I R K L E
                Qwirkle command:
                - play 1 tile : o <row> <col> <i>
                - play line : l <row> <col> <direction> <i1> [<i2>]
                - play plic-ploc : m <row1> <col1> <i1> [<row2> <col2> <i2>]
                - play first : f <i1> [<i2>]
                - help : h
                - pass : p
                - renew hand : r
                - quit : q
                i : index in list of tiles
                d : direction in l (left), r (right), u (up), d(down)""");
    }
    /**
     * This method displays the grid. It first find the minimum and maximum rows and column with tiles on them, and it then
     * displays it.
     * @param grid
     */
    public static void display(GridView grid) {
        // Find the minimum and maximum rows and columns with tiles on them
        int rowMin=91,rowMax=0;
        int colMin=91,colMax=0;
        for (int i = 0; i < 91; i++) {
            for (int j = 0; j < 91; j++) {
                if (grid.get(i,j)!=null){
                    rowMin=Math.min(rowMin,i);
                    rowMax=Math.max(rowMax,i);
                    colMin=Math.min(colMin,j);
                    colMax=Math.max(colMax,j);
                }
            }
        }
        System.out.println();
        if(!grid.isEmpty()){
            for(int i=rowMin;i<=rowMax;i++){
                System.out.printf("%2d |",i);
                for(int j=colMin;j<=colMax;j++){
                    Tile tile = grid.get(i,j);
                    if(tile!=null){
                        System.out.print(tileColor(grid.get(i,j))+tileShape(grid.get(i,j)));
                        System.out.print(" ");
                    }else{
                        System.out.print("   ");
                    }
                }
                System.out.print("\u001B[39m");
                System.out.println();
            }
            System.out.print("   ");
            for(int j=colMin;j<=colMax;j++){
                System.out.printf(" %2d",j);
            }
        }
        System.out.println();
    }
    /**
     * This method displays error messages.
     * @param message
     */
    public static void displayError(String message){
        System.out.println(message);
    }
    /**
     * This method displays message without going to the next line
     * @param message
     */
    public static void displayMessage(String message){
        System.out.print(message);
    }
    /**
     * This method is used to get a color according to the tile given in parameter.
     * @param tile
     * @return
     */
    private static String tileColor(Tile tile) {
        return switch (tile.color()) {
            case BLUE -> "\u001B[38;2;0;0;255m";
            case RED -> "\u001B[38;2;255;0;0m";
            case GREEN -> "\u001B[38;2;0;255;0m";
            case ORANGE -> "\u001B[38;2;255;165;0m";
            case YELLOW -> "\u001B[38;2;255;255;0m";
            case PURPLE -> "\u001B[38;2;128;0;128m";
            default -> "";
        };
    }
    /**
     * This method is used to get a shape according to the tile given in parameter.
     * @param tile
     * @return
     */
    private static String tileShape(Tile tile){
        return switch (tile.shape()){
            case CROSS -> "x ";
            case SQUARE -> "[]";
            case ROUND -> "o ";
            case STAR -> "* ";
            case PLUS -> "+ ";
            case DIAMOND -> "<>";
        };
    }
}
