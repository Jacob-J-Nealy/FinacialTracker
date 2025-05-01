package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    public static Scanner scanner = new Scanner(System.in);

    // Didn't need to use Date & Time Formatters because Array List and transactions file is already in ISO format
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        System.out.println("Loading Application...");
        loadTransactions(FILE_NAME);
        boolean running = true;

        while (running) {
            // Transaction Home Screen
            String input = displayTransactionsHomeScreen();

            // Switch Case for Home Screen Menu
            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    System.out.println("Closing Application...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }



    public static void loadTransactions(String fileName) {

        String line;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                allTransactions.add(new Transaction(date, time, description, vendor, amount));
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("ERROR");
        }
        // Each line of the file represents a single transaction in the following format:
        // <date>|<time>|<description>|<vendor>|<amount>
        // For example: 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.
    }

    private static void addDeposit(Scanner scanner) {
        try {
            // Deposit Selection Screen
            System.out.println("Deposit Selection Screen");
            System.out.println("----------------------------------------");

            // User Date Input with try/catch & loop if user doesn't enter correct input
            LocalDate depositDateInput = null;
            System.out.print("Please enter date of Deposit (YYYY-MM-dd): ");
            while (depositDateInput == null) {
                try {
                    String dateInput = scanner.nextLine();
                    // Conversion of String to Date with variable format
                    depositDateInput = LocalDate.parse(dateInput, DATE_FORMATTER);
                } catch (Exception e) {
                    System.err.print("Entered incorrect date format.\nPlease Enter in format (YYYY-MM-dd): ");
                }
            }

            // User Time Entry
            LocalTime depositTimeInput = null;
            System.out.print("Please enter time of Deposit (HH:mm:ss): ");
            while
                (depositTimeInput == null) {
                try {
                    String timeInput = scanner.nextLine();
                    // Conversion of String to Time with variable format
                    depositTimeInput = LocalTime.parse(timeInput, TIME_FORMATTER);
                } catch (Exception e) {
                    System.err.print("Entered incorrect time format.\nPlease Enter in format ((HH:mm:ss): ");
                }
            }

            // User Description Entry
            System.out.print("Please enter invoice description for deposit: ");
            String descriptionInput = scanner.nextLine();

            // User Vendor Entry
            System.out.print("Please enter the name of person depositing or vendor depositing income: ");
            String vendorInput = scanner.nextLine();

            // User Amount Entry
            double amountInput = 0;
            System.out.print("Please enter the amount to deposit: ");
            while (amountInput <= 0) {
                try {
                    amountInput = scanner.nextDouble();
                    scanner.nextLine();
                    if (amountInput <= 0) {
                        System.out.println("Amount must be greater than 0 please try again: ");
                    }
                } catch (Exception e) {
                    System.err.print("Invalid Input. Please enter amount here: ");
                    scanner.nextLine(); // scanner eater
                }
            }

            Transaction transaction = new Transaction(depositDateInput, depositTimeInput, descriptionInput, vendorInput, amountInput);
            allTransactions.add(transaction);
            String outputLine;
            outputLine = String.format("%s|%s|%s|%s|%.2f",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
            // Buffered Writer
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bufferedWriter.write(outputLine + "\n");
             bufferedWriter.close();


            System.out.println("You added: " + outputLine);



        } catch (Exception e) {
            System.err.println("Incorrect Input: Returning to Deposit Selection Screen...");
        }
    }

    private static void addPayment(Scanner scanner) {
        try {
            // Payment Selection Screen
            System.out.println("Payment Selection Screen");
            System.out.println("----------------------------------------");

            // User Date Input with try/catch & loop if user doesn't enter correct input
            LocalDate paymentDateInput = null;
            System.out.print("Please enter date of Payment (YYYY-MM-dd): ");
            while (paymentDateInput == null) {
                try {
                    String dateInput = scanner.nextLine();
                    // Conversion of String to Date
                    // Format Date Here use date time formatter
                    paymentDateInput = LocalDate.parse(dateInput);
                } catch (Exception e) {
                    System.err.print("Entered incorrect date format.\nPlease Enter in format (YYYY-MM-dd): ");
                }
            }

            // User Time Entry
            LocalTime paymentTimeInput = null;
            System.out.print("Please enter time of Payment (HH:mm:ss): ");
            while
            (paymentTimeInput == null) {
                try {
                    String timeInput = scanner.nextLine();
                    // Conversion of String to Time
                    paymentTimeInput = LocalTime.parse(timeInput);
                } catch (Exception e) {
                    System.err.print("Entered incorrect time format.\nPlease Enter in format (HH:mm:ss): ");
                }
            }

            // User Description Entry
            System.out.print("Please enter name of Payment: ");
            String descriptionInput = scanner.nextLine();

            // User Vendor Entry
            System.out.print("Please enter the Vendor name for Payment: ");
            String vendorInput = scanner.nextLine();

            // User Amount Entry
            double amountInput = 0;
            System.out.print("Please enter the amount for payment: ");
            while (amountInput >= 0) {
                try {
                    amountInput = scanner.nextDouble();
                    scanner.nextLine();
                    if (amountInput >= 0) {
                        System.err.println("Amount must be less than 0 please put negative in front of payment value: ");
                    }
                } catch (Exception e) {
                    System.err.print("Invalid Input. Please enter amount here: ");
                    scanner.nextLine(); // scanner eater
                }
            }

            Transaction transaction = new Transaction(paymentDateInput, paymentTimeInput, descriptionInput, vendorInput, amountInput);
            allTransactions.add(transaction);
            String outputLine;
            outputLine = String.format("%s|%s|%s|%s|%.2f",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
            // Buffered Writer
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            // change outline to transaction because to String
            bufferedWriter.write(outputLine + "\n");
            bufferedWriter.close();


            System.out.println("You added: " + outputLine);



        } catch (Exception e) {
            System.err.println("Incorrect Input: Returning to Payment Selection Screen...");
        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Welcome to Ledger Selection Screen");
            System.out.println("----------------------------------------");
            System.out.println("Choose an option by entering one of the corresponding letters: ");
            System.out.println("A) Display All Entries");
            System.out.println("D) Display Only Deposits");
            System.out.println("P) Display Only Payments");
            System.out.println("R) Reports Selection Screen");
            System.out.println("H) Go Back to Transaction Home Screen");
            System.out.print("Enter Here: ");
            String input = scanner.nextLine();
            System.out.println("----------------------------------------");

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                    System.out.println("Loading Home Screen...");
                    continue;
                default:
                    System.err.println("Invalid Option Please try again\n");
                    break;
            }
        }
    }

    public static String displayTransactionsHomeScreen() {
        System.out.println("\nWelcome to TransactionApp");
        System.out.println("----------------------------------------");
        System.out.println("Choose an option by entering one of the corresponding letters: ");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Enter Here: ");
        String input = scanner.nextLine();
        System.out.println("----------------------------------------");
        return input;
    }

    private static void displayLedger() {
        System.out.println("All Transactions");
        System.out.println("________________________________________");
        for (Transaction transaction : allTransactions) {
            System.out.println();
            System.out.println(transaction);
        }
        System.out.println("________________________________________");

    }

    private static void displayDeposits() {
        System.out.println("All Deposits");
        System.out.println("________________________________________");
        int i = 0;
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
        System.out.println("________________________________________");
    }

    private static void displayPayments() {
        System.out.println("All Payments");
        System.out.println("________________________________________");
        int i = 0;
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
        System.out.println("________________________________________");

    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {

            System.out.println("Reports Selection Screen");
            System.out.println("----------------------------------------");
            System.out.println("Choose an option by entering one of the corresponding letters: ");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Enter Here: ");
            String input = scanner.nextLine().trim();
            System.out.println("----------------------------------------");

            //Switch Case Menu for different filters
            switch (input) {
                case "1":
                    LocalDate startDate;
                    LocalDate endDate;



//                    Month currentMonth = LocalDate.now().getMonth();
//                    int currentYear = LocalDate.now().getYear();
//                    System.out.println("Transactions for Current Month:");
//                    for (Transaction transaction : allTransactions) {
//                        if (transaction.getDate().getMonth() == currentMonth && transaction.getDate().getYear() == currentYear) {
//                            System.out.println(transaction);
//                        }
//                    }
//                    System.out.println("______________________________________________");
//                    break;

                    case "2":
                    Month lastMonth = LocalDate.now().minusMonths(1).getMonth();
                    System.out.println("Transactions for Previous Month:\n");
                    for (Transaction transaction : allTransactions) {
                        if (transaction.getDate().getMonth() == lastMonth) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("______________________________________________");
                    break;

                case "3":
                    int currentYearCase3 = LocalDate.now().getYear();
                    System.out.println("Transactions for Current Year:\n");
                    for (Transaction transaction : allTransactions) {
                        if (transaction.getDate().getYear() == currentYearCase3) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("______________________________________________");
                    break;

                case "4":
                    int lastYear = LocalDate.now().minusYears(1).getYear();
                    System.out.println("Transactions from Last Year:\n");
                    for (Transaction transaction : allTransactions) {
                        if (transaction.getDate().getYear() == lastYear) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("______________________________________________");
                    break;

                case "5":
                    filterTransactionsByVendor("vendor");
                    break;

                case "0":
                    running = false;

                default:
                    System.out.println("Invalid option\n");
                    break;
            }
        }
    }

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
        // Is After & Is Before

        System.out.println("Filtering transaction from" + startDate + "to" + endDate);

        for (Transaction transaction : allTransactions) {
            if ((transaction.getDate().isEqual(startDate)   || transaction.getDate().isAfter (startDate)) &&
                    (transaction.getDate().isEqual(endDate) || transaction.getDate().isBefore(endDate))) {
                System.out.println(transaction);
            }
        }


    }

    private static void filterTransactionsByVendor(String vendor) {
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
        System.out.print("Please Enter Vendor Name: ");
        vendor = scanner.nextLine();

        for (Transaction transaction : allTransactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(transaction);
            }
        }
        System.out.println("______________________________________________");

    }

    /*

    Notes with Walter 4.28.25
    __________________________________________
    First Problem:  Saving transaction to csv file (payments and deposits)
    Second Problem: Displaying Ledger Properly
        * In real Life you start with User Stories
        * Work on ReadMe throughout Progress
            - Changing constantly
            -
        * Finish Add Deposit and Make Payments

    Notes with Raymond 4.28.25
    ___________________________________________
        * Mid-Thursday Capstone should be done
        * We are going to work with Dates
        * User Stories should take half a day (2 Hours)
        * Hint: In Transaction date should be Local
        * Do ReadME at the END

    Notes with Walter 4.29.25
    ________________________________________________
    * Extra Bonus to combine Deposit and Payment Method using If Else Statement
    * Store in the Object in Array List
    * Use FileWriter Object and Buffered Writer to write to CSV (payment and deposit)
    * General Ledger Done
    * Just Reports Left
    * Talk with Raymond about Payments and turning it from a negative to a positive

    Notes with Walter 4.30.25
    ________________________________________________
    * Sure Months refer only to current year
    * Make Sure if January it refers to December (conditional statement)
    * Has to be most recent at top when it prints (.sort)
    * Ask Raymond about pulling before making custom search
    * Ask raymond about private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) Method
    * Ask Raymond how to start Custom Search Option
    * Work on toString Display
    * Fix Add Payments
    * Remove

    Custom
    Search Vendor Report Menu

    Notes with Raymond 4.30.25
    ______________________________
    * Goals: Finish Report; User Stories; README


     */
}
