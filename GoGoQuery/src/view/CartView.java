package view;

import java.util.Optional;

import controller.CartController;
import controller.HomeController;
import controller.LoginController;
import entity.Cart;
import entity.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.UserModel;
import util.ParameterManager;
import util.StageManager;
import util.UserSession;

public class CartView {
	private final StageManager sm = StageManager.getInstance();
	private BorderPane bp;
	private VBox vb, vbItems, vbBilling;
	private HBox hbNavbar, hbItems;
	private FlowPane fpSearch;
	private Label lblTitle, lblCart, lblShowing, lblBilling, lblTotal, lblTax, lblCartEmpty;
	private TextField tfSearch;
	private Button btnSearch, btnLogout, btnCart, btnCheckout;
	private final UserSession session = UserSession.getInstance();
	private final HomeController hc = new HomeController();
	private ListView<HBox> lvItems;
	private CartController cc = new CartController();
	
	public CartView() {
		UserModel.getUserCarts();

		initialize();
		addComponent();
		arrangeComponent();
		setMouseEvent();
		setKeyEvent();

		loadListView();
	}

	private void loadListView() {
		double totalPrice = 0d;
		
		lvItems.getItems().clear();

		for (Cart cart : session.getUser().getCarts()) {
			Item item = cart.getItem();

			HBox hbItem = new HBox();
			Rectangle recItem = new Rectangle(40, 40, Color.GRAY);

			HBox hbItemDiv = new HBox();
			VBox vbItem = new VBox();
			Label lblItemName = new Label(item.getName());
			lblItemName.getStyleClass().addAll("text-2xl", "text-white");

			FlowPane fpItem = new FlowPane();
			Label lblItemPrice = new Label("$" + String.valueOf(item.getPrice()));
			lblItemPrice.getStyleClass().addAll("text-orange");
			fpItem.getChildren().addAll(lblItemPrice);
			vbItem.getChildren().addAll(lblItemName, fpItem);
			fpItem.setHgap(16);
			VBox vbQuantity = new VBox();
			Button btnRemove = new Button("x");
			Spinner<Integer> spQuantity = new Spinner<Integer>(0, item.getStock(), cart.getQuantity());
			vbQuantity.getChildren().addAll(btnRemove, spQuantity);
			vbQuantity.setSpacing(16);
			spQuantity.setMaxWidth(100);

			hbItem.getChildren().addAll(recItem, vbItem, vbQuantity);
			hbItem.setPadding(new Insets(16));
			hbItem.setAlignment(Pos.CENTER_LEFT);
			hbItem.getStyleClass().add("bg-gray");
			hbItemDiv.getChildren().add(hbItem);
			hbItemDiv.setPadding(new Insets(0, 0, 16, 0));

			lvItems.getItems().add(hbItemDiv);

			totalPrice += cart.getQuantity() * item.getPrice();

			spQuantity.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue == 0) {
			        removeItemFromCart(item);
				} else {
					cc.updateItemToCart(item, spQuantity.getValue());
				}
				UserModel.getUserCarts();
				loadListView();
			});
			
			btnRemove.setOnMouseClicked(event -> {
				removeItemFromCart(item);
				UserModel.getUserCarts();
				loadListView();
			});

		}
        editPage();
		lblTotal.setText("Total : $" + totalPrice);
		lblShowing.setText("Showing " + session.getUser().getCarts().size() + " items");
	}
	
	private void removeItemFromCart(Item item) {
		Alert alert = new Alert(AlertType.CONFIRMATION); // Use CONFIRMATION type
	    alert.setTitle("Item Removal Confirmation");
	    alert.setHeaderText("Do you want to remove this from your cart?");
	    alert.setContentText("Please confirm your choice");

	    // Show the alert and wait for the user's response
	    Optional<ButtonType> result = alert.showAndWait();
	    
	    // Check if the user clicked the OK button
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        // Proceed with removing the item
	        cc.removeItemFromCart(item); // Call method to remove item from cart
	    } 
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
		
		btnCheckout.setOnMouseClicked(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Checkout Confirmation");
			alert.setHeaderText("Are you sure you want to checkout your cart?");
			alert.setContentText("Please confirm your choice");
			
			Optional<ButtonType> result = alert.showAndWait();
			
		    if (result.isPresent() && result.get() == ButtonType.OK) {
		    	cc.checkout();
		    	UserModel.getUserCarts();
		    	loadListView();
		    	
		    	alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Transaction Information");
		    	alert.setHeaderText("Transaction success!");
		    	alert.setContentText("Your order is now in queue");
		    	alert.showAndWait();
		    }
		});
	}

	private void arrangeComponent() {
		bp.setPadding(new Insets(16));
		bp.getStyleClass().add("bg-gray");
		fpSearch.setAlignment(Pos.CENTER);
		hbNavbar.setSpacing(8);

		lblTitle.getStyleClass().addAll("text-3xl", "font-bold");
		hbNavbar.getStyleClass().add("bg-white");
		hbNavbar.setStyle("-fx-border-radius: 10px;");
		hbItems.setSpacing(32);
		vb.setSpacing(32);
		lblCart.getStyleClass().addAll("text-5xl", "text-white", "font-bold");
		lblCartEmpty.getStyleClass().addAll("text-3xl", "text-white");
		lblShowing.getStyleClass().add("text-white");
		lblBilling.getStyleClass().add("text-white");
		lblTotal.getStyleClass().addAll("text-orange", "font-bold", "text-3xl");
		lblTax.getStyleClass().add("text-white");
		btnCheckout.getStyleClass().addAll("bg-orange", "text-white");
		lvItems.setPrefWidth(600);
	}

	private void addComponent() {
		bp.setTop(vb);

		fpSearch.getChildren().addAll(tfSearch, btnSearch);

		hbNavbar.getChildren().addAll(lblTitle, fpSearch, btnCart, btnLogout);
		hbNavbar.setAlignment(Pos.CENTER);

		vbItems.getChildren().addAll(lblShowing, lvItems);
		vbBilling.getChildren().addAll(lblBilling, lblTotal, lblTax, btnCheckout);

		hbItems.getChildren().addAll(vbItems, vbBilling);

		editPage();
	}
	
	private void editPage() {
		vb.getChildren().clear();
		

		if (session.getUser().getCarts().size() == 0) {
			vb.getChildren().addAll(hbNavbar, lblCart, lblCartEmpty);
		} else {
			vb.getChildren().addAll(hbNavbar, lblCart, hbItems);
		}
	}

	private void initialize() {
		sm.getStage().setTitle("GoGoQuery - My Cart");

		bp = new BorderPane();
		hbItems = new HBox();
		vb = new VBox();
		vbItems = new VBox();
		vbBilling = new VBox();
		hbNavbar = new HBox();
		fpSearch = new FlowPane();
		lblTitle = new Label("GoGoQuery");
		lblCart = new Label(session.getUsername() + "'s Cart");
		lblShowing = new Label("Showing " + session.getUser().getCarts().size() + " items in cart");
		lblBilling = new Label("Billing Summary");
		lblTotal = new Label("Total : $asda");
		lblTax = new Label("* Tax and delivery cost included");
		tfSearch = new TextField();
		btnCart = new Button("My Cart");
		btnSearch = new Button("Search");
		btnLogout = new Button("Logout");
		btnCheckout = new Button("Checkout Items");
		lvItems = new ListView<HBox>();
		lblCartEmpty = new Label("Your cart is empty!");
	}

	public Pane getPane() {
		return bp;
	}
}
