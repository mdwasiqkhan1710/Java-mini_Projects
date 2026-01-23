package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public String openAccount(String name, String email, String accountType) {

        String customerId = UUID.randomUUID().toString();
        Customer c = new Customer(email, customerId, name);
        customerRepository.save(c);
        String accountNumber = getAccountNumber();

        // CORRECTED: Fixed the parameter order
        Account account = new Account(accountNumber, customerId, (double) 0, accountType);

        //Save account data
        accountRepository.save(account);

        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found for account number: " + accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.DEPOSIT);
        transactionRepository.add(transaction);
    }

    @Override
    public void deposit() {

    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found for account number: " + accountNumber));
        if(account.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient Balance in your account!");
        }
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.WITHDRAW);
        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {
        if(fromAcc.equals(toAcc)){
            throw new RuntimeException("Amount can not be transferred to same bank account, recheck both account numbers!");
        }
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new RuntimeException("Account not found for account number: " + fromAcc));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new RuntimeException("Account not found for account number: " + toAcc));
        if(from.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient Balance in your account!");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        Transaction fromTransaction = new Transaction(from.getAccountNumber(), amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.TRANSFER_OUT);
        Transaction toTransaction = new Transaction(to.getAccountNumber(), amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.TRANSFER_IN);

        transactionRepository.add(fromTransaction);
        transactionRepository.add(toTransaction);
    }

    @Override
    public List<Transaction> getStatement(String account) {
        return TransactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
        List<Account> result = new ArrayList<>();
        for(Customer c : customerRepository.findAll()){
            if(c.getName().toLowerCase().contains(query))
                result.addAll(accountRepository.findByCustomerId(c.getId()));
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        return String.format("AC%06d", size);
    }
}