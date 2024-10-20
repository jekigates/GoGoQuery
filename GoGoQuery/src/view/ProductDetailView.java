package view;

import controller.CartController;
import controller.HomeController;
import controller.LoginController;
import entity.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.ParameterManager;
import util.StageManager;
import util.UserSession;

public class ProductDetailView {
	private final StageManager sm = StageManager.getInstance();
	private BorderPane bp;
	private VBox vb, vbItem, vbRight, vbBestSeller, vbQuantity;
	private HBox hbNavbar, hbItem;
	private FlowPane fpSearch, fpStock;
	private Label lblTitle, lblItemName, lblItemPrice, lblItemCategory, lblItemDetail, lblItemDescription,
			lblBestSeller, lblSetItemQuantity, lblStock;
	private TextField tfSearch;
	private Button btnSearch, btnLogout, btnAddToCart, btnCart;
	private Spinner<Integer> spQuantity;
	private final UserSession session = UserSession.getInstance();
	private final HomeController hc = new HomeController();
	private Item item;
	private Rectangle recItem;

	public ProductDetailView(Item item) {
		this.item = item;

		initialize();
		addComponent();
		arrangeComponent();
		setMouseEvent();
		setKeyEvent();
	}

	private void setKeyEvent() {
		tfSearch.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				ParameterManager.put("keyword", tfSearch.getText());

				hc.index();
			}
		});
	}

	private void setMouseEvent() {
		lblTitle.setOnMouseClicked(event -> {
			hc.index();
		});

		btnSearch.setOnMouseClicked(event -> {
			ParameterManager.put("keyword", tfSearch.getText());

			hc.index();
		});

		btnLogout.setOnMouseClicked(event -> {
			session.cleanUserSession();

			LoginController lc = new LoginController();
			lc.index();
		});
		
		btnAddToCart.setOnMouseClicked(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			
			CartController cc = new CartController();
			
			int unit = cc.insertItemToCart(item, spQuantity.getValue());

			int newUnit = unit + spQuantity.getValue();
			if (newUnit > item.getStock()) {
				newUnit = item.getStock();
			}
			
			if (unit == 0) {
//				kalau belum ada di cart
				alert.setTitle("Item Added To Cart");
				alert.setHeaderText("Item Added Successfully");
				alert.setContentText("The item '" + item.getName() + "' has been added to your cart with a quantity of " + newUnit + " unit(s).");
			} else if (unit < item.getStock()) {
//				kalau ada di cart, tapi pas nambah belum max angka stock
				alert.setTitle("Item Already in Cart");
				alert.setHeaderText("Quantity Updated");
				alert.setContentText("'" + item.getName() + "' is already in your cart[" + unit + " unit(s)]. The quantity has been updated to " + newUnit + " units.");
			} else {
//				kalau udah max sesuai stock
				alert.setTitle("Quantity Exceeds Stock");
				alert.setHeaderText("Not enough stock available");
				alert.setContentText("There are " + item.getStock() + " units left in stock for this item, and you already have " + unit + " units in your cart. The quantity in your cart has been adjusted to the maximum available stock.");
			}	
			alert.showAndWait();
		});
		
		btnCart.setOnMouseClicked(event -> {
			HomeController hc = new HomeController();
			hc.cartIndex();
		});
	}

	private void arrangeComponent() {
		bp.setTop(vb);

		bp.setPadding(new Insets(16));
		bp.getStyleClass().add("bg-gray");
		fpSearch.setAlignment(Pos.CENTER);
		hbNavbar.setSpacing(8);

		lblTitle.getStyleClass().addAll("text-3xl", "font-bold", "cursor-pointer");

		lblItemName.getStyleClass().addAll("text-xl", "font-bold", "text-white");
		lblItemPrice.getStyleClass().addAll("text-6xl", "font-bold", "text-orange");
		lblItemCategory.getStyleClass().add("text-white");
		lblSetItemQuantity.getStyleClass().add("text-white");
		lblItemDetail.getStyleClass().add("text-orange");
		lblItemDescription.getStyleClass().add("text-white");

		lblBestSeller.getStyleClass().add("text-white");
		lblStock.getStyleClass().add("text-white");
		fpStock.setHgap(16);
		vbQuantity.setSpacing(16);
		btnAddToCart.getStyleClass().addAll("bg-orange", "text-white", "font-bold");

		hbItem.setSpacing(32);
		hbNavbar.getStyleClass().add("bg-white");
		hbNavbar.setStyle("-fx-border-radius: 10px;");
		vb.setSpacing(32);
	}

	private void addComponent() {
		fpSearch.getChildren().addAll(tfSearch, btnSearch);
		
		hbNavbar.getChildren().addAll(lblTitle, fpSearch, btnCart, btnLogout);
		hbNavbar.setAlignment(Pos.CENTER);

		vbItem.getChildren().addAll(lblItemName, lblItemPrice, lblItemCategory, lblItemDetail, lblItemDescription);

		vbBestSeller.getChildren().add(lblBestSeller);
		fpStock.getChildren().addAll(spQuantity, lblStock);
		vbQuantity.getChildren().addAll(lblBestSeller, lblSetItemQuantity, fpStock, btnAddToCart);
		vbRight.getChildren().addAll(vbBestSeller, vbQuantity);
		
		hbItem.getChildren().addAll(recItem, vbItem, vbRight);
		
		vb.getChildren().addAll(hbNavbar, hbItem);
		
	}

	private void initialize() {
		sm.getStage().setTitle("GoGoQuery - Product Detail");

		bp = new BorderPane();
		vb = new VBox();
		vbRight = new VBox();
		vbBestSeller = new VBox();
		vbQuantity = new VBox();
		hbNavbar = new HBox();
		fpSearch = new FlowPane();
		fpStock = new FlowPane();
		lblTitle = new Label("GoGoQuery");
		tfSearch = new TextField();
		btnSearch = new Button("Search");
		btnLogout = new Button("Logout");
		btnAddToCart = new Button("Add To Cart");
		btnCart = new Button("My Cart");
		recItem = new Rectangle(320, 320, Color.GRAY);
		hbItem = new HBox();
		vbItem = new VBox();
		lblItemName = new Label(item.getName());
		lblItemPrice = new Label("$" + item.getPrice());
		lblItemCategory = new Label("Category : " + item.getCategory());
		lblItemDetail = new Label("Item Detail");
		lblItemDescription = new Label(item.getDescription());
		lblBestSeller = new Label("Best Seller!");
		lblSetItemQuantity = new Label("Set item quantity");
		lblStock = new Label("Stock : " + item.getStock());
		spQuantity = new Spinner<Integer>(1, item.getStock(), 1);
	}

	public Pane getPane() {
		return bp;
	}
}
