package com.example.atm_machine;

import com.example.atm_machine.Entity.Account;
import com.example.atm_machine.Entity.Transaction;
import com.example.atm_machine.Service.AccountDAO;
import com.example.atm_machine.Service.DepositThread;
import com.example.atm_machine.Service.TransactionDAO;
import com.example.atm_machine.Service.WithdrawalThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ATMMainMenuController {
//    Properties ATMMainMenu
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker endDatePicker;
    @FXML
    private ListView<Transaction> transactionListView;
    @FXML
    private Label messageLabel;

    private final Account authenticateAccount = ATMApp.getAuthenticatedAccount(); //get account logged
    private double amount; //amount to withdrawal or deposit
    private final TextInputDialog dialog;
    private final Transaction newTransaction;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
//    end properties

//    Constructor ATMMainMenu
    public ATMMainMenuController(TextInputDialog dialog, Transaction newTransaction, AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.dialog = dialog;
        this.newTransaction = newTransaction;
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    } // end Constructor

//
    @FXML
    public void handleCheckBalance(ActionEvent actionEvent) {
        messageLabel.setText("Bank Balance: " + authenticateAccount.getBalance());
    }

    @FXML
    public void handleDeposit(ActionEvent actionEvent) {
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Deposit Money");
        dialog.setContentText("Please enter the amount you would like to deposit: ");
        dialog.showAndWait().ifPresent(response -> {
            amount = Double.parseDouble(dialog.getEditor().getText());
//            set amount input to transaction for thread
            newTransaction.setAmount(amount);
            newTransaction.setDateTime(LocalDateTime.now());

//            Run deposit thread
            DepositThread depositThread = new DepositThread(
                    authenticateAccount
                    , newTransaction
                    , accountDAO
                    , transactionDAO
            );
            Thread thread = new Thread(depositThread);
            thread.start();
            try {
                thread.join();//wait thread done
            } catch (InterruptedException e) {
                e.printStackTrace();
                messageLabel.setText("Error");
            }
            messageLabel.setText("Deposited $ " + amount + " to " + authenticateAccount.getBalance());
        });
    }

    @FXML
    public void handleWithdraw(ActionEvent actionEvent) {
        dialog.setTitle("Withdraw");
        dialog.setHeaderText("Withdraw Money");
        dialog.setContentText("Please enter the amount you would like to withdraw: ");
        dialog.showAndWait().ifPresent(response -> {
            amount = Double.parseDouble(dialog.getEditor().getText());
            newTransaction.setAmount(amount);
            newTransaction.setDateTime(LocalDateTime.now());

//            run withdrawal thread
            WithdrawalThread withdrawalThread = new WithdrawalThread(
                    authenticateAccount
                    , newTransaction
                    , accountDAO
                    , transactionDAO
            );
            Thread thread = new Thread(withdrawalThread);
            thread.start();
            try {
                thread.join();//wait thread done
            } catch (InterruptedException e) {
                e.printStackTrace();
                messageLabel.setText("Error");
            }

//            message when done
            if(authenticateAccount.getBalance() >= amount){
                messageLabel.setText("Withdraw Successful");
            }
            if(authenticateAccount.getBalance() < amount){
                messageLabel.setText("Withdraw Failed");
            }
        });
    }

    @FXML
    public void handleTransactionHistory(ActionEvent actionEvent) {
//        get transaction from database by account.card_number
        List<Transaction> transactions = transactionDAO.findByAccount(authenticateAccount);
//        convert List to ObservableList
        ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(transactions);
//        display with ListView
        transactionListView.setItems(transactionObservableList);
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) {
//        set authenticate account null
        ATMApp.setAuthenticatedAccount(null);
//        display Login page
        ATMApp.showLogin();
        messageLabel.setText("Logged Out");
    }

    @FXML
    public void handleFilterTransactions(ActionEvent actionEvent) {
        LocalDate starDate = startDatePicker.getValue();//start date to filer
        LocalDate endDate = endDatePicker.getValue();//end date to filter

//        check if null
        if(starDate == null || endDate == null){
            messageLabel.setText("Please enter a valid date");
        }
//        check if starDate after endDate
        if(starDate.isAfter(endDate)){
            messageLabel.setText("End Date is after Start Date");
        }

        if(starDate.isBefore(endDate)){
//            filter List transaction of this account from database by star date and end date
            List<Transaction> transactions = transactionDAO.findByAccount(authenticateAccount).stream()
                    .filter(a->a.getDateTime().toLocalDate().isAfter(starDate)
                    && a.getDateTime().toLocalDate().isBefore(endDate))
                    .toList();
//            convert List to ObservableList
            ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(transactions);
//            display with ListView
            transactionListView.setItems(transactionObservableList);
        }
    }
}
