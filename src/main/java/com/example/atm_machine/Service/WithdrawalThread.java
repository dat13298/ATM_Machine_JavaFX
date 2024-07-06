package com.example.atm_machine.Service;

import com.example.atm_machine.Entity.Account;
import com.example.atm_machine.Entity.EStatus;
import com.example.atm_machine.Entity.ETransactionType;
import com.example.atm_machine.Entity.Transaction;

import java.time.LocalDateTime;

public class WithdrawalThread extends TransactionService implements Runnable{

//constructor extends
    public WithdrawalThread(Account account, Transaction transaction, AccountDAO accountDAO, TransactionDAO transactionDAO) {
        super(account, transaction, accountDAO, transactionDAO);
    }

    @Override
    public Account transaction() {
        try {
            super.getTransaction().setTransactionId(0);
            super.getTransaction().setAmount(super.getTransaction().getAmount());
            super.getTransaction().setAccount(super.getAccount());
            super.getTransaction().setDescription("Withdrawal Money");
            super.getTransaction().setDateTime(LocalDateTime.now());
            super.getTransaction().setTransactionType(ETransactionType.W);

//            check if balance already for withdrawal
            if(super.getAccount().getBalance() >= super.getTransaction().getAmount() ){
                super.getAccount().setBalance(super.getAccount().getBalance() - super.getTransaction().getAmount());
                super.getTransaction().setEStatus(EStatus.C);
//            update balance
                super.getAccountDAO().update(super.getAccount());
            }

//            set status if not enough for withdrawal
            if(super.getAccount().getBalance() < super.getTransaction().getAmount()){
                super.getTransaction().setEStatus(EStatus.R);
            }

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
