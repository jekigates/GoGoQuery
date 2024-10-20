package main;

import controller.HomeController;
import controller.LoginController;
import controller.ProductController;
import entity.Item;
import entity.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.StageManager;
import util.UserSession;
import view.CartView;

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
		
		User user = new User(34, "2004-07-17", "jekigates@gmail.com", "jekigates123", "Male", "Shopper");
		UserSession.getInstance(user);
		
//		LoginController lc = new LoginController();
//		lc.index();
		HomeController hc = new HomeController();
//		hc.index();
		hc.cartIndex();
//		ProductController pc = new ProductController();
//		Item item = new Item(1, "Tokai AST-52SH VWH/CJ Goldstar Sound in Vintage White", 496.69, "Test", 8, "Guitar");
//		pc.show(item);
		
        // Load the CSS stylesheet
        String css = this.getClass().getResource("/css/styles.css").toExternalForm();
        sm.getStage().getScene().getStylesheets().add(css);
		sm.getStage().show();
	}

}
