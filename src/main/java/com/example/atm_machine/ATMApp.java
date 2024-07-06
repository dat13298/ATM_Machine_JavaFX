package com.example.atm_machine;

import com.example.atm_machine.Connectivity.MySQLConnection;
import com.example.atm_machine.Entity.Account;
import com.example.atm_machine.Entity.Transaction;
import com.example.atm_machine.Service.AccountDAO;
import com.example.atm_machine.Service.TransactionDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ATMApp extends Application {
    private static Stage primaryStage;
    private static Account authenticatedAccount;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ATMApp.primaryStage = primaryStage;
        primaryStage.setTitle("ATM Machine");
        showLogin();
    }

//    LOGIN PAGE
    public static void showLogin() {
        try {
//            Instantiate the necessary object
            AccountDAO accountDAO = new AccountDAO(MySQLConnection.getConnection());

//            Create FXMLLoader and set ControllerFactory
            FXMLLoader loader = new FXMLLoader(ATMApp.class.getResource("login.fxml"));
            loader.setControllerFactory(param -> new ATMLoginController(accountDAO));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    MAIN MENU PAGE
    public static void showMainMenu(){
        try {
//            Instantiate the necessary object
            TextInputDialog dialog = new TextInputDialog();
            Transaction newTransaction = new Transaction();
            AccountDAO accountDAO = new AccountDAO(MySQLConnection.getConnection());
            TransactionDAO transactionDAO = new TransactionDAO(MySQLConnection.getConnection());
            TransactionDAO.transactions = new ArrayList<Transaction>();

//            Create FXMLLoader and set ControllerFactory
            FXMLLoader loader = new FXMLLoader(ATMApp.class.getResource("MainMenu.fxml"));
            loader.setControllerFactory(param -> new ATMMainMenuController(dialog, newTransaction, accountDAO, transactionDAO));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    set authenticate Account when logged
    public static void setAuthenticatedAccount(Account account) {
        authenticatedAccount = account;
    }

//    get authenticate Account when logged
    public static Account getAuthenticatedAccount() {
        return authenticatedAccount;
    }
}