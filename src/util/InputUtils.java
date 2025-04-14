package util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputUtils {
    public static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String promptForValidEmail() {
        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine().trim();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        }
        return email;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean promptYesNo(String message) {
        while (true) {
            System.out.print(message);
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    public static Date promptForValidDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                String dateStr = scanner.nextLine().trim();
                Date date = dateFormat.parse(dateStr);
                if (date.before(new Date())) {
                    System.out.println("Invalid date. Please enter a date that is today or in the future.");
                } else {
                    return date;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
    }

    public static int promptForMenuSelection(int min, int max) {
        while (true) {
            try {
                System.out.print("Please select a menu option: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}