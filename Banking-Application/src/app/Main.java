package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

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
                case "2" -> deposit(scanner);
                case "3" -> withdraw(scanner);
                case "4" -> transfer(scanner);
                case "5" -> statement(scanner);
                case "6" -> listAccounts(scanner);
                case "7" -> searchAccounts(scanner);
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
        Double initial = Double.valueOf(amountStr);
        bankService.openAccount(name, email, type);

    }

    private static void deposit(Scanner scanner) {

    }

    private static void withdraw(Scanner scanner) {

    }

    private static void transfer(Scanner scanner) {

    }

    private static void statement(Scanner scanner) {

    }

    private static void listAccounts(Scanner scanner) {

    }

    private static void searchAccounts(Scanner scanner) {

    }

}
