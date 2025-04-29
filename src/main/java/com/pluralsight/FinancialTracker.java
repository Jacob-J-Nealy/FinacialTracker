package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Transaction Home Screen
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

            // Switch Case for Menu
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
                    // Conversion of String to Date
                    // Format Date Here use date time formatter
                    depositDateInput = LocalDate.parse(dateInput);
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
                    // Conversion of String to Time
                    depositTimeInput = LocalTime.parse(timeInput);
                } catch (Exception e) {
                    System.err.print("Entered incorrect time format.\nPlease Enter in format ((HH:mm:ss): ");
                }
            }

            // User Description Entry
            System.out.print("Please enter invoice description for deposit: ");
            String descriptionInput = scanner.nextLine();

            // User Vendor Entry
            System.out.print("Please enter the name of person depositing that item was purchased from: ");
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
            String outputLine = "";
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
        // This method should prompt the user to enter the date, time, description, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount received should be a positive number then transformed to a negative number.
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.

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
                    paymentDateInput = LocalDate.parse(dateInput);
                } catch (Exception e) {
                    System.err.print("Entered incorrect date format.\nPlease Enter in format (YYYY-MM-dd): ");
                }
            }

            // User Time Entry
            LocalTime paymentTimeInput = null;
            System.out.print("Please enter time of Payment (HH:mm:ss): ");
            while (paymentTimeInput == null)
                try {
                    String timeInput = scanner.nextLine();
                    // Conversion of String to Time
                    paymentTimeInput = LocalTime.parse(timeInput);
                } catch (Exception e) {
                    System.err.print("Entered incorrect time format.\nPlease Enter in format ((HH:mm:ss): ");
                }

            // User Description Entry
            System.out.print("Please enter name of item paid for: ");
            String descriptionInput = scanner.nextLine();

            // User Vendor Entry
            System.out.print("Please enter the vendor name: ");
            String vendorInput = scanner.nextLine();

            // User Amount Entry (COME BACK
            double amountInput = 0;
            System.out.print("Please enter a negative payment amount : ");
            while (amountInput >= 0) {
                try {
                    amountInput = scanner.nextDouble();
                    if (amountInput >= 0) {
                        System.out.println("Amount must be less than 0 remember to add negative. please try again: ");
                    }
                } catch (Exception e) {
                    System.err.print("Invalid Input. Please enter amount here: ");
                    scanner.nextLine(); // scanner eater
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }


    // Second
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

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
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
    }

    // Third Reports Menu
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    // Generate a report for all transactions within the current month,
                    // including the date, time, description, vendor, and amount for each transaction.
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, time, description, vendor, and amount for each transaction.
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, time, description, vendor, and amount for each transaction.

                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, time, description, vendor, and amount for each transaction.
                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, time, description, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
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
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
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

     */
}
