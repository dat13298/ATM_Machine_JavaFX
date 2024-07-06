package com.atm_machine;

import com.atm_machine.Entity.Account;
import com.atm_machine.Service.AccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ATMLoginController {
    @FXML
    private TextField cardNumberInput;
    @FXML
    private PasswordField pinInput;
    @FXML
    private Label messageLabel;
    private final AccountDAO accountDAO;

    public ATMLoginController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
//        get cardNumber input
        String cardNumber = cardNumberInput.getText();
//        get PIN input
        String pin = pinInput.getText();
        try {
//            get account from database
            Account account = accountDAO.authenticate(cardNumber, pin);
//            check if existed
            if (account != null) {
//                set account logged
                ATMApp.setAuthenticatedAccount(account);
                messageLabel.setText("You have successfully logged in!");
//                display Main Menu page
                ATMApp.showMainMenu();
            } else {
                messageLabel.setText("Invalid Card Number or PIN.");
            }
        }catch (Exception e){
            messageLabel.setText(e.getMessage());
        }
    }
}