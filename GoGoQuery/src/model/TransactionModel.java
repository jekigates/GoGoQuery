package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Transaction;
import util.Connect;

public class TransactionModel {
	/*
	 * SELECT ms.UserID, SUM(td.Quantity * mi.ItemPrice) AS Total FROM transactionheader th
JOIN transactiondetail td ON th.TransactionID = td.TransactionID
JOIN msuser ms ON th.UserID = ms.UserID
JOIN msitem mi ON td.ItemID = mi.ItemID
GROUP BY ms.UserID
	 * 
	 * SELECT th.TransactionID, th.UserID, ms.UserEmail, th.DateCreated, th.Total, th.Status FROM TransactionHeader th
JOIN MsUser ms ON th.UserID = ms.UserID
	 * 
	 * 
	 * */
    private static final Connect con = Connect.getInstance();

    public static ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        
        String query = "SELECT th.TransactionID, th.UserID, ms.UserEmail, th.DateCreated, SUM(td.Quantity * mi.ItemPrice) AS Total, th.Status FROM transactionheader th JOIN transactiondetail td ON th.TransactionID = td.TransactionID JOIN msuser ms ON th.UserID = ms.UserID JOIN msitem mi ON td.ItemID = mi.ItemID GROUP BY th.TransactionID, th.UserID, ms.UserEmail, th.DateCreated ORDER BY th.TransactionID DESC";
        
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                // Fetch data from the ResultSet
                int transactionId = rs.getInt("TransactionID");
                int userId = rs.getInt("UserID");
                String userEmail = rs.getString("UserEmail");
                String date = rs.getString("DateCreated");
                double total = rs.getDouble("Total");
                String status = rs.getString("Status");

                // Create a Transaction object and add it to the list
                Transaction transaction = new Transaction(transactionId, userId, userEmail, date, total, status);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public static void sendPackage(int transactionId) {
        // SQL query to update the transaction status to 'Sent'
        String query = "UPDATE transactionheader SET Status = 'Sent' WHERE TransactionID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            // Set the transaction ID in the prepared statement
            ps.setInt(1, transactionId);

            // Execute the update
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
