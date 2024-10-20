package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Cart;
import entity.Item;
import entity.User;
import util.Connect;
import util.UserSession;

public class UserModel {
	private static final Connect con = Connect.getInstance();

	public static User findUser(String pEmail, String pPassword) {
		String query = "SELECT * FROM MsUser WHERE UserEmail = ? AND UserPassword = ?";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, pEmail);
			ps.setString(2, pPassword);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("UserID");
				String dob = String.valueOf(rs.getDate("UserDOB"));
				String email = rs.getString("UserEmail");
				String password = rs.getString("UserPassword");
				String gender = rs.getString("UserGender");
				String role = rs.getString("UserRole");
				User user = new User(id, dob, email, password, gender, role);
				
				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void getUserCarts() {
		ArrayList<Cart> carts = new ArrayList<Cart>();
		UserSession session = UserSession.getInstance();
		String query = "SELECT * FROM MsCart WHERE UserID = ?";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, session.getUserId());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt("UserID");
				int itemId = rs.getInt("ItemID");
				int quantity = rs.getInt("quantity");
				
				Cart cart = new Cart(userId, itemId, quantity);
				Item item = ItemModel.findItem(itemId);
				cart.setItem(item);
				carts.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		session.getUser().setCarts(carts);
	}

	public static void register(String email, String password, String gender, String role, String dob) {
		String query = "INSERT INTO MsUser (UserEmail, UserPassword, UserGender, UserRole, UserDob) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, gender);
			ps.setString(4, role);
			ps.setString(5, dob);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isEmailTaken(String email) {
		String query = "SELECT * FROM MsUser WHERE UserEmail = ?";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
