package com.atm_machine.Service;

import com.atm_machine.Entity.Account;
import com.atm_machine.Entity.EStatus;
import com.atm_machine.Entity.ETransactionType;
import com.atm_machine.Entity.Transaction;

import java.time.LocalDateTime;

public class DepositThread extends TransactionService implements Runnable {
//    construct extends
    public DepositThread(Account account, Transaction transaction, AccountDAO accountDAO, TransactionDAO transactionDAO) {
        super(account, transaction, accountDAO, transactionDAO);
    }

    @Override
    public Account transaction() {
        try {
//            deposit money
            super.getAccount().setBalance(super.getAccount().getBalance() + super.getTransaction().getAmount());
//            set value for transaction
            super.getTransaction().setTransactionId(0);
            super.getTransaction().setAmount(super.getTransaction().getAmount());
            super.getTransaction().setAccount(super.getAccount());
            super.getTransaction().setDescription("Deposit Money");
            super.getTransaction().setDateTime(LocalDateTime.now());
            super.getTransaction().setTransactionType(ETransactionType.D);
            super.getTransaction().setEStatus(EStatus.C);
//            update balance
            super.getAccountDAO().update(super.getAccount());
//            insert new transaction
            super.getTransactionDAO().save(super.getTransaction());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getAccount();
    }

    @Override
    public void run() {
        transaction();
    }
}
