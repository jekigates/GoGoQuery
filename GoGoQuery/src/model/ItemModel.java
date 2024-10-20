package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Item;
import util.Connect;

public class ItemModel {
	private static final Connect con = Connect.getInstance();

	public static Item findItem(int itemId) {
		String query = "SELECT * FROM MsItem WHERE ItemID = ?";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, itemId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("ItemID");
				String name = rs.getString("ItemName");
				double price = rs.getDouble("ItemPrice");
				String description = rs.getString("ItemDesc");
				int stock = rs.getInt("ItemStock");
				String category = rs.getString("ItemCategory");

				Item item = new Item(id, name, price, description, stock, category);
				return item;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static ArrayList<String> getAvailableCategories() {
		ArrayList<String> categories = new ArrayList<String>();
		String query = "SELECT DISTINCT ItemCategory FROM MsItem WHERE ItemStock > 0";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String category = rs.getString("ItemCategory");

				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categories;
	}

	public static ArrayList<Item> getAvailableItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		String query = "SELECT * FROM MsItem WHERE ItemStock > 0";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ItemID");
				String name = rs.getString("ItemName");
				double price = rs.getDouble("ItemPrice");
				String description = rs.getString("ItemDesc");
				int stock = rs.getInt("ItemStock");
				String category = rs.getString("ItemCategory");

				Item item = new Item(id, name, price, description, stock, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	public static ArrayList<Item> getAvailableItemsByCategory(String pCategory) {
		ArrayList<Item> items = new ArrayList<Item>();
		String query = "SELECT * FROM MsItem WHERE ItemCategory LIKE ? AND ItemStock > 0";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, "%" + pCategory + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ItemID");
				String name = rs.getString("ItemName");
				double price = rs.getDouble("ItemPrice");
				String description = rs.getString("ItemDesc");
				int stock = rs.getInt("ItemStock");
				String category = rs.getString("ItemCategory");

				Item item = new Item(id, name, price, description, stock, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	public static ArrayList<Item> getAvailableItemsByKeyword(String pKeyword) {
		ArrayList<Item> items = new ArrayList<Item>();
		String query = "SELECT * FROM MsItem WHERE ItemName LIKE ? AND ItemStock > 0";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, "%" + pKeyword + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ItemID");
				String name = rs.getString("ItemName");
				double price = rs.getDouble("ItemPrice");
				String description = rs.getString("ItemDesc");
				int stock = rs.getInt("ItemStock");
				String category = rs.getString("ItemCategory");

				Item item = new Item(id, name, price, description, stock, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	public static void insertItem(String name, String description, String category, double price, int stock) {
		String query = "INSERT INTO MsItem (ItemName, ItemDesc, ItemCategory, ItemPrice, ItemStock) VALUES (?, ?, ?, ?, ?)";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, category);
			ps.setDouble(4, price);
			ps.setInt(5, stock);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
