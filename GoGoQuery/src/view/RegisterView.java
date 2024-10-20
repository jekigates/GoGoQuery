package view;

import java.time.LocalDate;
import java.time.Period;

import controller.LoginController;
import controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import util.StageManager;

public class RegisterView {
	private final StageManager sm = StageManager.getInstance();
	private BorderPane bp;
	private VBox vb, vbEmail, vbPassword, vbConfirmPassword, vbDOB, vbGender, vbLogin;
	private Label lblTitle, lblRegister, lblEmail, lblPassword, lblConfirmPassword, lblDOB, lblGender, lblLogin;
	private TextField tfEmail;
	private PasswordField pfPassword, pfConfirmPassword;
	private DatePicker dpDOB;
	private FlowPane fpGender;
	private RadioButton rbMale, rbFemale;
	private ToggleGroup tgGender;
	private CheckBox cbAccept;
	private Button btnRegister;

	public RegisterView() {
		initialize();
		addComponent();
		arrangeComponent();
		setMouseEvent();
		setKeyEvent();
	}

	private void handleSubmit() {
		String email = tfEmail.getText();
		String password = pfPassword.getText();
		String confirmPassword = pfConfirmPassword.getText();
		LocalDate dob = dpDOB.getValue();
		
		String gender = "";
		if (rbMale.isSelected()) {
			gender = "Male";
		} else if (rbFemale.isSelected()) {
			gender = "Female";
		}
		
		boolean isAccepted = cbAccept.isSelected();

		Alert alert = new Alert(AlertType.ERROR);
		String errorMsg = "";
		alert.setTitle("Register Failed");
		alert.setHeaderText("Register Error");

		if (email.isEmpty()) {
			errorMsg = "Email must be filled";
		} else if (!email.endsWith("@gomail.com")) {
			errorMsg = "Email must ends with '@gomail.com'";
		} else if (hasInvalidChars(email)) {
			errorMsg = "no special characters is allowed other than '@', '_', or '-'";
		} else if (hasEmailTaken(email)) {
			errorMsg = "Email is already taken. Please use other address.";
		} else if (password.isEmpty()) {
			errorMsg = "Password must be filled";
		} else if (!isAlphanumeric(password)) {
			errorMsg = "Password must be alphanumeric";
		} else if (!password.equals(confirmPassword)) {
			errorMsg = "Password don't match";
		} else if (dob == null) {
			errorMsg = "Please select date of birth";
		} else if (!isIllegalAge(dpDOB.getValue())) {
			errorMsg = "User must be at least 17 years old";
		} else if (gender.isEmpty()) {
			errorMsg = "Select your gender";
		} else if (!isAccepted) {
			errorMsg = "You must agree to the terms and conditions";
		}

		if (!errorMsg.isEmpty()) {
			alert.setContentText(errorMsg);
			alert.showAndWait();
			return;
		}
		
		RoleView view = new RoleView(email, confirmPassword, gender, String.valueOf(dob));
		sm.getStage().getScene().setRoot(view.getPane());
	}

	private boolean isIllegalAge(LocalDate birthDate) {
		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Calculate the period between the birth date and current date
		Period age = Period.between(birthDate, currentDate);

		// Check if the person is older than 17 years
		return age.getYears() > 17;
	}

	private boolean isAlphanumeric(String password) {
		boolean hasLetter = false;
		boolean hasDigit = false;

		for (char c : password.toCharArray()) {
			if (Character.isLetter(c)) {
				hasLetter = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			}
			// If both conditions are met, return true early
			if (hasLetter && hasDigit) {
				return true;
			}
		}

		return false; // If either condition is not met, return false
	}

	private boolean hasEmailTaken(String email) {
		RegisterController rc = new RegisterController();
		
		return rc.isEmailTaken(email);
	}

	private boolean hasInvalidChars(String email) {
		String specials = "@_-.";

		for (char c : email.toCharArray()) {
			if (!Character.isLetterOrDigit(c)) {
				if (!specials.contains(String.valueOf(c))) {
					System.out.println(c);
					return true; // Invalid character found
				}
			}
		}

		return false; // No invalid characters found
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
		});

		pfConfirmPassword.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handleSubmit();
			}
		});

		dpDOB.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handleSubmit();
			}
		});
	}

	private void setMouseEvent() {
		btnRegister.setOnMouseClicked(event -> {
			handleSubmit();
		});

		lblLogin.setOnMouseClicked(event -> {
			LoginController lc = new LoginController();
			lc.index();
		});

	}

	private void arrangeComponent() {
		lblTitle.getStyleClass().add("text-white");
		BorderPane.setAlignment(lblTitle, Pos.BOTTOM_CENTER);
		lblTitle.getStyleClass().addAll("text-5xl", "font-bold");
		lblEmail.getStyleClass().addAll("font-bold", "text-gray");
		lblPassword.getStyleClass().addAll("font-bold", "text-gray");
		lblConfirmPassword.getStyleClass().addAll("font-bold", "text-gray");
		lblDOB.getStyleClass().addAll("font-bold", "text-gray");
		lblGender.getStyleClass().addAll("font-bold", "text-gray");
		lblLogin.getStyleClass().add("cursor-pointer");
		lblRegister.getStyleClass().addAll("text-5xl", "font-bold");
		btnRegister.setPrefWidth(Double.MAX_VALUE);
		btnRegister.getStyleClass().addAll("bg-orange", "text-white", "font-bold", "form-max-width", "cursor-pointer");
		tfEmail.setPadding(new Insets(8));
		pfPassword.setPadding(new Insets(8));
		pfConfirmPassword.setPadding(new Insets(8));
		btnRegister.setPadding(new Insets(8));

		vb.setPadding(new Insets(16));
		vb.getStyleClass().add("bg-white");
		vb.setSpacing(16);
		vb.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		vbEmail.getStyleClass().add("form-max-width");
		vbPassword.getStyleClass().add("form-max-width");
		vbLogin.setAlignment(Pos.CENTER);
		bp.setPadding(new Insets(16));
		bp.getStyleClass().add("bg-gray");
		bp.setTop(lblTitle);
		bp.setCenter(vb);

		fpGender.setHgap(8);
		rbMale.setToggleGroup(tgGender);
		rbFemale.setToggleGroup(tgGender);
	}

	private void addComponent() {
		vbEmail.getChildren().addAll(lblEmail, tfEmail);
		vbPassword.getChildren().addAll(lblPassword, pfPassword);
		vbConfirmPassword.getChildren().addAll(lblConfirmPassword, pfConfirmPassword);
		vbDOB.getChildren().addAll(lblDOB, dpDOB);
		fpGender.getChildren().addAll(rbMale, rbFemale);
		vbGender.getChildren().addAll(lblGender, fpGender);
		vbLogin.getChildren().add(lblLogin);
		vb.getChildren().addAll(lblRegister, vbEmail, vbPassword, vbConfirmPassword, vbDOB, vbGender, cbAccept,
				btnRegister, vbLogin);
	}

	private void initialize() {
		sm.getStage().setTitle("GoGoQuery - Register");

		bp = new BorderPane();
		vb = new VBox();
		vbEmail = new VBox();
		vbPassword = new VBox();
		vbConfirmPassword = new VBox();
		vbDOB = new VBox();
		vbGender = new VBox();
		vbLogin = new VBox();
		lblTitle = new Label("GoGoQuery");
		lblRegister = new Label("Register");
		lblEmail = new Label("Email");
		lblPassword = new Label("Password");
		lblConfirmPassword = new Label("Confirm Password");
		lblDOB = new Label("Date of Birth");
		lblGender = new Label("Gender");
		lblLogin = new Label("Already have an account? Sign in Here!");
		tfEmail = new TextField();
		pfPassword = new PasswordField();
		pfConfirmPassword = new PasswordField();
		pfPassword = new PasswordField();
		dpDOB = new DatePicker();
		tgGender = new ToggleGroup();
		fpGender = new FlowPane();
		rbMale = new RadioButton("Male");
		rbFemale = new RadioButton("Female");
		cbAccept = new CheckBox("I accept the Terms and Conditions.");
		btnRegister = new Button("Register");
	}

	public Pane getPane() {
		return bp;
	}
}
