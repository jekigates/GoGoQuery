package view;

import java.util.ArrayList;

import controller.HomeController;
import controller.LoginController;
import controller.ProductController;
import entity.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

public class ShopperHomeView {
	private final StageManager sm = StageManager.getInstance();
	private BorderPane bp;
	private VBox vb, vbFilter, vbCategory, vbShowing;
	private HBox hbNavbar, hbItems;
	private FlowPane fpSearch, fpCategory;
	private Label lblTitle, lblWelcome, lblFilter, lblCategory, lblShowing;
	private TextField tfSearch;
	private Button btnSearch, btnLogout, btnApply, btnCart;
	private ComboBox<String> cbCategory;
	private ArrayList<String> categories;
	private ListView<HBox> lvItems;
	private ArrayList<Item> items;
	private final UserSession session = UserSession.getInstance();
	private final HomeController hc = new HomeController();
	private String keyword = "";
	
	public ShopperHomeView(ArrayList<String> categories, ArrayList<Item> items) {
		this.categories = categories;
		String keyword = (String) ParameterManager.get("keyword");
		
		if (keyword != null) {
			this.keyword = keyword;
			this.items = hc.searchItemsByKeyword(keyword);
		} else {
			this.items = items;
		}
		
		initialize();
		addComponent();
		arrangeComponent();
		
		setKeyEvent();
		setMouseEvent();
		
		loadListView();
	}

	private void setKeyEvent() {
		tfSearch.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				cbCategory.setValue(null);
				items = hc.searchItemsByKeyword(tfSearch.getText());
				loadListView();
			}
		});
	}

	private void setMouseEvent() {
		btnApply.setOnMouseClicked(event -> {
			tfSearch.clear();
			keyword = cbCategory.getValue();
			items = hc.searchItemsByCategory(keyword);
			loadListView();
		});
		
		btnSearch.setOnMouseClicked(event -> {
			cbCategory.setValue(null);
			keyword = tfSearch.getText();
			items = hc.searchItemsByKeyword(keyword);
			loadListView();
		});
		
		btnLogout.setOnMouseClicked(event -> {
			session.cleanUserSession();
			
			LoginController lc = new LoginController();
			lc.index();
		});
		
		btnCart.setOnMouseClicked(event -> {
			hc.cartIndex();
		});
	}

	private void loadListView() {
		lvItems.getItems().clear();
		
		for (Item item : items) {
			HBox hbItem = new HBox();
			Rectangle recItem = new Rectangle(40, 40, Color.GRAY);
			
			HBox hbItemDiv = new HBox();
			VBox vbItem = new VBox();
			Label lblItemName = new Label(item.getName());
			lblItemName.getStyleClass().addAll("text-2xl", "text-white");
			
			FlowPane fpItem = new FlowPane();
			Label lblItemPrice = new Label("$" + String.valueOf(item.getPrice()));
			lblItemPrice.getStyleClass().addAll("text-orange");
			Label lblItemStock = new Label(String.valueOf(item.getStock() + " Left"));
			lblItemStock.getStyleClass().addAll("text-white", "bg-red");
			fpItem.getChildren().addAll(lblItemPrice, lblItemStock);
			vbItem.getChildren().addAll(lblItemName, fpItem);
			fpItem.setHgap(16);
			
			hbItem.getChildren().addAll(recItem, vbItem);
			hbItem.setPadding(new Insets(16));
			hbItem.setAlignment(Pos.CENTER_LEFT);
			hbItem.getStyleClass().add("bg-gray");
			hbItemDiv.getChildren().add(hbItem);
			hbItemDiv.setPadding(new Insets(0, 0, 16, 0));
			
			hbItem.setOnMouseClicked(event -> {
				ProductController pc = new ProductController();
				pc.show(item);
			});
			
			lvItems.getItems().add(hbItemDiv);
		}
		
		if (!tfSearch.getText().isEmpty()) {
			lblShowing.setText("Showing " + items.size() + " items for '" + tfSearch.getText() + "'");
		} else if (cbCategory.getValue() != null) {
			lblShowing.setText("Showing + " + items.size() + " items in '" + cbCategory.getValue() + "' category");
		} else {
			lblShowing.setText("Showing " + items.size() + " items");
		}
	}

	private void arrangeComponent() {
		bp.setTop(vb);
		bp.setPadding(new Insets(16));
		bp.getStyleClass().add("bg-gray");
		fpSearch.setAlignment(Pos.CENTER);
		hbNavbar.setSpacing(8);
		
		lblTitle.getStyleClass().addAll("text-3xl", "font-bold");
		hbNavbar.getStyleClass().add("bg-white");
		hbNavbar.setStyle("-fx-border-radius: 10px;");
		
		lblWelcome.getStyleClass().addAll("text-6xl", "font-bold", "text-white");
		lblFilter.getStyleClass().add("text-white");
		btnCart.getStyleClass().add("cursor-pointer");
		
		vbCategory.getStyleClass().addAll("bg-white");
		vbShowing.getStyleClass().addAll("bg-white");
		hbItems.setSpacing(32);
		
		for (String category : categories) {
			cbCategory.getItems().add(category);
		}
		lvItems.setPrefWidth(600);
	}

	private void addComponent() {
		fpSearch.getChildren().addAll(tfSearch, btnSearch);
		
		hbNavbar.getChildren().addAll(lblTitle, fpSearch, btnCart, btnLogout);
		hbNavbar.setAlignment(Pos.CENTER);
		
		fpCategory.getChildren().addAll(cbCategory, btnApply);
		vbCategory.getChildren().addAll(lblCategory, fpCategory);
		
		vbFilter.getChildren().addAll(lblFilter, vbCategory);
		
		vbShowing.getChildren().addAll(lblShowing, lvItems);
		
		hbItems.getChildren().addAll(vbFilter, vbShowing);
		
		vb.getChildren().addAll(hbNavbar, lblWelcome, hbItems);
	}

	private void initialize() {
		sm.getStage().setTitle("GoGoQuery - Home");
		
		bp = new BorderPane();
		vb = new VBox();
		vbFilter = new VBox();
		vbCategory = new VBox();
		vbShowing = new VBox();
		hbNavbar = new HBox();
		hbItems = new HBox();
		fpSearch = new FlowPane();
		fpCategory = new FlowPane();
		lblTitle = new Label("GoGoQuery");
		lblShowing = new Label("Showing " + items.size() + " items");
		tfSearch = new TextField(keyword);
		btnSearch = new Button("Search");
		btnLogout = new Button("Logout");
		btnApply = new Button("Apply");
		btnCart = new Button("My Cart");
		lblFilter = new Label("Filter");
		lblCategory = new Label("Category");
		lblWelcome = new Label("Welcome, " + session.getUsername());
		cbCategory = new ComboBox<String>();
		lvItems = new ListView<HBox>();
	}
	
	public Pane getPane() {
		return bp;
	}
}
