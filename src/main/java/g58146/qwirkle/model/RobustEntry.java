package g58146.qwirkle.model;
import java.util.Scanner;

public class RobustEntry {
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
     * This method is used to only take numbers as input
     */
    public static int inputNumbersOnly(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Invalid input. Please input numbers only.");
            }
        }
    }
    /**
     * This method is used to take numbers letters and spaces as input
     */
    public static String mixedInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.matches("[a-zA-Z0-9 ]+")) {
            System.out.println("Invalid input. Please enter characters or numbers only.");
            input = scanner.nextLine();
        }
        return input;
    }
}