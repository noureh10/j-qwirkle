package g58146.qwirkle.app;
import org.apache.commons.lang3.StringUtils;
import g58146.qwirkle.model.*;
import g58146.qwirkle.view.*;
import java.util.Scanner;
import g58146.qwirkle.view.View;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args){
        String[] playerNames = askPlayerNames();
        Game game = new Game(playerNames);
        GridView GridView = game.getGrid();
        View view = new View();
        do{
            int cpn = game.getCurrentPlayer();
            Player p = game.getPlayers(cpn);
            view.displayPlayer(p);
            view.display(GridView);
            view.displayHand(p);
            view.displayHelp();
            break;



        }while(true);




    }
    private static void commandHub(Game g) {
        while(true){
          String input = input();
          char[] c = input.toCharArray();
          if (c.length> 0) {
          switch(input.charAt(0)){
            case 'o':
                if (input.length()==4&&StringUtils.isNumeric(input.substring(1))){
                    int row = Character.getNumericValue(c[1]);
                    int col = Character.getNumericValue(c[2]);
                    int i = Character.getNumericValue(c[3]);
                    while (true) {
                        try {
                            g.play(row, col, i);
                             break;
                        }catch(Exception e){
                            System.out.println("Invalid move. Please try again.");
                            input = input();
                            c = input.toCharArray();
                            row = Character.getNumericValue(c[1]);
                            col = Character.getNumericValue(c[2]);
                            i=Character.getNumericValue(c[3]);
                        }
                    }
                }
                case 'l':
                    while(true){
                        if (c.length >= 4) {
                            try {
                                int row = Integer.parseInt(String.valueOf(c[1]));
                                int col = Integer.parseInt(String.valueOf(c[2]));
                                char dirChar = Character.toLowerCase(c[3]);
                                Direction direction;
                                switch (dirChar) {
                                case 'l':
                                    direction = Direction.LEFT;
                                    break;
                                case 'r':
                                    direction = Direction.RIGHT;
                                    break;
                                case 'u':
                                    direction = Direction.UP;
                                    break;
                                case 'd':
                                    direction = Direction.DOWN;
                                    break;
                                default:
                                    System.out.println("Invalid direction."
                                        + "Please try again.");
                                    return;
                                }
                                int[] tileIndexes = new int[6];
                                for(int i = 4; i < c.length; i++) {
                                    if(StringUtils.isNumeric(String.valueOf(c[i]))) {
                                        int index = Integer.parseInt
                                            (String.valueOf(c[i]));
                                        if (index >= 1 && index <= 6) {
                                            tileIndexes[i-4] = index;
                                        }
                                    }
                                }
                                g.play(row, col, direction,tileIndexes);
                                break;
                            }catch(Exception e){
                                    System.out.println("Invalid input."
                                            + "Please try again.");            
                            }
                    }
                    }
                case 'm':
                    
                    // handle "m" command
                    break;
                case 'f':
                    // handle "f" command
                    break;
                case 'p':
                    // handle "p" command
                    break;
                case 'q':
                    // handle "q" command
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }  
        }
        
    }
    private static String[] askPlayerNames() {
        String[] playerNames = new String[4];
        Scanner scanner = new Scanner(System.in);
        int numPlayers=0;
        while (true) {
            System.out.print("Enter player name: ");
            playerNames[numPlayers] = onlyCharacters();
            numPlayers++;
            System.out.print("Add another player? (y/n): ");
            String response = scanner.nextLine().toLowerCase();
            if (!response.equals("y")) {
                break;
            }
        }
        return playerNames;
    }
    private static String onlyCharacters(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.matches("[a-zA-Z]+")) {
            System.out.println("Invalid input. Please enter characters only.");
            input = scanner.nextLine();
        }
        return input;
    }
    private static String input(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.matches("[a-zA-Z0-9]+")) {
            System.out.println("Invalid input. Please enter characters or numbers only.");
            input = scanner.nextLine();
        }
        return input;
    }

}
