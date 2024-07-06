package com.atm_machine.Service;

import com.atm_machine.Generic.ATMRepository;
import com.atm_machine.Connectivity.MySQLConnection;
import com.atm_machine.Entity.Account;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            pstmt.setString(1, encryptUsingMD5(cardNumber));
            pstmt.setString(2, encryptUsingMD5(pin));
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
//    hash string
    public static String encryptUsingMD5(String input) throws NoSuchAlgorithmException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        StringBuilder hashText = new StringBuilder(no.toString(16));
        while (hashText.length() < 32) {
            hashText.insert(0, "0");
        }
        return hashText.toString();
    }
}
