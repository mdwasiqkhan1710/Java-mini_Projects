package repository;

import domain.Transaction;

import java.util.*;

public class TransactionRepository {

    private static final Map<String, List<Transaction>> txByAccount = new HashMap<>();

    public static List<Transaction> findByAccount(String account) {
        return new ArrayList<>(txByAccount.getOrDefault(account, Collections.emptyList()));
    }

    public void add(Transaction transaction) {
        txByAccount.computeIfAbsent(transaction.getAccountNumber(),
                k -> new ArrayList<>()).add(transaction);
    }
}
