package com.atm_machine.Service;

import com.atm_machine.Entity.Account;
import com.atm_machine.Entity.Transaction;

//abstract class transactionService
public abstract class TransactionService {
//    properties
    private final Account account;
    private final Transaction transaction;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

//    constructor
    public TransactionService(Account account, Transaction transaction, AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.account = account;
        this.transaction = transaction;
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }//end constructor

    public Account getAccount() {
        return account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }

    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

//    abstract method transaction
    public abstract Account transaction();

}
