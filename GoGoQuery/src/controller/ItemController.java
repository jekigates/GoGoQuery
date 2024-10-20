package controller;

import entity.Item;
import model.ItemModel;
import util.StageManager;
import view.ProductDetailView;

public class ItemController {
	private final StageManager sm = StageManager.getInstance();
	
	public void show(Item item) {
		ProductDetailView view = new ProductDetailView(item);
		sm.getStage().getScene().setRoot(view.getPane());
	}

	public void insertItem(String name, String description, String category, double price, int quantity) {
		ItemModel.insertItem(name, description, category, price, quantity);
	}
}
