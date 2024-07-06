package com.example.atm_machine.Service;

import com.example.atm_machine.Connectivity.MySQLConnection;
import com.example.atm_machine.Entity.Account;
import com.example.atm_machine.Generic.ATMRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO implements ATMRepository<Account> {
    private Connection conn = MySQLConnection.getConnection();

//    constructor null
    public AccountDAO(){;}
//    constructor AccountDAO
    public AccountDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public Account save(Account user) {
        return null;
    }

//    Update balance
    @Override
    public Account update(Account user) {
        PreparedStatement pstmt = null;
        String query = "UPDATE accounts a " +
                "SET a.pin = ? , a.balance = ? " +
                "WHERE a.card_number = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getPin());
            pstmt.setDouble(2, user.getBalance());
            pstmt.setString(3, user.getCardNumber());
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public Account findById(String id) {
        return null;
    }

//    get account by cardNumber and PIN
    public Account authenticate(String cardNumber, String pin) {
        PreparedStatement pstmt = null;
        String query = "SELECT card_number, pin, balance" +
                " FROM accounts " +
                "WHERE card_number = ? AND pin = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, pin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
//                Account authenticate
                return new Account(
                        rs.getString("card_number")
                        ,rs.getString("pin")
                        ,rs.getDouble("balance"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
