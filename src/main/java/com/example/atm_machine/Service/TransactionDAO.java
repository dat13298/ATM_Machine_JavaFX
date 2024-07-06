package com.example.atm_machine.Service;

import com.example.atm_machine.Connectivity.MySQLConnection;
import com.example.atm_machine.Entity.Account;
import com.example.atm_machine.Entity.EStatus;
import com.example.atm_machine.Entity.ETransactionType;
import com.example.atm_machine.Entity.Transaction;
import com.example.atm_machine.Generic.ATMRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionDAO implements ATMRepository<Transaction> {
    private Connection conn = MySQLConnection.getConnection();
    public static List<Transaction> transactions;

//    constructor null
    public TransactionDAO() {;}
//    constructor TransactionDAO
    public TransactionDAO(Connection conn) {
        this.conn = conn;
    }

//    insert Transaction
    @Override
    public Transaction save(Transaction transaction) {
        PreparedStatement pstmt = null;
        String query = "INSERT INTO transactions " +
                "(amount, description, date_time, card_number, type, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, transaction.getAmount());
            pstmt.setString(2, transaction.getDescription());
            pstmt.setTimestamp(3, Timestamp.valueOf(transaction.getDateTime()));
            pstmt.setString(4, transaction.getAccount().getCardNumber());
            pstmt.setString(5, transaction.getTransactionType().getType());
            pstmt.setString(6, transaction.getEStatus().getStatus());
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction findById(String id) {
        return null;
    }

//    get all Transaction by account
    public List<Transaction> findByAccount(Account account) {
        PreparedStatement pstmt = null;
        String query = "SELECT t.transactionId, t.amount, t.description, t.date_time, t.card_number, t.type, t.status " +
                "FROM transactions t " +
                "INNER JOIN accounts a ON t.card_number = a.card_number " +
                "WHERE a.card_number = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, account.getCardNumber());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
//                set value Transaction
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transactionId"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDescription(rs.getString("description"));
                Timestamp dateTime = rs.getTimestamp("date_time");
                LocalDateTime localDateTime = dateTime == null ? null : dateTime.toLocalDateTime();
                transaction.setDateTime(localDateTime);
                transaction.setAccount(account);
                transaction.setTransactionType(ETransactionType.fromString(rs.getString("type")));
                transaction.setEStatus(EStatus.getStatus(rs.getString("status")));
//                add Transaction to List
                transactions.add(transaction);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return transactions;
    }

}
