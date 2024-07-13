import java.util.Scanner;

public class CinemaManagement {
    private static final int ROWS = 5;
    private static final int SEATS = 16;
    private static final int[] PRICES = {10, 10, 12, 14, 15}; // Prices for each row
    private static int[][] seats = new int[ROWS][SEATS];
    private static Ticket[] tickets = new Ticket[ROWS * SEATS];
    private static int ticketCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to The London Lumiere");
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    buy_ticket(scanner);
                    break;
                case 2:
                    cancel_ticket(scanner);
                    break;
                case 3:
                    print_seating_area();
                    break;
                case 4:
                    find_first_available();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket(scanner);
                    break;
                case 7:
                    sort_tickets();
                    break;
                case 8:
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void displayMenu() {
       
        System.out.println("\n1. Buy a ticket");
        System.out.println("2. Cancel a ticket");
        System.out.println("3. Print seating area");
        System.out.println("4. Find first available seat");
        System.out.println("5. Print tickets information");
        System.out.println("6. Search for a ticket");
        System.out.println("7. Sort tickets by price");
        System.out.println("8. Exit");
        System.out.println("\nPlease select an option:");
    }

    private static void buy_ticket(Scanner scanner) {
        System.out.println("Enter row number (1-5): ");
        int row = scanner.nextInt() - 1;
        System.out.println("Enter seat number (1-16): ");
        int seat = scanner.nextInt() - 1;

        if (isValidSeat(row, seat) && seats[row][seat] == 0) {
            System.out.println("Enter your name: ");
            String name = scanner.next();
            System.out.println("Enter your surname: ");
            String surname = scanner.next();
            System.out.println("Enter your email: ");
            String email = scanner.next();

            Person person = new Person(name, surname, email);
            int price = PRICES[row];
            Ticket ticket = new Ticket(row, seat, price, person);

            seats[row][seat] = 1;
            tickets[ticketCount++] = ticket;
            System.out.println("The seat has been booked");
        } else {
            System.out.println("This seat is not available or invalid seat number");
        }
    }

    private static void cancel_ticket(Scanner scanner) {
        System.out.println("Enter row number (1-5): ");
        int row = scanner.nextInt() - 1;
        System.out.println("Enter seat number (1-16): ");
        int seat = scanner.nextInt() - 1;

        if (isValidSeat(row, seat) && seats[row][seat] == 1) {
            seats[row][seat] = 0;
            for (int i = 0; i < ticketCount; i++) {
                if (tickets[i].getRow() == row && tickets[i].getSeat() == seat) {
                    tickets[i] = tickets[--ticketCount]; // Remove the ticket
                    tickets[ticketCount] = null; // Nullify the last element
                    System.out.println("The seat has been cancelled");
                    return;
                }
            }
        } else {
            System.out.println("This seat is already available or invalid seat number");
        }
    }

    private static void print_seating_area() {
        System.out.println("Seating area (O = available, X = sold):");

        System.out.println("*********************************\n*\t\tSCREEN \t \t*\n*********************************");

        for (int row = 0; row < ROWS; row++) {
            for (int seat = 0; seat < SEATS; seat++) {
                if (seat == 8) System.out.print(" "); // Gap between seats 8 and 9
                System.out.print(seats[row][seat] == 0 ? 'O' : 'X');
            }
            System.out.println("\t" + "($" + PRICES[row] + ")");
        }
    }

    private static void find_first_available() {
        for (int row = 0; row < ROWS; row++) {
            for (int seat = 0; seat < SEATS; seat++) {
                if (seats[row][seat] == 0) {
                    System.out.println("First available seat: Row " + (row + 1) + ", Seat " + (seat + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats");
    }

    private static void print_tickets_info() {
        int total = 0;
        for (int i = 0; i < ticketCount; i++) {
            tickets[i].printTicketInfo();
            total += tickets[i].getPrice();
        }
        System.out.println("Total price of tickets sold: $" + total);
    }

    private static void search_ticket(Scanner scanner) {
        System.out.println("Enter row number (1-5): ");
        int row = scanner.nextInt() - 1;
        System.out.println("Enter seat number (1-16): ");
        int seat = scanner.nextInt() - 1;

        if (isValidSeat(row, seat) && seats[row][seat] == 1) {
            for (int i = 0; i < ticketCount; i++) {
                if (tickets[i].getRow() == row && tickets[i].getSeat() == seat) {
                    tickets[i].printTicketInfo();
                    return;
                }
            }
        }
        else if(isValidSeat(row, seat) && seats[row][seat] == 0){
            System.out.println("This seat is available");
        }

        else System.out.println("Invalid seat number");
        
    }

    private static void sort_tickets() {
        for (int i = 0; i < ticketCount - 1; i++) {
            for (int j = 0; j < ticketCount - i - 1; j++) {
                if (tickets[j].getPrice() > tickets[j + 1].getPrice()) {
                    Ticket temp = tickets[j];
                    tickets[j] = tickets[j + 1];
                    tickets[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < ticketCount; i++) {
            tickets[i].printTicketInfo();
        }
    }

    private static boolean isValidSeat(int row, int seat) {
        return row >= 0 && row < ROWS && seat >= 0 && seat < SEATS;
    }
}
