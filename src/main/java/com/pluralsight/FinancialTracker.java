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
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bufferedWriter.write(transaction + "\n");
            bufferedWriter.close();


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

            Transaction transaction = new Transaction(paymentDateInput, paymentTimeInput, descriptionInput, vendorInput, amountInput);
            allTransactions.add(transaction);
            // Buffered Writer
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            // change outline to transaction because to String
            bufferedWriter.write(transaction + "\n");
            bufferedWriter.close();


            System.out.println("You added: " + transaction);



        } catch (Exception e) {
            System.err.println("Incorrect Input: Returning to Payment Selection Screen...");
        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
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

    public static String displayTransactionsHomeScreen() {
        Collections.sort(allTransactions, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
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
        System.out.println("All Transactions (newest to oldest)");
        System.out.println("______________________________________________");
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction);

        }
        System.out.println("______________________________________________");

    }

    private static void displayDeposits() {
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
                    LocalDate case1StartDate = LocalDate.now().withDayOfMonth(1);
                    LocalDate case1EndDate   = LocalDate.now();
                    filterTransactionsByDate(case1StartDate, case1EndDate);
                    break;
                case "2":
                    LocalDate case2StartDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate case2EndDate   = LocalDate.now().withDayOfMonth(1).minusDays(1);
                    filterTransactionsByDate(case2StartDate, case2EndDate);
                    break;
                case "3":
                    LocalDate case3StartDate = LocalDate.now().withDayOfYear(1);
                    LocalDate case3EndDate = LocalDate.now();
                    filterTransactionsByDate(case3StartDate, case3EndDate);
                    break;
                case "4":
                    LocalDate case4StartDate = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate case4EndDate = LocalDate.now().minusYears(1);
                    filterTransactionsByDate(case4StartDate, case4EndDate);
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

    private static void doCustomSearch() {
        System.out.println("Loading Custom Search...\n");

        System.out.print("Please Enter Start Date or press 'enter' to skip (YYYY-MM-dd): ");
        String csStartDate = scanner.nextLine();
        LocalDate csStartDateParsed = LocalDate.parse(csStartDate, DATE_FORMATTER);

        System.out.print("Please Enter End Date press or 'enter' to skip (YYYY-MM-dd): ");
        String csEndDate = scanner.nextLine();
        LocalDate csEndDateParsed = LocalDate.parse(csEndDate, DATE_FORMATTER);
        for (Transaction transaction : allTransactions) {
            if ((transaction.getDate().isEqual(csStartDateParsed)   || transaction.getDate().isAfter (csStartDateParsed)) &&
                    (transaction.getDate().isEqual(csEndDateParsed) || transaction.getDate().isBefore(csEndDateParsed))) {
            }
        }

        System.out.print("Please Enter Saved Description or Invoice Name. press 'enter' to skip: ");
        String csDescription = scanner.nextLine();
        for (Transaction transaction : allTransactions) {
            if (transaction.getDescription().equalsIgnoreCase(csDescription));
        }

        System.out.print("Please Enter Saved Vendor Name or press 'enter' to skip: ");
        for (Transaction transaction : allTransactions) {
            if (transaction.getVendor().equalsIgnoreCase(transaction.getVendor())) {
            }
        }

        System.out.print("Please Enter Saved Amount. press 'enter' to skip: ");
        double csAmount = scanner.nextDouble();
        scanner.nextLine(); //scanner eater
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() == csAmount);
        }

        for (Transaction transaction : allTransactions) {
            if (transaction.getVendor().equalsIgnoreCase(transaction.getVendor())) {
                System.out.println(transaction);
            }
        }
        //System.out.println(transaction);
    }

    /*

    Notes with Walter & Raymond 5.1.25
    _____________________________________
    * Goals: Finish Report; Finish User Stories & README
    * Goals: Custom Search after 11:59am
    * Send Details to Walter for Presentation to be there
    * Remind Durante as well
    * Possible if getting exceeding or higher can become student tutor and get certification
    * What else can I do to get an exceeding/ what can I do to get a Leading

     */
}
