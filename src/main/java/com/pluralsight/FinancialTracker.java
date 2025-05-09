package com.pluralsight;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class FinancialTracker {
    private static ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    public static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        // Loads and Sorts the Transactions Array List in the beginning
        System.out.println("Loading Application...");
        loadTransactions(FILE_NAME);
        Collections.sort(allTransactions, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
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
            /* This splits each line of the csv file by the '|' and sorts into an array list called 'allTransactions'
                to display later for the user */
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
    }

    private static void addDeposit(Scanner scanner) {
        try {
            // Deposit Selection Screen
            System.out.println("Deposit Selection Screen");
            System.out.println("______________________________________________");

            // User Date Input with try/catch & loop if user doesn't enter correct input
            LocalDate depositDateInput = null;
            System.out.print("Please enter date of Deposit (YYYY-MM-dd): ");
            while (depositDateInput == null) {
                try {
                    String dateInput = scanner.nextLine();
                    // Conversion of String to Date with date formatter
                    depositDateInput = LocalDate.parse(dateInput, DATE_FORMATTER);
                } catch (Exception e) {
                    System.err.print("Entered incorrect date format.\nPlease Enter in format (YYYY-MM-dd): ");
                }
            }

            // User Time Entry with try/catch & loop if user doesn't enter correct input
            LocalTime depositTimeInput = null;
            System.out.print("Please enter time of Deposit (HH:mm:ss): ");
            while
                (depositTimeInput == null) {
                try {
                    String timeInput = scanner.nextLine();
                    // Conversion of String to Time with time formatter
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

            // User Amount Entry with try/catch & loop if user doesn't enter correct input
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
            // Adds to Transaction Array List
            Transaction transaction = new Transaction(depositDateInput, depositTimeInput, descriptionInput, vendorInput, amountInput);
            allTransactions.add(transaction);

            // BufferedWriter: writes new transaction to CSV File
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bufferedWriter.write(transaction + "\n");
            bufferedWriter.close();

            // Confirms that Transaction was added (user-friendly design chose)
            System.out.println("You added: " + transaction);

        } catch (Exception e) {
            System.err.println("Incorrect Input: Returning to Deposit Selection Screen...");
        }
    }

    private static void addPayment(Scanner scanner) {
        try {
            // Payment Selection Screen
            System.out.println("Payment Selection Screen");
            System.out.println("______________________________________________");

            // User Date Input with try/catch & loop if user doesn't enter correct input
            LocalDate paymentDateInput = null;
            System.out.print("Please enter date of Payment (YYYY-MM-dd): ");
            while (paymentDateInput == null) {
                try {
                    String dateInput = scanner.nextLine();
                    // Conversion of String to Date
                    paymentDateInput = LocalDate.parse(dateInput, DATE_FORMATTER);
                } catch (Exception e) {
                    System.err.print("Entered incorrect date format.\nPlease Enter in format (YYYY-MM-dd): ");
                }
            }

            // User Time Entry with try/catch & loop if user doesn't enter correct input
            LocalTime paymentTimeInput = null;
            System.out.print("Please enter time of Payment (HH:mm:ss): ");
            while
            (paymentTimeInput == null) {
                try {
                    String timeInput = scanner.nextLine();
                    // Conversion of String to Time
                    paymentTimeInput = LocalTime.parse(timeInput, TIME_FORMATTER);
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

            // User Amount Entry (converts to negative in Array List & CSV File)
            double amountInput = 0;
            System.out.print("Please enter the Amount for Payment: ");
            while (amountInput >= 0) {
                try {
                    amountInput = scanner.nextDouble();
                    amountInput *= -1;
                    scanner.nextLine();
                    if (amountInput >= 0) {
                        System.err.println("Please remove negative from payment value: ");
                    }
                } catch (Exception e) {
                    System.err.print("Invalid Input. Please enter amount here: ");
                    scanner.nextLine(); // scanner eater
                }
            }
            // Adds to Transaction Array List
            Transaction transaction = new Transaction(paymentDateInput, paymentTimeInput, descriptionInput, vendorInput, amountInput);
            allTransactions.add(transaction);

            // BufferedWriter: writes new transaction to CSV File
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bufferedWriter.write(transaction + "\n");
            bufferedWriter.close();

            // Confirms that Transaction was added (user-friendly design chose)
            System.out.println("You added: " + transaction);

        } catch (Exception e) {
            System.err.println("Incorrect Input: Returning to Payment Selection Screen...");
        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            // Ledger Menu Display
            System.out.println("Welcome to Ledger Selection Screen");
            System.out.println("______________________________________________");
            System.out.println("Choose an option by entering one of the corresponding letters: ");
            System.out.println("A) Display All Entries");
            System.out.println("D) Display Only Deposits");
            System.out.println("P) Display Only Payments");
            System.out.println("R) Reports Selection Screen");
            System.out.println("C) Do a Custom Search");
            System.out.println("H) Go Back to Transaction Home Screen");
            System.out.print("Enter Here: ");
            String input = scanner.nextLine();
            System.out.println("______________________________________________");

            // Switch Case for Ledger Menu
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
                case "C":
                    doCustomSearch();
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

    // Added this Custom Method
    public static String displayTransactionsHomeScreen() {
        // Really Important: This sorts the Transaction list whenever the User comes back to the Home Screen from adding a Payment or Depositing a Payment
        Collections.sort(allTransactions, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

        // Display Transactions Home Screen
        System.out.println("\nWelcome to TransactionApp");
        System.out.println("______________________________________________");
        System.out.println("Choose an option by entering one of the corresponding letters: ");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Enter Here: ");
        String input = scanner.nextLine();
        System.out.println("______________________________________________");
        return input;
    }

    private static void displayLedger() {
        // Displays All Transactions from Newest to Oldest
        System.out.println("All Transactions (newest to oldest)");
        System.out.println("______________________________________________");
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction);

        }
        System.out.println("______________________________________________");

    }

    private static void displayDeposits() {
        // Displays Only Deposits from Newest to Oldest
        System.out.println("All Deposits");
        System.out.println("______________________________________________");
        int i = 0;
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
        System.out.println("______________________________________________");
    }

    private static void displayPayments() {
        // Displays Only Payments from Newest to Oldest
        System.out.println("All Payments");
        System.out.println("______________________________________________");
        int i = 0;
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
        System.out.println("______________________________________________");

    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            // Displays Report Selection Screen
            System.out.println("Reports Selection Screen");
            System.out.println("______________________________________________");
            System.out.println("Choose an option by entering one of the corresponding letters: ");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date (This Year)");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Enter Here: ");
            String input = scanner.nextLine().trim();
            System.out.println("______________________________________________");

            //Switch Case Menu for different filters
            switch (input) {
                case "1":
                    // Displays Month to Current Date
                    LocalDate case1StartDate = LocalDate.now().withDayOfMonth(1);
                    LocalDate case1EndDate   = LocalDate.now();
                    filterTransactionsByDate(case1StartDate, case1EndDate);
                    break;
                case "2":
                    // Displays Previous Month
                    LocalDate case2StartDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate case2EndDate   = LocalDate.now().withDayOfMonth(1).minusDays(1);
                    filterTransactionsByDate(case2StartDate, case2EndDate);
                    break;
                case "3":
                    // Displays Year to Current Date
                    LocalDate case3StartDate = LocalDate.now().withDayOfYear(1);
                    LocalDate case3EndDate = LocalDate.now();
                    filterTransactionsByDate(case3StartDate, case3EndDate);
                    break;
                case "4":
                    // Displays Previous Year
                    LocalDate case4StartDate = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate case4EndDate = LocalDate.now().minusYears(1);
                    filterTransactionsByDate(case4StartDate, case4EndDate);
                    break;
                case "5":
                    // Filter and Display Transactions by Vendor
                    filterTransactionsByVendor("vendor");
                    break;
                case "0":
                    // Go Back
                    running = false;
                default:
                    // Invalid Option
                    System.out.println("Invalid option\n");
                    break;
            }
        }
    }

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This Method is used to the various filters on the Reports Screen
        System.out.println("Filtering Transactions...\n");
        for (Transaction transaction : allTransactions) {
            if ((transaction.getDate().isEqual(startDate)   || transaction.getDate().isAfter (startDate)) &&
                    (transaction.getDate().isEqual(endDate) || transaction.getDate().isBefore(endDate))) {
                System.out.println(transaction);
            }
        }
        System.out.println("______________________________________________");


    }

    private static void filterTransactionsByVendor(String vendor) {
        /* This Method filters by Vendor and If no transactions match the specified vendor name,
        the method prints a message indicating that there are no results. */

        boolean found = false; // set false to print error message if no transactions are found

        System.out.print("Please Enter Vendor Name: ");
        vendor = scanner.nextLine();

        for (Transaction transaction : allTransactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(transaction);
                found = true; // sets true if at least one transaction is found
            }
        }
        // If no transaction is found this will print
        if (!found) {
            System.out.println("No transactions found from this Vendor");
        }
        System.out.println("______________________________________________");

    }

    private static void doCustomSearch() {
        /* This is my Custom Search Bonus to save you some time Raymond this program only works when:
            * user inputs values correctly first try
            * user does not hit enter to skip

           I did not have enough time to finish this code but, I would fix it by:
            * adding while loop with a try catch similar to my addDeposits Method
            * adding a " || null for the different matching case                                       */ //Note for Raymond
        System.out.println("Loading Custom Search...\n");
        System.out.println("Welcome to Custom Search Bonus!");
        System.out.println("______________________________________________");

        System.out.print("Please Enter Start Date or press 'enter' to skip (YYYY-MM-dd): ");
        String csStartDate = scanner.nextLine();
        LocalDate csStartDateParsed = LocalDate.parse(csStartDate, DATE_FORMATTER);

        System.out.print("Please Enter End Date press or 'enter' to skip (YYYY-MM-dd): ");
        String csEndDate = scanner.nextLine();
        LocalDate csEndDateParsed = LocalDate.parse(csEndDate, DATE_FORMATTER);

        System.out.print("Please Enter Saved Description or Invoice Name. press 'enter' to skip: ");
        String csDescription = scanner.nextLine();

        System.out.print("Please Enter Saved Vendor Name or press 'enter' to skip: ");
        String csVendor = scanner.nextLine();

        System.out.print("Please Enter Saved Amount. press 'enter' to skip: ");
        double csAmount = scanner.nextDouble();
        scanner.nextLine(); //scanner eater

        for (Transaction csTransaction : allTransactions) {
            boolean matchDates = ((csTransaction.getDate().isEqual(csStartDateParsed) || csTransaction.getDate().isAfter(csStartDateParsed)) &&
                    (csTransaction.getDate().isEqual(csEndDateParsed) || csTransaction.getDate().isBefore(csEndDateParsed)));

            boolean matchDescription = (csTransaction.getDescription().equalsIgnoreCase(csDescription));

            boolean matchVendor = (csTransaction.getVendor().equalsIgnoreCase(csVendor));

            boolean matchAmount = (csTransaction.getAmount() == csAmount);

            System.out.println("\nCustom Search has found...");
            if (matchDates && matchDescription && matchVendor && matchAmount) {
                System.out.println(csTransaction);
            }
        }
    }

}
