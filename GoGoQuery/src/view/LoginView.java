package view;

import controller.LoginController;
import controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import util.StageManager;

public class LoginView {
	private final StageManager sm = StageManager.getInstance();
	private BorderPane bp;
	private VBox vb, vbEmail, vbPassword, vbRegister;
	private Label lblTitle, lblLogin, lblEmail, lblPassword, lblRegister;
	private TextField tfEmail;
	private PasswordField pfPassword;
	private Button btnLogin;

	public LoginView() {		
		initialize();
		addComponent();
		arrangeComponent();
		setMouseEvent();
		setKeyEvent();
	}
	
	private void handleSubmit() {
		String email = tfEmail.getText();
		String password = pfPassword.getText();
		
		Alert alert = new Alert(AlertType.ERROR);
		String errorMsg = "";
		alert.setTitle("Invalid Login");
		alert.setHeaderText("Log in failed");
		
		if (email.isEmpty() || password.isEmpty()) {
			errorMsg = "Please fill out all fields.";
		} else {
			LoginController lc = new LoginController();
			
			if (lc.authenticate(email, password)) {
				System.out.println("berhasil");
				return;
			} else {
				errorMsg = "You entered a wrong email or password.";
			}
		}
		
		alert.setContentText(errorMsg);
		alert.showAndWait();
	}
	
	private void setKeyEvent() {
		tfEmail.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handleSubmit();
			}
		});
		
		pfPassword.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
					handleSubmit();
				}
			}
		);
	}

	private void setMouseEvent() {
		btnLogin.setOnMouseClicked(event -> {
			handleSubmit();
		});
		
		lblRegister.setOnMouseClicked(event -> {
			RegisterController rc = new RegisterController();
			rc.index();
		});
	}

	private void arrangeComponent() {
		BorderPane.setAlignment(lblTitle, Pos.BOTTOM_CENTER);
		lblTitle.getStyleClass().add("text-white");
		lblTitle.getStyleClass().addAll("text-5xl", "font-bold");
		lblEmail.getStyleClass().addAll("font-bold", "text-gray");
		lblPassword.getStyleClass().addAll("font-bold", "text-gray");
		lblRegister.getStyleClass().add("cursor-pointer");
		lblLogin.getStyleClass().addAll("text-5xl", "font-bold");
		btnLogin.setPrefWidth(Double.MAX_VALUE);
		btnLogin.getStyleClass().addAll("bg-orange", "text-white", "font-bold", "form-max-width", "cursor-pointer");
		btnLogin.setPadding(new Insets(8));
		tfEmail.setPadding(new Insets(8));
		pfPassword.setPadding(new Insets(8));
		
		vb.setPadding(new Insets(16));
		vb.getStyleClass().add("bg-white");
		vb.setSpacing(16);
		vb.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		
		vbEmail.getStyleClass().add("form-max-width");
		vbPassword.getStyleClass().add("form-max-width");
		vbRegister.setAlignment(Pos.CENTER);
		bp.setPadding(new Insets(16));
		bp.getStyleClass().add("bg-gray");
		bp.setTop(lblTitle);
		bp.setCenter(vb);
	}

	private void addComponent() {
		vbEmail.getChildren().addAll(lblEmail, tfEmail);
		vbPassword.getChildren().addAll(lblPassword, pfPassword);
		vbRegister.getChildren().add(lblRegister);
		vb.getChildren().addAll(lblLogin, vbEmail, vbPassword, btnLogin, vbRegister);
	}

	private void initialize() {
		sm.getStage().setTitle("GoGoQuery - Login");

		bp = new BorderPane();
		vb = new VBox();
		vbEmail = new VBox();
		vbPassword = new VBox();
		vbRegister = new VBox();
		lblTitle = new Label("GoGoQuery");
		lblLogin = new Label("Login");
		lblEmail = new Label("Email");
		lblPassword = new Label("Password");
		lblRegister = new Label("Are you new? Register here!");
		tfEmail = new TextField();
		pfPassword = new PasswordField();
		btnLogin = new Button("Login");
	}

	public Pane getPane() {
		return bp;
	}
}
