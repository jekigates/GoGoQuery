package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Item;
import util.Connect;
import util.UserSession;

public class CartModel {
	private static final Connect con = Connect.getInstance();
	
	public static int getItemUnit(Item item) {
		UserSession session = UserSession.getInstance();
		String query = "SELECT Quantity FROM MsCart WHERE UserID = ? AND ItemID = ?";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, session.getUserId());
			ps.setInt(2, item.getId());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int quantity = rs.getInt("Quantity");
				
				return quantity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public static void insertItemCart(Item item, int quantity) {
		UserSession session = UserSession.getInstance();
		String query = "INSERT INTO MsCart (UserID, ItemID, Quantity) VALUES(?, ?, ?)";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, session.getUserId());
			ps.setInt(2, item.getId());
			ps.setInt(3, quantity);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addItemCart(Item item, int quantity, int unit) {
	    UserSession session = UserSession.getInstance();
	    
	    // Calculate the desired new total quantity
	    int newTotalQuantity = quantity + unit;

	    // Ensure the total quantity does not exceed the stock
	    if (newTotalQuantity > item.getStock()) {
	        newTotalQuantity = item.getStock();  // Limit to stock
	    }

	    String query = "UPDATE MsCart SET Quantity = ? WHERE UserID = ? AND ItemID = ?";
	    
	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, newTotalQuantity);  // Use the correct final total quantity
	        ps.setInt(2, session.getUserId());
	        ps.setInt(3, item.getId());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void updateItemToCart(Item item, int quantity) {
	    UserSession session = UserSession.getInstance();
		
		String query = "UPDATE MsCart SET Quantity = ? WHERE UserID = ? AND ItemID = ?";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, quantity); // Use the correct final total quantity
			ps.setInt(2, session.getUserId());
			ps.setInt(3, item.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void removeItemFromCart(Item item) {
		UserSession session = UserSession.getInstance();
		int userId = session.getUserId(); // Get the current user's ID
		String query = "DELETE FROM MsCart WHERE UserID = ? AND ItemID = ?";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, userId);
			ps.setInt(2, item.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void checkout() {
	    UserSession session = UserSession.getInstance();
	    int userId = session.getUserId();
	    int transactionId = insertTransactionHeader(userId); // Get the transaction ID after inserting header

	    // Get all items in the user's cart
	    String query = "SELECT ItemID, Quantity FROM MsCart WHERE UserID = ?";
	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        // Process each item in the cart
	        while (rs.next()) {
	            int itemId = rs.getInt("ItemID");
	            int quantity = rs.getInt("Quantity");

	            // Insert transaction details for each item
	            insertTransactionDetail(transactionId, itemId, quantity);

	            // Update the stock in the MsItem table
	            updateStock(itemId, quantity);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Clear the user's cart after checkout
	    clearCart(userId);
	}

	private static int insertTransactionHeader(int userId) {
	    String query = "INSERT INTO TransactionHeader (UserID, DateCreated, Status) VALUES (?, ?, ?)";

	    try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        ps.setInt(1, userId);
	        ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now())); // Current date in yyyy-MM-dd format
	        ps.setString(3, "In Queue");
	        ps.executeUpdate();

	        // Get the generated TransactionID
	        ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            return generatedKeys.getInt(1); // Return the generated TransactionID
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // Return an invalid TransactionID if insertion fails
	}

	private static void insertTransactionDetail(int transactionId, int itemId, int quantity) {
	    String query = "INSERT INTO TransactionDetail (TransactionID, ItemID, Quantity) VALUES (?, ?, ?)";

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, transactionId);
	        ps.setInt(2, itemId);
	        ps.setInt(3, quantity);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private static void updateStock(int itemId, int quantity) {
	    String query = "UPDATE MsItem SET ItemStock = ItemStock - ? WHERE ItemID = ?";

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, quantity);
	        ps.setInt(2, itemId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private static void clearCart(int userId) {
	    String query = "DELETE FROM MsCart WHERE UserID = ?";

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, userId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
