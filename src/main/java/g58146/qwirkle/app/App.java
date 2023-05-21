package g58146.qwirkle.app;
import g58146.qwirkle.model.*;
import g58146.qwirkle.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The app class is used as a controller for the Qwirkle game
 * @author Nour
 */
public class App {
    private static GridView gridview;
    private static Game game;
    private static final String SAVE_FILE = "save.dat";
    private static boolean quitGame=false;
    /**
     * This method handles the user input and redirect to the corresponding method
     */
    private static void actions() {
        while (true) {
            View.displayMessage("Enter a command: ");
            String[] tokens = mixedInput().split("\\s+");
            if (tokens.length == 0 || tokens[0].isEmpty()) {
                View.displayError("The command you wrote is invalid");
                continue;
            }
            switch (tokens[0].toLowerCase()) {
                case "o" -> playOneTileCommand(tokens);
                case "l" -> lineCommand(tokens);
                case "m" -> plicPlocCommand(tokens);
                case "f" -> firstPlayCommand(tokens);
                case "p" -> game.pass();
                case "r" -> game.renew();
                case "q" -> quitGame=true;
                case "h" -> View.displayHelp();
                default -> View.displayError("The command you wrote doesn't match with any Qwirkle command");
            }
            break;
        }
    }
    /**
     * This method is used for the oneTile play command. It takes a array and process its elements to achieve a play.
     * @param tokens The command including a position and an index of a tile.
     */
    private static void playOneTileCommand(String[] tokens){
        if (tokens.length == 4) {
            try {
                int row = Integer.parseInt(tokens[1]);
                int col = Integer.parseInt(tokens[2]);
                int index = Integer.parseInt(tokens[3]) - 1;
                game.play(row, col, index);
            } catch (IndexOutOfBoundsException e) {
                View.displayError("You should enter a number for the deck included between 1 and 6");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            View.displayError("The 1 tile command is invalid because of its length! " +
                    "(should be 4)");
        }
    }
    /**
     * This method is used for the line play command. It takes a array and process its elements to achieve a play.
     * @param tokens The command including an initial position, a direction and index of card being played.
     */
    private static void lineCommand(String[] tokens){
        if(tokens.length>=4&&tokens.length<=9){
            try{
                int row = Integer.parseInt(tokens[1]);
                int col = Integer.parseInt(tokens[2]);
                String dirString = tokens[3];
                char dirChar = dirString.toLowerCase().charAt(0);
                int[] indexes = new int[tokens.length-4];
                for (int i = 4, j = 0; i < tokens.length; i++, j++) {
                    int index = Integer.parseInt(tokens[i]) - 1;
                    indexes[j] = index;
                }
                Direction dir = giveDirection(dirChar);
                game.play(row, col, dir, indexes);
            }catch(IndexOutOfBoundsException e){
                View.displayError("You should enter a number for the deck included between 1 and 6");
            }catch(Exception e){
                View.displayError(e.getMessage());
            }
        }else{
            View.displayError("The line command is invalid because of its length ! : " + tokens.length +
                    " (should be included between 4 and 9)");
        }
    }
    /**
     * This method is used for the plic-ploc play command. It takes a array and process its elements to achieve a play.
     * @param tokens The command including the row(s), column(s) and index(es) of tile(s)
     */
    private static void plicPlocCommand(String[] tokens){
        if((tokens.length - 1) % 3 == 0 &&
                (tokens.length >= 4 && tokens.length <= 19)) {
            try{
                int[] is = new int[tokens.length-1];
                for (int i = 1, j = 0; i < tokens.length; i += 3, j += 3){
                    is[j] = Integer.parseInt(tokens[i]);
                    is[j + 1] = Integer.parseInt(tokens[i + 1]);
                    is[j + 2] = Integer.parseInt(tokens[i + 2]) - 1;
                }
                game.play(is);
            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
                View.displayError("You should enter a number for the deck included between 1 and 6");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            View.displayError("The plic-ploc command invalid because of its length ! : " + tokens.length +
                    " (should be included between 4 and 19)");
        }
    }
    /**
     * This method is used for the first play command. It takes a array and process its elements to achieve a play.
     * @param tokens The command including the direction and indexes of tiles being played.
     */
    private static void firstPlayCommand(String[] tokens){
        if((tokens.length>= 3)&&(tokens.length<=8)){
            try{
                String dirString = tokens[1];
                char dirChar = dirString.toLowerCase().charAt(0);
                int[] indexes = new int[tokens.length-2];
                for (int i = 2, j = 0; i < tokens.length; i++, j++) {
                    indexes[j] = Integer.parseInt(tokens[i]) - 1;
                }
                Direction dir = giveDirection(dirChar);
                game.first(dir, indexes);
            }catch(IndexOutOfBoundsException e){
                View.displayError("You should enter a number for the deck included between 1 and 6");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            View.displayError("The first add command is invalid because of its length ! : "
                    +tokens.length+" (should be between 3 and 8 included)");
        }
    }
    /**
     * This method is used to take numbers letters and spaces as input
     */
    private static String mixedInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.matches("[a-zA-Z0-9 ]+")) {
            System.out.println("Invalid input. Please enter characters or numbers only.");
            input = scanner.nextLine();
        }
        return input;
    }
    /**
     * This method is used to only take letters as input
     */
    public static String inputLettersOnly(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.matches("[a-zA-Z]+")) {
                return input;
            } else {
                System.out.println("Invalid input. Please input letters only.");
            }
        }
    }
    /**
     * Returns a direction corresponding to a given character
     * @param dirChar The character representing the direction
     * @return The corresponding direction enum
     */
    private static Direction giveDirection(char dirChar) {
        return switch (dirChar) {
            case 'l' -> Direction.LEFT;
            case 'r' -> Direction.RIGHT;
            case 'u' -> Direction.UP;
            case 'd' -> Direction.DOWN;
            default -> throw new IllegalArgumentException("Invalid direction character: " + dirChar);
        };
    }
    /**
     * This methods prompt the user for the player names, it then returns an array of the player names.
     * @return An array of all the players
     */
    private static String[] askForPlayers() {
        List<String> playerNames = new ArrayList<>();
        do{
            String name = inputLettersOnly("Enter a name: ");
            playerNames.add(name);
        }while (inputLettersOnly("More players? (Y/N): ").equalsIgnoreCase("y"));
        return playerNames.toArray(new String[0]);
    }
    public static void main(String[] args){
        game = new Game(askForPlayers());
        gridview = game.getGrid();
        View.displayHelp();
        do{
            View.display(game.getCurrentPlayer());
            View.display(gridview);
            actions();
        }while(!quitGame);
    }
}