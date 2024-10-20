package controller;

import java.util.ArrayList;

import entity.Item;
import model.ItemModel;
import util.StageManager;
import util.UserSession;
import view.CartView;
import view.ShopperHomeView;

public class HomeController {
	private final StageManager sm = StageManager.getInstance();
	private final UserSession session = UserSession.getInstance();
	
	public void index() {
		ArrayList<String> categories = ItemModel.getAvailableCategories();
		ArrayList<Item> items = ItemModel.getAvailableItems();
		
		switch (session.getUser().getRole()) {
		case "Shopper":
			ShopperHomeView view = new ShopperHomeView(categories, items);
			sm.getStage().getScene().setRoot(view.getPane());
			break;
		case "Manager":
			break;
		default:
			break;
		}
	}

	public ArrayList<Item> searchItemsByCategory(String category) {
		ArrayList<Item> items = ItemModel.getAvailableItemsByCategory(category);
		
		return items;
	}
	
	public ArrayList<Item> searchItemsByKeyword(String keyword) {
		ArrayList<Item> items = ItemModel.getAvailableItemsByKeyword(keyword);
		
		return items;
	}
	
	public void cartIndex() {
		CartView view = new CartView();
		sm.getStage().getScene().setRoot(view.getPane());
	}
}
