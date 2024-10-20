package controller;

import entity.Item;
import model.CartModel;

public class CartController {
	public int insertItemToCart(Item item, int quantity) {
//		unit tuh jumlah item di cart user
		int unit = CartModel.getItemUnit(item);
		
		if (unit == 0) {
//			kalau belum ada di cart, tambahin
			CartModel.insertItemCart(item, quantity);
		} else {
//			kalau udh ada di cart, update
			CartModel.addItemCart(item, quantity, unit);
		}
		
		return unit;
	}

	public void updateItemToCart(Item item, Integer quantity) {
		CartModel.updateItemToCart(item, quantity);
	}

	public void removeItemFromCart(Item item) {
		CartModel.removeItemFromCart(item);
	}

	public void checkout() {
		CartModel.checkout();
	}
}
