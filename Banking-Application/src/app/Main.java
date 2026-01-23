package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        boolean running = true;

        System.out.println("Welcome to Console Bank....");

        while(running){
            System.out.println("""
                1. Open New Account
                2. Deposit money
                3. Withdraw money
                4. Transfer money
                5. Show Account statement
                6. List all Accounts
                7. Search Accounts by Customer name
                0. Exit the program
                """);

            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch(choice){
                case "0" -> running = false;
                case "1" -> openAccount(scanner, bankService);
                case "2" -> deposit(scanner, bankService);
                case "3" -> withdraw(scanner, bankService);
                case "4" -> transfer(scanner, bankService);
                case "5" -> statement(scanner, bankService);
                case "6" -> listAccounts(scanner, bankService);
                case "7" -> searchAccounts(scanner, bankService);
            }
        }

        scanner.close();
    }

    private static void openAccount(Scanner scanner, BankService bankService) {

        System.out.print("Customer Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Customer Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Account Type (Savings/Current): ");
        String type = scanner.nextLine().trim();

        System.out.print("Initial Deposit: ");
        String amountStr = scanner.nextLine().trim();
        double initial = Double.parseDouble(amountStr);
        String accountNumber = bankService.openAccount(name, email, type);

        if(initial > 0){
            bankService.deposit(accountNumber, initial, "Initial Deposit");
        }
        System.out.println("Account successfully opened, your account number is: " + accountNumber);
    }

    private static void deposit(Scanner scanner, BankService bankService) {

        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine().trim();
        System.out.print("Enter amount: ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.deposit(accountNumber, amount, "Deposit");
        System.out.print("Amount deposited!\n");

    }

    private static void withdraw(Scanner scanner,BankService bankService) {

        System.out.print("Enter your Account number: ");
        String accountNumber = scanner.nextLine().trim();
        System.out.print("Enter amount: ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.withdraw(accountNumber, amount, "Withdraw");
        System.out.print("Amount Withdrawn!\n");

    }

    private static void transfer(Scanner scanner, BankService bankService) {

        System.out.print("From Account: ");
        String from = scanner.nextLine().trim();
        System.out.print("To Account: ");
        String to = scanner.nextLine().trim();
        System.out.print("Amount to be Transferred: ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.transfer(from, to, amount, "Transfer");
        System.out.print("Amount Transferred Successfully!\n");
    }

    private static void statement(Scanner scanner, BankService bankService) {
        System.out.print("Account Number: ");
        String account = scanner.nextLine().trim();
        bankService.getStatement(account).forEach(t -> {
            System.out.println(t.getTimestamp() + " | " + t.getType() + " | " + t.getAmount()  + " | " + t.getNote());
        });
    }

    private static void listAccounts(Scanner scanner, BankService bankService) {
        bankService.listAccounts().forEach(a -> {
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }

    private static void searchAccounts(Scanner scanner, BankService bankService) {
        System.out.print("Customer name you wish to search for: ");
        String q = scanner.nextLine().trim();
        bankService.searchAccountsByCustomerName(q).forEach(account -> {
            System.out.println(account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance());
        });
    }

}
