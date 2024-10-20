package controller;

import entity.Item;
import util.StageManager;
import view.ProductDetailView;

public class ProductController {
	private final StageManager sm = StageManager.getInstance();
	
	public void show(Item item) {
		ProductDetailView view = new ProductDetailView(item);
		sm.getStage().getScene().setRoot(view.getPane());
	}
}
