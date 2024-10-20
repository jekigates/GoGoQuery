package view;

import controller.LoginController;
import controller.RoleController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.StageManager;

public class RoleView {
	private final StageManager sm = StageManager.getInstance();
	private BorderPane bp;
	private HBox hb;
	private VBox vbManager, vbShopper;
	private Label lblTitle, lblManagerTitle, lblManagerDescription, lblShopperTitle, lblShopperDescription, lblManagerLetter, lblShopperLetter;
	private Button btnManager, btnShopper;
	private Circle cirManagerBig, cirShopperBig;
	private StackPane spManager, spShopper;
	private String email, password, gender, role, dob;
	
	public RoleView(String email, String password, String gender, String dob) {
		initialize();
		addComponent();
		arrangeComponent();
		setMouseEvent();
		
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.dob = dob;
	}

	private void setMouseEvent() {
		btnManager.setOnMouseClicked(event -> {
			role = "Manager";
			handleSubmit();
		});
		
		btnShopper.setOnMouseClicked(event -> {
			role = "Shopper";
			handleSubmit();
		});
	}

	private void handleSubmit() {
		RoleController rc = new RoleController();
		rc.register(email, password, gender, role, dob);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Register information");
		alert.setHeaderText("Register success!");
		alert.setContentText("Please log in with your newly created account.");
		alert.showAndWait();
		
		LoginController lc = new LoginController();
		lc.index();
	}

	private void arrangeComponent() {
		lblTitle.getStyleClass().add("text-white");
		BorderPane.setAlignment(lblTitle, Pos.BOTTOM_CENTER);
		lblTitle.getStyleClass().addAll("text-5xl", "font-bold");
		
		vbManager.setPadding(new Insets(40));
		vbManager.getStyleClass().add("bg-white");
		vbManager.setSpacing(16);
		vbManager.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		
		vbShopper.setPadding(new Insets(40));
		vbShopper.getStyleClass().add("bg-white");
		vbShopper.setSpacing(16);
		vbShopper.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		
		lblManagerTitle.getStyleClass().addAll("text-3xl", "font-bold");
		lblShopperTitle.getStyleClass().addAll("text-3xl", "font-bold");
		lblManagerLetter.getStyleClass().add("text-3xl");
		lblShopperLetter.getStyleClass().add("text-3xl");
		
		btnManager.setPrefWidth(Double.MAX_VALUE);
		btnManager.getStyleClass().addAll("bg-orange", "text-white", "font-bold", "form-max-width", "cursor-pointer");
		btnManager.setPadding(new Insets(8));

		btnShopper.setPrefWidth(Double.MAX_VALUE);
		btnShopper.getStyleClass().addAll("bg-orange", "text-white", "font-bold", "form-max-width", "cursor-pointer");
		btnShopper.setPadding(new Insets(8));
		
		bp.setTop(lblTitle);
		bp.setPadding(new Insets(16));
		bp.getStyleClass().add("bg-gray");
		bp.setCenter(hb);
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(32);
		vbManager.setAlignment(Pos.CENTER);
		vbShopper.setAlignment(Pos.CENTER);
		
		cirShopperBig.setFill(Color.LIGHTGREEN);
		cirManagerBig.setFill(Color.LIGHTBLUE);
	}

	private void addComponent() {
		spManager.getChildren().addAll(cirManagerBig, lblManagerLetter);
		spShopper.getChildren().addAll(cirShopperBig, lblShopperLetter);
		
		vbManager.getChildren().addAll(spManager, lblManagerTitle, lblManagerDescription, btnManager);
		vbShopper.getChildren().addAll(spShopper, lblShopperTitle, lblShopperDescription, btnShopper);
		
		hb.getChildren().addAll(vbManager, vbShopper);
	}

	private void initialize() {
		sm.getStage().setTitle("GoGoQuery - Select Role");
		
		bp = new BorderPane();
		hb = new HBox();
		vbManager = new VBox();
		vbShopper = new VBox();
		lblTitle = new Label("GoGoQuery");
		lblManagerTitle = new Label("Manager");
		lblManagerDescription = new Label("Manage products and deliveries, be the ruler!");
		lblShopperTitle = new Label("Shopper");
		lblShopperDescription = new Label("Search products, manage your cart, go shopping!");
		lblManagerLetter = new Label("M");
		lblShopperLetter = new Label("S");
		btnManager = new Button("Register as Manager");
		btnShopper = new Button("Register as Shopper");
		
		cirManagerBig = new Circle(40.0f, 40.0f, 40.f);
		cirShopperBig = new Circle(40.0f, 40.0f, 40.f);
		
		spManager = new StackPane();
		spShopper = new StackPane();
	}
	
	public Pane getPane() {
		return bp;
	}
}
