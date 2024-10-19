package main;

import controller.HomeController;
import entity.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.StageManager;
import util.UserSession;

public class Main extends Application {
	private final StageManager sm = StageManager.getInstance();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		sm.setStage(primaryStage);
//		Set scene dengan border pane polos sebagai root biar nanti bisa swap scenes
		sm.getStage().setScene(new Scene(new BorderPane()));
		
		User user = new User(1, "2004-07-17", "jekigates@gmail.com", "jekigates123", "Male", "Shopper");
		UserSession session = UserSession.getInstance(user);
		
//		LoginController lc = new LoginController();
//		lc.index();
		HomeController hc = new HomeController();
		hc.index();
		
        // Load the CSS stylesheet
        String css = this.getClass().getResource("/css/styles.css").toExternalForm();
        sm.getStage().getScene().getStylesheets().add(css);
		sm.getStage().show();
	}

}
