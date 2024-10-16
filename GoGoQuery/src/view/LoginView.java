package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LoginView {
	private BorderPane bp;
	private Scene scene;
	private VBox vb, vbEmail, vbPassword, vbRegister;
	private Label lblTitle, lblLogin, lblEmail, lblPassword, lblRegister;
	private TextField tfEmail;
	private PasswordField pfPassword;
	private Button btnLogin;

	public LoginView() {
		initialize();
		addComponent();
		arrangeComponent();
	}

	private void arrangeComponent() {
		lblTitle.getStyleClass().add("text-white");
		BorderPane.setAlignment(lblTitle, Pos.BOTTOM_CENTER);
		lblTitle.getStyleClass().addAll("text-5xl", "font-bold");
		lblEmail.getStyleClass().addAll("font-bold", "text-gray");
		lblPassword.getStyleClass().addAll("font-bold", "text-gray");
		lblLogin.getStyleClass().addAll("text-5xl", "font-bold");
		btnLogin.setPrefWidth(Double.MAX_VALUE);
		btnLogin.getStyleClass().addAll("bg-orange", "text-white", "font-bold", "form-max-width", "cursor-pointer");
		
		vb.setPadding(new Insets(20));
		vb.getStyleClass().add("bg-white");
		vb.setSpacing(16);
		vb.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		vb.setAlignment(Pos.CENTER);
		
		vbEmail.getStyleClass().add("form-max-width");
		vbPassword.getStyleClass().add("form-max-width");
		vbRegister.setAlignment(Pos.CENTER);
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
		bp = new BorderPane();
		scene = new Scene(bp);
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

	public Scene getScene() {
		return scene;
	}
}
